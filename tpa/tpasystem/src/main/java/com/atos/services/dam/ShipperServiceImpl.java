package com.atos.services.dam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilter;
import com.atos.beans.ComboFilterNS;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.ShipperBean;
import com.atos.beans.dam.ShipperPointBean;
import com.atos.filters.dam.ShipperFilter;
import com.atos.filters.dam.ShipperPointFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.ShipperMapper;

@Service("shipperService")
public class ShipperServiceImpl implements ShipperService {

	/**
	* 
	*/
	private static final long serialVersionUID = 5738619896981240370L;
	@Autowired
	private ShipperMapper shipperMapper;
	@Autowired
	private SystemParameterMapper systemParameterMapper;

	
	public List<ShipperBean> selectShippers(ShipperFilter filter, BigDecimal systemId) {
		List<ShipperBean> lShippers = shipperMapper.selectShippers(filter);

		ComboFilterNS cfnsPoint;
		List<ComboFilterNS> lAllContractPoints = null;
		List<ComboFilterNS> lSourceContractPoints = null;
		List<ComboFilterNS> lTargetContractPoints = null;
		List<ShipperPointBean> lCurrentShipperPoints = null;
		
		// Se obtienen los puntos de contratos correspondientes al sistema onshore/offshore.
		ShipperPointFilter spFilter = new ShipperPointFilter();
		spFilter.setSystemId(systemId);
		// Como este selectShippers se usa al cargar la pagina, se obtienen datos para todas
		// las areas y no es necesario especificar una lista de areas.
		lAllContractPoints = shipperMapper.selectContractPoints(spFilter);
		
		for (ShipperBean bShipper : lShippers) {
			
			// los target, los obtengo de la BD. Solamente los vigentes (enddate nula).
			spFilter = new ShipperPointFilter();
			spFilter.setUserGroupId(bShipper.getIdn_user_group());
			spFilter.setNullEndDateFlag(true);
			// No es necesario un filtro de areas puesto que al cargar la pagina, 
			// se muestran puntos de todas las areas.
			lCurrentShipperPoints = shipperMapper.selectShipperPoints(spFilter);

			lTargetContractPoints = new ArrayList<ComboFilterNS>();
			for (ShipperPointBean spBean: lCurrentShipperPoints) {
				cfnsPoint = new ComboFilterNS(spBean.getSysPointId(),spBean.getSysPointCode());
				lTargetContractPoints.add(cfnsPoint);
			}
					
			// Los source son la diferencia de todos los posibles menos los asociados ya al shipper.
			lSourceContractPoints = new ArrayList<ComboFilterNS>();
			for (ComboFilterNS cfnsPointAll : lAllContractPoints) {
				if(lTargetContractPoints.contains(cfnsPointAll))
					continue;

				cfnsPoint = new ComboFilterNS(cfnsPointAll.getKey(),cfnsPointAll.getValue());
				lSourceContractPoints.add(cfnsPoint);
			}
			
			bShipper.setdLContractPoints(new DualListModel<ComboFilterNS>(lSourceContractPoints, lTargetContractPoints));
		}
		
		return lShippers;
	}

	public Map<String, Object> selectShipperId() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<ComboFilter> list = shipperMapper.selectShipperId();
		for (ComboFilter combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;

	}

	public List<String> selectCompanyName(String query) {
		return shipperMapper.selectCompanyName(query);
	}

	public List<ComboFilterNS> selectAreas(BigDecimal systemId) {
		// Se obtienen las areas correspondientes al sistema onshore/offshore.
		return shipperMapper.selectAreas(systemId);
	}
	
	public List<ComboFilterNS> selectContractPoints(BigDecimal systemId) {
		// Se obtienen los puntos de contratos correspondientes al sistema onshore/offshore.
		ShipperPointFilter spFilter = new ShipperPointFilter();
		spFilter.setSystemId(systemId);
		// No se especifican areas, asi que se obtienen para todas.
		
		return shipperMapper.selectContractPoints(spFilter);
	}
	
	public void refreshDualList(ShipperBean bShipper, BigDecimal systemId, BigDecimal[] filterAreaIds) 
	{
		ComboFilterNS cfnsPoint;
		List<ComboFilterNS> lAllContractPoints = null;
		List<ComboFilterNS> lSourceContractPoints = null;
		List<ComboFilterNS> lTargetContractPoints = null;
		List<ShipperPointBean> lCurrentShipperPoints = null;
		
		ShipperPointFilter spFilter = new ShipperPointFilter();
		spFilter.setSystemId(systemId);
		spFilter.setAreaIds(filterAreaIds);
		lAllContractPoints = shipperMapper.selectContractPoints(spFilter);
					
		// Se mantienen los parametros del spFilter de antes: filterAreaIds 
		spFilter.setUserGroupId(bShipper.getIdn_user_group());
		spFilter.setNullEndDateFlag(true);
		lCurrentShipperPoints = shipperMapper.selectShipperPoints(spFilter);

		lTargetContractPoints = new ArrayList<ComboFilterNS>();
		for (ShipperPointBean spBean: lCurrentShipperPoints) {
			cfnsPoint = new ComboFilterNS(spBean.getSysPointId(),spBean.getSysPointCode());
			lTargetContractPoints.add(cfnsPoint);
		}
				
		// Los source son la diferencia de todos los posibles menos los asociados ya al shipper.
		lSourceContractPoints = new ArrayList<ComboFilterNS>();
		for (ComboFilterNS cfnsPointAll : lAllContractPoints) {
			if(lTargetContractPoints.contains(cfnsPointAll))
				continue;

			cfnsPoint = new ComboFilterNS(cfnsPointAll.getKey(),cfnsPointAll.getValue());
			lSourceContractPoints.add(cfnsPoint);
		}
		
		bShipper.setdLContractPoints(new DualListModel<ComboFilterNS>(lSourceContractPoints, lTargetContractPoints));
	}
	
	@Transactional(rollbackFor = { Throwable.class })
	public String insertShipper(ShipperBean shipper) throws Exception {
		List<String> list = shipperMapper.getShipperId(shipper);
		if (list.size() > 0) {
			// the id is already inserted
			throw new Exception("-1");
		}
		int ins1 = shipperMapper.insertUserGroup(shipper);
		if (ins1 != 1) {
			throw new Exception("-2");
		}

		int ins2 = shipperMapper.insertShipper(shipper);
		if (ins2 != 1) {
			throw new Exception("-3");
		}

		// Se actualiza TPA_TSHIPPER_POINT
		ShipperPointBean spbTmpBean = null;
		List<ComboFilterNS> lTargetContractPoints = new ArrayList<ComboFilterNS>();
		if(shipper.getdLContractPoints()!= null)
			lTargetContractPoints = shipper.getdLContractPoints().getTarget();

		for(ComboFilterNS cfnsPoint:lTargetContractPoints) {
			int version = 1;
			
			spbTmpBean = new ShipperPointBean();
			spbTmpBean.setUserGroupId(shipper.getIdn_user_group());
			spbTmpBean.setSysPointId(cfnsPoint.getKey());
			spbTmpBean.setVersion(version);
			spbTmpBean.setStartDate(new Date());	// Se guarda trunc en el insert.
			int ins3 = shipperMapper.insertShipperPoint(spbTmpBean);
			if (ins3 != 1) {
				throw new Exception("-4");
			}
		}
		
		return "0";
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String updateShipper(ShipperBean shipper, BigDecimal[] filterAreaIds) throws Exception {

		int upd1 = shipperMapper.updateUserGroup(shipper);
		if (upd1 != 1) {
			throw new Exception("-1");
		}

		int upd2 = shipperMapper.updateShipper(shipper);
		if (upd2 != 1) {
			throw new Exception("-2");
		}

		// Se actualiza TPA_TSHIPPER_POINT
		ShipperPointBean spbTmpBean = null;
		// Se obtiene la lista de shipper-punto, incluyendo aquellos no vigentes (con endDate no nula).
		// Asi se puede hacer insert subiendo la version.
		ShipperPointFilter spFilter = new ShipperPointFilter();
		spFilter.setAreaIds(filterAreaIds);
		spFilter.setUserGroupId(shipper.getIdn_user_group());
		spFilter.setNullEndDateFlag(false);	
		List<ShipperPointBean> lCurrentShipperPoints = shipperMapper.selectShipperPoints(spFilter);

		List<ComboFilterNS> lTargetContractPoints = null;
		if(shipper.getdLContractPoints()!= null)
			lTargetContractPoints = shipper.getdLContractPoints().getTarget();
		
		
		// Si hay puntos en la picklist que no estan en la tabla de BD, se inserta.
		buclePuntosPickList:
		for(ComboFilterNS cfnsPoint:lTargetContractPoints) {
			int version = 1;
			//Para cada shipper_point en BD se compara con el obtenido en picklist
			for(ShipperPointBean spBean: lCurrentShipperPoints)
				if(spBean.getSysPointId().equals(cfnsPoint.getKey()))
					if(spBean.getEndDate()==null)
						// Este punto ya estaba vigente.
						continue buclePuntosPickList;
					else
						// Este punto esta en la tabla, pero estaba caducado. Ahora lo insertamos
						version = spBean.getVersion()+1;
			
			spbTmpBean = new ShipperPointBean();
			spbTmpBean.setUserGroupId(shipper.getIdn_user_group());
			spbTmpBean.setSysPointId(cfnsPoint.getKey());
			spbTmpBean.setVersion(version);
			spbTmpBean.setStartDate(new Date());	// Se guarda trunc en el insert.
			int upd3 = shipperMapper.insertShipperPoint(spbTmpBean);
			if (upd3 != 1) {
				throw new Exception("-3");
			}
		}
		
		// Si hay puntos en la tabla de BD que no estan en la picklist, se borran.
		buclePuntosBD:
		for(ShipperPointBean spBean: lCurrentShipperPoints) {
			
			// Si el punto tiene fecha fin, no se tiene en cuenta que no este en la picklit,
			// porque ya esta caducado.
			if(spBean.getEndDate()!=null)
				continue buclePuntosBD;
			
			//Para cada shipper_point en BD se compara con el obtenido en picklist
			for(ComboFilterNS cfnsPoint:lTargetContractPoints)		
				if(spBean.getSysPointId().equals(cfnsPoint.getKey()))
					continue buclePuntosBD;
			
			spbTmpBean = new ShipperPointBean();
			spbTmpBean.setUserGroupId(spBean.getUserGroupId());
			spbTmpBean.setSysPointId(spBean.getSysPointId());
			spbTmpBean.setVersion(spBean.getVersion());
			spbTmpBean.setEndDate(new Date());		// Se guarda trunc en el update.
			// Hace un update asignando endDate.
			int upd4 = shipperMapper.deleteShipperPoint(spbTmpBean);
			if (upd4 != 1) {
				throw new Exception("-4");
			}
		}
		
		return "0";
	}

	public SystemParameterBean getSystemParameter(String str) {
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(systemParameterMapper.getSysdate().get(0));
		bean.setParameter_name(str);
		bean.setUser_id((String) SecurityUtils.getSubject().getPrincipal());
		bean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		systemParameterMapper.getIntegerSystemParameter(bean);
		return bean;
	}
}
