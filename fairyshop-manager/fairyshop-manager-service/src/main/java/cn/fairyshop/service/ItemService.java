package cn.fairyshop.service;

import cn.fairyshop.common.pojo.EasyUIDataGridResult;
import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.pojo.TbItem;

public interface ItemService {
	
	public TbItem getItemById(Long itemId);
	
	public EasyUIDataGridResult getItemList(int page, int rows);
	
	public FSResult createItem(TbItem item, String desc);

}
