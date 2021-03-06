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
 * 查询所有器具前端处理器
 * @author YINXIAOKAI
 *
 */
@WebServlet("/listApplianceServlet.do")
public class ListApplianceServlet extends HttpServlet {
	

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置响应字符编码，类型，创建json对象
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		// 获取参数 pageNum, pageSize,totalRows
		/**
		 * easyUI传递过来的参数page，size 请求传输过去的数据total ，rows
		 */
		int pageNum = 1;//默认第一页
		if (req.getParameter("page") != null
				&& !req.getParameter("page").isEmpty()) {
			pageNum = Integer.parseInt(req.getParameter("page"));
		}
		int pageSize = 10;//默认一页十行
		if (req.getParameter("rows") != null
				&& !req.getParameter("rows").isEmpty()) {
			pageSize = Integer.parseInt(req.getParameter("rows"));
		}
		//获取查询条件
		String MeteringNumber=req.getParameter("MeteringNumber");
		String QCNumber=req.getParameter("QCNumber");
		String ApplianceName=req.getParameter("ApplianceName");		
		//传递参数
		List<Map<String,String>> result = ApplianceDao.getSearchResult(pageNum, pageSize, ApplianceName,MeteringNumber , QCNumber);
		JSONArray jsonArray = new JSONArray();
		for(Map<String, String> map : result) {
			jsonArray.add(map);
		}
		int totalRows = ApplianceDao.getSearchRows(ApplianceName, MeteringNumber, QCNumber);
		json.put("total", totalRows);
		json.put("rows", jsonArray);
		out.print(json);
		out.flush();
		out.close();
		System.out.println("查询所有器具前端处理器ListApplianceServlet类执行了");
	}
}
