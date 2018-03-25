package cn.fairyshop.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.ExceptionUtils;
import cn.fairyshop.rest.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService = null;
	
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public FSResult geItemParamItemById(@PathVariable Long itemId) {
		try {
			return FSResult.ok(itemService.geItemParamItemById(itemId));
		} catch (Exception e) {
			return FSResult.build(500, ExceptionUtils.getStackTrace(e));
		}
	}
	
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public FSResult getItemDescById(@PathVariable Long itemId) {
		try {
			return FSResult.ok(itemService.getItemDescById(itemId));
		} catch (Exception e) {
			return FSResult.build(500, ExceptionUtils.getStackTrace(e));
		}
	}

	/**
	 * 查询商品基本信息服务
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/base/{itemId}")
	@ResponseBody
	public FSResult geItemById(@PathVariable Long itemId) {
		try {
			return FSResult.ok(itemService.getItemById(itemId));
		} catch (Exception e) {
			return FSResult.build(500, ExceptionUtils.getStackTrace(e));
		}
	}

}
