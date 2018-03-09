package cn.fairyshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fairyshop.common.pojo.EasyUITreeNode;
import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.service.ContentCategoryService;

/**
 * 内容分类controller
 * @author 石龙飞
 *
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentCategoryService = null;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0") Long parentId) {
		return contentCategoryService.getContentCatList(parentId);
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public FSResult insertCategory(Long parentId, String name) {
		return contentCategoryService.insertCategory(parentId, name);
	}

}
