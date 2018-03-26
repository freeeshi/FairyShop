package cn.fairyshop.portal.service;

import java.io.IOException;

import cn.fairyshop.common.pojo.FSResult;
import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateNotFoundException;

public interface StaticPageService {
	
	public FSResult getItemHtml(Long itemId) throws Exception ;

}
