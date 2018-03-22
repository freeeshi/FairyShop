package cn.fairyshop.search.dao;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

import cn.fairyshop.search.pojo.SearchResult;

public interface SearchDao {
	
	public SearchResult search(SolrQuery solrQuery) throws SolrServerException, IOException;

}
