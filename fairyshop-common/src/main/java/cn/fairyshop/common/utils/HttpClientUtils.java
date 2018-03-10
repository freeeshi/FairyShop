package cn.fairyshop.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {
	
	public static String doPostJson(String url, String json) {
		// 创建HttpClient对象
		CloseableHttpClient client = HttpClients.createDefault();

		CloseableHttpResponse response = null;
		String result = "";
		
		try {
			// 创建HttpPost对象
			HttpPost post = new HttpPost(url);
				
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			post.setEntity(entity);
			
			// 执行请求，获得结果
			response = client.execute(post);
			result = EntityUtils.toString(response.getEntity());
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			try {
				// 关闭resopnse和client
				if(response != null) response.close();
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result; 
	}
	
	public static String doPost(String url) {
		return doPost(url, null);
	}
	
	public static String doPost(String url, Map<String, String> params) {
		// 创建HttpClient对象
		CloseableHttpClient client = HttpClients.createDefault();

		CloseableHttpResponse response = null;
		String result = "";
		
		try {
			// 创建HttpPost对象
			HttpPost post = new HttpPost(url);
			// 构造参数列表
			if(params != null) {
				List<NameValuePair> paramsList = new ArrayList<>();
				for(String key : params.keySet()) {
					paramsList.add(new BasicNameValuePair(key, params.get(key)));
				}
				
				StringEntity entity = new UrlEncodedFormEntity(paramsList, "utf-8");
				post.setEntity(entity);
			}
			
			// 执行请求，获得结果
			response = client.execute(post);
			result = EntityUtils.toString(response.getEntity());
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			try {
				// 关闭resopnse和client
				if(response != null) response.close();
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static String doGet(String url) {
		return doGet(url, null);
	}
	
	public static String doGet(String url, Map<String, String> params) {
		// 创建HttpClient对象
		CloseableHttpClient client = HttpClients.createDefault();
		
		CloseableHttpResponse response = null;
		String result = "";
		
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if(params != null) {
				for(String key : params.keySet()) {
					builder.addParameter(key, params.get(key));
				}
			}
			URI uri = builder.build();
			
			// 创建HttpGet对象，并且制定url
			HttpGet get = new HttpGet(uri);
			
			// 执行请求
			response = client.execute(get);
			
			// 判断状态码
			if(response.getStatusLine().getStatusCode() == 200) {
				// 获得返回结果的Entiyt对象
				HttpEntity entity = response.getEntity();
				// 获取响应内容
				result = EntityUtils.toString(entity, "utf-8");
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				// 关闭resopnse和client
				if(response != null) response.close();
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

}
