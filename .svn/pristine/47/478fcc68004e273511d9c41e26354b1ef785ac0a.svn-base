package com.ponyinc.minttrack.types;

import static android.provider.BaseColumns._ID;
import static com.ponyinc.minttrack.Constants.ACCOUNT_ACTIVE;
import static com.ponyinc.minttrack.Constants.ACCOUNT_NAME;
import static com.ponyinc.minttrack.Constants.ACCOUNT_TBLNAM;
import static com.ponyinc.minttrack.Constants.ACCOUNT_TOTAL;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ponyinc.minttrack.MintData;
/**
*	Class represents the accounts object 
*   and contains methods for interacting with them 
*/
public class Accounts {
	private MintData MintLink;
	private static SQLiteDatabase db;
	private static Cursor cursor;
	
	/** Secondary Constructor
	*	@param mintdata Takes a MintData object that represents the actual database object
	*/
	public Accounts(MintData mintdata) {
		MintLink = mintdata;
	}
	/** Method is used to add an account to the account table.
	*	@param strName The name of the account
	*	@param initalValue The initial account balance of the account
	*	@param isActive Is account active
	*/
	public void addAccount(final String strName, final double initalValue, final boolean isActive) {
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_NAME, strName);
		values.put(ACCOUNT_TOTAL, initalValue);
		if(isActive == true)
			values.put(ACCOUNT_ACTIVE, "active");
		else
			values.put(ACCOUNT_ACTIVE, "inactive");
		db.insertOrThrow(ACCOUNT_TBLNAM, null, values);
	}
	/** Method used to query the accounts table based on an accounts ID
	*	@para _id Account ID that you wish to query
	*/
	public Cursor getAccount(final long _id) {
		final String[] FROM = { _ID, ACCOUNT_NAME, ACCOUNT_TOTAL,
				ACCOUNT_ACTIVE, };
		final String ORDER_BY = _ID + " DESC";

		db = MintLink.getReadableDatabase();

		cursor = db.query(ACCOUNT_TBLNAM, FROM, "_ID=" + _id, null,
				null, null, ORDER_BY);

		return cursor;
	}
	/** Method is used to get a list of only accounts that have an active status
	*	@return Returns a Cursor contains accounts that are active in the accounts table
	*/
	public Cursor getActiveAccounts() {
		final String[] FROM = { _ID, ACCOUNT_NAME, ACCOUNT_TOTAL,
				ACCOUNT_ACTIVE, };
		final String ORDER_BY = _ID + " ASC";

		db = MintLink.getReadableDatabase();

		cursor = db.query(ACCOUNT_TBLNAM, FROM, "ACCOUNT_ACTIVE ='active'", null,
				null, null, ORDER_BY);

		return cursor;
	}
	/** Method is used to get a Cursor of all the accounts in the account table
	*	@return A Cursor containing all the accounts on the account table
	*/
	public Cursor getAllAccounts() {
		final String[] FROM = { _ID, ACCOUNT_NAME, ACCOUNT_TOTAL,
				ACCOUNT_ACTIVE, };
		final String ORDER_BY = _ID + " ASC";
		db = MintLink.getReadableDatabase();
		cursor = db.query(ACCOUNT_TBLNAM, FROM, null, null, null, null,
				ORDER_BY);
		return cursor;
	}
	/** Method is used to deactivate an account
	*	@param acc_id ID of the account which you wish to disable
	*/
	public void DeactivateAccount(final long acc_id)
	// set account to inactive
	{
		db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_ACTIVE, "inactive");
		db.update(ACCOUNT_TBLNAM, values, _ID + "=" + acc_id, null);
	}
	/** Method is used to edit the name of an existing account
	*	@param acc_id ID of the account you want to modify
	*	@param strName Name that will replace the account
	*/
	public void EditAccountName(final long acc_id, final String strName) {
		db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_NAME, strName);
		db.update(ACCOUNT_TBLNAM, values, _ID + "=" + acc_id, null);
	}
	/** Method modifies the balance total of an account
	*	@param acc_id ID of the account you want to modify
	*	@param total New amount to be set as the balance of the account
	*/
	public void EditAccountTotal(final long acc_id, final double total) {
		db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_TOTAL, total);
		db.update(ACCOUNT_TBLNAM, values, _ID + "=" + acc_id, null);
	}
	/** Method is used to reactive an inactive account that already exists
	*	@param acc_id ID of account which you want to activate
	*/
	public void ReactivateAccount(final long acc_id)
	// set account to inactive
	{
		db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_ACTIVE, "active");
		db.update(ACCOUNT_TBLNAM, values, _ID + "=" + acc_id, null);
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
