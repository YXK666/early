package com.sgb.servlet.system;

import com.sgb.dao.ApplianceDao;
import com.sgb.utils.FormatDate;
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
* @author yxk
*/
@WebServlet("/getTimeOutServlet.do")
public class GetTimeOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置响应字符编码，类型，创建json对象
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("appliance/json;chartset=UTF-8");
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		List<Map<String, String>> list = ApplianceDao.getThisMonthExpire(1, 5, 0, 0, null, null, null);
		//提示框状态 0：检查完毕，负数今天还有检查任务未完成，正数距离检查的天数
		String day = "0";
		if(list.size()!=0&&list!=null) {
			String string = list.get(0).get("expireDate");//距离最近的检查日期
			day = FormatDate.getDay(string);
			if(Integer.parseInt(day)<=0) {
				//今天还有检查任务未完成
				json.put("day", -1);
			}else {
				//距离最近的检查天数
				json.put("day", day);
			}			
		}else {	
			//0
			json.put("day", day);
		}
		System.out.println("距离最近的检查天数："+day);
		out.print(json);
		out.flush();
	}

}

