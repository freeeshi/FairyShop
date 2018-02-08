package cn.fairyshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.fairyshop.common.pojo.EasyUIDataGridResult;
import cn.fairyshop.mapper.TbItemMapper;
import cn.fairyshop.pojo.TbItem;
import cn.fairyshop.pojo.TbItemExample;
import cn.fairyshop.pojo.TbItemExample.Criteria;
import cn.fairyshop.service.ItemService;

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

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		// 分页操作
		PageHelper.startPage(page, rows);
		
		// 进行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> items = itemMapper.selectByExample(example);
		
		// 取出分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(items);
		
		// 构造返回对象EasyUIDataGridResult
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(items);
		
		return result;
	}

}
