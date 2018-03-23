package cn.fairyshop.portal.service;

import cn.fairyshop.portal.pojo.SearchResult;

public interface SearchService {
	
	public SearchResult search(String keyword, int page, int rows);

}
