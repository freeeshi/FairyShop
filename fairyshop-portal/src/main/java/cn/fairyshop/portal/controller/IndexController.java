package cn.fairyshop.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fairyshop.portal.service.ContentService;

@Controller
public class IndexController {
	
	@Autowired
	private ContentService contentService = null;
	
	@RequestMapping("/index")
	public String showIndex(Model model) {
		String json = contentService.getAdList();
		model.addAttribute("ad1", json);
		
		return "index";
	}
	
	@RequestMapping("/testpost")
	@ResponseBody
	public String testPost(String name, String pass) {
		System.out.println(name + "  " + pass);
		return "OK";
	}

}
