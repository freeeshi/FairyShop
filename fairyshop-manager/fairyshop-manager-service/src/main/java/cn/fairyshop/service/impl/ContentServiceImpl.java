package cn.fairyshop.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.mapper.TbContentMapper;
import cn.fairyshop.pojo.TbContent;
import cn.fairyshop.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper = null;

	@Override
	public FSResult insertContent(TbContent content) {
		// 补全content信息
		content.setCreated(new Date());
		content.setUpdated(new Date());
		// 插入content
		contentMapper.insert(content);
		
		return FSResult.ok();
	}

}
