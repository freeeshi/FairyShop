package cn.fairyshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fairyshop.common.pojo.EasyUITreeNode;
import cn.fairyshop.mapper.TbItemCatMapper;
import cn.fairyshop.pojo.TbItemCat;
import cn.fairyshop.pojo.TbItemCatExample;
import cn.fairyshop.pojo.TbItemCatExample.Criteria;
import cn.fairyshop.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper itemCatMapper = null;

	@Override
	public List<EasyUITreeNode> getItemCatList(Long parentId) {
		// 设置查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		// 进行查询
		List<TbItemCat> itemCats = itemCatMapper.selectByExample(example);
		
		// 构造EasyUITreeNode对象
		List<EasyUITreeNode> treeNodes = new ArrayList<>();
		for(TbItemCat t : itemCats) {
			// 构造节点
			EasyUITreeNode treeNode = new EasyUITreeNode();
			treeNode.setId(t.getId());
			treeNode.setText(t.getName());
			treeNode.setState(t.getIsParent() ? "closed" : "open");
			// 添加节点
			treeNodes.add(treeNode);
		}
		
		return treeNodes;
	}

}
