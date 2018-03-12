package cn.fairyshop.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.ExceptionUtils;
import cn.fairyshop.common.utils.JsonUtils;
import cn.fairyshop.mapper.TbContentMapper;
import cn.fairyshop.pojo.TbContent;
import cn.fairyshop.pojo.TbContentExample;
import cn.fairyshop.pojo.TbContentExample.Criteria;
import cn.fairyshop.rest.component.JedisClient;
import cn.fairyshop.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Value("${REDIS_CONTENT_KEY}")
	private String REDIS_CONTENT_KEY;
	
	@Autowired
	private JedisClient jedisClient = null;
	
	@Autowired
	private TbContentMapper contentMapper = null;

	@Override
	public FSResult getContentList(Long cid) {
		FSResult result = null;
		List<TbContent> list = null;
		try {
			// 先查询redis缓存中是否有数据
			try {
				String json = jedisClient.hget(REDIS_CONTENT_KEY, cid+"");
				if(json != null) {
					list = JsonUtils.jsonToList(json, TbContent.class);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// 若缓存中没有数据，则进行数据库查询
			if(list == null) {
				// 设置查询条件
				TbContentExample example = new TbContentExample();
				Criteria criteria = example.createCriteria();
				criteria.andCategoryIdEqualTo(cid);
				// 执行查询
				list = contentMapper.selectByExampleWithBLOBs(example);
			}
			
			// 将数据库读出的数据用redis缓存
			try {
				jedisClient.hset(REDIS_CONTENT_KEY, cid+"", JsonUtils.objectToJson(list));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			result = FSResult.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
			result = FSResult.build(500, ExceptionUtils.getStackTrace(e));
		}
		
		return result;
	}

	@Override
	public FSResult syncContent(Long cid) {
		FSResult result = null;
		try {
			jedisClient.hdel(REDIS_CONTENT_KEY, cid+"");
			result = FSResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			result = FSResult.build(500, ExceptionUtils.getStackTrace(e));
		}
		
		return result;
	}

}
