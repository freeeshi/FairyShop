package cn.fairyshop.portal.service;

import cn.fairyshop.pojo.TbItem;

public interface ItemService {
	
	public TbItem getItemById(Long itemId);
	public String getItemDescById(Long itemId);
	public String getItemParamItemById(Long itemId);

}
