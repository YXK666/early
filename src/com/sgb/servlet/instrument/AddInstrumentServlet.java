package com.sgb.servlet.instrument;

import com.sgb.dao.InstrumentDao;
import com.sgb.entity.Instrument;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 添加PM前端控制器
 * @author YXK
 *
 */
@WebServlet("/addInstrumentServlet.do")
public class AddInstrumentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 设置响应字符编码，类型，创建json对象
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		// 建立对象，获取和设置参数，对于和外键相连的字段判断是否有选择
		Instrument itm = new Instrument();
		String instrumentName = req.getParameter("instrumentName");
		if(instrumentName!=null&&!instrumentName.equals("")) {itm.setInstrumentName(instrumentName);}
		
		String instrumentNumber = req.getParameter("instrumentNumber");
		if(instrumentNumber!=null&&!instrumentNumber.equals("")) {itm.setInstrumentNumber(instrumentNumber);}
		
		String qCNumber = req.getParameter("qCNumber");
		if(qCNumber!=null&&!"".equals(qCNumber)) { itm.setQcNumber(qCNumber); }
		
		String sequenceNumber = req.getParameter("sequenceNumber");
		if(sequenceNumber!=null&&!"".equals(sequenceNumber)) { itm.setSequenceNumber(sequenceNumber); }
		
		String usedPosition = req.getParameter("usedPosition");
		if(usedPosition!=null&&!"".equals(usedPosition)) { itm.setUsedPosition(usedPosition); }		
		
		String registerDate = req.getParameter("registerDate");
		if(registerDate!=null&&!"".equals(registerDate)) { itm.setRegisterDate(registerDate); }
		
		String period = req.getParameter("period");
		itm.setPeriod(Integer.parseInt(period));
		
		String planTime = req.getParameter("planTime");	//一个计划时间
		itm.setPlanTime(planTime);
		
		String finishTime = req.getParameter("finishTime");
		if(finishTime!=null&&!"".equals(finishTime)) { itm.setFinishTime(finishTime); }	
		
		String maintainMan = req.getParameter("maintainMan");
		if(maintainMan!=null&&!"".equals(maintainMan)) { itm.setMaintainMan(maintainMan); }	
		
		itm.setStatus(0);//默认初始对象状态为0
		System.out.println("添加PM前端控制器AddInstrumentServlet你前端的初始对象："+itm.toString());
		
		boolean b = false;
		b = InstrumentDao.addInstrument(itm);//添加一个初始查询的对象
		
		System.out.println("添加PM前端控制器AddInstrumentServlet:初始查询的对象添加"+b);
		itm.setStatus(1);//添加一个待查询的对象状态为1，

		
		itm.setFinishTime(null);//将完成时间设置为空
		boolean c = InstrumentDao.addInstrument(itm);
		System.out.println("添加PM前端控制器AddInstrumentServlet用于检查的对象为："+itm.toString());
		System.out.println("用于检查的PM对象添加:"+(c?"成功":"失败"));
		if(b) {
			json.put("success", true);
			json.put("msg", "添加成功");
		}else {
			json.put("success", false);
			json.put("msg", "添加失败");
		}
		out.print(json);
		out.flush();
		out.close();
		
	}

}
