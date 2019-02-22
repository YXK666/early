package com.sgb.servlet.instrument;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class toGetApplianceInfoServlet
 */
@WebServlet("/toGetInstrumentHistoryServlet.do")
public class ToGetInstrumentHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String instrumentNumber =req.getParameter("instrumentNumber");
		this.getServletContext().setAttribute("instrumentNumber",instrumentNumber);
		req.getRequestDispatcher("/manage/listInstrumentHistory.jsp").forward(req, resp);
	}
}
