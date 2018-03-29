package cn.fairyshop.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.fairyshop.pojo.TbUser;
import cn.fairyshop.portal.pojo.CartItem;
import cn.fairyshop.portal.pojo.OrderInfo;
import cn.fairyshop.portal.service.CartService;
import cn.fairyshop.portal.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private CartService cartService = null;
	
	@Autowired
	private OrderService orderService = null;
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String  createOrder(OrderInfo orderInfo, HttpServletRequest request, Model model) {
		// 从request中取出用户信息
		TbUser user = (TbUser) request.getAttribute("user");
		
		// 补全orderInfo信息
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		
		// 调用serviceOrder服务，获取订单号
		String orderId = orderService.createOrder(orderInfo);
		
		// 传递参数给jsp
		model.addAttribute("orderId", orderId);
		model.addAttribute("payment", orderInfo.getPayment());
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusDays(3);
		model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		
		return "success";
	}
	
	@RequestMapping("/order-cart")
	public String showCartList(HttpServletRequest request, Model model) {
		List<CartItem> cartItems = cartService.getCartItems(request);
		model.addAttribute("cartList", cartItems);
		return "order-cart";
	}

}
