package cn.fairyshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.mapper.TbItemParamMapper;
import cn.fairyshop.pojo.TbItemParam;
import cn.fairyshop.pojo.TbItemParamExample;
import cn.fairyshop.pojo.TbItemParamExample.Criteria;

@Service
public class ItemParamServiceImpl implements cn.fairyshop.service.ItemParamService {
	
	@Autowired
	private TbItemParamMapper itemParamMapper = null;

	@Override
	public FSResult getItemParamByCid(Long cid) {
		// 根据cid查询参数模板
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		// 执行查询
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		// 判断是否查询到结果
		if(list != null && list.size() > 0) {
			TbItemParam itemParam = list.get(0);
			return FSResult.ok(itemParam);
		}
		
		return FSResult.ok();
	}

}
