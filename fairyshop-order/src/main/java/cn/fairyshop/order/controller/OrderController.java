package cn.fairyshop.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.ExceptionUtils;
import cn.fairyshop.common.utils.JsonUtils;
import cn.fairyshop.order.pojo.OrderInfo;
import cn.fairyshop.order.service.OrderService;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService = null;
	
	@RequestMapping(value="/order/create", method=RequestMethod.POST)
	@ResponseBody
	public FSResult createOrder(@RequestBody OrderInfo orderInfo) {
		try {
			return orderService.createOrder(orderInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return FSResult.build(500, ExceptionUtils.getStackTrace(e));
		}
	}

}
