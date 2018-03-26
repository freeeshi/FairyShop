package cn.fairyshop.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.ExceptionUtils;
import cn.fairyshop.portal.service.StaticPageService;

@Controller
public class StaticPageController {
	
	@Autowired
	private StaticPageService staticPageService = null;
	
	@RequestMapping("/gen/item/{itemId}")
	@ResponseBody
	public FSResult genItemPage(@PathVariable Long itemId) {
		try {
			return staticPageService.getItemHtml(itemId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return FSResult.build(500, ExceptionUtils.getStackTrace(e));
		}
	}

}
