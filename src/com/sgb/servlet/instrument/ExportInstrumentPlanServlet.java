package com.sgb.servlet.instrument;

import com.sgb.dao.InstrumentDao;
import com.sgb.entity.Instrument;
import com.sgb.utils.ExportExcelUtil;
import com.sgb.utils.ExportExcelWrapper;
import com.sgb.utils.FormatDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class ExportAppliancePlanServlet
 */
@WebServlet("/exportInstrumentPlanServlet.do")
public class ExportInstrumentPlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 设置响应字符编码，类型，创建json对象
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");
		String date=req.getParameter("planTime");
		int year = 0;
		int month = 0;
		if(date!=null&&!date.equals("")) {
			int [] array = FormatDate.getYearMonth(date);
			year = array[0];
			month = array[1];
		List<Instrument> aplList = InstrumentDao.getAllInstrumentByExpireDate(year,month);
		String[] columnNames = { "ID", "仪器名称", "仪器编号","QC编号","序列号","使用地点","登记日期","周期","计划时间","完成时间","维护人","status"};
		String fileName =year+"-"+month;
		ExportExcelWrapper<Instrument> util = new ExportExcelWrapper<Instrument>();
		util.exportExcel(fileName, fileName, columnNames, aplList, resp, ExportExcelUtil.EXCEL_FILE_2003);
		}
	}
}
