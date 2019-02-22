package com.sgb.servlet.appliance;

import com.sgb.dao.ApplianceDao;
import com.sgb.dao.CheckManDao;
import com.sgb.dao.DeptDao;
import com.sgb.entity.Appliance;
import com.sgb.entity.CheckMan;
import com.sgb.entity.Department;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/toCheckApplianceServlet.do")
public class ToCheckApplianceServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		Appliance apl =ApplianceDao.getApplianceById(id);
		req.setAttribute("appliance",apl);//仪器
		List<Department> deptList = DeptDao.getAllDept(0, 0);
		List<CheckMan> checkMan = CheckManDao.getAllCheckMan(0, 0);
		req.setAttribute("depts", deptList);//使用部门集合	
		req.setAttribute("checkMan", checkMan);//检查集合
		req.getRequestDispatcher("/manage/checkAppliance.jsp").forward(req,resp);
	}
}
