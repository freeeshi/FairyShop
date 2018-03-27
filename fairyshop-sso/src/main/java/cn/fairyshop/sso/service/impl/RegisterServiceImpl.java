package cn.fairyshop.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.mapper.TbUserMapper;
import cn.fairyshop.pojo.TbUser;
import cn.fairyshop.pojo.TbUserExample;
import cn.fairyshop.pojo.TbUserExample.Criteria;
import cn.fairyshop.sso.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {
	
	@Autowired
	private TbUserMapper userMapper = null;

	@Override
	public FSResult checkData(String param, int type) {
		// 检查用户名、电话、邮箱是否重复
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		
		// 1-username  2-phone  3-email
		if( 1 == type) {
			criteria.andUsernameEqualTo(param);
		}else if( 2 == type) {
			criteria.andPhoneEqualTo(param);
		}else if( 3 == type) {
			criteria.andEmailEqualTo(param);
		} 
		
		// 执行查询
		List<TbUser> users = userMapper.selectByExample(example);
		
		if(users == null || users.isEmpty()) {
			return FSResult.ok(true);
		}
		
		return FSResult.ok(false);
	}

	@Override
	public FSResult register(TbUser user) {
		// 校验用户注册数据
		// 校验用户名和密码
		if(user.getUsername() == null || StringUtils.isEmpty(user.getUsername())
				|| user.getPassword() == null || StringUtils.isEmpty(user.getPassword())){
			return FSResult.build(400, "用户名或密码不能为空！");
		}
		
		FSResult fsResult = checkData(user.getUsername(), 1);
		if(!(boolean) fsResult.getData()) {
			return FSResult.build(400, "用户名已存在！");
		}
		
		// 校验电话
		if(user.getPhone() != null) {
			if(!(boolean) checkData(user.getPhone(), 2).getData()) {
				return FSResult.build(400, "电话不能重复！");
			}
		}
		
		// 校验邮箱
		if(user.getEmail() != null) {
			if(!(boolean) checkData(user.getEmail(), 3).getData()) {
				return FSResult.build(400, "邮箱不能重复！");
			}
		}
		
		// 对密码进行MD5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		
		user.setCreated(new Date());
		user.setUpdated(new Date());
		userMapper.insert(user);
		
		return FSResult.ok();
	}

}
