package cn.fairyshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.pojo.TbItemParam;
import cn.fairyshop.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	
	@Autowired
	private ItemParamService itemParamService = null;
	
	@RequestMapping("/query/itemcatid/{cid}")
	@ResponseBody
	public FSResult getItemParamByCid(@PathVariable Long cid) {
		FSResult result = itemParamService.getItemParamByCid(cid); 
		TbItemParam itemParam = (TbItemParam) result.getData();
		return result;
	}
	
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public FSResult insertItemParam(@PathVariable Long cid, String paramData) {
		FSResult result = itemParamService.insertItemParam(cid, paramData);
		return result;
	}

}
