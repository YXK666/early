package com.sgb.servlet.appliance;

import com.sgb.dao.ApplianceDao;
import com.sgb.dao.DeptDao;
import com.sgb.dao.PersonDao;
import com.sgb.entity.Appliance;
import com.sgb.entity.Department;
import com.sgb.entity.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class ToAddApplianceServlet
 */
@WebServlet("/toEditApplianceServlet.do")
public class ToEditApplianceServlet extends HttpServlet {
	

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id = req.getParameter("id");
		
		Appliance apl =ApplianceDao.getApplianceById(id);
		req.setAttribute("appliance",apl);//仪器
		
		List<Person> personList = PersonDao.getAllPerson(0,0,null);
		req.setAttribute("person", apl.getPersonUsed());//当前人
		req.setAttribute("personList", personList);//使用人集合
		
		List<Department> deptList = DeptDao.getAllDept(0, 0);
		req.setAttribute("dept", apl.getDepartmentUsed());//当前部门
		req.setAttribute("deptList", deptList);//使用部门集合
		
		req.getRequestDispatcher("/manage/editAppliance.jsp").forward(req,resp);
	}

}
