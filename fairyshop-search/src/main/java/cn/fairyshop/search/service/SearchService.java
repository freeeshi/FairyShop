package cn.fairyshop.search.service;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

import cn.fairyshop.search.pojo.SearchResult;

public interface SearchService {
	
	public SearchResult search(String queryString, int page, int rows) throws SolrServerException, IOException ;

}
