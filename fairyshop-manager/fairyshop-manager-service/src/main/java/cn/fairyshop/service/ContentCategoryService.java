package cn.fairyshop.service;

import java.util.List;

import cn.fairyshop.common.pojo.EasyUITreeNode;
import cn.fairyshop.common.pojo.FSResult;

public interface ContentCategoryService {
	
	List<EasyUITreeNode> getContentCatList(Long parentId);
	
	FSResult insertCategory(Long parentId, String name);

}
