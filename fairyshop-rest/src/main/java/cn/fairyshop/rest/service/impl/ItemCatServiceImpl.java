package cn.fairyshop.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fairyshop.mapper.TbItemCatMapper;
import cn.fairyshop.pojo.TbItemCat;
import cn.fairyshop.pojo.TbItemCatExample;
import cn.fairyshop.pojo.TbItemCatExample.Criteria;
import cn.fairyshop.rest.pojo.CatNode;
import cn.fairyshop.rest.pojo.ItemCatResult;
import cn.fairyshop.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper itemCatMapper = null;

	@Override
	public ItemCatResult getItemCatList() {
		// 递归查询商品分类列表
		List itemCatList = getItemCatList(0l);
		// 构造结果
		ItemCatResult itemCatResult = new ItemCatResult();
		itemCatResult.setData(itemCatList);
		
		return itemCatResult;
	}
	
	private List getItemCatList(Long parentId) {
		// 查询结果列表
		List resultList = new ArrayList<>();
		
		// 构造查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbItemCat> itemCatList = itemCatMapper.selectByExample(example);
		
		// 遍历查询结果
		int i = 0;
		for(TbItemCat itemCat : itemCatList) {
			// 判断是否为父节点，对i进行不同的构造
			if(itemCat.getIsParent()) {
				// 构造节点
				CatNode catNode = new CatNode();
				// 设置url
				catNode.setUrl("/products/" + itemCat.getId() + ".html");
				// 设置name
				if(itemCat.getParentId() == 0) {
					i++;
					catNode.setName("<a href='/products/" + itemCat.getId() + ".html'>" + itemCat.getName() + "</a>");
				}else {
					catNode.setName(itemCat.getName());
				}
				// 设置item
				catNode.setItem(getItemCatList(itemCat.getId()));	
				
				// 添加节点
				resultList.add(catNode);
			}else {
				// 设置item
				String item = "/products/" + itemCat.getId() + ".html|" + itemCat.getName();
				// 添加节点
				resultList.add(item);
			}
			
			// 限制查询商品目录条数为14
			if(i > 13) {
				break;
			}
		}
		return resultList;
	}

}
