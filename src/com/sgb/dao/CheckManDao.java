package com.sgb.dao;

import com.sgb.entity.CheckMan;
import com.sgb.utils.DBUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CheckManDao {

	private static CheckManDao instance;

	private CheckManDao() {
	}

	public static CheckManDao getInstance() {
		if (instance == null) {
			instance = new CheckManDao();
		}
		return instance;
	}

	/**
	 * @deprecated获取所有
	 * @param pageNum  页数
	 * @param pageSize 页面的记录数
	 * @return 所有的记录集合
	 * @return
	 */
	public static List<CheckMan> getAllCheckMan(int pageNum, int pageSize) {
		ArrayList<CheckMan> deptList = new ArrayList<CheckMan>();
		List<Map<String, String>> query = null;
		if (pageNum == 0) {// 不分页
			query = DBUtil.query("select * from tb_CheckMan");
		} else {// 分页
			query = DBUtil.query("select * from tb_CheckMan limit ?,?", (pageNum - 1) * pageSize, pageSize);
		}
		for (Map<String, String> map : query) {
			CheckMan department = new CheckMan();
			try {
				BeanUtils.populate(department, map);// 工具类，把获取的值纳入对象中
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			deptList.add(department);// 将对象存入list集合中
		}
		System.out.println("CheckMan类getAllCheckMan方法————可选CheckMan共有数量为："+deptList.size());
		return deptList;
	}
	
	/**
	 * 通过id获取CheckMan
	 * @param id
	 * @return CheckMan对象
	 */
	public static CheckMan getCheckManById(String id) {
		Map<String, String> row = new HashMap<String, String>();
		if (id != null && id.length() > 0) {
			row = DBUtil.queryRow("select * from tb_CheckMan where id = ?", Integer.parseInt(id));
		}
		CheckMan apl = new CheckMan();
		try {
			BeanUtils.populate(apl, row);
			
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println("DeptDao类getCheckManById方法————CheckMan为："+apl.toString());
		return apl;
	}
	
	/**
	 * 查询所有部门的总量
	 * @return
	 * @throws SQLException
	 */
	public static int getTotalRows() throws SQLException  {
		List<Map<String,String>> query = DBUtil.query("select * from tb_CheckMan");	
		return query.size();
	}

	/**
	 * 添加部门
	 * @param CheckMan
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean addCheckMan(CheckMan dept) throws SQLException {
		return DBUtil.add(dept, "tb_CheckMan")==1;
	}
}
