package cn.fairyshop.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fairyshop.common.utils.JsonUtils;
import cn.fairyshop.portal.pojo.SearchResult;
import cn.fairyshop.portal.service.SearchService;

@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService = null;
	
	@RequestMapping("/search")
	public String search(@RequestParam("q") String keyword, 
			@RequestParam(defaultValue="1") Integer page, 
			@RequestParam(defaultValue="60") Integer rows, Model model) {
		// 转换字符集，解决get乱码
		try {
			keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			keyword = "";
			e.printStackTrace();
		}
		
		// 执行搜索
		SearchResult searchResult = searchService.search(keyword, page, rows);
		
		// 设置需要传递的参数参数
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", searchResult.getRecordCount());
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("page", page);
		
		// 返回逻辑视图
		return "search";
	}

}
