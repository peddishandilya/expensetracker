package com.ponyinc.minttrack.basetypes;

public class Account {
	private long aId;
	private String aName;
	private double aTotal;
	private String aActive;
	//Default constructor
	public Account()
	{
		setId(-1);
		setName("");
		setTotal(0.0);
		setActive("false");
	}
	//Secondary constructor
	public Account(long id, double total, String name, String active)
	{
		setId(id);
		setName(name);
		setTotal(total);
		setActive(active);
	}
	public void setId(long aId) {
		this.aId = aId;
	}
	public long getId() {
		return aId;
	}
	public void setName(String aName) {
		this.aName = aName;
	}
	public String getName() {
		return aName;
	}
	public void setTotal(double aTotal) {
		this.aTotal = aTotal;
	}
	public double getTotal() {
		return aTotal;
	}
	public void setActive(String aActive) {
		this.aActive = aActive;
	}
	public String isActive() {
		return aActive;
	}
}
