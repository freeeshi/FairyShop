package cn.fairyshop.search.controller;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.ExceptionUtils;
import cn.fairyshop.search.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService = null;
	
	@RequestMapping("/importall")
	@ResponseBody
	public FSResult importAll() {
		try {
			return itemService.importItem();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return FSResult.build(500, ExceptionUtils.getStackTrace(e));
		}
	}

}
