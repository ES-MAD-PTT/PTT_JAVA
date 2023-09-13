/**
 * Very simple bean that authenticates the user via Apache Shiro, using JSF
 * @author Daniel Mascarenhas
 */
package com.atos.beans.security;

import org.apache.logging.log4j.LogManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.logging.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;


@ManagedBean(name = "shiroLoginBean")
@ViewScoped
public class ShiroLoginBean implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3402786826278723944L;

	private static final Logger log = LogManager.getLogger(ShiroLoginBean.class);

    private String username;
    private String password;

    @ManagedProperty("#{servletContext}")
    private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
    
    public ShiroLoginBean() {
        
    }
    
    /**
     * Try and authenticate the user
     */
    public void doLogin() {
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(getUsername(), getPassword());

        try {
            subject.login(token);

            this.username = (String) subject.getPrincipal();
            FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
            log.info("User " + this.username + " logged in");
        }
        catch (UnknownAccountException ex) {
            facesError(msgs.getString("login_unknown_account"));
            log.error(ex.getMessage(), ex);
        }
        catch (IncorrectCredentialsException ex) {
            //facesError("Wrong password");
        	facesError(ex.getMessage());
            log.error(ex.getMessage(), ex);
        }
        catch (LockedAccountException ex) {
            //facesError("Locked account");
        	facesError(ex.getMessage());
            log.error(ex.getMessage(), ex);
        }
        catch (ExcessiveAttemptsException ex) {
            facesError(ex.getMessage());
            log.error(ex.getMessage(), ex);
        }
        catch (AuthenticationException ex) {
            facesError(msgs.getString("login_unknown_error") + " " + ex.getMessage());
            log.error(ex.getMessage(), ex);
        }
        catch (IOException ex){
            facesError(msgs.getString("login_unknown_error") + " " + ex.getMessage());
            log.error(ex.getMessage(), ex);
        }
        finally {
            token.clear();
        }
    }
	
    public void doLogout() throws IOException {
    	log.info("User " + SecurityUtils.getSubject().getPrincipal() + " logged out");
    	SecurityUtils.getSubject().logout();

    	String contextPath = null;
		// Esta variable puede depender del contenedor de servlets. Si no se pudiera obtener, por defecto, tendra valore "/tpasystem".
		if(servletContext!=null) 
			contextPath = servletContext.getContextPath();
		
		if(contextPath == null)
			contextPath = "/tpasystem";
		
		FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/login.xhtml");

		// Se limpia la sesion, para que empiece sin datos de la sesion anterior.
		// En concreto se necesita que se cargue el ChangeSystemView conociendo el username para 
		// saber si tiene que cargarse el sistema onshore u offshore.
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
    /**
     * Adds a new SEVERITY_ERROR FacesMessage for the ui
     * @param message Error Message
     */
    private void facesError(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String senha) {
        this.password = senha;
    }

}
