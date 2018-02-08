package cn.fairyshop.service;

import java.util.List;

import cn.fairyshop.common.pojo.EasyUITreeNode;

public interface ItemCatService {
	
	public List<EasyUITreeNode> getItemCatList(Long parentId);

}
