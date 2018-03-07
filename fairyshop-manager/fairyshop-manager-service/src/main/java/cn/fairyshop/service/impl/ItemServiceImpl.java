package cn.fairyshop.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.fairyshop.common.pojo.EasyUIDataGridResult;
import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.IDUtils;
import cn.fairyshop.common.utils.JsonUtils;
import cn.fairyshop.mapper.TbItemDescMapper;
import cn.fairyshop.mapper.TbItemMapper;
import cn.fairyshop.mapper.TbItemParamItemMapper;
import cn.fairyshop.pojo.TbItem;
import cn.fairyshop.pojo.TbItemDesc;
import cn.fairyshop.pojo.TbItemExample;
import cn.fairyshop.pojo.TbItemExample.Criteria;
import cn.fairyshop.pojo.TbItemParamItem;
import cn.fairyshop.pojo.TbItemParamItemExample;
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
	
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper = null;

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
	public FSResult createItem(TbItem item, String desc, String itemParam) {
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
		
		// 添加商品规格参数
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		// 插入商品规格参数
		itemParamItemMapper.insert(itemParamItem);
		
		return FSResult.ok();
	}

	@Override
	public String getItemParamHtml(Long itemId) {
		// 根据商品ID查询商品的规格参数
		TbItemParamItemExample example = new TbItemParamItemExample();
		cn.fairyshop.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		// 执行查询
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		
		// 判断查询结果，非空的话构建html内容
		String html = "";
		if(list != null || list.size() > 0) {
			// 取出查询结果
			TbItemParamItem itemParamItem = list.get(0);
			// 取出json格式的参数字符串
			String paramData = itemParamItem.getParamData();
			// 将json数据转化成Map集合
			List<Map> mapList = JsonUtils.jsonToList(paramData, Map.class);
			
			// 遍历map，构建html
			StringBuffer sb = new StringBuffer();
			
			sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">");
			sb.append("  <tbody>");
			for(Map map : mapList) {
				// 构建组标题
				sb.append("    <tr>");
				sb.append("      <th class=\"tdTitle\"  colspan=\"2\">" + map.get("group") +"</th>");
				sb.append("    </tr>");

				// 构建各个参数标题
				List<Map> mapList2 = (List<Map>) map.get("params");
				for(Map map2 : mapList2) {
					sb.append("    <tr>");
					sb.append("      <td class=\"tdTitle\">" + map2.get("k") +"</td>");
					sb.append("      <td>" + map2.get("v") +"</td>");
					sb.append("    </tr>");
				}
				
			}
			sb.append("  </tbody>");
			sb.append("</table>");
			
			html = sb.toString();	
		}
		
		return html;
	}

}
