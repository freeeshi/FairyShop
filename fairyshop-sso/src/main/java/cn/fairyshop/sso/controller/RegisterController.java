package cn.fairyshop.sso.controller;

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
import cn.fairyshop.pojo.TbUser;
import cn.fairyshop.sso.service.RegisterService;

@Controller
@RequestMapping("/user")
public class RegisterController {
	
	@Autowired
	private RegisterService registerService = null;
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	@ResponseBody
	public FSResult register(TbUser user) {
		try {
			return registerService.register(user);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return FSResult.build(400, ExceptionUtils.getStackTrace(e));
		}
	}
	
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param, @PathVariable int type, String callback) {
		try {
			FSResult result = registerService.checkData(param, type);
			if(StringUtils.isNotBlank(callback)) {
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

}
