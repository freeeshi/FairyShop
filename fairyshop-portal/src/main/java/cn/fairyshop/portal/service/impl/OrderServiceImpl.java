package cn.fairyshop.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.HttpClientUtils;
import cn.fairyshop.common.utils.JsonUtils;
import cn.fairyshop.portal.pojo.OrderInfo;
import cn.fairyshop.portal.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;

	@Override
	public String createOrder(OrderInfo orderInfo) {
		// 将订单信息转换成json数据
		String json = JsonUtils.objectToJson(orderInfo);
		// 调用order系统的服务，发送订单信息
		String url = ORDER_BASE_URL + ORDER_CREATE_URL;
		String result = HttpClientUtils.doPostJson(url, json);
	
		// 接收返回的信息，取出订单号
		String  orderId = (String) JsonUtils.jsonToPojo(result, FSResult.class).getData();
		
		return orderId;
	}

}
