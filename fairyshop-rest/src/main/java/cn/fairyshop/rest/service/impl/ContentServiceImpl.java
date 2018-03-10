package cn.fairyshop.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.ExceptionUtils;
import cn.fairyshop.mapper.TbContentMapper;
import cn.fairyshop.pojo.TbContent;
import cn.fairyshop.pojo.TbContentExample;
import cn.fairyshop.pojo.TbContentExample.Criteria;
import cn.fairyshop.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper = null;

	@Override
	public FSResult getContentList(Long cid) {
		FSResult result = null;
		try {
			// 设置查询条件
			TbContentExample example = new TbContentExample();
			Criteria criteria = example.createCriteria();
			criteria.andCategoryIdEqualTo(cid);
			// 执行查询
			List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
			
			result = FSResult.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
			result = FSResult.build(500, ExceptionUtils.getStackTrace(e));
		}
		
		return result;
	}

}
