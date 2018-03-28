package cn.fairyshop.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.CookieUtils;
import cn.fairyshop.common.utils.JsonUtils;
import cn.fairyshop.pojo.TbItem;
import cn.fairyshop.portal.pojo.CartItem;
import cn.fairyshop.portal.service.CartService;
import cn.fairyshop.portal.service.ItemService;

/**
 * 购物车service
 * 
 * @author 石龙飞
 *
 */
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private ItemService itemService = null;

	@Value("${COOKIE_EXPIRE}")
	private Integer COOKIE_EXPIRE;

	@Override
	public FSResult addCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
		// 从cookie中取出购物车列表
		List<CartItem> itemList = getCartItemList(request);

		// 如果购物车里存在此商品，让其数量加num
		boolean haveItem = false;
		for (CartItem cartItem : itemList) {
			if (cartItem.getId().longValue() == itemId.longValue()) {
				cartItem.setNum(cartItem.getNum() + num);
				haveItem = true;
				break;
			}
		}

		// 如果购物车里不存在此商品，调用ItemService服务获得对应商品信息，设置一条购物车内容
		if (haveItem == false) {
			TbItem item = itemService.getItemById(itemId);
			CartItem cartItem = new CartItem();
			cartItem.setId(itemId);
			cartItem.setNum(num);
			cartItem.setPrice(item.getPrice());
			cartItem.setTitle(item.getTitle());
			String images = item.getImage();
			if (StringUtils.isNotBlank(images)) {
				String[] image = images.split(",");
				cartItem.setImage(image[0]);
			}

			// 加入购物车
			itemList.add(cartItem);
		}

		// 设置cookie
		CookieUtils.setCookie(request, response, "FS_CART", JsonUtils.objectToJson(itemList), COOKIE_EXPIRE, true);

		return FSResult.ok();
	}

	/**
	 * 取购物车列表
	 * 
	 * @param request
	 * @return
	 */
	private List<CartItem> getCartItemList(HttpServletRequest request) {
		try {
			// 获取cookie中的购物车信息
			String json = CookieUtils.getCookieValue(request, "FS_CART", true);

			// 将信息转换成list，并返回
			List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
			return list == null ? new ArrayList<CartItem>() : list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<CartItem>();
		}

	}

	@Override
	public List<CartItem> getCartItems(HttpServletRequest request) {
		// 获取购物车列表
		List<CartItem> cartItemList = getCartItemList(request);
		return cartItemList;
	}

	@Override
	public FSResult updateCartItem(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
		// 获取购物车信息
		List<CartItem> itemList = getCartItemList(request);

		// 如果购物车里存在此商品，更新数量为num
		for (CartItem cartItem : itemList) {
			if (cartItem.getId().longValue() == itemId.longValue()) {
				cartItem.setNum(num);
				break;
			}
		}

		// 设置cookie
		CookieUtils.setCookie(request, response, "FS_CART", JsonUtils.objectToJson(itemList), COOKIE_EXPIRE, true);

		return FSResult.ok();
	}

	@Override
	public FSResult deleteCartItem(Long itemId, HttpServletRequest request, HttpServletResponse response) {
		// 获取购物车信息
		List<CartItem> itemList = getCartItemList(request);

		// 如果购物车里存在此商品，删除该商品
		for (int i = 0; i < itemList.size(); i++) {
			if (itemList.get(i).getId().longValue() == itemId.longValue()) {
				itemList.remove(i);
				break;
			}
		}
		
		// 设置cookie
		CookieUtils.setCookie(request, response, "FS_CART", JsonUtils.objectToJson(itemList), COOKIE_EXPIRE, true);
		
		return FSResult.ok();

	}

}
