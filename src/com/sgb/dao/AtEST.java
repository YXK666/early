package com.sgb.dao;

import java.time.LocalDate;

/**
 * @deprecated测试类
 * @author Administrator
 */
public class AtEST {
	public static void main(String[] args) {
		LocalDate date = LocalDate.parse("2018-12-01");
		System.out.println(date);
		date.minusDays(20);
		System.out.println(date);
		/*String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 1001; i < 100000; i++) {
			int number = random.nextInt(5);
			int j = random.nextInt(10);
			sb.append(str.charAt(number));
			String s = sb.toString();
			String day = FormatDate.addDay("2018-12-10", i);
			String uid = UUID.randomUUID().toString().substring(0, 5) + i;
			// int id = Integer.parseInt(uid);
			String abc = "A";
			if (i >= 1000 && i < 10000) {
				abc = "B";
			} else if (i > 10000 && i < 100000) {
				abc = "C";
			}
			
			Instrument instrument = new Instrument(i,uid,uid, abc, j,"SHN48", "2018-11-11", 10, day, null, abc, 0);
			InstrumentDao.addInstrument(instrument);
			System.out.println(i+instrument.toString());
		}*/
		/*List<Map<String, String>> list = InstrumentDao.getSearchResult(1, 10, "", "", "");
			 Map<String, String> map = list.get(1);
			 for(Entry<String, String> entry : map.entrySet()) {
				 System.out.println("Key = " + entry.getKey() + ", Value = "+ entry.getValue());
			 }
			*/ 
		/*List<Map<String,String>> result = ApplianceDao.getCheckHistory(1, 100, 2018, 12,"9",null,null);
		for(Map<String, String> map:result) {
			for(Entry<String, String> entry:map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = "+ entry.getValue());
			}			
		}*/	
		//List<Map<String, String>> list = InstrumentDao.getThisMonthExpire(1,5,2018,12,null,null,null);
//		for(Map<String, String> map:list) {
//			for(Entry<String, String> key :map.entrySet()) {
//				System.out.println(key.getKey()+":"+key.getValue());
//			}
//		}
		/*int i = InstrumentDao.getThisMonthSearchRows(2018,12,null,null,null);
		System.out.println(i);*/
		}
		/*
		 * List<Map<String, String>> list = ApplianceDao.getThisMonthExpire(1, 10, 0, 0,
		 * null, null, null); String string = list.get(0).get("expireDate");
		 * System.out.println(string); Date date = new Date(); SimpleDateFormat sdf =
		 * new SimpleDateFormat("yyyy-MM-dd"); String d = sdf.format(date); String out =
		 * FormatDate.getTimeOut(d,string); System.out.println(out);
		 */
		/*
		 * List<Appliance> list = ApplianceDao.getAppliance(1, 10); for(Appliance
		 * a:list) { System.out.println(a.toString()); }
		 */
		/*
		 * for(Map<String, String> map :list) { for (Entry<String, String> entry :
		 * map.entrySet()) { System.out.println("Key = " + entry.getKey() + ", Value = "
		 * + entry.getValue()); }
		 */
		// 遍历map中的键
		/*
		 * for (String key : map.keySet()) { System.out.println("Key = " + key); }
		 * //遍历map中的值 for (String value : map.values()) { System.out.println("Value = "
		 * + value); }
		 */
		// Appliance appliance = ApplianceDao.getApplianceById("2");
		// System.out.println(appliance.toString());

	}

