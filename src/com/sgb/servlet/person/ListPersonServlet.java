package com.sgb.servlet.person;

import com.sgb.dao.PersonDao;
import com.sgb.entity.Person;
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
 * 查询所有人
 * @author YINXIAOKAI
 *
 */
@WebServlet("/listPersonServlet.do")
public class ListPersonServlet extends HttpServlet {
	

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
		//传递参数
		String username = req.getParameter("username");
		List<Person> result = PersonDao.getAllPerson(pageNum, pageSize,username);
		int totalRows = PersonDao.getTotalRows(username);
		JSONArray jsonArray = new JSONArray();
		for(Person d : result) {
			jsonArray.add(d);
		}
		json.put("total", totalRows);
		json.put("rows", jsonArray);
		out.print(json);
		out.flush();
		out.close();		
	}
}
