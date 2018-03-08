package cn.fairyshop.rest.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fairyshop.common.utils.JsonUtils;
import cn.fairyshop.rest.pojo.ItemCatResult;
import cn.fairyshop.rest.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService = null; 
	
	//方法一，适用所有版本
//	@RequestMapping(value="/list", produces=MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
//	@ResponseBody
//	public String getItemCatList(String callback) {
//		ItemCatResult result = itemCatService.getItemCatList();
//		String json = JsonUtils.objectToJson(result);
//		
//		// 若callback不为空，则使用的是jsonp请求，需要返回js片段
//		if(!StringUtils.isBlank(callback)) {
//			json = callback + "(" + json + ");";
//		}
//		
//		return json;
//	}
	
	// 方法二，适用spring4.1以上版本
	@RequestMapping("/list")
	@ResponseBody
	public Object getItemCatList(String callback) {
		ItemCatResult result = itemCatService.getItemCatList();
		
		// 若callback不为空，则使用的是jsonp请求，需要返回js片段
		if(StringUtils.isBlank(callback)) {
			return result;
		}
		
		// 如果不为空，支持jsonp调用
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
		mappingJacksonValue.setJsonpFunction(callback);
		return mappingJacksonValue;
	}

}
