package com.sgb.servlet.instrument;

import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *  删除仪器的前端控制器
 * @author YXK
 *
 */
@WebServlet("/deleteInstrumentServlet.do")
public class DeleteInstrumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 设置响应字符编码，类型，创建json对象
				resp.setCharacterEncoding("UTF-8");
				resp.setContentType("application/json; charset=UTF-8");
				PrintWriter out = resp.getWriter();
				JSONObject json = new JSONObject();
				// 获取参数
				String id = req.getParameter("id");
				//验证是否存在任务,如果存在则返回删除失败
				boolean isExistTask = false;
				if(isExistTask){
					json.put("success", false);
					json.put("msg", "还有任务未处理完毕");
				}else{
					// 执行删除
					boolean b = false;
					if (b) {
						json.put("success", true);
						json.put("msg", "删除成功");
					} else {
						json.put("success", false);
						json.put("msg", "删除失败");
					}
				}
				out.print(json);
				out.flush();
				out.close();
			}
	

}
