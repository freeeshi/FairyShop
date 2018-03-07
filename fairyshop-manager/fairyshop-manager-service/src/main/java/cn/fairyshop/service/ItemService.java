package cn.fairyshop.service;

import cn.fairyshop.common.pojo.EasyUIDataGridResult;
import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.pojo.TbItem;

public interface ItemService {
	
	TbItem getItemById(Long itemId);
	
	EasyUIDataGridResult getItemList(int page, int rows);
	
	FSResult createItem(TbItem item, String desc, String itemParam);
	
	String getItemParamHtml(Long itemId);

}
