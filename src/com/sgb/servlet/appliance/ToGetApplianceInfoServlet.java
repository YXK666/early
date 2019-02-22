package com.sgb.servlet.appliance;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class toGetApplianceInfoServlet
 */
@WebServlet("/togetApplianceInfoServlet.do")
public class ToGetApplianceInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

       
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String meteringNumber =req.getParameter("meteringNumber");
		//req.setAttribute("MeteringNumber",MeteringNumber);
		this.getServletContext().setAttribute("meteringNumber",meteringNumber);
		req.getRequestDispatcher("/manage/applianceInfo.jsp").forward(req, resp);
	}

}
