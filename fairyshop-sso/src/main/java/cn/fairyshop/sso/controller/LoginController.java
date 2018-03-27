package cn.fairyshop.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.ExceptionUtils;
import cn.fairyshop.sso.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService = null;

	@RequestMapping("/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		try {
			FSResult result = loginService.getUserByToken(token);
			if (callback != null && StringUtils.isNoneBlank(callback)) {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			}
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return FSResult.build(500, ExceptionUtils.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	public FSResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		try {
			return loginService.login(username, password, request, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return FSResult.build(500, ExceptionUtils.getStackTrace(e));
		}
	}

}
