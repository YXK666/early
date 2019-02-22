package com.sgb.entity;

public class Department {
	
	private int deptId;
	private String deptName;
	private String address;
	private String phone;
	private String gg;
	public Department() {
		
	}
	public Department(int deptId, String deptName, String address, String phone, String gg) {
		super();
		this.deptId = deptId;
		this.deptName = deptName;
		this.address = address;
		this.phone = phone;
		this.gg = gg;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getaddress() {
		return address;
	}
	public void setaddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGg() {
		return gg;
	}
	public void setGg(String gg) {
		this.gg = gg;
	}
	@Override
	public String toString() {
		return "Department [deptId=" + deptId + ", deptName=" + deptName + ", address=" + address + ", phone=" + phone
				+ ", gg=" + gg + "]";
	}
	
}
