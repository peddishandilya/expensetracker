package com.ponyinc.minttrack.basetypes;

import com.ponyinc.minttrack.Constants;

public class Category {
	private long cId;
	private String cName;
	private String cType;
	private double cTotal;
	private String cActive;
	
	//Default constructor
	public Category()
	{
		setId(-1);
		setName("");
		setType(-1);
		setTotal(0.0);
		setActive("false");
	}
	//Secondary constructor
	public Category(long id, double total, String name, int type, String active)
	{
		setId(id);
		setName(name);
		setType(type);
		setTotal(total);
		setActive(active);
	}
	
	public void setId(long cId)
	{
		this.cId = cId;
	}
	public long getId()
	{
		return cId;
	}
	
	public void setName(String cName)
	{
		this.cName = cName;
	}
	public String getName()
	{
		return cName;
	}

	public void setType(int cType) {
		switch(cType)
		{
		case Constants.REASON_TYPE_EXPENSE:
			this.cType = "Expense";
			break;
		case Constants.REASON_TYPE_INCOME:
			this.cType = "Income";
			break;
		default:
			this.cType = "Unknown";
			break;
		}
	}

	public String getType() {
		return cType;
	}

	public void setTotal(double cTotal) {
		this.cTotal = cTotal;
	}

	public double getTotal() {
		return cTotal;
	}

	public void setActive(String cActive) {
		this.cActive = cActive;
	}

	public String isActive() {
		return cActive;
	}
}
