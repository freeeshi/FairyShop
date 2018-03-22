package cn.fairyshop.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.search.mapper.ItemMapper;
import cn.fairyshop.search.pojo.SearchItem;
import cn.fairyshop.search.service.ItemService;
 

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private HttpSolrClient solrClient = null;
	
	@Autowired
	private ItemMapper itemMapper = null;

	@Override
	public FSResult importItem() throws SolrServerException, IOException{
		// 从数据库中查询数据
		List<SearchItem> list = itemMapper.getItemList();
		// 遍历结果集
		for(SearchItem item : list) {
			// 创建文本对象
			SolrInputDocument document = new SolrInputDocument();
			// 添加域
			document.addField("id", item.getId());
			document.addField("item_title", item.getTitle());
			document.addField("item_sell_point", item.getSell_point());
			document.addField("item_price", item.getPrice());
			document.addField("item_image", item.getImage());
			document.addField("item_category_name", item.getCategory_name());
			document.addField("item_desc", item.getItem_desc());
			// 添加到索引库中
			solrClient.add(document);
		}
		// 提交
		solrClient.commit();
		
		return FSResult.ok();
	}

}
