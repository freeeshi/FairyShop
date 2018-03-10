package cn.fairyshop.httpclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientTest {
	
	@Test
	public void testHttpPost() throws ClientProtocolException, IOException {
		
		CloseableHttpClient client = HttpClients.createDefault();
		
		HttpPost post = new HttpPost("http://localhost:8082/testpost.html");
		
		List<NameValuePair> formlist = new ArrayList<>();
		formlist.add(new BasicNameValuePair("name", "dd"));
		formlist.add(new BasicNameValuePair("pass", "world"));
		
		StringEntity entity = new UrlEncodedFormEntity(formlist, "utf-8");
		post.setEntity(entity);
		
		CloseableHttpResponse response = client.execute(post);
		
		HttpEntity httpEntity = response.getEntity();
		String result = EntityUtils.toString(httpEntity);
		
		response.close();
		client.close();
		
		System.out.println(result);
	}
	
	@Test
	public void testHttpGet() throws ClientProtocolException, IOException {
		// 创建HttpClient对象
		CloseableHttpClient client = HttpClients.createDefault();
		// 创建HttpGet对象，并且制定url
		HttpGet get = new HttpGet("http://www.baidu.com");
		// 执行请求
		CloseableHttpResponse response = client.execute(get);
		// 获得返回结果的Entiyt对象
		HttpEntity entity = response.getEntity();
		// 获取响应内容
		String result = EntityUtils.toString(entity);
		// 关闭resopnse和client
		response.close();
		client.close();
		
		System.out.println(result);
		
	}

}
