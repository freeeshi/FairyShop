package cn.fairyshop.search.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;

import cn.fairyshop.search.dao.SearchDao;
import cn.fairyshop.search.pojo.SearchItem;
import cn.fairyshop.search.pojo.SearchResult;

public class SearchDaoImpl implements SearchDao {
	
	@Autowired
	private HttpSolrClient solrClient = null;

	@Override
	public SearchResult search(SolrQuery solrQuery) throws SolrServerException, IOException {
		// 执行查询
		QueryResponse response = solrClient.query(solrQuery);
		// 获取返回结构列表
		SolrDocumentList documentList = response.getResults();
		// 遍历结果集，添加到集合中
		List<SearchItem> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : documentList) {
			SearchItem item = new SearchItem();
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setId((String) solrDocument.get("id"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			
			// 取高亮显示
			// 取高亮显示结果Map集合
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			// 取出单条高亮信息
			List<String> titleList = highlighting.get(solrDocument.get("id")).get("item_title");
			// 获取高亮内容，如果为空的话，title设置为查询的非高亮的结果
			String title = "";
			if(titleList != null && titleList.size() != 0) {
				title = titleList.get(0);
			}else {
				title = (String) solrDocument.get("item_title");
			}
			item.setTitle(title);
			
			itemList.add(item);
		}
		
		// 构造SearchResult
		SearchResult result = new SearchResult();
		result.setItemList(itemList);
		result.setRecordCount(documentList.getNumFound());
		
		return result;
	}

}
