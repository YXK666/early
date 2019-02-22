package com.sgb.servlet.appliance;

import com.sgb.dao.ApplianceDao;
import com.sgb.entity.Appliance;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 检查计量器具的前端控制器类
 * 
 * @author yxk
 *
 */
@WebServlet("/checkApplianceServlet.do")
public class CheckApplianceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/*
	 * 正常检查：合格+改日期+精度
	 * 非正常检查：停用/报废
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 设置响应字符编码，类型，创建json对象
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		// 建立对象，获取和设置参数
		String id = req.getParameter("id");
		Appliance a1 = ApplianceDao.getApplianceById(id);// 旧的
		Appliance a2 = new Appliance();// 新的

		String meteringNumber = req.getParameter("meteringNumber");// 计量编号
		a2.setMeteringNumber(meteringNumber);

		String fixedAssetsNumber = req.getParameter("fixedAssetsNumber");// 固定资产标号

		a2.setFixedAssetsNumber(Integer.parseInt(fixedAssetsNumber));

		String qCNumber = req.getParameter("qCNumber");// QC编号
		a2.setQcNumber(qCNumber);

		String enableTime = req.getParameter("enableTime");// 启用时间
		a2.setEnableTime(enableTime);

		String applianceName = req.getParameter("applianceName");// 器具名称
		a2.setApplianceName(applianceName);

		String applianceModel = req.getParameter("applianceModel");// 器具型号
		a2.setApplianceModel(applianceModel);

		String applianceRange = req.getParameter("applianceRange");// 器具量程范围
		a2.setApplianceRange(applianceRange);

		String accuracyClass = req.getParameter("accuracyClass");// 器具精度等级
		a2.setAccuracyClass(accuracyClass);

		String manufacturer = req.getParameter("manufacturer");// 制造厂
		a2.setManufacturer(manufacturer);

		String manufacturerNumber = req.getParameter("manufacturerNumber");// 出厂编号
		a2.setManufacturerNumber(manufacturerNumber);

		String departmentUsed = req.getParameter("departmentUsed");// 使用部门
		a2.setDepartmentUsed(departmentUsed);

		String applianceCategory = req.getParameter("applianceCategory");// 器具类别
		a2.setApplianceCategory(applianceCategory);
		
		//使用人
		String personUsed = req.getParameter("personUsed");
		if(personUsed!=null&&!"".equals(personUsed)) {	
			a2.setPersonUsed(personUsed);
		}
		String aBCCategory = req.getParameter("aBCCategory");// aBC级别
		a2.setAbcCategory(aBCCategory);

		String managementState = req.getParameter("managementState");// 管理状态
		a1.setManagementState(managementState);
		a2.setManagementState(managementState);
		


//				String lastVerificationTime = req.getParameter("lastVerificationTime");// 上次检查时间
//				a1.setLastVerificationTime(lastVerificationTime);

		String verificationDate = req.getParameter("verificationDate");// 检定日期
		a1.setVerificationDate(verificationDate);// 作为旧对象检定日期历史记录
		a2.setLastVerificationTime(verificationDate);// 作为新对象‘上次’检定日期
		//根据需求 作为初始页面的时间（将检查历史单独放一张表操纵性更好）
		Appliance orginApp = ApplianceDao.getApplianceByMeteringNumber(meteringNumber, "初始");
		int orginId= orginApp.getId();//获取Id --update
		orginApp.setLastVerificationTime(verificationDate);//上次检查时间
		
		String verificationPeriod = req.getParameter("verificationPeriod");// 检定周期
		a2.setVerificationPeriod(Integer.parseInt(verificationPeriod));
		orginApp.setVerificationPeriod(Integer.parseInt(verificationPeriod));

		String expireDate = req.getParameter("expireDate");// 到期日期/下次检查
		a2.setExpireDate(expireDate);// 新对象的下次检查时间

		String verificationDepartment = req.getParameter("verificationDepartment");// 检查单位
		a1.setVerificationDepartment(verificationDepartment);// 旧对象

		String verificationResult = req.getParameter("verificationResult");// 检定结果
		a1.setVerificationResult(verificationResult);// 旧对象默认为合格
		String errorAccuracy = req.getParameter("errorAccuracy");// 精度损失
		if (errorAccuracy != null) {
			a1.setErrorAccuracy(Float.parseFloat(errorAccuracy));// 旧对象损失
			orginApp.setErrorAccuracy(Float.parseFloat(errorAccuracy));// 旧对象损失
		}
		System.out.println("检查计量器具的前端控制器类CheckApplianceServlet：旧对象为" + a1.toString());
		boolean b = false;
		boolean a = ApplianceDao.updatePayer(a1, Integer.parseInt(id));	// 更新旧对象
		System.out.println("旧对象更新" + (a ? "success" : "false"));
		
		if (verificationResult.equals("合格")) {
			a2.setVerificationResult("待查");// 新对象状态为待查	
			System.out.println("检查计量器具的前端控制器类CheckApplianceServlet：新待查对象为" + a2.toString());				
			b = ApplianceDao.addAppliance(a2);// 添加一个新对象
			System.out.println("检查计量器具的前端控制器类CheckApplianceServlet：" + "新对象添加" + (b ? "success" : "false"));
		}else if(verificationResult.equals("停用")) {
			orginApp.setManagementState("停用");
			orginApp.setStopUsingTime(verificationDate);
		}else if(verificationResult.equals("报废")) {
			orginApp.setManagementState("报废");
			orginApp.setDiscardedTime(verificationDate);
		}
		System.out.println("检查计量器具的前端控制器类CheckApplianceServlet：初始对象为" + orginApp.toString());
		boolean c = ApplianceDao.updatePayer(orginApp, orginId);
		System.out.println("检查计量器具的前端控制器类CheckApplianceServlet：初始对象更新"+c);
		if (a) {
			json.put("success", true);
			json.put("msg", "检查成功");

		} else {
			json.put("success", false);
			json.put("msg", "检查失败");
		}
		out.print(json);
		out.flush();
		out.close();

	}

}
