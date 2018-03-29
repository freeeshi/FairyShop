package cn.fairyshop.order.service;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.order.pojo.OrderInfo;

public interface OrderService {
	
	public FSResult createOrder(OrderInfo orderInfo);
	

}
