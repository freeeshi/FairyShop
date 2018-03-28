package cn.fairyshop.portal.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.CookieUtils;
import cn.fairyshop.common.utils.HttpClientUtils;
import cn.fairyshop.common.utils.JsonUtils;
import cn.fairyshop.pojo.TbUser;
import cn.fairyshop.portal.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Value("${SSO_BASE_URL}")
	private String SSO_BASE_URL;
	@Value("${USER_TOKEN_URL}")
	private String USER_TOKEN_URL;

	@Override
	public TbUser getUserByToken(HttpServletRequest request) {
		try {
			// 从cookie中获取token		
			String token = CookieUtils.getCookieValue(request, "FS_TOKEN");
			
			// token为空的话返回null
			if(StringUtils.isBlank(token)) {
				return null;
			}
			
			// token不为空，调用sso服务，获取user信息
			String url = SSO_BASE_URL + USER_TOKEN_URL + token;
			String json = HttpClientUtils.doGet(url);
			FSResult fsResult = JsonUtils.jsonToPojo(json, FSResult.class);
			
			// 得到错误的信息或者用户不存在，返回null
			if(fsResult.getStatus() != 200) {
				return null;
			}
			
			// 用户存在，取出user并返回
			TbUser user = (TbUser) FSResult.formatToPojo(json, TbUser.class).getData();
			return user;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
