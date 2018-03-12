package cn.fairyshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.HttpClientUtils;
import cn.fairyshop.pojo.TbContent;
import cn.fairyshop.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	
	@Autowired
	private ContentService contentService = null;
	
	@RequestMapping("/save")
	@ResponseBody
	public FSResult insertContent(TbContent content) {
		FSResult result = contentService.insertContent(content);
		
		// 调用rest中的清空缓存的服务
		HttpClientUtils.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
		
		return result;
	}

}
