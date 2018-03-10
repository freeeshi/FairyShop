package cn.fairyshop.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.HttpClientUtils;
import cn.fairyshop.common.utils.JsonUtils;
import cn.fairyshop.pojo.TbContent;
import cn.fairyshop.portal.pojo.AdNode;
import cn.fairyshop.portal.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REST_CONTENT_URL}")
	private String REST_CONTENT_URL;
	
	@Value("${REST_CONTENT_AD1_CID}")
	private String REST_CONTENT_AD1_CID;

	@Override
	public String getAdList() {
		// 调用服务获取数据
		String json = HttpClientUtils.doGet(REST_BASE_URL + REST_CONTENT_URL + REST_CONTENT_AD1_CID);
		// 将数据中的data转化成content列表
		FSResult fsResult = FSResult.formatToList(json, TbContent.class);
		List<TbContent> contentList = (List<TbContent>) fsResult.getData();

		// 遍历contentList，构造AdNode
		List<AdNode> adNodes = new ArrayList<>();
		for(TbContent content : contentList) {
			AdNode node = new AdNode();
			node.setHeight(240);
			node.setWidth(670);
			node.setSrc(content.getPic());
			
			node.setHeightB(240);
			node.setWidthB(550);
			node.setSrcB(content.getPic2());
			
			node.setAlt(content.getSubTitle());
			node.setHref(content.getUrl());
			
			adNodes.add(node);
		}
		
		// 将adNodes转换成json数据返回
		String result = JsonUtils.objectToJson(adNodes);
		return result;
	}

}
