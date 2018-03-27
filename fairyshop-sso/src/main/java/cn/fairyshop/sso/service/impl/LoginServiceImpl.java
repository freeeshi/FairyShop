package cn.fairyshop.sso.service.impl;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.CookieUtils;
import cn.fairyshop.common.utils.JsonUtils;
import cn.fairyshop.mapper.TbUserMapper;
import cn.fairyshop.pojo.TbUser;
import cn.fairyshop.pojo.TbUserExample;
import cn.fairyshop.pojo.TbUserExample.Criteria;
import cn.fairyshop.sso.component.JedisClient;
import cn.fairyshop.sso.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private TbUserMapper userMapper = null;

	@Autowired
	private JedisClient jedisClient = null;

	@Value("${REDIS_SESSION_KEY}")
	private String REDIS_SESSION_KEY;
	@Value("${SESSION_EXPIRE_SECOND}")
	private Integer SESSION_EXPIRE_SECOND;

	@Override
	public FSResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		// 根据用户名查询用户信息
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> users = userMapper.selectByExample(example);

		// 校验用户是否存在
		if (users == null || users.isEmpty()) {
			return FSResult.build(400, "用户名或密码错误！");
		}

		// 校验密码
		if (!users.get(0).getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
			return FSResult.build(400, "用户名或密码错误！");
		}

		// 登录成功，将信息写入redis，写cookie
		// 置用户密码为空
		users.get(0).setPassword(null);

		// 将用户信息存入redis
		// 生成token，作为key
		String token = UUID.randomUUID().toString();
		// 构造key
		String key = REDIS_SESSION_KEY + ":" + token;
		// 写入redis
		jedisClient.set(key, JsonUtils.objectToJson(users.get(0)));
		jedisClient.expire(key, SESSION_EXPIRE_SECOND);

		// 将token写入cookie
		CookieUtils.setCookie(request, response, "FS_TOKEN", token);

		return FSResult.ok(token);
	}

	@Override
	public FSResult getUserByToken(String token) {
		// 通过token在redis中获得用户信息
		String key = REDIS_SESSION_KEY + ":" + token;
		String json = jedisClient.get(key);

		// 不存在说明session已经过期
		if (json == null || StringUtils.isBlank(json)) {
			return FSResult.build(400, "用户session已经过期！");
		}

		// session未过期，取出用户信息
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		// 重置session过期时间
		jedisClient.expire(key, SESSION_EXPIRE_SECOND);

		return FSResult.ok(user);
	}

}
