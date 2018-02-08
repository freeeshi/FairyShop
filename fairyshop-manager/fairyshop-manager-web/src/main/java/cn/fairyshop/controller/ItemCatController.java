package cn.fairyshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fairyshop.common.pojo.EasyUITreeNode;
import cn.fairyshop.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService = null;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(value="id", defaultValue="0") Long parentId){
//		System.out.println(itemCatService.getItemCatList(parentId).toString());
//		return null;
		return itemCatService.getItemCatList(parentId);
	}

}
