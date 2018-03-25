package cn.fairyshop.rest.service;

import cn.fairyshop.pojo.TbItem;
import cn.fairyshop.pojo.TbItemDesc;
import cn.fairyshop.pojo.TbItemParamItem;

public interface ItemService {
	
	public TbItem getItemById(Long itemId);
	public TbItemDesc getItemDescById(Long itemId);
	public TbItemParamItem geItemParamItemById(Long itemId);	
	
}
