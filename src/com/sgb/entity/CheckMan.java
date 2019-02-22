package com.sgb.entity;

public class CheckMan {
	private int id;
	private String checkMan;
	public CheckMan() {

	}
	public CheckMan(int id, String checkMan) { 
		this.id = id;
		this.checkMan = checkMan;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCheckMan() {
		return checkMan;
	}
	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
	}
	@Override
	public String toString() {
		return "CheckMan [id=" + id + ", checkMan=" + checkMan + "]";
	}
	

}
