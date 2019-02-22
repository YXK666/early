package com.sgb.entity;

/**
 * 计量器具实体类
 * @author YXK
 *
 */
public class Appliance {
	
	private int id;
	private String meteringNumber;// 计量编号
	private int fixedAssetsNumber;// 固定资产标号
	private String qcNumber;// QC编号
	private String enableTime;// 启用时间
	private String applianceName;// 器具名称
	private String applianceModel;// 器具型号
	private String applianceRange;// 器具量程范围
	private String accuracyClass;// 器具精度等级
	private String manufacturer;// 制造厂
	private String manufacturerNumber;// 出厂编号
	private String departmentUsed;// 使用部门
	private String personUsed;//使用人
	private String applianceCategory;// 器具类别
	private String abcCategory;// ABC类别
	private String managementState;// 管理状态
	private String lastVerificationTime;// 上次检查时间
	private String verificationDate;// 检定日期
	private int verificationPeriod;// 检定周期
	private String expireDate;// 到期日期/下次检查
	private String verificationDepartment;// 检定部门
	private String verificationResult;// 检定结果
	private String stopUsingTime;// 停用
	private String discardedTime;//报废时间
	private float errorAccuracy;// 精度损失
	public Appliance() {		
	}
	public Appliance(int id, String meteringNumber, int fixedAssetsNumber, String qcNumber, String enableTime,
			String applianceName, String applianceModel, String applianceRange, String accuracyClass,
			String manufacturer, String manufacturerNumber, String departmentUsed, String personUsed,
			String applianceCategory, String abcCategory, String managementState, String lastVerificationTime,
			String verificationDate, int verificationPeriod, String expireDate, String verificationDepartment,
			String verificationResult, String stopUsingTime, String discardedTime, float errorAccuracy) {
		super();
		this.id = id;
		this.meteringNumber = meteringNumber;
		this.fixedAssetsNumber = fixedAssetsNumber;
		this.qcNumber = qcNumber;
		this.enableTime = enableTime;
		this.applianceName = applianceName;
		this.applianceModel = applianceModel;
		this.applianceRange = applianceRange;
		this.accuracyClass = accuracyClass;
		this.manufacturer = manufacturer;
		this.manufacturerNumber = manufacturerNumber;
		this.departmentUsed = departmentUsed;
		this.personUsed = personUsed;
		this.applianceCategory = applianceCategory;
		this.abcCategory = abcCategory;
		this.managementState = managementState;
		this.lastVerificationTime = lastVerificationTime;
		this.verificationDate = verificationDate;
		this.verificationPeriod = verificationPeriod;
		this.expireDate = expireDate;
		this.verificationDepartment = verificationDepartment;
		this.verificationResult = verificationResult;
		this.stopUsingTime = stopUsingTime;
		this.discardedTime = discardedTime;
		this.errorAccuracy = errorAccuracy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMeteringNumber() {
		return meteringNumber;
	}
	public void setMeteringNumber(String meteringNumber) {
		this.meteringNumber = meteringNumber;
	}
	public int getFixedAssetsNumber() {
		return fixedAssetsNumber;
	}
	public void setFixedAssetsNumber(int fixedAssetsNumber) {
		this.fixedAssetsNumber = fixedAssetsNumber;
	}
	public String getQcNumber() {
		return qcNumber;
	}
	public void setQcNumber(String qcNumber) {
		this.qcNumber = qcNumber;
	}
	public String getEnableTime() {
		return enableTime;
	}
	public void setEnableTime(String enableTime) {
		this.enableTime = enableTime;
	}
	public String getApplianceName() {
		return applianceName;
	}
	public void setApplianceName(String applianceName) {
		this.applianceName = applianceName;
	}
	public String getApplianceModel() {
		return applianceModel;
	}
	public void setApplianceModel(String applianceModel) {
		this.applianceModel = applianceModel;
	}
	public String getApplianceRange() {
		return applianceRange;
	}
	public void setApplianceRange(String applianceRange) {
		this.applianceRange = applianceRange;
	}
	public String getAccuracyClass() {
		return accuracyClass;
	}
	public void setAccuracyClass(String accuracyClass) {
		this.accuracyClass = accuracyClass;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getManufacturerNumber() {
		return manufacturerNumber;
	}
	public void setManufacturerNumber(String manufacturerNumber) {
		this.manufacturerNumber = manufacturerNumber;
	}
	public String getDepartmentUsed() {
		return departmentUsed;
	}
	public void setDepartmentUsed(String departmentUsed) {
		this.departmentUsed = departmentUsed;
	}
	public String getPersonUsed() {
		return personUsed;
	}
	public void setPersonUsed(String personUsed) {
		this.personUsed = personUsed;
	}
	public String getApplianceCategory() {
		return applianceCategory;
	}
	public void setApplianceCategory(String applianceCategory) {
		this.applianceCategory = applianceCategory;
	}
	public String getAbcCategory() {
		return abcCategory;
	}
	public void setAbcCategory(String abcCategory) {
		this.abcCategory = abcCategory;
	}
	public String getManagementState() {
		return managementState;
	}
	public void setManagementState(String managementState) {
		this.managementState = managementState;
	}
	public String getLastVerificationTime() {
		return lastVerificationTime;
	}
	public void setLastVerificationTime(String lastVerificationTime) {
		this.lastVerificationTime = lastVerificationTime;
	}
	public String getVerificationDate() {
		return verificationDate;
	}
	public void setVerificationDate(String verificationDate) {
		this.verificationDate = verificationDate;
	}
	public int getVerificationPeriod() {
		return verificationPeriod;
	}
	public void setVerificationPeriod(int verificationPeriod) {
		this.verificationPeriod = verificationPeriod;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getVerificationDepartment() {
		return verificationDepartment;
	}
	public void setVerificationDepartment(String verificationDepartment) {
		this.verificationDepartment = verificationDepartment;
	}
	public String getVerificationResult() {
		return verificationResult;
	}
	public void setVerificationResult(String verificationResult) {
		this.verificationResult = verificationResult;
	}
	public String getStopUsingTime() {
		return stopUsingTime;
	}
	public void setStopUsingTime(String stopUsingTime) {
		this.stopUsingTime = stopUsingTime;
	}
	public String getDiscardedTime() {
		return discardedTime;
	}
	public void setDiscardedTime(String discardedTime) {
		this.discardedTime = discardedTime;
	}
	public float getErrorAccuracy() {
		return errorAccuracy;
	}
	public void setErrorAccuracy(float errorAccuracy) {
		this.errorAccuracy = errorAccuracy;
	}
	@Override
	public String toString() {
		return "Appliance [id=" + id + ", meteringNumber=" + meteringNumber + ", fixedAssetsNumber=" + fixedAssetsNumber
				+ ", qcNumber=" + qcNumber + ", enableTime=" + enableTime + ", applianceName=" + applianceName
				+ ", applianceModel=" + applianceModel + ", applianceRange=" + applianceRange + ", accuracyClass="
				+ accuracyClass + ", manufacturer=" + manufacturer + ", manufacturerNumber=" + manufacturerNumber
				+ ", departmentUsed=" + departmentUsed + ", personUsed=" + personUsed + ", applianceCategory="
				+ applianceCategory + ", abcCategory=" + abcCategory + ", managementState=" + managementState
				+ ", lastVerificationTime=" + lastVerificationTime + ", verificationDate=" + verificationDate
				+ ", verificationPeriod=" + verificationPeriod + ", expireDate=" + expireDate
				+ ", verificationDepartment=" + verificationDepartment + ", verificationResult=" + verificationResult
				+ ", stopUsingTime=" + stopUsingTime + ", discardedTime=" + discardedTime + ", errorAccuracy="
				+ errorAccuracy + "]";
	}
	
}
