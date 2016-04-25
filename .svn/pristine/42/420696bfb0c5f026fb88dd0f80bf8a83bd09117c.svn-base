package com.ponyinc.minttrack.types;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ponyinc.minttrack.Constants;
import com.ponyinc.minttrack.MintData;

/** Interface to transaction table */
public class Transactions implements Constants {
	private MintData MintLink;
	private static SQLiteDatabase db;
	private static Cursor cursor;
	
	/**
	 * Create an object to talk to transaction table
	 * 
	 * @param mintdata
	 *            database to connect to
	 */
	public Transactions(MintData mintdata) {
		MintLink = mintdata;
	}

	/**
	 * Transfer founds from one account to another
	 * 
	 * @param ToAccount_ID
	 *            id of account which money will transfered to
	 * @param FromAccount_ID
	 *            id of account which money will be transfered from
	 * @param Amount
	 *            the amount to be transfered
	 * @param Note
	 *            a note by the user
	 * @param Date
	 *            date transfer took place
	 * @param Category
	 *            reason for transfer
	 */
	public void createTransfer(final String[] newInfo)
	// Date yyyymmdd - ex: 20110202 - no dashes or slashes- fill space with
	// leading zeros
	{
		db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TRANSACTION_TOACCOUNT, Long.parseLong(newInfo[TO]));
		values.put(TRANSACTION_FROMACCOUNT, Long.parseLong(newInfo[FROM]));
		values.put(TRANSACTION_AMOUNT, Double.parseDouble(newInfo[AMOUNT]));
		values.put(TRANSACTION_CATEGORY, Long.parseLong(newInfo[CATEGORY]));
		values.put(TRANSACTION_NOTE, newInfo[NOTE]);
		values.put(TRANSACTION_DATE, newInfo[DATE]);
		values.put(TRANSACTION_TYPE, TRANS_TYPE_TRANSFER);

		db.insertOrThrow(TRANSACTION_TBLNAM, null, values);
		db.close();
	}

	/**
	 * Take founds from one account for a given reason
	 * 
	 * @param FromAccount_ID
	 *            id of account which money will be taken from
	 * @param Amount
	 *            the amount to be taken
	 * @param Note
	 *            a note by the user
	 * @param Date
	 *            date expense took place
	 * @param Category
	 *            reason for expense
	 */
	public void createExpense(final String[] newInfo) {

		db = MintLink.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(TRANSACTION_FROMACCOUNT, Long.parseLong(newInfo[FROM]));
		values.put(TRANSACTION_AMOUNT, Double.parseDouble(newInfo[AMOUNT]));
		values.put(TRANSACTION_NOTE, newInfo[NOTE]);
		values.put(TRANSACTION_CATEGORY, Long.parseLong(newInfo[CATEGORY]));
		values.put(TRANSACTION_DATE, newInfo[DATE]);
		values.put(TRANSACTION_TYPE, TRANS_TYPE_EXPENSE);

		db.insertOrThrow(TRANSACTION_TBLNAM, null, values);
		db.close();
	}

	/**
	 * Add founds to one account because of income
	 * 
	 * @param ToAccount_ID
	 *            id of account which money will put into
	 * @param Amount
	 *            the amount to be add
	 * @param Note
	 *            a note by the user
	 * @param Date
	 *            date income too place
	 * @param Category
	 *            reason for income
	 */
	public void createIncome(final String[] newInfo) {

		db = MintLink.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(TRANSACTION_TOACCOUNT, Long.parseLong(newInfo[TO]));
		values.put(TRANSACTION_AMOUNT, Double.parseDouble(newInfo[AMOUNT]));
		values.put(TRANSACTION_NOTE, newInfo[NOTE]);
		values.put(TRANSACTION_DATE, newInfo[DATE]);
		values.put(TRANSACTION_TYPE, TRANS_TYPE_INCOME);
		values.put(TRANSACTION_CATEGORY, Long.parseLong(newInfo[CATEGORY]));

		db.insertOrThrow(TRANSACTION_TBLNAM, null, values);
		db.close();
	}

	/**
	 * get all transactions
	 * 
	 * @return Cursor of transactions
	 */
	public Cursor getTransactions() {
		db = MintLink.getReadableDatabase();
		cursor = db.rawQuery("SELECT " + TRANSACTION_TBLNAM + "._ID, "
				+ TRANSACTION_AMOUNT + ", " + TRANSACTION_NOTE + ", "
				+ TRANSACTION_TYPE + ", " + TRANSACTION_DATE + ", "
				+ TRANSACTION_CATEGORY + ", " + TRANSACTION_TOACCOUNT + ", "
				+ TRANSACTION_FROMACCOUNT + ", C1." + CATEGORY_NAME
				+ " AS CATNAME, A1." + ACCOUNT_NAME + " AS ACT1NAME, A2."
				+ ACCOUNT_NAME + " AS ACT2NAME FROM " + TRANSACTION_TBLNAM
				+ " LEFT JOIN " + ACCOUNT_TBLNAM + " A1 ON "
				+ TRANSACTION_TOACCOUNT + " = A1." + _ID + " LEFT JOIN "
				+ ACCOUNT_TBLNAM + " A2 ON " + TRANSACTION_FROMACCOUNT
				+ " = A2." + _ID + " LEFT JOIN " + CATEGORY_TBLNAM + " C1 ON "
				+ TRANSACTION_CATEGORY + " = C1." + _ID, null);
		return cursor;
	}

	public Cursor getTransactions(final String FromDate, final String ToDate) {
		db = MintLink.getReadableDatabase();
		cursor = db.rawQuery("SELECT " + TRANSACTION_TBLNAM + "._ID, "
				+ TRANSACTION_AMOUNT + ", " + TRANSACTION_NOTE + ", "
				+ TRANSACTION_TYPE + ", " + TRANSACTION_DATE + ", "
				+ TRANSACTION_CATEGORY + ", " + TRANSACTION_TOACCOUNT + ", "
				+ TRANSACTION_FROMACCOUNT + ", C1." + CATEGORY_NAME
				+ " AS CATNAME, A1." + ACCOUNT_NAME + " AS ACT1NAME, A2."
				+ ACCOUNT_NAME + " AS ACT2NAME FROM " + TRANSACTION_TBLNAM
				+ " LEFT JOIN " + ACCOUNT_TBLNAM + " A1 ON "
				+ TRANSACTION_TOACCOUNT + " = A1." + _ID + " LEFT JOIN "
				+ ACCOUNT_TBLNAM + " A2 ON " + TRANSACTION_FROMACCOUNT
				+ " = A2." + _ID + " LEFT JOIN " + CATEGORY_TBLNAM + " C1 ON "
				+ TRANSACTION_CATEGORY + " = C1." + _ID + " WHERE "
				+ TRANSACTION_DATE + " BETWEEN '" + FromDate + "' AND '"
				+ ToDate + "'", null);
		return cursor;
	}

	/**
	 * 
	 * @param transID
	 * @return cursor of
	 */
	public Cursor getTransaction(final long transID) {
		final String[] FROM = { _ID, TRANSACTION_TOACCOUNT,
				TRANSACTION_FROMACCOUNT, TRANSACTION_AMOUNT, TRANSACTION_TYPE,
				TRANSACTION_DATE, TRANSACTION_CATEGORY, TRANSACTION_NOTE, };
		final String ORDER_BY = _ID + " DESC";

		db = MintLink.getReadableDatabase();

		cursor = db.query(TRANSACTION_TBLNAM, FROM, "_ID=" + transID,
				null, null, null, ORDER_BY);

		return cursor;
	}

	public void updateTransfer(final long trans_ID, final String[] newInfo)
	// Date mmddyyyy - ex: 02052010 - no dashes or slashes- fill space with
	// leading zeros
	{
		db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TRANSACTION_TOACCOUNT, Long.parseLong(newInfo[TO]));
		values.put(TRANSACTION_FROMACCOUNT, Long.parseLong(newInfo[FROM]));
		values.put(TRANSACTION_AMOUNT, Double.parseDouble(newInfo[AMOUNT]));
		values.put(TRANSACTION_CATEGORY, Long.parseLong(newInfo[CATEGORY]));
		values.put(TRANSACTION_NOTE, newInfo[NOTE]);
		values.put(TRANSACTION_DATE, newInfo[DATE]);
		values.put(TRANSACTION_TYPE, TRANS_TYPE_TRANSFER);

		db.update(TRANSACTION_TBLNAM, values, _ID + "=" + trans_ID, null);
		db.close();
	}

	public void updateExpense(final long trans_ID, final String[] newInfo) {

		db = MintLink.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(TRANSACTION_FROMACCOUNT, Long.parseLong(newInfo[FROM]));
		values.put(TRANSACTION_AMOUNT, Double.parseDouble(newInfo[AMOUNT]));
		values.put(TRANSACTION_NOTE, newInfo[NOTE]);
		values.put(TRANSACTION_CATEGORY, Long.parseLong(newInfo[CATEGORY]));
		values.put(TRANSACTION_DATE, newInfo[DATE]);
		values.put(TRANSACTION_TYPE, TRANS_TYPE_EXPENSE);

		db.update(TRANSACTION_TBLNAM, values, _ID + "=" + trans_ID, null);
		db.close();
	}

	public void updateIncome(final long trans_ID, final String[] newInfo) {

		db = MintLink.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(TRANSACTION_TOACCOUNT, Long.parseLong(newInfo[TO]));
		values.put(TRANSACTION_AMOUNT, Double.parseDouble(newInfo[AMOUNT]));
		values.put(TRANSACTION_NOTE, newInfo[NOTE]);
		values.put(TRANSACTION_DATE, newInfo[DATE]);
		values.put(TRANSACTION_TYPE, TRANS_TYPE_INCOME);
		values.put(TRANSACTION_CATEGORY, Long.parseLong(newInfo[CATEGORY]));

		db.update(TRANSACTION_TBLNAM, values, _ID + "=" + trans_ID, null);
		db.close();
	}

	public void removeTransaction(final long trans_ID) {
		db = MintLink.getWritableDatabase();

		db.delete(TRANSACTION_TBLNAM, "_ID =" + trans_ID, null);
		db.close();
	}

	/**
	 * Clear Transaction table
	 */
	public void ClearTable() {
		db = MintLink.getWritableDatabase();
		db.delete(TRANSACTION_TBLNAM, null, null);
		db.close();
	}
	
	/**
	 * Access DB object to close when needed
	 * @return
	 */
	public SQLiteDatabase getDB()
	{
		return db;
	}
	
	/**
	 * Access cursor object to close when needed
	 * @return
	 */
	public Cursor getCursor()
	{
		return cursor;
	}
}
