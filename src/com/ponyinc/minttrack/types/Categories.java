package com.ponyinc.minttrack.types;


import static android.provider.BaseColumns._ID;
import static com.ponyinc.minttrack.Constants.CATEGORY_ACTIVE;
import static com.ponyinc.minttrack.Constants.CATEGORY_NAME;
import static com.ponyinc.minttrack.Constants.CATEGORY_TBLNAM;
import static com.ponyinc.minttrack.Constants.CATEGORY_TOTAL;
import static com.ponyinc.minttrack.Constants.CATEGORY_TYPE;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ponyinc.minttrack.MintData;
/** 
*	Class represents the Categories object 
*   and contains methods for interacting with them
*	@author Christopher C. Wilkins
*/
public class Categories {
	private MintData MintLink;
	private static SQLiteDatabase db;
	private static Cursor cursor;
	
	/** Secondary Constructor
	*	@param mintdata Database object to be used for querys
	*/
	public Categories(MintData mintdata) {
		MintLink = mintdata;
	}
	/** Outputs a cursor containing all categories
	*	@return A cursor containing all categories
	*/
	public Cursor getAllCategories() {
		final String[] FROM = { _ID, CATEGORY_NAME, CATEGORY_TOTAL,
				CATEGORY_TYPE, CATEGORY_ACTIVE,};
		final String ORDER_BY = CATEGORY_NAME + " DESC";
		db = MintLink.getReadableDatabase();
		cursor = db.query(CATEGORY_TBLNAM, FROM, null, null, null, null,
				ORDER_BY);
		return cursor;
	}
	/** Outputs a cursor containing all categories
	*	@return A cursor containing all categories
	*/
	public Cursor getActiveCategories() {
		final String[] FROM = { _ID, CATEGORY_NAME, CATEGORY_TOTAL,
				CATEGORY_TYPE, CATEGORY_ACTIVE,};
		final String ORDER_BY = CATEGORY_NAME + " DESC";
		db = MintLink.getReadableDatabase();
		cursor = db.query(CATEGORY_TBLNAM, FROM, "CATEGORY_ACTIVE = 'active'", null, null, null,
				ORDER_BY);
		return cursor;
	}
	/**
	 * 
	 * @param type 0 for income 1 for expense
	 * @return Cursor of category of type income or expense
	 */
	public Cursor getCategories(final int type) 
	{
		final String[] FROM = { _ID, CATEGORY_NAME, CATEGORY_TOTAL,
				CATEGORY_TYPE, CATEGORY_ACTIVE,};
		final String ORDER_BY = CATEGORY_NAME + " DESC";
		db = MintLink.getReadableDatabase();
		
		cursor = db.query(CATEGORY_TBLNAM, FROM, "CATEGORY_TYPE=" + type, null, null, null,
				ORDER_BY);
		
		return cursor;
	}

	/** Outputs a cursor with a single category from the database from the ID
	*	@param intID ID of the category you want
	*	@return A cursor contain the category based on the ID handed to the method
	*/
	public Cursor getCategory(final long intID) {
		final String[] FROM = { _ID, CATEGORY_NAME, CATEGORY_TOTAL,
				CATEGORY_TYPE, CATEGORY_ACTIVE,};
		final String ORDER_BY = CATEGORY_NAME + " DESC";
		final String SELECTION = "_ID=" + intID;

		db = MintLink.getReadableDatabase();
		cursor = db.query(CATEGORY_TBLNAM, FROM, SELECTION, null, null,
				null, ORDER_BY);
		return cursor;
	}
	/** Method is used to add an category to the category table
	*	@param strName Name of the new account
	*	@param initalValue Inital balance the category will be initalized to
	*	@param iType Type of category that it is
	*/
	public void addCategory(final String strName, final double initalValue, final int iType, final boolean isActive) {
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_NAME, strName);
		values.put(CATEGORY_TOTAL, initalValue);
		values.put(CATEGORY_TYPE, iType);
		if(isActive == true)
			values.put(CATEGORY_ACTIVE, "active");
		else
			values.put(CATEGORY_ACTIVE, "inactive");
		
		db.insertOrThrow(CATEGORY_TBLNAM, null, values);
	}
	/** Method is used to deactivate an category
	*	@param acc_id ID of the category which you wish to disable
	*/
	public void deactivateCategory(final long acc_id)
	// set category to inactive
	{
		db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_ACTIVE, "inactive");
		db.update(CATEGORY_TBLNAM, values, _ID + "=" + acc_id, null);
	}
	/** Method is used to reactive an inactive category that already exists
	*	@param acc_id ID of category which you want to activate
	*/
	public void reactivateCategory(final long cat_id)
	// set category to active
	{
		db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_ACTIVE, "active");
		db.update(CATEGORY_TBLNAM, values, _ID + "=" + cat_id, null);
	}
	/** Used to edit an existing categories type
	*	@param iCatId The ID of the category being modified
	*	@param iType New type for the category
	*/
	public void editCategoryType(final long iCatId, final int iType) {
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_TYPE, iType);
		db.update(CATEGORY_TBLNAM, values, "_ID=" + iCatId, null);
	}
	/** Used to edit an existing categories name
	*	@param iCatID iCatId The ID of the category being modified
	*	@param strCatName New name to be applied to the category
	*/
	public void editCategoryName(final long iCatID, final String strCatName) {
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_NAME, strCatName);
		db.update(CATEGORY_TBLNAM, values, "_ID=" + iCatID, null);
	}
	/** Used to edit an existing category's balance
	*	@param iCatID ID of the category being modified
	*	@param dblTotal New balance amount for the category
	*/
	public void updateCategory(final long iCatID, final double dblTotal) {
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_TOTAL, dblTotal);
		db.update(CATEGORY_TBLNAM, values, "_ID=" + iCatID, null);
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
