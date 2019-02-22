package com.sgb.servlet.instrument;

import com.sgb.dao.InstrumentDao;
import com.sgb.dao.PersonDao;
import com.sgb.entity.Instrument;
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
@WebServlet("/toEditInstrumentServlet.do")
public class ToCopyInstrumentServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id = req.getParameter("id");
		Instrument instrument = InstrumentDao.getInstrumentById(id);
		//给添加页面提供数据
		req.setAttribute("instrument", instrument);
		List<Person> personList = PersonDao.getAllPerson(0,0,null);	
		req.setAttribute("person", instrument.getMaintainMan());
		req.setAttribute("personList", personList);
		req.getRequestDispatcher("/manage/editInstrument.jsp").forward(req,resp);
	}

}
