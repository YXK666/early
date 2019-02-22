package com.sgb.servlet.instrument;

import com.sgb.dao.InstrumentDao;
import com.sgb.entity.Instrument;
import com.sgb.utils.FormatDate;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 	@维护PM前端控制器
 * @author YXK
 *
 */
@WebServlet("/checkInstrumentServlet.do")
public class CheckInstrumentServlet extends HttpServlet {

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
		// 建立对象,获取和设置参数，对于和外键相连的字段判断是否有选择
		String id = req.getParameter("id");
		Instrument itmOld = InstrumentDao.getInstrumentById(id);
		Instrument itm = new Instrument();
		String instrumentName = req.getParameter("instrumentName");
		itm.setInstrumentName(instrumentName);
		String instrumentNumber = req.getParameter("instrumentNumber");
		//通过instrumentNumber 和状态0获取初始页面的对象
		Instrument orginIns = InstrumentDao.getInstrumentByInstrumentNumber(instrumentNumber,0);
		int orginId = orginIns.getId();
		
		if(instrumentNumber!=null&&!"".equals(instrumentNumber)) {
			itm.setInstrumentNumber(instrumentNumber);
		}
		
		String qCNumber = req.getParameter("qCNumber");
		if(qCNumber!=null&&!"".equals(qCNumber)) {
			itm.setQcNumber(qCNumber);
		}
		
		String sequenceNumber = req.getParameter("sequenceNumber");
		if(sequenceNumber!=null&&!"".equals(sequenceNumber)) {
			itm.setSequenceNumber(sequenceNumber);
		}
		
		String usedPosition = req.getParameter("usedPosition");
		if(usedPosition!=null&&!"".equals(usedPosition)) {
			itm.setUsedPosition(usedPosition);
		}		
		
		String registerDate = req.getParameter("registerDate");
		if(registerDate!=null&&!"".equals(registerDate)) {
			itm.setRegisterDate(registerDate);
		}
		
		String period = req.getParameter("period");
		itm.setPeriod(Integer.parseInt(period));
		orginIns.setPeriod(Integer.parseInt(period));
		
		String planTime = req.getParameter("planTime");	
		itm.setPlanTime(planTime);//一个计划时间
		orginIns.setPlanTime(planTime);
		
		String finishTime = req.getParameter("finishTime");
		orginIns.setFinishTime(finishTime);
		
		if(finishTime!=null&&!"".equals(finishTime)) {
			itmOld.setFinishTime(finishTime);
			String fin = FormatDate.minusDay(finishTime, 1);
			String date = FormatDate.addMonth(fin, Integer.parseInt(period));
			itm.setPlanTime(date);//给新对象设置下次维护日期
		}		
		String maintainMan = req.getParameter("maintainMan");
		if(maintainMan!=null&&!"".equals(maintainMan)) {
			itm.setMaintainMan(maintainMan);
			itmOld.setMaintainMan(maintainMan);
			orginIns.setMaintainMan(maintainMan);
		}	
		itmOld.setStatus(2);//已经维护好了的对象
		itm.setStatus(1);//1待维护（默认初始对象状态为0）
		System.out.println("维护PM前端控制器CheckInstrumentServlet你前端新添加的待维护对象："+itm.toString());
		boolean b = false;
		//添加一个待维护的对象
		InstrumentDao.addInstrument(itm);
		b = InstrumentDao.updateInstrument(itmOld, Integer.parseInt(id));
		System.out.println("维护PM前端控制器CheckInstrumentServlet你前端完成维护旧对象："+itmOld.toString());
		//修改初始对象
		System.out.println("维护PM前端控制器CheckInstrumentServlet修改后的初始对象："+itmOld.toString());
		Boolean c = InstrumentDao.updateInstrument(orginIns, orginId);		
		System.out.println("维护PM前端控制器CheckInstrumentServlet修改后的初始对象修改："+c);
		if(b) {
			json.put("success", true);
			json.put("msg", "维护成功");
		}else {
			json.put("success", false);
			json.put("msg", "维护失败");
		}
		out.print(json);
		out.flush();
		out.close();
		
	}

}
