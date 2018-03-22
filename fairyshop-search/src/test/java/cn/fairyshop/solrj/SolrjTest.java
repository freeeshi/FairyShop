package cn.fairyshop.solrj;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrjTest {
	
	@Test
	public void testQuery() throws SolrServerException, IOException {
		// 创建连接
		String url = "http://192.168.0.151:8080/solr/collection1";
		HttpSolrClient solrClient = new HttpSolrClient(url);
		// 创建查询对象
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		// 执行查询
		QueryResponse response = solrClient.query(query);
		// 取查询结果
		SolrDocumentList documentList = response.getResults();
		for(SolrDocument s : documentList) {
			System.out.println(s.get("id"));
			System.out.println(s.get("item_title"));
			System.out.println(s.get("item_sell_point"));
		}
		
	}
	
	@Test
	public void testSolrj() throws SolrServerException, IOException {
		// 创建连接
		String url = "http://192.168.0.151:8080/solr/collection1";
		HttpSolrClient solrClient = new HttpSolrClient(url);
		// 创建文档对象
		SolrInputDocument document = new SolrInputDocument();
		// 添加域
		document.addField("id", "solrtest01");
		document.addField("item_title", "测试商品");
		document.addField("item_sell_point", "买点");
		// 添加到索引库
		solrClient.add(document);
		// 提交
		solrClient.commit();
		// 关闭连接
		solrClient.close();
	}

}
