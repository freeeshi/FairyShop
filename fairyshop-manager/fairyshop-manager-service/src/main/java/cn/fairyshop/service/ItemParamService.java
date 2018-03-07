package cn.fairyshop.service;

import cn.fairyshop.common.pojo.FSResult;

public interface ItemParamService {
	
	FSResult getItemParamByCid(Long cid);
	
	FSResult insertItemParam(Long cid, String paramData);

}
