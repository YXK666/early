
package com.sgb.dao;

import com.sgb.entity.Appliance;
import com.sgb.utils.DBUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @deprecated这是器具的CRUD方法类
 * @author YINXIAOKAI
 *
 */
public class ApplianceDao {
	/*
	 * 单例模式，只初始化一次
	 */
	private static ApplianceDao instance;

	private ApplianceDao() {
	}

	public static ApplianceDao getInstance() {
		if (instance == null) {
			instance = new ApplianceDao();
		}
		return instance;
	}

	/**
	 * @deprecated分页获取所有的器具的方法
	 * @param pageNum  页数
	 * @param pageSize 页面的记录数
	 * @return 所有的记录集合
	 */
	public static List<Appliance> getAppliance(int pageNum, int pageSize) {
		ArrayList<Appliance> appliances = new ArrayList<Appliance>();// 创建一个泛型为<Appliance>对象的集合
		List<Map<String, String>> list = DBUtil.query("select * from tb_appliance where VerificationResult = '初始'  ORDER BY MeteringNumber limit ?,? ", (pageNum - 1) * pageSize,
				pageSize);// 调用工具类，返回List<Map<String, String>>
		for (Map<String, String> map : list) {// 遍历list集合，获取所有对象
			Appliance appliance = new Appliance();// 初始化对象
			try {
				BeanUtils.populate(appliance,map);// 工具类，把获取的值纳入对象中
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			appliances.add(appliance);// 将对象存入list集合中
		}
		System.out.println("分页获取所有器具");
		return appliances;
	}

	/**
	 * 条件查询所有器具
	 * @param pageNum 当前页数
	 * @param pageSize 页面记录数
	 * @param ApplianceName 计量名称
	 * @param MeteringNumber 器具编号
	 * @param QCNumber QC编号
	 * @return 返回List<Map<String, String>>
	 */
	public static List<Map<String, String>> getSearchResult(int pageNum, int pageSize, String ApplianceName, String MeteringNumber,String QCNumber) {
		boolean bName = ApplianceName != null && ApplianceName.toString().length() > 0;
		boolean bNum = MeteringNumber != null && MeteringNumber.toString().length() > 0;
		boolean bQCNumber = QCNumber != null && QCNumber.toString().length() > 0;

		String sql = "select * from tb_appliance where VerificationResult = '初始' ";
	
		if (bName) {
			sql = sql + "  and ApplianceName like '%" + ApplianceName + "%' ";
		}
		if (bNum) {
			sql = sql + " and MeteringNumber like '%" + MeteringNumber + "%' ";
		}
		if (bQCNumber) {
			sql = sql + " and QCNumber like '%" + QCNumber + "%' ";
		}

		sql = sql + "ORDER BY MeteringNumber limit ?,?";
		System.out.println("模糊查询"+sql);
		List<Map<String, String>> list = DBUtil.query(sql, (pageNum - 1) * pageSize, pageSize);
		return list;
	}
	
	/**
	 * @deprecated获取所有的记录总数，用于分页
	 * @param ApplianceName 计量名称
	 * @param MeteringNumber 器具编号
	 * @param QCNumber QC编号
	 * @return 记录总数
	 */
	public static int getSearchRows(String ApplianceName, String MeteringNumber,String QCNumber) {
		boolean bApplianceName = ApplianceName != null && ApplianceName.toString().length() > 0;
		boolean bMeteringNumber = MeteringNumber != null && MeteringNumber.toString().length() > 0;
		boolean bQCNumber = QCNumber != null && QCNumber.toString().length() > 0;

		String sql = "select * from tb_appliance where VerificationResult = '初始' ";
	
		if (bApplianceName) {
			sql = sql + "and ApplianceName like '%" + ApplianceName + "%' ";
		}
		if (bMeteringNumber) {
			sql = sql + " and MeteringNumber like '%" + MeteringNumber + "%' ";
		}
		if (bQCNumber) {
			sql = sql + " and QCNumber like '%" + QCNumber + "%' ";
		}
		List<Map<String, String>> list = DBUtil.query(sql);
		return list.size();
		
	}
	
	/**
	 * 添加器具
	 * @param Appliance 器具对象
	 * @return 添加是否成功
	 */
	public static boolean addAppliance(Appliance apl) {
		int row = DBUtil.add(apl, "tb_appliance");
		return row == 1;
	}
	
	/**
	 * 通过id获取器具
	 * @param id
	 * @return Appliance对象
	 */
	public static Appliance getApplianceById(String id) {
		Map<String, String> row = new HashMap<String, String>();
		if (id != null && id.length() > 0) {
			row = DBUtil.queryRow("select * from tb_appliance where id = ?", Integer.parseInt(id));
		}
		Appliance apl = new Appliance();
		try {
			BeanUtils.populate(apl, row);
			
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println("ApplianceDao类：通过id获取器具"+id);
		return apl;
	}
	
	/**
	 * 检查器具
	 * 
	 * @param Appliance 传入参数为创建的器具对象
	 * @param id    执行id
	 * @return 是否更新成功
	 */
	public static boolean updatePayer(Appliance apl, Integer id) {
		int rows = DBUtil.edit(apl, "tb_appliance", id);
		return rows == 1;
	}
	
	/**
	 * 根据MeteringNumber获取精度趋势
	 * @param MeteringNumber 器具编号
	 * @return List<Map<String, String>>
	 */
	public static List<Map<String, String>> getInfo( String meteringNumber) {		
		String sql = "SELECT expireDate , errorAccuracy FROM tb_appliance WHERE verificationResult != '初始'   ";		
		sql = sql+ " AND meteringNumber = " + meteringNumber +" ORDER BY expireDate ASC";
		System.out.println("根据MeteringNumber获取精度趋势"+sql);
		List<Map<String, String>> list = DBUtil.query(sql);
		return list;		
	}
	
	/**
	 *  To 获取某年某月的检查
	 * @param year 年
	 * @param month 月
	 * @param ApplianceName 计量名称
	 * @param MeteringNumber 计量编号
	 * @param QCNumber 器具编号
	 * @return List<Map<String,String>>
	 */
	public static List<Map<String,String>> getThisMonthExpire(int pageNum, int pageSize,int year,int month,String MeteringNumber,String QCNumber,String ApplianceName){
		boolean bApplianceName = ApplianceName != null && ApplianceName.toString().length() > 0;
		boolean bMeteringNumber = MeteringNumber != null && MeteringNumber.toString().length() > 0;
		boolean bQCNumber = QCNumber != null && QCNumber.toString().length() > 0;
		boolean bYear = year!=0;
		boolean bMonth =month!=0;
		Calendar now = Calendar.getInstance();
		String sql = "select * from tb_appliance where VerificationResult = '待查'  ";
		
		if (bYear) {
			sql = sql + " and YEAR(expireDate) = " + year;
		}else {
			sql = sql + " and YEAR(expireDate) = " + now.get(Calendar.YEAR);
		}
		if (bMonth) {
			sql = sql + " and MONTH(expireDate) = " + month;
		}else{
			sql = sql + " and MONTH(expireDate) = " + (now.get(Calendar.MONTH)+1)+ "  ";
		}	
		if (bMeteringNumber) {
			sql = sql + " and MeteringNumber like '%" + MeteringNumber + "%' ";
		}
		if (bQCNumber) {
			sql = sql + " and QCNumber like '%"+QCNumber +"%' ";
		}
		if (bApplianceName) {
			sql = sql + " and ApplianceName like '%"+ApplianceName +"%' ";
		}
		sql = sql + " ORDER BY ExpireDate ASC limit ?,?";
		System.out.println("getThisMonthExpire获取某年某月的检查"+sql);
		List<Map<String,String>> list = DBUtil.query(sql,(pageNum - 1) * pageSize, pageSize);
		return list;		
	}
	
	/**
	 *  通过meteringNumber获取等待检查Appliance
	 * @param meteringNumber
	 * @return Appliance
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Appliance getThisMonthExpire(String meteringNumber) {
		Boolean b = meteringNumber != null && meteringNumber.length()>0;
		String sql = "select * from tb_appliance where VerificationResult = '待查'  ";
		System.out.println("ApplianceDao类:通过meteringNumber获取 等待检查的Appliance");
		if(b) {
			sql = sql + " and meteringNumber =" + meteringNumber;
			List<Map<String, String>> query = DBUtil.query(sql);
			if(query.isEmpty()) {
				return null;
			}else {
			Appliance appliance = new Appliance();// 初始化对象
			try {
				BeanUtils.populate(appliance,query.get(0));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return appliance;
			}
		}
		return null;
		
	}
	/**
	 * @deprecated获取所有的记录总数，用于分页
	 * @param ApplianceName 计量名称
	 * @param MeteringNumber 器具编号
	 * @param QCNumber QC编号
	 * @return 记录总数
	 */
	public static int getThisMonthSearchRows(int year,int month, String MeteringNumber,String QCNumber,String ApplianceName) {
		boolean bApplianceName = ApplianceName != null && ApplianceName.toString().length() > 0;
		boolean bMeteringNumber = MeteringNumber != null && MeteringNumber.toString().length() > 0;
		boolean bQCNumber = QCNumber != null && QCNumber.toString().length() > 0;

		boolean bYear = year != 0;
		boolean bMonth = month != 0;
		Calendar now = Calendar.getInstance();
		String sql = "select * from tb_appliance where VerificationResult = '待查'  ";
		
		if (bYear) {
			sql = sql + " and YEAR(expireDate) = " + year;
		}else {
			
			sql = sql + " and YEAR(expireDate) = " + now.get(Calendar.YEAR);
		}
		if (bMonth) {
			sql = sql + " and MONTH(expireDate) = " + month;
		}else{
			sql = sql + " and MONTH(expireDate) = " + (now.get(Calendar.MONTH)+1)+ "  ";
		}	
		if (bMeteringNumber) {
			sql = sql + " and meteringNumber like '%" + MeteringNumber + "%' ";
		}
		if (bQCNumber) {
			sql = sql + " and qcNumber like '%" + QCNumber + "%' ";
		}
		if (bApplianceName) {
			sql = sql + " and applianceName like '%" + ApplianceName + "%' ";
		}
		List<Map<String, String>> list = DBUtil.query(sql);
		System.out.println("ApplianceDao类getThisMonthSearchRows方法 list大小"+list.size());
		return list.size();	
	}
	/**
	 *  To 获取某年某月的检查记录
	 * @param year 年
	 * @param month 月
	 * @param ApplianceName 计量名称
	 * @param MeteringNumber 计量编号
	 * @param QCNumber 器具编号
	 * @return List<Map<String,String>>
	 */
	public static List<Map<String,String>> getCheckHistory(int pageNum, int pageSize,int year,int month,String MeteringNumber,String QCNumber,String ApplianceName){
		boolean bApplianceName = ApplianceName != null && ApplianceName.toString().length() > 0;
		boolean bMeteringNumber = MeteringNumber != null && MeteringNumber.toString().length() > 0;
		boolean bQCNumber = QCNumber != null && QCNumber.toString().length() > 0;
		boolean bYear = year!=0;
		boolean bMonth =month!=0;
		
		String sql = "select * from tb_appliance where VerificationResult != '初始'   ";
		
		if (bYear) {
			sql = sql + " and YEAR(expireDate) = " + year;
		}
		if (bMonth) {
			sql = sql + " and MONTH(expireDate) = " + month;
		}	
		if (bMeteringNumber) {
			sql = sql + " and MeteringNumber like '%" + MeteringNumber + "%' ";
		}
		if (bQCNumber) {
			sql = sql + " and QCNumber like '%"+QCNumber +"%' ";
		}
		if (bApplianceName) {
			sql = sql + " and ApplianceName like '%"+ApplianceName +"%' ";
		}
		sql = sql + " ORDER BY ExpireDate ASC limit ?,?";
		System.out.println("获取某年某月的检查"+sql);
		List<Map<String,String>> list = DBUtil.query(sql,(pageNum - 1) * pageSize, pageSize);
		return list;		
	}
	/**
	 * 根据编号查询历史
	 * @param meteringNumber
	 * @return
	 */
	public static List<Appliance> getCheckHistory(String meteringNumber){
		boolean bMeteringNumber = meteringNumber != null && meteringNumber.toString().length() > 0;

		String sql = "select * from tb_appliance where VerificationResult != '初始'  ";
		if (bMeteringNumber) {
			sql = sql + " and meteringNumber =" + meteringNumber ;
		}
		ArrayList<Appliance> appliances = new ArrayList<Appliance>();
		List<Map<String, String>> list = DBUtil.query(sql);
		for (Map<String, String> map : list) {// 遍历list集合，获取所有对象
			Appliance appliance = new Appliance();// 初始化对象
			try {
				BeanUtils.populate(appliance,map);// 工具类，把获取的值纳入对象中
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			appliances.add(appliance);// 将对象存入list集合中
		}
		System.out.println("ApplianceDao类getCheckHistory方法:取所有器具"+sql);
		return appliances;
		
	}
	/**
	 * @deprecated获取所有的记录总数，用于分页
	 * @param ApplianceName 计量名称
	 * @param MeteringNumber 器具编号
	 * @param QCNumber QC编号
	 * @return 记录总数
	 */
	public static int getCheckHistoryRows(int year,int month, String MeteringNumber,String QCNumber,String ApplianceName) {
		boolean bApplianceName = ApplianceName != null && ApplianceName.toString().length() > 0;
		boolean bMeteringNumber = MeteringNumber != null && MeteringNumber.toString().length() > 0;
		boolean bQCNumber = QCNumber != null && QCNumber.toString().length() > 0;

		boolean bYear = year != 0;
		boolean bMonth = month != 0;

		String sql = "select * from tb_appliance where VerificationResult != '初始'  ";
		
		if (bYear) {
			sql = sql + " and YEAR(expireDate) = " + year;
		}
		if (bMonth) {
			sql = sql + " and MONTH(expireDate) = " + month;
		}	
		if (bApplianceName) {
			sql = sql + "and ApplianceName like '%" + ApplianceName + "%' ";
		}
		if (bMeteringNumber) {
			sql = sql + " and MeteringNumber like '%" + MeteringNumber + "%' ";
		}
		if (bQCNumber) {
			sql = sql + " and QCNumber like '%" + QCNumber + "%' ";
		}
		List<Map<String, String>> list = DBUtil.query(sql);
		return list.size();	
	}

	/**
	 * 打印某月的检查计划
	 * @param year
	 * @param month
	 * @return List<Appliance>
	 */
	public static List<Appliance> getAllApplianceByExpireDate(int year,int month) {
		boolean bYear = year!=0;
		boolean bMonth =month!=0;
		Calendar now = Calendar.getInstance();
		String sql = "select * from tb_appliance where VerificationResult ='待查' ";
		if (bYear) {
			sql = sql + " and YEAR(expireDate) = " + year;
		}else {
			
			sql = sql + " and YEAR(expireDate) = " + now.get(Calendar.YEAR);
		}
		if (bMonth) {
			sql = sql + " and MONTH(expireDate) = " + month;
		}else{
			sql = sql + " and MONTH(expireDate) = " + (now.get(Calendar.MONTH)+1)+ "  ";
		}
		ArrayList<Appliance> appliances = new ArrayList<Appliance>();
		List<Map<String, String>> list = DBUtil.query(sql);
		for (Map<String, String> map : list) {// 遍历list集合，获取所有对象
			Appliance appliance = new Appliance();// 初始化对象
			try {
				BeanUtils.populate(appliance,map);// 工具类，把获取的值纳入对象中
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			appliances.add(appliance);// 将对象存入list集合中
		}
		System.out.println("ApplianceDao类getAllApplianceByExpireDate方法:取所有器具打印EXCEL"+sql);
		return appliances;
	}
	
	/**
	 * 根据id删除器具的方法
	 * @param id
	 * @return boolean
	 */
	public static boolean deleteById(int id) {
		return DBUtil.delete(id, "tb_appliance")==1;
	}
	
	public static Appliance getApplianceByMeteringNumber(String meteringNumber,String result) {
		System.out.println("ApplianceDao类getApplianceByMeteringNumber方法"+"meteringNumber="+meteringNumber+"状态="+result);
		String sql = "select * from tb_appliance where MeteringNumber =  '"+meteringNumber +"'   and  VerificationResult = '"+result+"'";
		List<Map<String, String>> query = DBUtil.query(sql);
		if(query.isEmpty()) {
			return null;
		}else {
		Appliance appliance = new Appliance();// 初始化对象
		try {
			BeanUtils.populate(appliance,query.get(0));
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return appliance;
		}
	}
	
	
	}
	
	
	/*public static void main(String[] args) {
		List<Appliance> list = getAllApplianceByExpireDate(2018,12);
		int length = list.size();
		for(int i =0;i<length;i++) {
			System.out.println(list.get(i).toString());
		}
	}*/

