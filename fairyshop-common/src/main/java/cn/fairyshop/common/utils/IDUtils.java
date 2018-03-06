package cn.fairyshop.common.utils;

import java.util.Random;

public class IDUtils {
	
	public static String genImageName() {
		// 取当前时间，包含毫秒
		long millis= System.currentTimeMillis();
		
		// 生成三位长的随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		
		// 生成随机的图片名
		String str = millis + String.format("%03d", end3);
		
		return str;
	}

	/*
	 * 生成商品ID
	 */
	public static long genItemId() {
		// 取当前时间，包含毫秒
		long millis= System.currentTimeMillis();
		
		// 生成两位长的随机数
		Random random = new Random();
		int end2 = random.nextInt(99);
		
		// 生成随机的ID
		String str = millis + String.format("%02d", end2);
		long ID = new Long(str);
		
		return ID;
	}

}
