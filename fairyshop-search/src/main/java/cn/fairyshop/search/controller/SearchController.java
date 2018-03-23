package cn.fairyshop.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.ExceptionUtils;
import cn.fairyshop.search.pojo.SearchResult;
import cn.fairyshop.search.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService = null;

	@RequestMapping("/q")
	@ResponseBody
	public FSResult search(@RequestParam(defaultValue = "") String keyword, 
			@RequestParam(defaultValue="1") Integer page,
			@RequestParam(defaultValue="30") Integer rows) {
		
		FSResult result = null;
		
		try {
			// 转换字符集，解决get乱发
			keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
			
			SearchResult searchResult = searchService.search(keyword, page, rows);
			result = FSResult.ok(searchResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = FSResult.build(500, ExceptionUtils.getStackTrace(e));
		}
		
		return result;
	}

}
