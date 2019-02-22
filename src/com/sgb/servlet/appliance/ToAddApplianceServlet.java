package com.sgb.servlet.appliance;

import com.sgb.dao.DeptDao;
import com.sgb.dao.PersonDao;
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
@WebServlet("/toAddApplianceServlet.do")
public class ToAddApplianceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("");
		List<Department> deptList = DeptDao.getAllDept(0, 0);		
//		Appliance apl =ApplianceDao.getApplianceById(id);
//		req.setAttribute("appliance",apl);
		req.setAttribute("depts", deptList);
		List<Person> personList = PersonDao.getAllPerson(0,0,null);
		req.setAttribute("personList", personList);//使用人集合
		req.getRequestDispatcher("/manage/addAppliance.jsp").forward(req,
				resp);
	}

}
