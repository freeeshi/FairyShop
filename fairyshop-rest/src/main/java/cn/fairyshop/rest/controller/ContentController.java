package cn.fairyshop.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.rest.service.ContentService;

@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService = null;
	
	@RequestMapping("/content/{cid}")
	@ResponseBody
	public FSResult getContentList(@PathVariable Long cid) {
		return contentService.getContentList(cid);
	}

}
