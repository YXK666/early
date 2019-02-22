package com.sgb.servlet.dept;

import com.sgb.dao.DeptDao;
import com.sgb.entity.Department;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Servlet implementation class AddApplianceServlet
 */
@WebServlet("/addDeptServlet.do")
public class AddDeptServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 设置响应字符编码，类型，创建json对象
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		// 建立对象，获取和设置参数，对于和外键相连的字段判断是否有选择
		Department dept = new Department();
		String deptName = req.getParameter("deptName");
		dept.setDeptName(deptName);
		System.out.println(dept.toString());
		boolean b = false;
		try {
			b = DeptDao.addDepartment(dept);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//初始
		if(b) {
			json.put("success", true);
			json.put("msg", "添加成功");
		}else {
			json.put("success", false);
			json.put("msg", "添加失败");
		}
		out.print(json);
		out.flush();
		out.close();
		
	}

}
