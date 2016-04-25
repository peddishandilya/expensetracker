package com.ponyinc.minttrack;

import android.provider.BaseColumns;
/**This is an interface for the MintTrack application SQLite database
 * @author Christopher C. Wilkins
 */
public interface Constants extends BaseColumns {
	public static final String PATH_TO_DB = "/data/data/com.ponyinc.minttrack/databases/mint.db";
	public static final String ACCOUNT_TBLNAM = "account";
	public static final String CATEGORY_TBLNAM = "category";
	public static final String TRANSACTION_TBLNAM = "tran_table";

	// Columns in the Accounts Table
	public static final String ACCOUNT_NAME = "account_name";
	public static final String ACCOUNT_TOTAL = "account_total";
	public static final String ACCOUNT_ACTIVE = "account_active";

	// Columns in the Category Table
	public static final String CATEGORY_NAME = "category_name";
	public static final String CATEGORY_TOTAL = "category_total";
	public static final String CATEGORY_TYPE = "category_type";
	public static final String CATEGORY_ACTIVE = "category_active";

	// Columns in the Transaction Table
	
	//Date of transaction
	public static final String TRANSACTION_DATE = "tran_date";
	//_ID from account table of where the money is going too
	public static final String TRANSACTION_TOACCOUNT = "tran_toaccount";
	//_ID from account table of where the money is coming from
	public static final String TRANSACTION_FROMACCOUNT = "tran_fromaccount";
	//The amount of the transaction
	public static final String TRANSACTION_AMOUNT = "tran_amount";
	//The reason or category of the transaction. _ID from category table
	public static final String TRANSACTION_CATEGORY = "tran_category";
	//A note left by the user as a reminder of the transaction
	public static final String TRANSACTION_NOTE = "tran_note";
	//The type of transaction 0=Income 1= Expense 2=Transfer
	public static final String TRANSACTION_TYPE = "tran_type";
	
	//Used Constants
	
	public static final int REASON_TYPE_INCOME = 0;
	public static final int REASON_TYPE_EXPENSE = 1;
	
	public static final int TRANS_TYPE_INCOME = 0;
	public static final int TRANS_TYPE_EXPENSE = 1;
	public static final int TRANS_TYPE_TRANSFER = 2;
	
	//Entry Info Constants
	public static final int AMOUNT = 0;
	public static final int DATE = 1;
	public static final int FROM = 2;
	public static final int TO = 3;
	public static final int CATEGORY = 4;
	public static final int NOTE = 5;
}