package cn.fairyshop.service;

import cn.fairyshop.common.pojo.EasyUIDataGridResult;
import cn.fairyshop.pojo.TbItem;

public interface ItemService {
	
	public TbItem getItemById(Long itemId);
	
	public EasyUIDataGridResult getItemList(int page, int rows);

}
