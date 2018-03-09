package cn.fairyshop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fairyshop.common.pojo.EasyUITreeNode;
import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.mapper.TbContentCategoryMapper;
import cn.fairyshop.pojo.TbContentCategory;
import cn.fairyshop.pojo.TbContentCategoryExample;
import cn.fairyshop.pojo.TbContentCategoryExample.Criteria;
import cn.fairyshop.service.ContentCategoryService;

/**
 * 内容分类service
 * @author 石龙飞
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper = null;

	@Override
	public List<EasyUITreeNode> getContentCatList(Long parentId) {
		// 通过parentId查询节点信息
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		
		// 需要返回的列表
		List<EasyUITreeNode> result = new ArrayList<>();
		
		// 遍历查询结果，构造
		for(TbContentCategory contentCategory : list) {
			// 构造节点信息
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(contentCategory.getId());
			node.setState(contentCategory.getIsParent() ? "closed" : "open");
			node.setText(contentCategory.getName());
			// 添加节点
			result.add(node);
		}
		
		return result;
	}

	@Override
	public FSResult insertCategory(Long parentId, String name) {
		// 构造节点信息
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		contentCategory.setIsParent(false);
		contentCategory.setStatus(1);
		contentCategory.setSortOrder(1);
		// 执行插入
		contentCategoryMapper.insert(contentCategory);

		// 获得主键作为返回结果的data
		Long id = contentCategory.getId();
		
		// 查找父节点
		TbContentCategory parentCategrop = contentCategoryMapper.selectByPrimaryKey(parentId);
		// 若父节点是叶子节点，则更新父节点为非叶子节点
		if(!parentCategrop.getIsParent()) {
			// 更新父节点
			parentCategrop.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parentCategrop);
		}
		
		return FSResult.ok(id);
	}

}
