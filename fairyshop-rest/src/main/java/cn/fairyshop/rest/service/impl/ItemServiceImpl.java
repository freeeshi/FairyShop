package cn.fairyshop.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.fairyshop.common.utils.JsonUtils;
import cn.fairyshop.mapper.TbItemDescMapper;
import cn.fairyshop.mapper.TbItemMapper;
import cn.fairyshop.mapper.TbItemParamItemMapper;
import cn.fairyshop.pojo.TbItem;
import cn.fairyshop.pojo.TbItemDesc;
import cn.fairyshop.pojo.TbItemParamItem;
import cn.fairyshop.pojo.TbItemParamItemExample;
import cn.fairyshop.pojo.TbItemParamItemExample.Criteria;
import cn.fairyshop.rest.component.JedisClient;
import cn.fairyshop.rest.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private JedisClient jedisClient = null;

	@Autowired
	private TbItemMapper itemMapper = null;

	@Autowired
	private TbItemDescMapper itemDescMapper = null;

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper = null;

	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${ITEM_BASE_INFO_KEY}")
	private String ITEM_BASE_INFO_KEY;
	@Value("${ITEM_DESC_KEY}")
	private String ITEM_DESC_KEY;
	@Value("${ITEM_PARAM_KEY}")
	private String ITEM_PARAM_KEY;
	@Value("${ITEM_EXPIRE_SECOND}")
	private Integer ITEM_EXPIRE_SECOND;

	@Override
	public TbItemParamItem geItemParamItemById(Long itemId) {
		// 根据商品id查询商品描述

		// 先在redis中查找，找到的话直接返回
		String key = REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_PARAM_KEY;
		try {
			String json = jedisClient.get(key);
			if (StringUtils.isNotBlank(json)) {
				TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return itemParamItem;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// 在数据库中查找
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> itemParamItems = itemParamItemMapper.selectByExampleWithBLOBs(example);
		
		if (itemParamItems != null && itemParamItems.size() != 0) {
			TbItemParamItem itemParamItem = itemParamItems.get(0);
			// 将查找到的数据写入redis
			try {

				jedisClient.set(key, JsonUtils.objectToJson(itemParamItem));
				jedisClient.expire(key, ITEM_EXPIRE_SECOND);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			return itemParamItem;
		}

		return null;
	}

	@Override
	public TbItemDesc getItemDescById(Long itemId) {
		// 根据商品id查询商品描述

		// 先在redis中查找，找到的话直接返回
		String key = REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_DESC_KEY;
		try {
			String json = jedisClient.get(key);
			if (StringUtils.isNotBlank(json)) {
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return itemDesc;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// 在数据库中查找
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

		// 将查找到的数据写入redis
		try {
			jedisClient.set(key, JsonUtils.objectToJson(itemDesc));
			jedisClient.expire(key, ITEM_EXPIRE_SECOND);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return itemDesc;
	}

	@Override
	public TbItem getItemById(Long itemId) {
		// 根据商品id查询商品

		String key = REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_BASE_INFO_KEY;
		// 先在redis缓存中查找，找到的话直接返回
		try {
			// 在redis缓存中查找数据，找到的话直接返回
			String json = jedisClient.get(key);
			if (StringUtils.isNotBlank(json)) {
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				return item;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// 在数据库中查找
		TbItem item = itemMapper.selectByPrimaryKey(itemId);

		// 向redis中添加缓存
		try {
			// 设置键值对
			jedisClient.set(key, JsonUtils.objectToJson(item));
			// 设置过期时间
			jedisClient.expire(key, ITEM_EXPIRE_SECOND);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return item;
	}

}
