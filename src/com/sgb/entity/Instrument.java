package com.sgb.entity;

public class Instrument {
	private int id;
	private String instrumentName;
	private String instrumentNumber;
	private String qcNumber;
	private String sequenceNumber;
	private String usedPosition;
	private String registerDate;
	private int period;
	private String planTime;
	private String finishTime;
	private String maintainMan;
	private int status;
	public Instrument() {
	}
	public Instrument(int id, String instrumentName, String instrumentNumber, String qcNumber, String sequenceNumber,
			String usedPosition, String registerDate, int period, String planTime, String finishTime,
			String maintainMan, int status) {
		this.id = id;
		this.instrumentName = instrumentName;
		this.instrumentNumber = instrumentNumber;
		this.qcNumber = qcNumber;
		this.sequenceNumber = sequenceNumber;
		this.usedPosition = usedPosition;
		this.registerDate = registerDate;
		this.period = period;
		this.planTime = planTime;
		this.finishTime = finishTime;
		this.maintainMan = maintainMan;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInstrumentName() {
		return instrumentName;
	}
	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}
	public String getInstrumentNumber() {
		return instrumentNumber;
	}
	public void setInstrumentNumber(String instrumentNumber) {
		this.instrumentNumber = instrumentNumber;
	}
	public String getQcNumber() {
		return qcNumber;
	}
	public void setQcNumber(String qcNumber) {
		this.qcNumber = qcNumber;
	}
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getUsedPosition() {
		return usedPosition;
	}
	public void setUsedPosition(String usedPosition) {
		this.usedPosition = usedPosition;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getPlanTime() {
		return planTime;
	}
	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getMaintainMan() {
		return maintainMan;
	}
	public void setMaintainMan(String maintainMan) {
		this.maintainMan = maintainMan;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Instrument [id=" + id + ", instrumentName=" + instrumentName + ", instrumentNumber=" + instrumentNumber
				+ ", qcNumber=" + qcNumber + ", sequenceNumber=" + sequenceNumber + ", usedPosition=" + usedPosition
				+ ", registerDate=" + registerDate + ", period=" + period + ", planTime=" + planTime + ", finishTime="
				+ finishTime + ", maintainMan=" + maintainMan + ", status=" + status + "]";
	}

}
