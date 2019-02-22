package com.sgb.servlet.appliance;

import com.sgb.dao.ApplianceDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class GetApplianceInfoServlet
 * 器具误差趋势图
 */
@WebServlet("/getApplianceInfoServlet.do")
public class GetApplianceInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置响应字符编码，类型，创建json对象
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		String meteringNumber = (String) this.getServletContext().getAttribute("meteringNumber");
	
		System.out.println(meteringNumber);
		List<Map<String,String>> list = ApplianceDao.getInfo(meteringNumber);
		int total = ApplianceDao.getSearchRows(null, meteringNumber, null);		
		JSONArray jsonArray = new JSONArray();
		for(Map<String,String>map:list) {
			jsonArray.add(map);
		}
		json.put("rows", jsonArray);
		json.put("total", total);
		out.print(json);
		out.flush();
		out.close();
		this.getServletContext().removeAttribute("meteringNumber");//清除ServletContext
	}
	

}
