package cn.fairyshop.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.portal.pojo.CartItem;

public interface CartService {

	public FSResult addCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);

	public List<CartItem> getCartItems(HttpServletRequest request);

	public FSResult updateCartItem(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);

	public FSResult deleteCartItem(Long itemId, HttpServletRequest request, HttpServletResponse response);

}
