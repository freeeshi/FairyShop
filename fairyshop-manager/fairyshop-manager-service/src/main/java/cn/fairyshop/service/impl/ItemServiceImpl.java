package cn.fairyshop.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.fairyshop.common.pojo.EasyUIDataGridResult;
import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.IDUtils;
import cn.fairyshop.mapper.TbItemDescMapper;
import cn.fairyshop.mapper.TbItemMapper;
import cn.fairyshop.pojo.TbItem;
import cn.fairyshop.pojo.TbItemDesc;
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
	
	@Autowired
	private TbItemDescMapper itemDescMapper = null;

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

	@Override
	public FSResult createItem(TbItem item, String desc) {
		// 生成商品ID
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// 商品状态  1-正常  2-下架  3-删除
		item.setStatus((byte) 1);
		// 更新时间和更改时间
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 插入商品
		itemMapper.insert(item);
		
		// 商品描述
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 插入商品描述
		itemDescMapper.insert(itemDesc);
		
		return FSResult.ok();
	}

}
