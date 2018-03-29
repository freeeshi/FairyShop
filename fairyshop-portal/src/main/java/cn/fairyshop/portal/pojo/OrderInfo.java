package cn.fairyshop.portal.pojo;

import java.util.List;

import cn.fairyshop.pojo.TbOrder;
import cn.fairyshop.pojo.TbOrderItem;
import cn.fairyshop.pojo.TbOrderShipping;

public class OrderInfo extends TbOrder {

	private List<TbOrderItem> orderItems;
	private TbOrderShipping orderShipping;

	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}

	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}

}
