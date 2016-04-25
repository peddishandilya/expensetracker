package com.ponyinc.minttrack;

import static android.provider.BaseColumns._ID;
import static com.ponyinc.minttrack.Constants.ACCOUNT_ACTIVE;
import static com.ponyinc.minttrack.Constants.ACCOUNT_NAME;
import static com.ponyinc.minttrack.Constants.ACCOUNT_TBLNAM;
import static com.ponyinc.minttrack.Constants.ACCOUNT_TOTAL;
import static com.ponyinc.minttrack.Constants.CATEGORY_ACTIVE;
import static com.ponyinc.minttrack.Constants.CATEGORY_NAME;
import static com.ponyinc.minttrack.Constants.CATEGORY_TBLNAM;
import static com.ponyinc.minttrack.Constants.CATEGORY_TOTAL;
import static com.ponyinc.minttrack.Constants.CATEGORY_TYPE;
import static com.ponyinc.minttrack.Constants.TRANSACTION_AMOUNT;
import static com.ponyinc.minttrack.Constants.TRANSACTION_CATEGORY;
import static com.ponyinc.minttrack.Constants.TRANSACTION_DATE;
import static com.ponyinc.minttrack.Constants.TRANSACTION_FROMACCOUNT;
import static com.ponyinc.minttrack.Constants.TRANSACTION_NOTE;
import static com.ponyinc.minttrack.Constants.TRANSACTION_TBLNAM;
import static com.ponyinc.minttrack.Constants.TRANSACTION_TOACCOUNT;
import static com.ponyinc.minttrack.Constants.TRANSACTION_TYPE;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**This class creates the SQLite database for the MintTrack Application
 * @author Christopher C. Wilkins
 **/
public class MintData extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "mint.db";
	private static final int DATABASE_VERSION = 1;

	/**Secondary Constructor
	 * @param ctx The context MintData is being created in
	 */
	public MintData(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + ACCOUNT_TBLNAM + " (" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + ACCOUNT_TOTAL
				+ " DOUBLE, " + ACCOUNT_NAME + " TEXT NOT NULL, "
				+ ACCOUNT_ACTIVE + " TEXT);");

		db.execSQL("CREATE TABLE " + CATEGORY_TBLNAM + " (" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + CATEGORY_TOTAL
				+ " DOUBLE, " + CATEGORY_NAME + " TEXT NOT NULL, "
				+ CATEGORY_TYPE + " INTEGER NOT NULL, " + CATEGORY_ACTIVE
				+ " TEXT);");

		db.execSQL("CREATE TABLE " + TRANSACTION_TBLNAM + " (" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + TRANSACTION_DATE
				+ " TEXT, " + TRANSACTION_TOACCOUNT + " INTEGER, "
				+ TRANSACTION_FROMACCOUNT + " INTEGER, " + TRANSACTION_AMOUNT
				+ " DOUBLE, " + TRANSACTION_CATEGORY + " INTEGER, "
				+ TRANSACTION_NOTE + " TEXT, " + TRANSACTION_TYPE
				+ " INTEGER);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TBLNAM);
		db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TBLNAM);
		db.execSQL("DROP TABLE IF EXISTS " + TRANSACTION_TBLNAM);
		onCreate(db);
	}
}