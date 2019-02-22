package com.sgb.servlet.instrument;

import com.sgb.dao.PersonDao;
import com.sgb.entity.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
/**
 * 添加PM的前端控制器
 * @author yxk
 *
 */
@WebServlet("/toAddInstrumentServlet.do")
public class ToAddInstrumentServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Person> personList = PersonDao.getAllPerson(0,0,null);
		
		req.setAttribute("personList", personList);//给添加页面提供数据
		req.getRequestDispatcher("/manage/addInstrument.jsp").forward(req,
				resp);
	}

}
