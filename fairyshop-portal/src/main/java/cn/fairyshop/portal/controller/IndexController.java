package cn.fairyshop.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
	
	@RequestMapping("/index")
	public String showIndex() {
		return "index";
	}
	
	@RequestMapping("/testpost")
	@ResponseBody
	public String testPost(String name, String pass) {
		System.out.println(name + "  " + pass);
		return "OK";
	}

}
