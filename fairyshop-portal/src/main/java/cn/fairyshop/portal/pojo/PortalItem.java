package cn.fairyshop.portal.pojo;

import cn.fairyshop.pojo.TbItem;

public class PortalItem extends TbItem {
	
	public String[] getImages() {
		String image = getImage();
		if(image != null && !image.equals("")) {
			String[] images = image.split(",");
			return images;
		}
		return null;
	}

}
