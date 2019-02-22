package com.sgb.dao;

import com.sgb.entity.Department;
import com.sgb.utils.DBUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class DeptDao {

	private static DeptDao instance;

	private DeptDao() {
	}

	public static DeptDao getInstance() {
		if (instance == null) {
			instance = new DeptDao();
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
	public static List<Department> getAllDept(int pageNum, int pageSize) {
		ArrayList<Department> deptList = new ArrayList<Department>();
		List<Map<String, String>> query = null;
		if (pageNum == 0) {// 不分页
			query = DBUtil.query("select * from tb_department");
		} else {// 分页
			query = DBUtil.query("select * from tb_department limit ?,?", (pageNum - 1) * pageSize, pageSize);
		}
		for (Map<String, String> map : query) {
			Department department = new Department();
			try {
				BeanUtils.populate(department, map);// 工具类，把获取的值纳入对象中
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			deptList.add(department);// 将对象存入list集合中
		}
		System.out.println("DeptDao类getAllDept方法————可选部门共有数量为："+deptList.size());
		return deptList;
	}
	
	/**
	 * 通过id获取Department
	 * @param id
	 * @return Department对象
	 */
	public static Department getDepartmentById(String id) {
		Map<String, String> row = new HashMap<String, String>();
		if (id != null && id.length() > 0) {
			row = DBUtil.queryRow("select * from tb_department where deptid = ?", Integer.parseInt(id));
		}
		Department apl = new Department();
		try {
			BeanUtils.populate(apl, row);
			
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println("DeptDao类getDepartmentById方法————部门为："+apl.toString());
		return apl;
	}
	
	/**
	 * 查询所有部门的总量
	 * @return
	 * @throws SQLException
	 */
	public static int getTotalRows() throws SQLException  {
		List<Map<String,String>> query = DBUtil.query("select * from tb_department");	
		return query.size();
	}

	/**
	 * 添加部门
	 * @param dept
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean addDepartment(Department dept) throws SQLException {
		return DBUtil.add(dept, "tb_department")==1;
	}
}
