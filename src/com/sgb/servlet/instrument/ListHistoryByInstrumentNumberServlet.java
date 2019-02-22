package com.sgb.servlet.instrument;

import com.sgb.dao.InstrumentDao;
import com.sgb.entity.Instrument;
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

/**
 *  查询Instrument所有检查历史前端控制器
 * @author YINXIAOKAI
 *
 */
@WebServlet("/listHistoryByInstrumentNumberServlet.do")
public class ListHistoryByInstrumentNumberServlet extends HttpServlet {
	

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
				
				//获取查询条件
				String instrumentNumber = (String) this.getServletContext().getAttribute("instrumentNumber");
				System.out.println("instrumentNumber------------"+instrumentNumber);
			
				//传递参数
				List<Instrument> result = InstrumentDao.getCheckHistory(instrumentNumber);
				JSONArray jsonArray = new JSONArray();
				for(Instrument map : result) {
					jsonArray.add(map);
				}
				json.put("rows", jsonArray);
				out.print(json);
				out.flush();
				
				out.close();
				this.getServletContext().removeAttribute("instrumentNumber");//清除ServletContext
			}
	
}
