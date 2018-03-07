package cn.fairyshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	
	@Autowired
	private ItemParamService itemParamService = null;
	
	@RequestMapping("/query/itemcatid/{cid}")
	@ResponseBody
	public FSResult getItemParamByCid(@PathVariable Long cid) {
		return itemParamService.getItemParamByCid(cid);
	}

}
