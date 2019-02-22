package com.sgb.servlet.appliance;

import com.sgb.dao.ApplianceDao;
import com.sgb.entity.Appliance;
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
 * 这是计量器具的添加仪器的处理器类
 */
@WebServlet("/addApplianceServlet.do")
public class AddApplianceServlet extends HttpServlet {

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
		
		// 建立对象，获取和设置参数，对于和外键相连的字段判断是否有选择
		Appliance apl = new Appliance();
		
		String meteringNumber = req.getParameter("meteringNumber");// 计量编号
		apl.setMeteringNumber(meteringNumber);
		
		String fixedAssetsNumber = req.getParameter("fixedAssetsNumber");// 固定资产标号
		if (!fixedAssetsNumber.equals("") && fixedAssetsNumber != null) {
		apl.setFixedAssetsNumber(Integer.parseInt(fixedAssetsNumber));
		}
		
		String qCNumber = req.getParameter("qCNumber");// QC编号
		if(qCNumber != null &&!"".equals(qCNumber)) {
		apl.setQcNumber(qCNumber);
		}
		
		String enableTime = req.getParameter("enableTime");// 启用时间
		if (!enableTime.equals("") && enableTime != null) {
			apl.setEnableTime(enableTime);
		}
		
		String applianceName = req.getParameter("applianceName");// 器具名称
		apl.setApplianceName(applianceName);
		
		String applianceModel = req.getParameter("applianceModel");// 器具型号
		if(applianceModel!=null && !"".equals(applianceModel)) {
			apl.setApplianceModel(applianceModel);
		}
		
		String applianceRange = req.getParameter("applianceRange");// 器具量程范围
		if (applianceRange != null && !applianceRange.equals("")) {
			apl.setApplianceRange(applianceRange);
		}
		
		String accuracyClass = req.getParameter("accuracyClass");// 器具精度等级
		if (accuracyClass != null && !accuracyClass.equals("")) {
			apl.setAccuracyClass(accuracyClass);
		}

		String manufacturer = req.getParameter("manufacturer");		// 制造厂
		if (manufacturer != null && !manufacturer.equals("")) {
			apl.setManufacturer(manufacturer);
		}

		String manufacturerNumber = req.getParameter("manufacturerNumber");		// 出厂编号
		if (manufacturerNumber != null && !manufacturerNumber.equals("")) {
			apl.setManufacturerNumber(manufacturerNumber);
		}
		
		String departmentUsed = req.getParameter("departmentUsed");// 使用部门
		apl.setDepartmentUsed(departmentUsed);
		
		String applianceCategory = req.getParameter("applianceCategory");// 器具类别
		if (applianceCategory != null && !applianceCategory.equals("")) {
			apl.setApplianceCategory(applianceCategory);
		}

		
		String aBCCategory = req.getParameter("aBCCategory");// ABC类别
		apl.setAbcCategory(aBCCategory);
		
		String managementState = req.getParameter("managementState");// 管理状态
		apl.setManagementState(managementState);
		
		String lastVerificationTime = req.getParameter("lastVerificationTime");// 上次检查时间
		apl.setLastVerificationTime(lastVerificationTime);
		
		String verificationPeriod = req.getParameter("verificationPeriod");// 检定周期
		apl.setVerificationPeriod(Integer.parseInt(verificationPeriod));
		// 报废时间
		String discardedTime = req.getParameter("discardedTime");// 报废时间
		if (discardedTime != null && !discardedTime.equals("")) {
			apl.setDiscardedTime(discardedTime);
		}

		String errorAccuracy = req.getParameter("errorAccuracy");// 精度损失
		apl.setErrorAccuracy(Float.parseFloat(errorAccuracy));
		apl.setVerificationResult("初始");

		String personUsed = req.getParameter("personUsed");// 使用人
		apl.setPersonUsed(personUsed);

		String stopUsingTime = req.getParameter("stopUsingTime");		// 停用时间
		if (stopUsingTime != null && !stopUsingTime.equals("")) {
			apl.setStopUsingTime(stopUsingTime);
		}

		boolean b = ApplianceDao.addAppliance(apl);// 初始
		System.out.println("AddApplianceServlet类，仪器初始状态对象:" + apl.toString());

		if (!managementState.equals("停用") && !managementState.equals("报废")) {
			apl.setVerificationResult("待查");
			// 到期日期/下次检查
			String dd = FormatDate.addMonth(lastVerificationTime, Integer.parseInt(verificationPeriod));
			String dd2 = FormatDate.minusDay(dd,1);
			apl.setExpireDate(dd2);
			ApplianceDao.addAppliance(apl);// 未来检查
			System.out.println("AddApplianceServlet类，仪器待检查状态对象:" + apl.toString());
		}

		if (b) {
			json.put("success", true);
			json.put("msg", "添加成功");
		} else {
			json.put("success", false);
			json.put("msg", "添加失败");
		}
		out.print(json);
		out.flush();
		out.close();

	}

}
