package com.atos.views;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.shiro.SecurityUtils;

import com.atos.services.SystemService;
import com.atos.utils.Constants;

@ManagedBean(name = "changeSystemView")
@SessionScoped
public class ChangeSystemView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2039930540584900529L;
	static private String onshore = "onshore.css";
	static private String offshore = "offshore.css";
	
	private String system;
	private String system_css;

	private List<TreeMap<String,Object>> list;
	
	private BigDecimal idn_onshore;
	private BigDecimal idn_offshore;
	private BigDecimal idn_active;

	@ManagedProperty("#{systemService}")
    transient private SystemService service;
     
    public void setService(SystemService service) {
        this.service = service;
    }
	
    @ManagedProperty("#{servletContext}")
    private ServletContext servletContext;
        
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@PostConstruct
    public void init() {
		list = service.getSystems();
		this.setIdnSystems();
		
		// No se puede usar el "userBean", porque en caso de logout, al cargar la pagina de login
		// se carga este ChangeSystemBean sin que esté disponible el userBean.
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		
		// Cuando se pulsa el boton de Logout, se borra el usuario que ha accedido al sistema. 
		// Entonces, por defecto, se carga el sistema onshore.
		// Tras limpiar la sesion, se vuelve a cargar este ChangeSystemBean, ya con el usuario disponible.
		if(username==null || service.userHasOnshoreProfile(username)){
			this.system = Constants.ONSHORE;
			this.system_css = onshore;
			this.idn_active = idn_onshore;
		} else {
			this.system = Constants.OFFSHORE;
			this.system_css = offshore;
			this.idn_active = idn_offshore;
		}
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}
	
	public String getSystem_css() {
		return system_css;
	}

	public void setSystem_css(String system_css) {
		this.system_css = system_css;
	}

	public BigDecimal getIdn_onshore() {
		return idn_onshore;
	}

	public void setIdn_onshore(BigDecimal idn_onshore) {
		this.idn_onshore = idn_onshore;
	}

	public BigDecimal getIdn_offshore() {
		return idn_offshore;
	}

	public void setIdn_offshore(BigDecimal idn_offshore) {
		this.idn_offshore = idn_offshore;
	}

	public BigDecimal getIdn_active() {
		return idn_active;
	}

	public void setIdn_active(BigDecimal idn_active) {
		this.idn_active = idn_active;
	}

	// Methods
	public String changeSystem() throws IOException{

		String contextPath = null;
		// Esta variable puede depender del contenedor de servlets. Si no se pudiera obtener, por defecto, tendra valore "/tpasystem".
		if(servletContext!=null) 
			contextPath = servletContext.getContextPath();

		if(contextPath == null)
			contextPath = "/tpasystem";
		
		if(this.system.equals(Constants.ONSHORE)){
			this.system = Constants.OFFSHORE;
			this.system_css = offshore;
			this.idn_active = idn_offshore;
			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/home.xhtml");
			return this.system;
		} else {
			this.system = Constants.ONSHORE;
			this.system_css = onshore;
			this.idn_active = idn_onshore;
			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/home.xhtml");
			return this.system;
		}
	}
	public boolean isOnshore(){
		if(this.system.equals(Constants.ONSHORE)){
			return true;
		} else {
			return false;
		}
	}
	public boolean isOffshore(){
		if(this.system.equals(Constants.OFFSHORE)){
			return true;
		} else {
			return false;
		}
	}
	
	private void setIdnSystems(){
		for(int i=0;i<list.size();i++){
			TreeMap<String,Object> map = list.get(i);
			if(map.get("CODE").equals(Constants.ONSHORE)){
				this.setIdn_onshore((BigDecimal)map.get("IDN"));
			}
			if(map.get("CODE").equals(Constants.OFFSHORE)){
				this.setIdn_offshore((BigDecimal)map.get("IDN"));
			}
		}
	}
	
}