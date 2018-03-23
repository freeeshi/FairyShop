package cn.fairyshop.search.service.impl;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fairyshop.search.dao.SearchDao;
import cn.fairyshop.search.pojo.SearchResult;
import cn.fairyshop.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private SearchDao searchDao = null;

	@Override
	public SearchResult search(String queryString, int page, int rows) throws SolrServerException, IOException {
		// 创建查询条件
		SolrQuery solrQuery = new SolrQuery();
		// 设置查询条件按
		solrQuery.setQuery(queryString);
		// 设置分页条件
		solrQuery.setStart((page-1)*rows);
		solrQuery.setRows(rows);
		// 设置默认搜索域
		solrQuery.set("df", "item_title");
		// 打开高亮
		solrQuery.setHighlight(true);
		// 设置高亮域
		solrQuery.set("hl.fl", "item_title");
		// 设置高亮显示的格式，css样式
		solrQuery.setHighlightSimplePre("<font class=\"skcolor_ljg\">");
		solrQuery.setHighlightSimplePost("</font>");
		
		// 执行查询
		SearchResult result = searchDao.search(solrQuery);
		// 取出查询总记录数 
		Long recordCount = result.getRecordCount();
		// 计算页数
		int pageCount = (int) (recordCount / rows);
		if(recordCount % rows != 0)
			pageCount++;
		// 设置页数和当前页
		result.setPageCount(pageCount);
		result.setCurPage(page);
		
		return result;
	}

}
