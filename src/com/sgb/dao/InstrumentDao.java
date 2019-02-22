package com.sgb.dao;

import com.sgb.entity.Instrument;
import com.sgb.utils.DBUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @deprecated这是PM的CRUD方法类
 * @author YINXIAOKAI
 *
 */
public class InstrumentDao {
	/*
	 * 单例模式，只初始化一次
	 */
	private static InstrumentDao instance;

	private InstrumentDao() {
	}

	public static InstrumentDao getInstance() {
		if (instance == null) {
			instance = new InstrumentDao();
		}
		return instance;
	}

	/**
	 * （分页）获取所有器具的方法
	 * @param pageNum 页数
	 * @param pageSize 页面记录数
	 * @return List<Instrument>
	 */
	public static List<Instrument> getInstrument(int pageNum, int pageSize) {
		ArrayList<Instrument> appliances = new ArrayList<Instrument>();// 创建一个泛型为<Appliance>对象的集合
		List<Map<String, String>> list = DBUtil.query("select * from tb_pm where status = 0 ", (pageNum - 1) * pageSize,
				pageSize);// 调用工具类，返回List<Map<String, String>>
		for (Map<String, String> map : list) {// 遍历list集合，获取所有对象
			Instrument instrument = new Instrument();// 初始化对象
			try {
				BeanUtils.populate(instrument,map);// 工具类，把获取的值纳入对象中
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			appliances.add(instrument);// 将对象存入list集合中
		}
		System.out.println("InstrumentDao：分页获取所有器具方法getInstrument执行了");
		return appliances;
	}

	/**
	 *      分页查询所有仪器的方法
	 * @param pageNum 当前页数
	 * @param pageSize 页面记录数
	 * @param instrumentName 名称
	 * @param instrumentNumber 器具编号
	 * @param QCNumber QC编号
	 * @return 返回List<Map<String, String>>
	 */
	public static List<Map<String, String>> getSearchResult(int pageNum, int pageSize, String instrumentName, String instrumentNumber,String QCNumber) {
		boolean bName = instrumentName != null && instrumentName.toString().length() > 0;
		boolean bNum = instrumentNumber != null && instrumentNumber.toString().length() > 0;
		boolean bQCNumber = QCNumber != null && QCNumber.toString().length() > 0;

		String sql = "select * from tb_pm where status = 0 " ;
	
		if (bName) {
			sql = sql + " and instrumentName like '%" + instrumentName + "%' ";
		}
		if (bNum) {
			sql = sql + " and instrumentNumber like '%" + instrumentNumber + "%' ";
		}
		if (bQCNumber) {
			sql = sql + " and QCNumber like '%" + QCNumber + "%' ";
		}

		sql = sql + " limit ?,?";
		System.out.println("InstrumentDao分页查询所有器具方法getSearchResult执行了"+sql);
		List<Map<String, String>> list = DBUtil.query(sql, (pageNum - 1) * pageSize, pageSize);
		return list;
	}
	
	/**
	 *  获取所有的记录总数，用于分页
	 * @param instrumentName 计量名称
	 * @param instrumentNumber 器具编号
	 * @param QCNumber QC编号
	 * @return 记录总数
	 */
	public static int getSearchRows(String instrumentName, String instrumentNumber,String QCNumber) {
		boolean bApplianceName = instrumentName != null && instrumentName.toString().length() > 0;
		boolean bMeteringNumber = instrumentNumber != null && instrumentNumber.toString().length() > 0;
		boolean bQCNumber = QCNumber != null && QCNumber.toString().length() > 0;

		String sql = "select * from tb_pm where status = 0 ";
	
		if (bApplianceName) {
			sql = sql + " and instrumentName like '%" + instrumentName + "%' ";
		}
		if (bMeteringNumber) {
			sql = sql + " and instrumentNumber like '%" + instrumentNumber + "%' ";
		}
		if (bQCNumber) {
			sql = sql + " and QCNumber like '%" + QCNumber + "%' ";
		}
		List<Map<String, String>> list = DBUtil.query(sql);
		return list.size();
		
	}
	
	/**
	 *  添加器具
	 * @param instrument对象
	 * @return 添加是否成功
	 */
	public static boolean addInstrument(Instrument itm) {
		int row = DBUtil.add(itm, "tb_pm");
		return row == 1;
	}
	
	/**
	 *  通过id获取器具
	 * @param id
	 * @return Appliance对象
	 */
	public static Instrument getInstrumentById(String id) {
		Map<String, String> row = new HashMap<String, String>();
		if (id != null && id.length() > 0) {
			row = DBUtil.queryRow("select * from tb_pm where id = ?", Integer.parseInt(id));
		}
		Instrument ins = new Instrument();
		try {
			BeanUtils.populate(ins, row);
			
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println("InstrumentDao类：通过id获取器具,id="+id);
		return ins;
	}
	
	/**
	 * 	检查器具
	 * @param Appliance 传入参数为创建的器具对象
	 * @param id    执行id
	 * @return 是否更新成功
	 */
	public static boolean updateInstrument(Instrument itm, Integer id) {
		int rows = DBUtil.edit(itm, "tb_pm", id);
		return rows == 1;
	}
	
	/**
	 *  	分页查询维护计划的方法
	 * @param pageNum 当前页面
	 * @param pageSize	当前页面记录数
	 * @param year	当前年份
	 * @param month	当前月份
	 * @param instrumentNumber 仪器编号
	 * @param QCNumber qcnumber
	 * @param instrumentName 仪器名称
	 * @return List<Map<String,String>>
	 */
	public static List<Map<String,String>> getThisMonthExpire(int pageNum, int pageSize,int year,int month,String instrumentNumber,String QCNumber,String instrumentName){
		boolean bInstrumentName = instrumentName != null && instrumentName.toString().length() > 0;
		boolean bInstrumentNumber = instrumentNumber != null && instrumentNumber.toString().length() > 0;
		boolean bQCNumber = QCNumber != null && QCNumber.toString().length() > 0;
		boolean bYear = year!=0;
		boolean bMonth =month!=0;
		Calendar now = Calendar.getInstance();
		String sql = " select * from tb_pm where status = 1  ";
		
		if (bYear) {
			sql = sql + " and YEAR(planTime) = " + year;
		}else {
			sql = sql + " and YEAR(planTime) = " + now.get(Calendar.YEAR);
		}
		if (bMonth) {
			sql = sql + " and MONTH(planTime) = " + month;
		}else{
			sql = sql + " and MONTH(planTime) = " + (now.get(Calendar.MONTH)+1)+ "  ";
		}	
		if (bInstrumentNumber) {
			sql = sql + " and instrumentNumber like '%" + instrumentNumber + "%' ";
		}
		if (bQCNumber) {
			sql = sql + " and QCNumber like '%"+QCNumber +"%' ";
		}
		if (bInstrumentName) {
			sql = sql + " and instrumentName like '%"+instrumentName +"%' ";
		}
		sql = sql + " ORDER BY planTime ASC limit ?,?";
		System.out.println("InstrumentDao类getThisMonthExpire获取某年某月的检查："+sql);
		List<Map<String,String>> list = DBUtil.query(sql,(pageNum - 1) * pageSize, pageSize);
		return list;		
	}
	/**
	 *   查询当前日期总记录数的方法
	 * @param year 当前年份
	 * @param month 当前月份
	 * @param instrumentNumber 仪器编号
	 * @param QCNumber qcNumber
	 * @param instrumentName 仪器名称
	 * @return int 总记录数
	 */
	public static int getThisMonthSearchRows(int year,int month,String instrumentNumber,String QCNumber,String instrumentName) {
		boolean bInstrumentName = instrumentName != null && instrumentName.toString().length() > 0;
		boolean bInstrumentNumber = instrumentNumber != null && instrumentNumber.toString().length() > 0;
		boolean bQCNumber = QCNumber != null && QCNumber.toString().length() > 0;

		boolean bYear = year != 0;
		boolean bMonth = month != 0;
		Calendar now = Calendar.getInstance();
		String sql = "select * from tb_pm where status = 1   ";
		
		if (bYear) {
			sql = sql + " and YEAR(planTime) = " + year;
		}else {
			sql = sql + " and YEAR(planTime) = " + now.get(Calendar.YEAR);
		}
		if (bMonth) {
			sql = sql + " and MONTH(planTime) = " + month;
		}else{
			sql = sql + " and MONTH(planTime) = " + (now.get(Calendar.MONTH)+1)+ "  ";
		}	
		if (bInstrumentNumber) {
			sql = sql + " and instrumentNumber like '%" + instrumentNumber + "%' ";
		}
		if (bQCNumber) {
			sql = sql + " and QCNumber like '%"+QCNumber +"%' ";
		}
		if (bInstrumentName) {
			sql = sql + " and instrumentName like '%"+instrumentName +"%' ";
		}
		sql = sql + " ORDER BY planTime ASC ";
	
		List<Map<String, String>> list = DBUtil.query(sql);
		return list.size();	
	}
	/**
	 *   分页获取指定日期的历史检查记录
	 * @param pageNum 当前页面
	 * @param pageSize	当前页面记录数
	 * @param year	年份
	 * @param month 月份
	 * @param instrumentNumber 仪器编号
	 * @param QCNumber QCNumber
	 * @param instrumentName 仪器名称
	 * @return List<Map<String,String>>
	 */
	public static List<Map<String,String>> getCheckHistory(int pageNum, int pageSize,int year,int month,String instrumentNumber,String QCNumber,String instrumentName){
		boolean bInstrumentName = instrumentName != null && instrumentName.toString().length() > 0;
		boolean bInstrumentNumber = instrumentNumber != null && instrumentNumber.toString().length() > 0;
		boolean bQCNumber = QCNumber != null && QCNumber.toString().length() > 0;
		boolean bYear = year!=0;
		boolean bMonth =month!=0;
		Calendar now = Calendar.getInstance();
		String sql = "select * from tb_pm where status = 2 ";
		
		if (bYear) {
			sql = sql + " and YEAR(finishTime) = " + year;
		}else {
			sql = sql + " and YEAR(finishTime) = " + now.get(Calendar.YEAR);
		}
		if (bMonth) {
			sql = sql + " and MONTH(finishTime) = " + month;
		}else{
			sql = sql + " and MONTH(finishTime) = " + (now.get(Calendar.MONTH)+1)+ "  ";
		}	
		if (bInstrumentNumber) {
			sql = sql + " and instrumentNumber like '%" + instrumentNumber + "%' ";
		}
		if (bQCNumber) {
			sql = sql + " and QCNumber like '%"+QCNumber +"%' ";
		}
		if (bInstrumentName) {
			sql = sql + " and instrumentName like '%"+instrumentName +"%' ";
		}
		sql = sql + " ORDER BY finishTime ASC limit ?,?";
		System.out.println("获取某年某月的检查："+sql);
		List<Map<String,String>> list = DBUtil.query(sql,(pageNum - 1) * pageSize, pageSize);
		return list;		
	}
	/**
	 *   获取历史记录数
	 * @param year 年份
	 * @param month 月份
	 * @param instrumentNumber 仪器编号
	 * @param QCNumber
	 * @param instrumentName 仪器名称
	 * @return
	 */
	public static int getCheckHistoryRows(int year,int month,String instrumentNumber,String QCNumber,String instrumentName) {
		boolean bInstrumentName = instrumentName != null && instrumentName.toString().length() > 0;
		boolean bInstrumentNumber = instrumentNumber != null && instrumentNumber.toString().length() > 0;
		boolean bQCNumber = QCNumber != null && QCNumber.toString().length() > 0;

		boolean bYear = year != 0;
		boolean bMonth = month != 0;
		Calendar now = Calendar.getInstance();
		String sql = "select * from tb_pm where status = 2   ";
		
		if (bYear) {
			sql = sql + " and YEAR(finishTime) = " + year;
		}else {
			sql = sql + " and YEAR(finishTime) = " + now.get(Calendar.YEAR);
		}
		if (bMonth) {
			sql = sql + " and MONTH(finishTime) = " + month;
		}else{
			sql = sql + " and MONTH(finishTime) = " + (now.get(Calendar.MONTH)+1)+ "  ";
		}	
		if (bInstrumentNumber) {
			sql = sql + " and instrumentNumber like '%" + instrumentNumber + "%' ";
		}
		if (bQCNumber) {
			sql = sql + " and QCNumber like '%"+QCNumber +"%' ";
		}
		if (bInstrumentName) {
			sql = sql + " and instrumentName like '%"+instrumentName +"%' ";
		}
		sql = sql + " ORDER BY finishTime ASC ";
	
		List<Map<String, String>> list = DBUtil.query(sql);
		return list.size();	
	}
	/**
	 * 根据编号查询历史
	 * @param meteringNumber
	 * @return
	 */
	public static List<Instrument> getCheckHistory(String instrumentNumber){
		boolean bInstrumentNumber = instrumentNumber != null && instrumentNumber.toString().length() > 0;

		String sql = "select * from tb_PM where status = 2 ";
		if (bInstrumentNumber) {
			sql = sql + " and instrumentNumber =" + instrumentNumber ;
		}
		ArrayList<Instrument> instruments = new ArrayList<Instrument>();
		List<Map<String, String>> list = DBUtil.query(sql);
		for (Map<String, String> map : list) {// 遍历list集合，获取所有对象
			Instrument instrument = new Instrument();// 初始化对象
			try {
				BeanUtils.populate(instrument,map);// 工具类，把获取的值纳入对象中
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			instruments.add(instrument);// 将对象存入list集合中
		}
		System.out.println("InstrumentDao类getCheckHistory方法:取所有仪器"+sql);
		return instruments;
		
	}
	/**
	 * 打印某月的检查计划
	 * @param year
	 * @param month
	 * @return List<Appliance>
	 */
	public static List<Instrument> getAllInstrumentByExpireDate(int year,int month) {
		boolean bYear = year!=0;
		boolean bMonth =month!=0;
		Calendar now = Calendar.getInstance();
		String sql = "select * from tb_pm where status = 1 ";
		if (bYear) {
			sql = sql + " and YEAR(planTime) = " + year;
		}else {
			
			sql = sql + " and YEAR(planTime) = " + now.get(Calendar.YEAR);
		}
		if (bMonth) {
			sql = sql + " and MONTH(planTime) = " + month;
		}else{
			sql = sql + " and MONTH(planTime) = " + (now.get(Calendar.MONTH)+1)+ "  ";
		}
		ArrayList<Instrument> inss = new ArrayList<Instrument>();
		List<Map<String, String>> list = DBUtil.query(sql);
		for (Map<String, String> map : list) {// 遍历list集合，获取所有对象
			Instrument ins = new Instrument();// 初始化对象
			try {
				BeanUtils.populate(ins,map);// 工具类，把获取的值纳入对象中
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			inss.add(ins);// 将对象存入list集合中
		}
		System.out.println("InstrumentDao类getAllInstrumentByExpireDate方法:取所有器具打印EXCEL"+sql);
		return inss;
	}

	/**
	 *     A根据状态和号码查询仪器
	 * @param instrumentNumber
	 * @param status
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Instrument getInstrumentByInstrumentNumber(String instrumentNumber, int status){
		String sql = "Select * from tb_pm where instrumentNumber ='"+instrumentNumber+"' and status ="+status;
		System.out.println("InstrumentDao类getInstrumentByInstrumentNumber"+sql);
		List<Map<String, String>> query = DBUtil.query(sql);		
		if(!query.isEmpty()) {
			Instrument instrument = null;
			try {
				instrument = new Instrument();
				BeanUtils.populate(instrument, query.get(0));
			} catch (IllegalAccessException e) {
				System.out.println("查不到"+instrumentNumber+"状态"+status);
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				System.out.println("查不到"+instrumentNumber+"状态"+status);
				e.printStackTrace();
			}
			return instrument;
		}
		return null;
	}
}
