package cn.fairyshop.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.portal.pojo.CartItem;
import cn.fairyshop.portal.service.CartService;

@Controller
public class CartController {

	@Autowired
	private CartService cartService = null;
	
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request,
			HttpServletResponse response) {
		cartService.deleteCartItem(itemId, request, response);
		return "redirect:/cart/cart.html";
	}
	
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public FSResult updateCartItem(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		return cartService.updateCartItem(itemId, num, request, response);
	}

	@RequestMapping("/cart/cart")
	public String showCartItems(HttpServletRequest request, Model model) {
		List<CartItem> items = cartService.getCartItems(request);
		model.addAttribute("cartList", items);
		return "cart";
	}

	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		cartService.addCart(itemId, num, request, response);
		return "cart-success";
	}

}
