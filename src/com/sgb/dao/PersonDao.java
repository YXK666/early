package com.sgb.dao;

import com.sgb.entity.Person;
import com.sgb.utils.DBUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class PersonDao {

	private static PersonDao instance;

	private PersonDao() {
	}

	public static PersonDao getInstance() {
		if (instance == null) {
			instance = new PersonDao();
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
	public static List<Person> getAllPerson(int pageNum, int pageSize,String username) {
		String sql = "select * from tb_person ";
		boolean bName = username!=null && username.toString().length()>0;
		ArrayList<Person> pList = new ArrayList<Person>();
		List<Map<String, String>> query = null;
		if (pageNum == 0) {// 不分页
			query = DBUtil.query(" select * from tb_person  ");
		} else {// 分页
			if(bName) {//"and ApplianceName like '%" + ApplianceName + "%' "
				sql = sql + "  where username like '%"+username+"%'  limit ?,?  " ;
				query = DBUtil.query(sql, (pageNum - 1) * pageSize, pageSize);
			}else {
				query = DBUtil.query("select * from tb_person limit ?,?", (pageNum - 1) * pageSize, pageSize);
			}
			
		}
		for (Map<String, String> map : query) {
			Person person = new Person();
			try {
				BeanUtils.populate(person, map);// 工具类，把获取的值纳入对象中
				
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			pList.add(person);// 将对象存入list集合中
		}
		System.out.println("PersonDao类getAllPerson方法————可选添加共有数量为："+pList.size());
		return pList;
	}
	
	/**
	 * 查询所有部门的总量
	 * @return
	 * @throws SQLException
	 */
	public static int getTotalRows(String username) {
		String sql = "select * from tb_person  ";
		boolean bName = username!=null && username.toString().length()>0;
		List<Map<String,String>> query = null;
		if(bName) {
			sql = sql +  " where username like '%"+username+"%' " ;
			query = DBUtil.query(sql);
			return query.size();
		}
		query = DBUtil.query(sql);	
		return query.size();
	}
	
	
	/**
	 * 通过id获取人
	 * @param id
	 * @return Appliance对象
	 */
	public static Person getPersonById(String id) {
		Map<String, String> row = new HashMap<String, String>();
		if (id != null && id.length() > 0) {
			row = DBUtil.queryRow("select * from tb_person where id = ?", Integer.parseInt(id));
		}
		Person p = new Person();
		try {
			BeanUtils.populate(p, row);
			
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println("PersonDao类getPersonById方法——："+p.toString());
		return p;
	}
	

	/**
	 * 添加人
	 * @param person
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean addPerson(Person p) throws SQLException {
		return DBUtil.add(p, "tb_person")==1;
	}
}
