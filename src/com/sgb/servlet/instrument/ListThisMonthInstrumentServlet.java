package com.sgb.servlet.instrument;

import com.sgb.dao.InstrumentDao;
import com.sgb.utils.FormatDate;
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
 * TO :查看某年某月的Pm检查计划
 * @author YINXIAOKAI
 *
 */
@WebServlet("/listThisMonthInstrumentServlet.do")
public class ListThisMonthInstrumentServlet extends HttpServlet {
	

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
		String instrumentNumber=req.getParameter("instrumentNumber");
		String qcNumber=req.getParameter("qCNumber");
		String instrumentName=req.getParameter("instrumentName");
		String date=req.getParameter("planTime");
		System.out.println("日期参数"+date);
		int year = 0;
		int month = 0;
		if(date!=null&&!date.equals("")) {
			int [] array = FormatDate.getYearMonth(date);
			year = array[0];
			month = array[1];
		System.out.println(year+"   " +month);
		}
		//传递参数
		List<Map<String,String>> result = InstrumentDao.getThisMonthExpire(pageNum, pageSize, year, month,instrumentNumber,qcNumber,instrumentName);
		JSONArray jsonArray = new JSONArray();
		for(Map<String, String> map : result) {
			jsonArray.add(map);
		}
		int totalRows = InstrumentDao.getThisMonthSearchRows( year, month,instrumentNumber,qcNumber,instrumentName);
		json.put("total", totalRows);
		json.put("rows", jsonArray);
		out.print(json);
		out.flush();
		out.close();		
	}
}
