package com.atos.mapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import oracle.jdbc.OracleConnection;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;

public class BigDecimalListTypeHandler extends BaseTypeHandler<List<BigDecimal>> 
										implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5734083949071669757L;

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<BigDecimal> parameter, JdbcType jdbcType)
	        throws SQLException {

			java.sql.Array sqlArray = null;
        	Object[] tmpArray = new Object[parameter.size()];
    	    for (int j = 0; j < parameter.size(); j++)
    	    	tmpArray[j] = (Object) parameter.get(j);
    	    
    	    Connection conn = ps.getConnection();
    	    if(conn instanceof OracleConnection){
    	    	OracleConnection oraConn = (OracleConnection)conn;
    	    	sqlArray = oraConn.createARRAY("US_TPA.T_ARR_IDN", tmpArray);
    	    } else {
    	    	// Este metodo siempre devuelve una excepcion. Pero no entrara por aqui si siempre usamos el driver de Oracle.
    	    	sqlArray = conn.createArrayOf("US_TPA.T_ARR_IDN", tmpArray);
    	    }
    	    ps.setArray(i, sqlArray);
	}
	
	// Los metodos de getNullableResult no se han probado, porque no se han usado hasta ahora.
	@Override
	public List<BigDecimal> getNullableResult(ResultSet rs, String columnName) throws SQLException {

		List<BigDecimal> alRes = new ArrayList<BigDecimal>();

		Array sqlArray = rs.getArray(columnName);
		BigDecimal[] tmpArray = (BigDecimal[])sqlArray.getArray();
	    for (int i = 0; i < tmpArray.length; i++)
	    	alRes.add(tmpArray[i]);
	    	
	    return alRes;
	}

	@Override
	public List<BigDecimal> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		List<BigDecimal> alRes = new ArrayList<BigDecimal>();

		Array sqlArray = rs.getArray(columnIndex);
		BigDecimal[] tmpArray = (BigDecimal[])sqlArray.getArray();
	    for (int i = 0; i < tmpArray.length; i++)
	    	alRes.add(tmpArray[i]);
	    	
	    return alRes;
	}

	@Override
	public List<BigDecimal> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
	
		List<BigDecimal> alRes = new ArrayList<BigDecimal>();
	
		Array sqlArray = cs.getArray(columnIndex);
		BigDecimal[] tmpArray = (BigDecimal[])sqlArray.getArray();
	    for (int i = 0; i < tmpArray.length; i++)
	    	alRes.add(tmpArray[i]);
	    	
	    return alRes;
	}

}
