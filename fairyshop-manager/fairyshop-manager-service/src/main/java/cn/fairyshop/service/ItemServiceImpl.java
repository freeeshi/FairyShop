package cn.fairyshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fairyshop.mapper.TbItemMapper;
import cn.fairyshop.pojo.TbItem;
import cn.fairyshop.pojo.TbItemExample;
import cn.fairyshop.pojo.TbItemExample.Criteria;

/*
 * 测试：通过itemId查询商品信息
 */
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper itemMapper = null;

	@Override
	public TbItem getItemById(Long itemId) {
		TbItem item = null;
		
		// 通过主键查询
		//item = itemMapper.selectByPrimaryKey(itemId);
		
		// 通过条件查询
		TbItemExample example = new TbItemExample();
		// 设置条件
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		// 获取查询列表
		List<TbItem> items = itemMapper.selectByExample(example);
		// 判断items，返回设置查询结果
		if(items != null && items.size() > 0) {
			item = items.get(0);
		}
		
		return item;
	}

}
