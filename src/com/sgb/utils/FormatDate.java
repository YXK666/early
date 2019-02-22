package com.sgb.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormatDate {
	/**
	 * 将日期对象转化为字符串
	 * @param date 日期对象
	 * @return 日期对象对应的字符串
	 */
	public static String format(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	/**
	 * 取得两个字符串对应日期对象的间隔天数
	 * @param executeTime 执行时间
	 * @param recordDate 记录时间
	 * @return 返回间隔天数
	 */
	public static String getTimeOut(String executeTime,String recordDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long out  = 0;
		try {
			if(recordDate!=null&&executeTime!=null){
				out = sdf.parse(recordDate).getTime() - sdf.parse(executeTime).getTime();
				return String.valueOf((out/1000/60/60/24));
			}				
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 当前月份加上n月
	 * @param date yyyy-MM-dd格式字符串
	 * @param month 月份
	 * @return date字符串
	 */
	public static String addMonth(String date,int month) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化 模板
		Date d = null;//初始化
		try {  
            d = sdf.parse(date);//将字符串格式化 为日期
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        Calendar cl = Calendar.getInstance();  //创建日历对象
        cl.setTime(d);//设置时间
        cl.add(Calendar.MONTH, +month); //加月份 
        d = cl.getTime();  
		return sdf.format(d);		
	}
	/**
	 *  加减天数，默认是减，如果加天数则传入负数
	 * @param date yyyy-MM-dd格式字符串
	 * @param month 月份
	 * @return date字符串
	 */
	public static String minusDay(String date,int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化 模板
		Date d = null;//初始化
		try {  
            d = sdf.parse(date);//将字符串格式化 为日期
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        Calendar cl = Calendar.getInstance();  //创建日历对象
        cl.setTime(d);//设置时间
        cl.add(Calendar.DAY_OF_MONTH, -day); //加天数
        d = cl.getTime();  
		return sdf.format(d);		
	}
	/**
	 * 
	 * @param date字符串日期
	 * @return返回一个数组，下表0为年份，1为月份
	 */
	public static int[] getYearMonth(String date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date d = null;
		Calendar cl = Calendar.getInstance();  
		try {	
			d = sdf.parse(date);			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cl.setTime(d);
		int year=cl.get(Calendar.YEAR);
        int month=cl.get(Calendar.MONTH) + 1;
        int []  array= {year,month};
		return array;
		}
	
	/**
	 * @查询指定时间与系统时间的间隔天数
	 * @param recordDate指定时间String类型
	 * @return 间隔天数String类型
	 */
	public static String getDay(String recordDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String executeTime =sdf.format(new Date());
		long out  = 0;
		try {
			if(recordDate!=null&&executeTime!=null){
				out = sdf.parse(recordDate).getTime() - sdf.parse(executeTime).getTime();
				return String.valueOf((out/1000/60/60/24));
			}				
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String addMonth = minusDay("2018-10-1 18:00:00", 10);
		System.out.println(addMonth);
		int[] is = getYearMonth("2018-9");
		System.out.println(is[0]+"   "+is[1]);
	}
	
	}

