package com.wisedu.crowd.common.util;

import java.math.BigDecimal;


public class DecimalUtil {

	/**
	 * 0
	 */
	public static BigDecimal ZERO=new BigDecimal("0");
	/**
	 * 100
	 */
	public static BigDecimal ONE_HUNDRED=new BigDecimal("100");
	public static BigDecimal toDecimal(Object o){
		if(o==null){
			return new BigDecimal("0");
		}
		String b=StringUtil.toStr(o);
		if(StringUtil.isEmpty(b)){
			return new BigDecimal("0");
		}
		return new BigDecimal(b);
	}
	public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
		b1 = changeNull(b1);
		b2 = changeNull(b2);
		return b1.add(b2);
	}

	public static BigDecimal changeNull(BigDecimal b) {
		if (b == null) {
			b = new BigDecimal(Double.valueOf("0"));
		}
		return b;
	}

	// 进行加法运算
	public static double add(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.add(b2).doubleValue();
	}

	// 进行减法运算
	public static BigDecimal sub(BigDecimal b1, BigDecimal b2) {
		b1 = changeNull(b1);
		b2 = changeNull(b2);
		return b1.subtract(b2);
	}

	// 进行减法运算
	public static double sub(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.subtract(b2).doubleValue();
	}

	// 进行乘法运算
	public static BigDecimal mul(BigDecimal b1, BigDecimal b2) {
		b1 = changeNull(b1);
		b2 = changeNull(b2);
		return b1.multiply(b2);
	}

	// 进行乘法运算
	public static double mul(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.multiply(b2).doubleValue();
	}

	// 进行除法运算
	public static BigDecimal div(BigDecimal b1, BigDecimal b2) {
		return div(b1, b2, 4);
	}

	// 进行除法运算
	public static double div(double d1, double d2, int len) {
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		if(b2==null || DecimalUtil.ZERO.compareTo(b2)==0){
			return 0;
		}
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	// 进行除法运算
	public static BigDecimal div(BigDecimal b1, BigDecimal b2, int len) {
		b1 = changeNull(b1);
		b2 = changeNull(b2);
		if(b2==null || DecimalUtil.ZERO.compareTo(b2)==0){
			return DecimalUtil.ZERO;
		}
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP);
	}

	// 进行四舍五入操作
	public static BigDecimal round(BigDecimal b1) {
		return round(b1, 4);
	}

	// 进行四舍五入操作
	public static BigDecimal round(BigDecimal b1, int len) {
		BigDecimal b2 = new BigDecimal(1);
		// 任何一个数字除以1都是原数字
		// ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP);
	}

	// 进行四舍五入操作
	public static double round(Double d, int len) {
		if(d==null){
			return 0;
		}
		BigDecimal b1 = new BigDecimal(d);
		BigDecimal b2 = new BigDecimal(1);
		// 任何一个数字除以1都是原数字
		// ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	// 测试
	public static void main(String[] args) {
		System.out.print(DecimalUtil.div(new BigDecimal("20"), new BigDecimal("3")));

	}
}