package cn.fairyshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.pojo.TbContent;
import cn.fairyshop.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService contentService = null;
	
	@RequestMapping("/save")
	@ResponseBody
	public FSResult insertContent(TbContent content) {
		return contentService.insertContent(content);
	}

}
