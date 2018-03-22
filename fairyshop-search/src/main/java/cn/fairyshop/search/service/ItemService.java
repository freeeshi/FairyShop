package cn.fairyshop.search.service;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

import cn.fairyshop.common.pojo.FSResult;

public interface ItemService {
	
	public FSResult importItem() throws SolrServerException, IOException;

}
