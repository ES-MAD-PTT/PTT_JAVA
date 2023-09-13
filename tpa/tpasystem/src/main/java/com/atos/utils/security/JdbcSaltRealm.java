package com.atos.utils.security;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.util.JdbcUtils;
import org.apache.shiro.authc.LockedAccountException;

import com.atos.beans.security.AttemptControlBean;

public class JdbcSaltRealm extends JdbcRealm {

	private int maxLoginAttempts = 5;
	private int delayHours = 1;
	private static final String delayHoursParamName = "LOGIN.DELAY.HOURS";
	private static final String maxLoginAttemptsParamName = "LOGIN.MAX.ATTEMPTS";
	private static final String processUsername = "loginProcess";		// Para hacer llamar a funciones de BD.
	private static final String processLanguage = "en";
	private static final String systemParameterFunctionCall = "{?= call pck_parameter.get_integer_value(?,?,?,?,?,?)}";
	
	private static final String attemptsControlQuery = "select tlog.idn_user, tlog.login_period_start, tlog.last_login_attempt, tlog.login_attempt_count "
														+ "from tpa_tuser_login tlog, tpa_tuser tus where tlog.idn_user = tus.idn_user and user_id = ?";

	private static final String userInfoQuery = "select idn_user from tpa_tuser where user_id=?";
	
	private static final String insertAttemptsControl = "INSERT INTO tpa_tuser_login "
													+ "(idn_user, login_period_start, last_login_attempt, login_attempt_count, AUD_INS_USER, AUD_LAST_USER) "
													+ "VALUES (?,?,?,?,'loginProcess','loginProcess')"; 
	
	private static final String updateAttemptsControl = "UPDATE tpa_tuser_login "
														+ "SET login_period_start = ?, last_login_attempt = ?, login_attempt_count = ?, "
														+ "AUD_LAST_USER='loginProcess' "
														+ "WHERE idn_user = ?"; 
	
	private static final String errMsgLabel = "AAA";
	private static final SimpleDateFormat sdfMessages = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	private static final Logger log = LogManager.getLogger("com.atos.utils.security.JdbcSaltRealm");

	public JdbcSaltRealm() {
		setSaltStyle(SaltStyle.COLUMN);
	}
	
	// Llamada a este procedimiento almacenado en BD:
	//   FUNCTION get_integer_value(p_parameter_code IN tpa_tparameter.parameter_code%TYPE,
	//           p_date           IN DATE,
	//           p_user           IN VARCHAR2,
	//           p_language       IN VARCHAR2,
	//           p_error_code     OUT INTEGER,
	//           p_error_desc     OUT VARCHAR2) RETURN INTEGER IS
	private int getSystemParameter(String param) throws SQLException, AuthenticationException{
		
		int iValue=-1;
		int errorCode=0;
		String errorDesc=null;
		Connection conn = null;
		CallableStatement cs = null;
		try {
			conn = dataSource.getConnection();
			cs = conn.prepareCall(systemParameterFunctionCall);
			
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2,param);
			cs.setDate(3, new java.sql.Date((new Date()).getTime()));
			cs.setString(4, processUsername);
			cs.setString(5, processLanguage);
			cs.registerOutParameter(6, Types.INTEGER);
			cs.registerOutParameter(7, Types.VARCHAR);
			cs.execute();
			
			iValue = cs.getInt(1);
			errorCode = cs.getInt(6);
			errorDesc = cs.getString(7);
			
			if( errorCode != 0)
				throw new AuthenticationException(errorDesc);

		} finally {
			JdbcUtils.closeStatement(cs);
			JdbcUtils.closeConnection(conn);
		}

		return iValue;    	
    }
	
	private AttemptControlBean getAttempsControlInfo(String username) throws SQLException{
		
		AttemptControlBean acbInfo = new AttemptControlBean();
		acbInfo.setUsername(username);
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(attemptsControlQuery);
			ps.setString(1, username);
			rs = ps.executeQuery();

			boolean foundResult = false;
			while (rs.next())
			{
				if (foundResult) {
					throw new AuthenticationException("tpa_tlogin_attempts: More than one user row found for user [" + username + "]. Usernames must be unique.");
				}
				
				int userId = rs.getInt(1);
				acbInfo.setUserId(userId);
				// El campo loginPeriodStart podria ser nulo en la BD.
				Date loginPeriodStart = (rs.getTimestamp(2)==null)? null : new Date (rs.getTimestamp(2).getTime());
				acbInfo.setLoginPeriodStartTimestamp(loginPeriodStart);
				Date lastLoginAttempt = new Date (rs.getTimestamp(3).getTime());
				acbInfo.setLastLoginAttemptTimestamp(lastLoginAttempt);
				int loginAttemptCount = rs.getInt(4); 
				acbInfo.setLoginAttemptCount(loginAttemptCount);

				foundResult = true;
			}
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
		}

		return acbInfo;    	
    }


	private void resetAttemptInfo(AttemptControlBean attemptInfo) throws SQLException{

		attemptInfo.setLoginAttemptCount(0);
		attemptInfo.setLastLoginAttemptTimestamp(new Date());
		// La fecha de loginPeriodStart quedara con valor nulo.
		attemptInfo.setLoginPeriodStartTimestamp(null);

		saveAttemptInfo(attemptInfo);
    }

	private void saveNewLoginPeriodStart(AttemptControlBean attemptInfo) throws SQLException{

		Date now = new Date();
		// Se retrasa la hora en que se puede volver a conectar, en delayHours horas desde la actual. 
		Calendar calDelay = Calendar.getInstance();
		calDelay.setTime(now);
		calDelay.add(Calendar.HOUR, delayHours);
		attemptInfo.setLoginPeriodStartTimestamp(calDelay.getTime());
		attemptInfo.setLastLoginAttemptTimestamp(now);
		attemptInfo.setLoginAttemptCount(0);
		
		saveAttemptInfo(attemptInfo);
	}

	private void saveNewCount(AttemptControlBean attemptInfo) throws SQLException{

		// El contador se ha guardado antes en el AttemptControlBean.
		attemptInfo.setLastLoginAttemptTimestamp(new Date());
		// La fecha de loginPeriodStart quedara con valor nulo.
		attemptInfo.setLoginPeriodStartTimestamp(null);
		
		saveAttemptInfo(attemptInfo);
	}
	
	private void saveAttemptInfo(AttemptControlBean attemptInfo) throws SQLException{
		Integer userId = attemptInfo.getUserId();
		
		// Si no se conoce el userId es que no habia registro inicialmente y ha que hacer INSERT. En caso contrario, UPDATE.
		if(userId==null) {
			Integer iUserId = getUserId(attemptInfo.getUsername());
			attemptInfo.setUserId(iUserId);
			insertAttemptInfo(attemptInfo);
		}
		else{
			updateAttemptInfo(attemptInfo);
		}
    }
	
	private Integer getUserId(String username) throws SQLException{
		
		Integer resUserId = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(userInfoQuery);
			ps.setString(1, username);
			rs = ps.executeQuery();

			boolean foundResult = false;
			while (rs.next())
			{
				if (foundResult) {
					throw new AuthenticationException("tpa_tuser: More than one user row found for user [" + username + "]. Usernames must be unique.");
				}
				
				resUserId = rs.getInt(1);

				foundResult = true;
			}
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
		}

		return resUserId;    	
    }
	
	private void insertAttemptInfo(AttemptControlBean attemptInfo) throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(insertAttemptsControl);

			ps.setInt(1, attemptInfo.getUserId());
			// El campo loginPeriodStart no es obligatorio en la BD, asi que se puede querer insertar nulo.
			Date dLoginPeriodStart =attemptInfo.getLoginPeriodStartTimestamp();
			Timestamp tLoginPeriodStart = (dLoginPeriodStart==null)? null : new Timestamp(dLoginPeriodStart.getTime());
			ps.setTimestamp(2, tLoginPeriodStart);
			ps.setTimestamp(3, new Timestamp(attemptInfo.getLastLoginAttemptTimestamp().getTime()));
			ps.setInt(4, attemptInfo.getLoginAttemptCount());

			// execute insert SQL stetement
			ps.executeUpdate();

			//System.out.println("Record is inserted into DBUSER table!");
			
		} finally {

			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
			//if (preparedStatement != null) {
			//	preparedStatement.close();
			//}
		}

	}
	
	
	private void updateAttemptInfo(AttemptControlBean attemptInfo)  throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(updateAttemptsControl);

			// El campo loginPeriodStart no es obligatorio en la BD, asi que se puede querer insertar nulo.
			Date dLoginPeriodStart =attemptInfo.getLoginPeriodStartTimestamp();
			Timestamp tLoginPeriodStart = (dLoginPeriodStart==null)? null : new Timestamp(dLoginPeriodStart.getTime());
			ps.setTimestamp(1, tLoginPeriodStart);
			ps.setTimestamp(2, new Timestamp(attemptInfo.getLastLoginAttemptTimestamp().getTime()));
			ps.setInt(3, attemptInfo.getLoginAttemptCount());
			ps.setInt(4, attemptInfo.getUserId());

			// execute update SQL stetement
			ps.executeUpdate();

			//System.out.println("Record is updated to DBUSER table!");

		} finally {

			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
			//if (preparedStatement != null) {
			//	preparedStatement.close();
			//}
		}
	}
	
	// Antes de comprobar el usuario/contraseña se comprueba si el usuario puede intentarlo, o tiene el acceso bloqueado por un tiempo.
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		String errorMsg = null;
		AuthenticationInfo resAuthInfo = null;
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();

		if (username == null) {
			throw new AccountException("Null usernames are not allowed by this realm.");
		}

		// No use una conexion comun porque parecia que se cerraba a mitad del proceso.
		// En su lugar he abierto y cerrado una conexion nueva para cada select, insert o update con la BD.
		//Connection conn = null;
		try {
			// 0.- Se consultan de BD los parametros maxLoginAttempts y delayHours.
			maxLoginAttempts = getSystemParameter(maxLoginAttemptsParamName);
			delayHours = getSystemParameter(delayHoursParamName);
			
			// 1.- Consulta de tabla de informacion de reintentos.
			AttemptControlBean attemptInfo = getAttempsControlInfo(username);
			// Si no se hubiera obtenido registro de la BD, se obtendria un attemptInfo con campos nulos, incluido el userId.
			
			// 2.- ¿La fecha loginPeriodStart es no vacia y futura?
			// 2.1.- SI: Se devuelve error de bloqueo.
			Date now = new Date();
			Date loginPeriodStart = attemptInfo.getLoginPeriodStartTimestamp();
			if(loginPeriodStart!=null && loginPeriodStart.after(now)){
				errorMsg = msgs.getString("login_access_locked") + " " + sdfMessages.format(loginPeriodStart);
				throw new LockedAccountException(errorMsg);
			}
			
			// Despues de validar que el usuario no tiene el acceso bloqueado, se comprueba la contraseña (clase padre).
			// 2.2.- NO: ¿La contraseña es valida?
			// 2.2.1.- SI: Acceso correcto: dejar estado correcto en la tabla de intentos de login y dar acceso.
			//         ¿El num.reintentos leido es cero?			
			// 2.2.1.1.- NO: Hacer insert en tabla de info, con contador a cero, ultimo acceso...
			// 2.2.1.2.- En cualquier caso: DAR ACCESO y salir.
			super.saltIsBase64Encoded = false;
			resAuthInfo = super.doGetAuthenticationInfo(token);
			Integer iCount = attemptInfo.getLoginAttemptCount();
	        if (getCredentialsMatcher().doCredentialsMatch(upToken, resAuthInfo)) {
	        	
	        	// Si no hubiera registro inicial en la tabla de control, no hace falta resetearlo. No se crea el registro.
	        	if(iCount!=null && iCount.compareTo(0)!=0)
	        		resetAttemptInfo(attemptInfo);
	        		
	            return resAuthInfo; 
	        }
	        else {
				// 2.2.2.- NO: Mirar el numero de reintentos. Subir el numero. Si se ha llegado al maximo, establecer loginPeriodStart nuevo.
				//			Subir el contador en local. 			
				//			¿Se ha llegado al maximo?
	        	iCount = (iCount!=null)? iCount : 0;	// Por si no hubiera existido registro en BD.
	        	iCount = iCount + 1;
	        	attemptInfo.setLoginAttemptCount(iCount);
	        	if(iCount.compareTo(maxLoginAttempts)==0){
	    	        // 2.2.2.1.- SI: Hacer insert con loginPeriodStart, lastLoginAttemptTimestamp y contador a cero.
	    			//			Devolver error de ExcessiveAttemptsException, indicando la fecha en que podra volver a intentarlo.
	        		saveNewLoginPeriodStart(attemptInfo);
	        		errorMsg = msgs.getString("login_max_attempts_reached") + " " 
	        					+ sdfMessages.format(attemptInfo.getLoginPeriodStartTimestamp());
	    	        throw new ExcessiveAttemptsException(errorMsg);
	        	} 
	        	else {
	    			// 2.2.2.2.- NO: Hacer insert con lastLoginAttemptTimestamp y contador recien calculado.
	    			// 			Devolver error de IncorrectCredentialsException. Se puede indicar el numero de reintentos que quedan.
	        		saveNewCount(attemptInfo);
	        		int iRemainingAttempts = maxLoginAttempts - iCount;
	        		errorMsg = msgs.getString("login_wrong_pwd"); 
					errorMsg = errorMsg.replace(errMsgLabel, (new Integer(iRemainingAttempts)).toString());
	        		throw new IncorrectCredentialsException(errorMsg);
	        	}
	        }

		} catch (SQLException e) {
    		errorMsg = msgs.getString("login_sql_error"); 
			errorMsg = errorMsg.replace(errMsgLabel, username);
			if (log.isErrorEnabled()) {
				log.error(errorMsg, e);
			}

			throw new AuthenticationException(errorMsg, e);
		}
		
		// Este metodo de shiro, se supone que es para obtener las credenciales de usuario de la BD.
		// shiro comparara mas adelante las credenciales de la BD con las que ha insertado el usuario en la pantalla de login. 
		// Como aqui ya se hace una comparacion getCredentialsMatcher().doCredentialsMatch(...), al final se van a comparar 2 veces. 
		// Pero no pasa nada. Con esta implementacion, lo logica de reintentos se hace en un solo sitio.
	}
}