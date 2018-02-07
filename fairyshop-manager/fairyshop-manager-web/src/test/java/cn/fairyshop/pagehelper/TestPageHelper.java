package cn.fairyshop.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.fairyshop.mapper.TbItemMapper;
import cn.fairyshop.pojo.TbItem;
import cn.fairyshop.pojo.TbItemExample;

public class TestPageHelper {
	
	@Test
	public void testPageHelper() throws Exception {
		// 获取mapper代理对象
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*");
		TbItemMapper itemMapper = context.getBean(TbItemMapper.class);
		
		// 设置分页
		PageHelper.startPage(0, 20);
		
		// 执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> items = itemMapper.selectByExample(example);
		
		// 取分页后的结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(items);
		Long total = pageInfo.getTotal();
		System.out.println("totle:" + total);
		Integer size = pageInfo.getSize();
		System.out.println("size:" + size);
		int num = pageInfo.getPageNum();
		System.out.println("pageNum:" + num);
	}

}
