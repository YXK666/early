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
 * 这是计量器具的修改仪器的处理器类
 */
@WebServlet("/editApplianceServlet.do")
public class EditApplianceServlet extends HttpServlet {

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
		Appliance apl = new Appliance();
		//设置旧对象id
		String id = req.getParameter("id");
		apl.setId(Integer.parseInt(id));
		System.out.println("EditApplianceServlet类获取前端的id:" + id);
		
		String meteringNumber = req.getParameter("meteringNumber");// 计量编号
		apl.setMeteringNumber(meteringNumber);
		
		String fixedAssetsNumber = req.getParameter("fixedAssetsNumber");// 固定资产标号
		if(fixedAssetsNumber!=null&&!fixedAssetsNumber.equals("")) {	
		apl.setFixedAssetsNumber(Integer.parseInt(fixedAssetsNumber));
		}
		
		String qCNumber = req.getParameter("qCNumber");// QC编号
		if(qCNumber!=null&&!qCNumber.equals("")) {	
		apl.setQcNumber(qCNumber);}
		
		String enableTime = req.getParameter("enableTime");// 启用时间
		if (!enableTime.equals("") && enableTime != null) {
			apl.setEnableTime(enableTime);
		}
		
		String applianceName = req.getParameter("applianceName");// 器具名称
		apl.setApplianceName(applianceName);
		
		String applianceModel = req.getParameter("applianceModel");// 器具型号
		if (!applianceModel.equals("") && applianceModel != null) {
		apl.setApplianceModel(applianceModel);}
		
		String applianceRange = req.getParameter("applianceRange");// 器具量程范围
		if (!applianceRange.equals("") && applianceRange != null) {
		apl.setApplianceRange(applianceRange);}
		
		String accuracyClass = req.getParameter("accuracyClass");// 器具精度等级
		if (!accuracyClass.equals("") && accuracyClass != null) {
		apl.setAccuracyClass(accuracyClass);}
		
		String manufacturer = req.getParameter("manufacturer");// 制造厂
		if (!manufacturer.equals("") && manufacturer != null) {
		apl.setManufacturer(manufacturer);}
		
		String manufacturerNumber = req.getParameter("manufacturerNumber");// 出厂编号
		if (!manufacturerNumber.equals("") && manufacturerNumber != null) {
		apl.setManufacturerNumber(manufacturerNumber);}
		
		String departmentUsed = req.getParameter("departmentUsed");// 使用部门
		if (departmentUsed != null && !departmentUsed.equals("")) {
			apl.setDepartmentUsed(departmentUsed);
		}
		
		String personUsed = req.getParameter("personUsed");// 使用人
		if (personUsed != null && !personUsed.equals("")) {
			apl.setPersonUsed(personUsed);
			System.out.println("EditApplianceServlet类:使用人"+personUsed);
		}
		
		String applianceCategory = req.getParameter("applianceCategory");// 器具类别
		apl.setApplianceCategory(applianceCategory);
		
		String aBCCategory = req.getParameter("aBCCategory");// ABC类别
		apl.setAbcCategory(aBCCategory);
		
		String managementState = req.getParameter("managementState");// 管理状态
		apl.setManagementState(managementState);
		
		String lastVerificationTime = req.getParameter("lastVerificationTime");// 上次检查时间
		if (lastVerificationTime != null && !lastVerificationTime.equals("")) {
			apl.setLastVerificationTime(lastVerificationTime);
		}
		
		String verificationPeriod = req.getParameter("verificationPeriod");// 检定周期
		apl.setVerificationPeriod(Integer.parseInt(verificationPeriod));
		
		String stopUsingTime = req.getParameter("stopUsingTime");// 停用时间 
		if (stopUsingTime != null && !stopUsingTime.equals("")) {
			apl.setStopUsingTime(stopUsingTime);
		}
		
		String discardedTime = req.getParameter("discardedTime");// 报废时间
		if (discardedTime != null && !discardedTime.equals("")) {
			apl.setDiscardedTime(discardedTime);
		}
		
		String errorAccuracy = req.getParameter("errorAccuracy");// 精度损失
		if (errorAccuracy != null && !errorAccuracy.equals("")) {
			apl.setErrorAccuracy(Float.parseFloat(errorAccuracy));
		}
		apl.setVerificationResult("初始");
		// 根据 ’计量编号‘ 和 ’待检查‘ 条件获取待检查的对象,如果为空，跳过；如果是停用状态改为启用/合格，那么就添加一个待检查的对象
		if (ApplianceDao.getApplianceById(id).getManagementState().equals("停用") && managementState.equals("合格")) {
			System.out.println("EditApplianceServlet类：启动管理状态为停用的器具的方法开始了。。。。。");
			Appliance newApp = new Appliance();
			// 计量编号
			newApp.setMeteringNumber(meteringNumber);
			// 固定资产标号
			if(!fixedAssetsNumber.equals("")&&fixedAssetsNumber!=null) {
			newApp.setFixedAssetsNumber(Integer.parseInt(fixedAssetsNumber));
			}
			// QC编号
			if(!qCNumber.equals("")&&qCNumber!=null) {
			newApp.setQcNumber(qCNumber);
			}
			// 启用时间
			if (!enableTime.equals("") && enableTime != null) {
				newApp.setEnableTime(enableTime);
			}
			// 器具名称
			newApp.setApplianceName(applianceName);
			// 器具型号
			if(!applianceModel.equals("")&&applianceModel!=null) {			
			newApp.setApplianceModel(applianceModel);
			}
			// 器具量程范围
			if(applianceRange!=null&&!applianceRange.equals("")) {
				newApp.setApplianceRange(applianceRange);
			}		
			// 器具精度等级
			if(accuracyClass!=null&&!accuracyClass.equals("")) {
			newApp.setAccuracyClass(accuracyClass);
			}
			// 制造厂
			if(manufacturer!=null&&!manufacturer.equals("")) {
			newApp.setManufacturer(manufacturer);}
			// 出厂编号
			if(manufacturerNumber!=null&&!manufacturerNumber.equals("")) {
			newApp.setManufacturerNumber(manufacturerNumber);
			}
			// 使用部门
			if(departmentUsed!=null&&!departmentUsed.equals("")) {
			newApp.setDepartmentUsed(departmentUsed);
			}
			// 器具类别
			if(applianceCategory!=null&&!applianceCategory.equals("")) {
			newApp.setApplianceCategory(applianceCategory);
			}
			// ABC类别
			if(aBCCategory!=null&&!aBCCategory.equals("")) {
			newApp.setAbcCategory(aBCCategory);
			}
			// 管理状态
			newApp.setManagementState(managementState);
			// 上次检查时间
			if(lastVerificationTime != null && !lastVerificationTime.equals("")) {
				newApp.setLastVerificationTime(lastVerificationTime);
				// 到期日期/下次检查
				String dd = FormatDate.addMonth(lastVerificationTime, Integer.parseInt(verificationPeriod));
				String dd2 = FormatDate.minusDay(dd,1);
				newApp.setExpireDate(dd2);
			}
			// 检定周期
			newApp.setVerificationPeriod(Integer.parseInt(verificationPeriod));
			// 停用时间
			if (stopUsingTime != null && !stopUsingTime.equals("")) {
				//newApp.setStopUsingTime(stopUsingTime);
				//去掉停用时间
				apl.setStopUsingTime(null);
			}
			// 报废时间
			if (discardedTime != null && !discardedTime.equals("")) {
				//newApp.setDiscardedTime(discardedTime);
			}
			newApp.setVerificationResult("待查");
			// 使用人
			if(personUsed != null && !personUsed.equals("")) {
			newApp.setPersonUsed(personUsed);
			}
			
			boolean b = ApplianceDao.addAppliance(newApp);//
			System.out.println("从停用--》启用的带查询对象:添加" + b);
			System.out.println("EditApplianceServlet类，停用--》启用的待查询对象:" + newApp.toString());
			
			
			//根据 ’计量编号‘ 和 ’待检查‘ 条件获取待检查的对象,如果为空，跳过；如果是合格状态改为停用/报废，那么就修改一个待检查对象的’结果‘为报废停用/报废
		}else if(ApplianceDao.getApplianceById(id).getManagementState().equals("合格") && (managementState.equals("停用")||(managementState.equals("报废")))) {
			System.out.println("EditApplianceServlet类：停止管理状态为合格的器具的方法开始了。。。。。");
			Appliance appWait = ApplianceDao.getThisMonthExpire(meteringNumber);//获取将要报废/停用的器具
			if(appWait!=null) {
				int i = appWait.getId();// 获取id用于修改
				appWait.setMeteringNumber(meteringNumber);
				// 固定资产标号
				appWait.setFixedAssetsNumber(Integer.parseInt(fixedAssetsNumber));
				// QC编号
				appWait.setQcNumber(qCNumber);
				// 启用时间
				if (!enableTime.equals("") && enableTime != null) {
					appWait.setEnableTime(enableTime);
				}
				// 器具名称
				appWait.setApplianceName(applianceName);
				// 器具型号
				appWait.setApplianceModel(applianceModel);
				// 器具量程范围
				appWait.setApplianceRange(applianceRange);
				// 器具精度等级
				appWait.setAccuracyClass(accuracyClass);
				// 制造厂
				appWait.setManufacturer(manufacturer);
				// 出厂编号
				appWait.setManufacturerNumber(manufacturerNumber);
				// 使用部门
				if (departmentUsed != null && !departmentUsed.equals("")) {
					appWait.setDepartmentUsed(departmentUsed);
				}
				// 使用人
				if (personUsed != null && !personUsed.equals("")) {
					appWait.setPersonUsed(personUsed);
				}
				// 器具类别
				appWait.setApplianceCategory(applianceCategory);
				// ABC类别
				appWait.setAbcCategory(aBCCategory);
				// 管理状态
				appWait.setManagementState(managementState);
				// 上次检查时间
				if (lastVerificationTime != null && !lastVerificationTime.equals("")) {
					appWait.setLastVerificationTime(lastVerificationTime);
				}
				// 检定周期
				appWait.setVerificationPeriod(Integer.parseInt(verificationPeriod));
				// 停用时间
				if (stopUsingTime != null && !stopUsingTime.equals("")) {
					appWait.setStopUsingTime(stopUsingTime);
				}
				// 报废时间
				if (discardedTime != null && !discardedTime.equals("")) {
					appWait.setDiscardedTime(discardedTime);
				}
				// 精度损失
				appWait.setErrorAccuracy(Float.parseFloat(errorAccuracy));
				if (managementState.equals("停用") || managementState.equals("报废")) {
					System.out.println("管理状态" + managementState);
					appWait.setVerificationResult(managementState);
				}
				System.out.println("EditApplianceServlet类:停用的的待检查对象" + appWait.toString());
				boolean ab = ApplianceDao.updatePayer(appWait, i);
				System.out.println("EditApplianceServlet类:待检查对象停用" + ab);
				
				//不修改状态，修改其他属性
			}
		}else if(ApplianceDao.getApplianceById(id).getManagementState().equals("合格") && managementState.equals("合格") ){
				System.out.println("EditApplianceServlet类：修改待检查器具的方法开始了。。。。。");
				Appliance appWait2 = ApplianceDao.getThisMonthExpire(meteringNumber);//获取将要报废/停用的器具
				if(appWait2!=null) {
					int i = appWait2.getId();// 获取id用于修改
					appWait2.setMeteringNumber(meteringNumber);
					// 固定资产标号
					appWait2.setFixedAssetsNumber(Integer.parseInt(fixedAssetsNumber));
					// QC编号
					appWait2.setQcNumber(qCNumber);
					// 启用时间
					if (!enableTime.equals("") && enableTime != null) {
						appWait2.setEnableTime(enableTime);
					}
					// 器具名称
					appWait2.setApplianceName(applianceName);
					// 器具型号
					appWait2.setApplianceModel(applianceModel);
					// 器具量程范围
					appWait2.setApplianceRange(applianceRange);
					// 器具精度等级
					appWait2.setAccuracyClass(accuracyClass);
					// 制造厂
					appWait2.setManufacturer(manufacturer);
					// 出厂编号
					appWait2.setManufacturerNumber(manufacturerNumber);
					// 使用部门
					if (departmentUsed != null && !departmentUsed.equals("")) {
						appWait2.setDepartmentUsed(departmentUsed);
					}
					// 使用人
					if (personUsed != null && !personUsed.equals("")) {
						appWait2.setPersonUsed(personUsed);
					}
					// 器具类别
					appWait2.setApplianceCategory(applianceCategory);
					// ABC类别
					appWait2.setAbcCategory(aBCCategory);
					// 管理状态
					appWait2.setManagementState(managementState);
					// 上次检查时间
					if (lastVerificationTime != null && !lastVerificationTime.equals("")) {
						appWait2.setLastVerificationTime(lastVerificationTime);
					}
					// 检定周期
					appWait2.setVerificationPeriod(Integer.parseInt(verificationPeriod));
					// 停用时间
					if (stopUsingTime != null && !stopUsingTime.equals("")) {
						appWait2.setStopUsingTime(stopUsingTime);
					}
					// 报废时间
					if (discardedTime != null && !discardedTime.equals("")) {
						appWait2.setDiscardedTime(discardedTime);
					}
					// 精度损失
					appWait2.setErrorAccuracy(Float.parseFloat(errorAccuracy));

					System.out.println("EditApplianceServlet类:修改待检查对象" + appWait2.toString());
					boolean ab = ApplianceDao.updatePayer(appWait2, i);
					System.out.println("EditApplianceServlet类:待检查对象修改" + ab);
			}			
		}
		boolean b = ApplianceDao.updatePayer(apl, Integer.parseInt(id));// 初始
		System.out.println("EditApplianceServlet类,仪器初始状态对象:" + apl.toString());
		if (b) {
			json.put("success", true);
			json.put("msg", "修改成功");
		} else {
			json.put("success", false);
			json.put("msg", "修改失败");
		}
		out.print(json);
		out.flush();
		out.close();

	
	}
}
