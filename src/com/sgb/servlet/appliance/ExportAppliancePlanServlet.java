package com.sgb.servlet.appliance;

import com.sgb.dao.ApplianceDao;
import com.sgb.entity.Appliance;
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
@WebServlet("/exportAppliancePlanServlet.do")
public class ExportAppliancePlanServlet extends HttpServlet {
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
		String date=req.getParameter("ExpireDate");
		int year = 0;
		int month = 0;
		if(date!=null&&!date.equals("")) {
			int [] array = FormatDate.getYearMonth(date);
			year = array[0];
			month = array[1];
		List<Appliance> aplList = ApplianceDao.getAllApplianceByExpireDate(year,month);
		String[] columnNames = { "ID", "计量编号", "固定资产标号","QC编号","启用时间","器具名称","器具型号","器具量程范围","器具精度等级","制造厂","出厂编号","使用部门","使用人","器具类别","ABC类别","管理状态","上次检查时间","检定日期","检定周期","有效日期","检定部门","检定结果","停用时间","报废时间","精度损失"};
		String fileName =year+"-"+month;
		ExportExcelWrapper<Appliance> util = new ExportExcelWrapper<Appliance>();
		util.exportExcel(fileName, fileName, columnNames, aplList, resp, ExportExcelUtil.EXCEL_FILE_2003);
		}
	}
}
