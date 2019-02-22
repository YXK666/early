 package com.sgb.utils;

 import com.mchange.v2.c3p0.ComboPooledDataSource;

 import java.io.IOException;
 import java.lang.reflect.Field;
 import java.sql.*;
 import java.util.*;
 import java.util.logging.Logger;
/**
 * @deprecated连接数据库的工具类
 * @author YXK
 * 
 *
 */
public class DBUtil {
	/**
	 * 创建连接需要的参数，驱动，url，账号，密码
	 * @param sql 需要执行的sql语句
	 * @return
	 */

	private static String driverClass = "";//驱动
	private static String url = "";//地址
	private static String user = "";//用户名
	private static String password = "";//数据库密码
	private final static Logger logger = Logger.getLogger(DBUtil.class.getName());//日志
    private static ComboPooledDataSource cpds;//c3p0
 
	/**
	 * 在静态代码块中加载，获取参数
	 */
	static{
		Properties prop = new Properties();
		try {
			prop.load(DBUtil.class.getResourceAsStream("/jdbc.properties")); 
			driverClass = prop.getProperty("driverClass");
			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			Class.forName(driverClass);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @return 返回连接对象
	 */
	public static Connection getConnection(){
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 关闭操作
	 * @param rs 查询的结果集
	 * @param stat 查询语句对象
	 * @param conn 查询的连接对象
	 */
	public static void close(ResultSet rs,Statement stat,Connection conn){
		try {
			if(rs!=null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(stat!=null){
				stat.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	/**
	 * 统一的查询语句
	 * @param sql执行的sql预编译语句
	 * @param args可变参数
	 * @return 将结果返回到list集合
	 */
	public static List<Map<String, String>> query(String sql , Object...args){
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			if(args!=null&&args.length>0){
				for(int i = 0; i<args.length;i++){
					ps.setObject(i+1, args[i]);
				}
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			while(rs.next()){
				Map<String, String> rowsResult = new HashMap<String, String>();
				for(int i = 0 ; i<count ; i++){
					String columnLabel = rsmd.getColumnLabel(i+1);
					rowsResult.put(columnLabel, rs.getString(columnLabel));
				}
				result.add(rowsResult);
			}
			
		} catch (SQLException e){
			e.printStackTrace();
		}finally{
			close(rs,ps,conn);
		}
		return result;
	}
	
	/**
	 * 根据id查询指定记录
	 * @param sql 执行的sql语句
	 * @param row 指定id
	 * @return 返回记录结果
	 */
	public static Map<String,String> queryRow(String sql,int row){
		List<Map<String, String>> result = query(sql, row);
		if(result.size()==1){
			return result.get(0);
		}
		return null;
	}
	/**
	 * 根据唯一字段查询某一行是否存在，查询username是否存在
	 * @param sql 需要执行的sql语句
	 * @param row 指定唯一字段
	 * @return 返回是否包含该字段
	 */
	public static boolean queryExist(String sql,String row){
		List<Map<String, String>> result = query(sql, row);
		if(result.size()==1){
			return true;
		}
		return false;
	}
	/**
	 * 更新操作 根据sql语句
	 * @param sql sql语句
	 * @param args 传入的数据
	 * @return 返回受影响行数
	 */
	public static int update(String sql,Object...args){
		Connection conn = getConnection();
		PreparedStatement ps = null;
		int rows = 0;
		try {
				ps = conn.prepareStatement(sql);
				if(args!=null&&args.length>0){
					for(int i = 0; i<args.length;i++){
						System.out.println(args[i]);
						ps.setObject(i+1, args[i]);
					}
				}
				rows = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close(null,ps,conn);
		}
		return rows;
	}
	
	/**
	 * 添加操作 根据对象添加
	 * @param obj 需要添加到数据库对应的对象
	 * @param table 表名
	 * @return 返回受影响的行数
	 */
	public static int add(Object obj,String table) {
		Connection conn = getConnection();
		Class<?> cls = obj.getClass();
		Field [] fies = cls.getDeclaredFields();
		Statement st = null;
		StringBuilder sql = new StringBuilder("insert into "+table+"(");
		int rows = 0;
		try {
			st = conn.createStatement();
			for(Field f:fies){
				f.setAccessible(true);
				sql.append(f.getName()+",");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(") values (");
			for(Field f:fies){
				f.setAccessible(true);
					try {
						if(f.getType()==String.class){
							if(f.get(obj)!=null){
								sql.append("'"+f.get(obj).toString()+"',");
							}else{
								sql.append("null,");
							}
							
						}else{
							if(f.get(obj)!=null){
								sql.append(f.get(obj).toString()+",");
							}else{
								sql.append("null,");
							}
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(")");
			rows = st.executeUpdate(sql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close(null,st,conn);
		}
		return rows;
	}
	
	/**
	 * 删除操作，根据id删除
	 * @param id 需要的id
	 * @param table 表名
	 * @return 返回受影响的行数
	 */
	public static int delete(int id,String table) {
		Connection conn = getConnection();
		//Class<?> cls = obj.getClass();
		//Field [] fies = cls.getDeclaredFields();
		Statement st = null;
		StringBuilder sql = new StringBuilder("delete from "+table+"where id ="+id);
		int rows = 0;
		try {
			st = conn.createStatement();
			rows = st.executeUpdate(sql.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rows;
	}
	/**
	 * 获取添加记录的id
	 * @param obj 添加的对象
	 * @param table 表名
	 * @return 返回添加记录的id，如果添加失败返回-1
	 */
	public static int getAddId(Object obj,String table) {
		Connection conn = getConnection();
		Class<?> cls = obj.getClass();
		Field [] fies = cls.getDeclaredFields();
		Statement st = null;
		StringBuilder sql = new StringBuilder("insert into "+table+"(");
		int rows = 0;
		int id = -1;
		try {
			st = conn.createStatement();
			for(Field f:fies){
				f.setAccessible(true);
				sql.append(f.getName()+",");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(") values (");
			for(Field f:fies){
				f.setAccessible(true);
					try {
						if(f.getType()==String.class){
							if(f.get(obj)!=null){
								sql.append("'"+f.get(obj).toString()+"',");
							}else{
								sql.append("null,");
							}
							
						}else{
							if(f.get(obj)!=null){
								sql.append(f.get(obj).toString()+",");
							}else{
								sql.append("null,");
							}
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(")");
			rows = st.executeUpdate(sql.toString(),Statement.RETURN_GENERATED_KEYS);
			if(rows>0){
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()){
					id = rs.getInt(1);
				}
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close(null,st,conn);
		}
		return id;
	}
	
	
	/**
	 * 更新
	 * @param obj 需要添加的对象
	 * @param table 表名
	 * @return 受影响的行数
	 */
	public static int edit(Object obj,String table,Integer id){
		Connection conn = getConnection();
		Class cls = obj.getClass();
		Field [] fies = cls.getDeclaredFields();
		Statement st = null;
		StringBuilder sql = new StringBuilder("update  "+table+" set ");
		int row = 0;
		try {
			st = conn.createStatement();
			for(Field f:fies){
				f.setAccessible(true);
					try {
						if(f.getType()==String.class){
							if(f.get(obj)!=null){
								sql.append(f.getName()+"='"+f.get(obj)+"',");
							}else{
								sql.append(f.getName()+"=null"+",");
							}	
						}else{
								if(f.get(obj)!=null){
									sql.append(f.getName()+"="+f.get(obj)+",");
								}else{
									sql.append(f.getName()+"=null"+",");
								}	
						}
						
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(" where id = "+id);
			row = st.executeUpdate(sql.toString());			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close(null,st,conn);
		}
		return row;
	}	
}