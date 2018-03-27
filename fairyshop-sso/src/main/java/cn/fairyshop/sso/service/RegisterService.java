package cn.fairyshop.sso.service;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.pojo.TbUser;

public interface RegisterService {
	
	public FSResult checkData(String param, int type);
	public FSResult register(TbUser user);

}
