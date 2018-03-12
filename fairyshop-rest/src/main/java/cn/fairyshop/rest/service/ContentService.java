package cn.fairyshop.rest.service;

import cn.fairyshop.common.pojo.FSResult;

public interface ContentService {
	
	public FSResult getContentList(Long cid);
	
	public FSResult syncContent(Long cid);

}
