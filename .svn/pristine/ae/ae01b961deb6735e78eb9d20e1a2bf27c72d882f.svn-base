package com.ponyinc.minttrack.backuprestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.ponyinc.minttrack.Constants;
import com.ponyinc.minttrack.basetypes.Account;
import com.ponyinc.minttrack.basetypes.Category;
import com.ponyinc.minttrack.basetypes.Transaction;


public class BackupManager{
	
	private static Vector<Transaction> transactions = new Vector<Transaction>();
	private static Vector<Account> accounts = new Vector<Account>();
	private static Vector<Category> categories = new Vector<Category>();
	
	private static Cursor aCursor, cCursor, tCursor;
	private static SQLiteDatabase db;
	
	public BackupManager() {
		aCursor = db.query(Constants.ACCOUNT_TBLNAM, null, null, null, null, null, null);
		cCursor = db.query(Constants.CATEGORY_TBLNAM, null, null, null, null, null, null);
		tCursor = db.query(Constants.TRANSACTION_TBLNAM, null, null, null, null, null, null);
	}
	
	public static void openDB(){
		db =  SQLiteDatabase.openDatabase(Constants.PATH_TO_DB, null, SQLiteDatabase.OPEN_READONLY);
	}
	
	public static void closeDB(){
		db.close();
	}
	
	public void exportToXml()
	{
		//Access database and Retrieve data
		getData();
		//Format to XML
		String xmlData = "<Backup><Accounts size=\"" + accounts.size() + "\">";
		for(int i = 0; i < accounts.size(); i++)
		{
			xmlData += "<Account>";
			xmlData += "<Id>" + String.valueOf(accounts.get(i).getId()) + "</Id>";
			xmlData += "<Total>" + String.valueOf(accounts.get(i).getTotal()) + "</Total>";
			xmlData += "<Name>" + String.valueOf(accounts.get(i).getName()) + "</Name>";
			xmlData += "<Active>" + String.valueOf(accounts.get(i).isActive()) + "</Active>";
			xmlData += "</Account>";
		}
		xmlData += "</Accounts><Categories size=\"" + categories.size() + "\">";
		for(int i = 0; i < categories.size(); i++)
		{
			xmlData += "<Category>";
			xmlData += "<Id>" + String.valueOf(categories.get(i).getId()) + "</Id>";
			xmlData += "<Total>" + String.valueOf(categories.get(i).getTotal()) + "</Total>";
			xmlData += "<Name>" + String.valueOf(categories.get(i).getName()) + "</Name>";
			xmlData += "<Type>" + String.valueOf(categories.get(i).getType()) + "</Type>";
			xmlData += "<Active>" + String.valueOf(categories.get(i).isActive()) + "</Active>";
			xmlData += "</Category>";
		}
		xmlData += "</Categories><Transactions size=\"" + transactions.size() + "\">";
		for(int i = 0; i < transactions.size(); i++)
		{
			xmlData += "<Transaction>";
			xmlData += "<Id>" + String.valueOf(transactions.get(i).getId()) + "</Id>";
			xmlData += "<Date>" + String.valueOf(transactions.get(i).getDate()) + "</Date>";
			xmlData += "<To>" + String.valueOf(transactions.get(i).getToAccount()) + "</To>";
			xmlData += "<From>" + String.valueOf(transactions.get(i).getFromAccount()) + "</From>";
			xmlData += "<Amount>" + String.valueOf(transactions.get(i).getAmount()) + "</Amount>";
			xmlData += "<Category>" + String.valueOf(transactions.get(i).getCategory()) + "</Category>";
			xmlData += "<Note>" + String.valueOf(transactions.get(i).getNote()) + "</Note>";
			xmlData += "<Type>" + String.valueOf(transactions.get(i).getType()) + "</Type>";
			xmlData += "</Transaction>";
		}
		xmlData += "</Transactions></Backup>";
		
		File backupDir = new File(Environment.getExternalStorageDirectory(), "/MintTrack");
		backupDir.mkdir();
		File backupFile = new File(Environment.getExternalStorageDirectory(), "/MintTrack/mtbak.xml");
		try {
			FileOutputStream fos = new FileOutputStream(backupFile);
			fos.write(xmlData.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exportToHtml()
	{
		//Access database and Retrieve data
		getData();
		//Format to HTML
		String htmlTemplate = 
			"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
			"<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
			"<head><meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" />" +
			"<title>Untitled 1</title>" +
			"<style type=\"text/css\">#wrapper{margin: 0 auto; text-align: center;} #title{font: Tahoma 24px black; font-variant:small-caps;} .subtitle{font: Tahoma 18px; color: black; font-variant:small-caps;}</style>"+
			"</head>" +
			"<body><div id=\"wrapper\"><p id=\"title\">Your MintTrack Data</p>BODYTEXT</div></body>" +
			"</html>";
		String htmlData = "<p class=\"subtitle\">Accounts</p><table><tr><th>Name</th><th>Active?</th><th>Total</th></tr>";
		
		for(int i = 0; i < accounts.size(); i++)
		{
			htmlData += "<tr>";
			htmlData += "<td>" + String.valueOf(accounts.get(i).getName()) + "</td>";
			htmlData += "<td>" + String.valueOf(accounts.get(i).isActive()) + "</td>";
			htmlData += "<td>" + String.valueOf(accounts.get(i).getTotal()) + "</td>";
			htmlData += "</tr>";
		}
		htmlData += "</table><p class=\"subtitle\">Categories</p><table><tr><th>Name</th><th>Type</th><th>Active?</th><th>Total</th></tr>";
		for(int i = 0; i < categories.size(); i++)
		{
			htmlData += "<tr>";
			htmlData += "<td>" + String.valueOf(categories.get(i).getName()) + "</td>";
			htmlData += "<td>" + String.valueOf(categories.get(i).getType()) + "</td>";
			htmlData += "<td>" + String.valueOf(categories.get(i).isActive()) + "</td>";
			htmlData += "<td>" + String.valueOf(categories.get(i).getTotal()) + "</td>";
			htmlData += "</tr>";
		}
		htmlData += "</table><p class=\"subtitle\">Transactions</p><table><tr><th>Date</th><th>To</th><th>From</th><th>Amount</th><th>Category</th><th>Type</th><th>Note</th></tr>";
		for(int i = 0; i < transactions.size(); i++)
		{
			htmlData += "<tr>";
			htmlData += "<td>" + String.valueOf(transactions.get(i).getDate()) + "</td>";
			htmlData += "<td>" + String.valueOf(transactions.get(i).getToAccount()) + "</td>";
			htmlData += "<td>" + String.valueOf(transactions.get(i).getFromAccount()) + "</td>";
			htmlData += "<td>" + String.valueOf(transactions.get(i).getAmount()) + "</td>";
			htmlData += "<td>" + String.valueOf(transactions.get(i).getCategory()) + "</td>";
			htmlData += "<td>" + String.valueOf(transactions.get(i).getType()) + "</td>";
			htmlData += "<td>" + String.valueOf(transactions.get(i).getNote()) + "</td>";
			htmlData += "</tr>";
		}
		htmlData += "</table>";
		
		htmlTemplate.replace("BODYTEXT", htmlData);
		
		File backupDir = new File(Environment.getExternalStorageDirectory(), "/MintTrack");
		backupDir.mkdir();
		File backupFile = new File(Environment.getExternalStorageDirectory(), "/MintTrack/mtdata.htm");
		try {
			FileOutputStream fos = new FileOutputStream(backupFile);
			fos.write(htmlData.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets all user data and stores it into multiple vectors
	 * @param c Context to gain data from
	 */
	private static void getData()
	{
		transactions.clear();
		

		tCursor.moveToFirst();
		
		for(; !tCursor.isAfterLast(); tCursor.moveToNext())
		{
			transactions.add(
				new Transaction(
					//ID
						tCursor.getLong(0),
					//Date
						tCursor.getString(1),
					//To
						tCursor.getLong(2),
					//From
						tCursor.getLong(3),
					//Amount
						tCursor.getDouble(4),
					//Category
						tCursor.getLong(5),
					//Note
						tCursor.getString(6),
					//Type
						tCursor.getInt(7)
				)
			);
		}

		accounts.clear();
		
		aCursor.moveToFirst();
		
		for(; !aCursor.isAfterLast(); aCursor.moveToNext())
		{
			accounts.add(
				new Account(
					//ID
						aCursor.getLong(0),
					//Total
						aCursor.getDouble(1),
					//Name
						aCursor.getString(2),
					//Active
						aCursor.getString(3)
				)
			);
		}
		
		categories.clear();
		
		cCursor.moveToFirst();
		
		for(; !cCursor.isAfterLast(); cCursor.moveToNext())
		{
			categories.add(
				new Category(
					//ID
						cCursor.getLong(0),
					//Total
						cCursor.getDouble(1),
					//Name
						cCursor.getString(2),
					//Type
						cCursor.getInt(3),
					//Active
						cCursor.getString(4)
				)
			);
		}
		
		tCursor.close();
		cCursor.close();
		aCursor.close();
	}	
}
