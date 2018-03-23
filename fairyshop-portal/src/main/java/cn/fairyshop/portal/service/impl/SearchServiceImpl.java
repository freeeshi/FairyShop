package cn.fairyshop.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.HttpClientUtils;
import cn.fairyshop.portal.pojo.SearchResult;
import cn.fairyshop.portal.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;

	@Override
	public SearchResult search(String keyword, int page, int rows) {
		// 设置请求的参数
		Map<String, String> param = new HashMap<>();
		param.put("keyword", keyword);
		param.put("page", page+"");
		param.put("rows", rows+"");
		
		// 用HttpClientUtils请求数据
		String json = HttpClientUtils.doGet(SEARCH_BASE_URL, param);
		
		// 获取FSResult和SearchResult
		FSResult fsResult = FSResult.formatToPojo(json, SearchResult.class);
		SearchResult searchResult = (SearchResult) fsResult.getData();

		return searchResult;
	}

}
