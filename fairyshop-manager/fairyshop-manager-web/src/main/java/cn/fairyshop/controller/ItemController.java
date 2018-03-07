package cn.fairyshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fairyshop.common.pojo.EasyUIDataGridResult;
import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.pojo.TbItem;
import cn.fairyshop.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService = null;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		return itemService.getItemById(itemId);
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		return itemService.getItemList(page, rows);
	}
	
	@RequestMapping(value="/item/save")
	@ResponseBody
	public FSResult createItem(TbItem item, String desc, String itemParams) {
		FSResult result = itemService.createItem(item, desc, itemParams);
		return result;
	}
	
	@RequestMapping("/page/item/{itemId}")
	public String showItemParam(@PathVariable Long itemId, Model model) {
		String paramHtml = itemService.getItemParamHtml(itemId);
		model.addAttribute("paramHtml", paramHtml);
		return "itemparam";
	}

}
