package com.sgb.servlet.appliance;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class toGetApplianceInfoServlet
 */
@WebServlet("/toGetApplianceHistoryServlet.do")
public class ToGetApplianceHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String meteringNumber =req.getParameter("meteringNumber");
		this.getServletContext().setAttribute("meteringNumber",meteringNumber);
		req.getRequestDispatcher("/manage/listApplianceHistory.jsp").forward(req, resp);
	}
}
