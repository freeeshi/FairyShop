package cn.fairyshop.portal.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.pojo.TbItem;
import cn.fairyshop.portal.service.ItemService;
import cn.fairyshop.portal.service.StaticPageService;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class StaticPageServiceImpl implements StaticPageService {
	
	@Autowired
	private ItemService itemService = null;
	
	@Autowired
	private FreeMarkerConfig freeMarkerConfig = null;
	
	@Value("${STATIC_PAGE_PATH}")
	private String STATIC_PAGE_PATH;

	@Override
	public FSResult getItemHtml(Long itemId) throws Exception {
		// 获取商品信息
		TbItem item = itemService.getItemById(itemId);
		String itemDesc = itemService.getItemDescById(itemId);
		String itemParam = itemService.getItemParamItemById(itemId);
		
		//生成静态页面
		// 获取Configuration对象
		Configuration configuration = freeMarkerConfig.getConfiguration();
		// 获取指定的模板Template对象
		Template template = configuration.getTemplate("item.ftl");
		// 设置数据集
		Map root = new HashMap<>();
		root.put("item", item);
		root.put("itemDesc", itemDesc);
		root.put("itemParam", itemParam);
		// 获取Writer对象
		String path = STATIC_PAGE_PATH + itemId + ".html";
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
		// 调用process方法生成静态页面
		template.process(root, out);
		// 关闭输出流
		out.flush();
		out.close();
		return FSResult.ok();
	}

}
