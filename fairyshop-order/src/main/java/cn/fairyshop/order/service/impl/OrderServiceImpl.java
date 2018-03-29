package cn.fairyshop.order.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.mapper.TbOrderItemMapper;
import cn.fairyshop.mapper.TbOrderMapper;
import cn.fairyshop.mapper.TbOrderShippingMapper;
import cn.fairyshop.order.component.JedisClient;
import cn.fairyshop.order.pojo.OrderInfo;
import cn.fairyshop.order.service.OrderService;
import cn.fairyshop.pojo.TbOrder;
import cn.fairyshop.pojo.TbOrderItem;
import cn.fairyshop.pojo.TbOrderShipping;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderMapper orderMapper = null;

	@Autowired
	private TbOrderItemMapper orderItemMapper = null;

	@Autowired
	private TbOrderShippingMapper orderShippingMapper = null;

	@Autowired
	private JedisClient jedisClient = null;

	@Value("${REDIS_ORDER_GEN_KEY}")
	private String REDIS_ORDER_GEN_KEY;
	@Value("${ORDER_ID_BEGIN}")
	private String ORDER_ID_BEGIN;
	@Value("${REDIS_ORDER_DETAIL_GEN_KEY}")
	private String REDIS_ORDER_DETAIL_GEN_KEY;

	@Override
	public FSResult createOrder(OrderInfo orderInfo) {
		// 生成订单号
		String id = jedisClient.get(REDIS_ORDER_GEN_KEY);
		if (StringUtils.isBlank(id))
			jedisClient.set(REDIS_ORDER_GEN_KEY, ORDER_ID_BEGIN);
		Long orderId = jedisClient.incr(REDIS_ORDER_GEN_KEY);

		// 设置订单信息
		orderInfo.setOrderId(orderId.toString());
		// 1-未付款 2-已付款 3-已发货 4-交易成功 5-交易关闭
		orderInfo.setStatus(1);
		Date date = new Date();
		orderInfo.setCreateTime(date);
		orderInfo.setUpdateTime(date);
		// 插入订单信息
		orderMapper.insert(orderInfo);

		// 获取订单详细信息列表，循环遍历设置详细信息
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem orderItem : orderItems) {
			// 生成订单详情id
			Long orderDetailId = jedisClient.incr(REDIS_ORDER_DETAIL_GEN_KEY);
			// 设置订单详情信息
			orderItem.setOrderId(orderId.toString());
			orderItem.setId(orderDetailId.toString());
			// 插入详细信息
			orderItemMapper.insert(orderItem);
		}

		// 获取订单物流信息
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		// 设置订单物流信息
		orderShipping.setOrderId(orderId.toString());
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		// 插入物流信息
		orderShippingMapper.insert(orderShipping);

		return FSResult.ok(orderId.toString());
	}

}
