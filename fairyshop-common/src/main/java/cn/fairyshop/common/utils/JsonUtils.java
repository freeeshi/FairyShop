package cn.fairyshop.common.utils;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
	// 定义json对象
	private static final ObjectMapper MAPPER = new ObjectMapper();

	// 将对象转化成json字符串
	public static String objectToJson(Object data) {
		try {
			String json = MAPPER.writeValueAsString(data);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 将json数据转化成pojo对象
	public static <T> T jsonToPojo(String jsonData, Class<T> beanType){
		try {
			T t = MAPPER.readValue(jsonData, beanType);
			return t;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// 将json数据装换成pojo列表
	public static <T>List<T> jsonToList(String jsonData, Class<T> beanType){
		JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
		try {
			List<T> ts = MAPPER.readValue(jsonData, javaType);
			return ts;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
