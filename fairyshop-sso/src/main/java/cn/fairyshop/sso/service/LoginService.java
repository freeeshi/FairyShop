package cn.fairyshop.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.fairyshop.common.pojo.FSResult;

public interface LoginService {

	public FSResult login(String username, String password, HttpServletRequest request, HttpServletResponse response);

	public FSResult getUserByToken(String token);

}
