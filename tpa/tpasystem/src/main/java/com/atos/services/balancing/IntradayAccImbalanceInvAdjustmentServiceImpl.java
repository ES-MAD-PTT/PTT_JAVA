package com.atos.services.balancing;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.balancing.IntradayAccImbalanceInventoryAdjustmentBean;
import com.atos.filters.balancing.IntradayAccImbalanceInventoryAdjustmentFilter;
import com.atos.mapper.balancing.IntradayAccImbalanceInvAdjustmentMapper;

@Service("intradayAccImbInvAdjService")
public class IntradayAccImbalanceInvAdjustmentServiceImpl implements IntradayAccImbalanceInvAdjustmentService{
	
	private static final long serialVersionUID = 5738619896981240370L;
		
		@Autowired
		private IntradayAccImbalanceInvAdjustmentMapper intradayAccImbInvAdjMapper;

		
		public List<IntradayAccImbalanceInventoryAdjustmentBean> selectIntradayAccImbalanceInvAdjustment(IntradayAccImbalanceInventoryAdjustmentFilter filter){
			return intradayAccImbInvAdjMapper.selectIntradayAccImbalanceInvAdjustment(filter);
		}
	
		@Transactional( rollbackFor = { Throwable.class })
		public String insertIntradayAccImbalanceInvAdjustment(IntradayAccImbalanceInventoryAdjustmentBean bean) throws Exception {
		
			int ins = intradayAccImbInvAdjMapper.insertIntradayAccImbalanceInvAdjustment(bean);
			if(ins != 1){
				throw new Exception("-2");
			}
			
			return "0";
		}
}
