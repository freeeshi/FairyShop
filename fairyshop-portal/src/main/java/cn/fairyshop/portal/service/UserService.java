package cn.fairyshop.portal.service;

import javax.servlet.http.HttpServletRequest;

import cn.fairyshop.pojo.TbUser;

public interface UserService {
	
	public TbUser getUserByToken(HttpServletRequest request);

}
