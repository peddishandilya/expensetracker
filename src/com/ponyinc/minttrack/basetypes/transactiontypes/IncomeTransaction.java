package com.ponyinc.minttrack.basetypes.transactiontypes;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.ponyinc.minttrack.Constants;
import com.ponyinc.minttrack.MintData;
import com.ponyinc.minttrack.basetypes.Transaction;

public class IncomeTransaction extends Transaction implements Constants{

	public void update(MintData md, Transaction t) {
		// TODO Auto-generated method stub
		
	}

	public void create(MintData md, Transaction t) {
		SQLiteDatabase db = md.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TRANSACTION_TOACCOUNT, t.getToAccount());
		values.put(TRANSACTION_AMOUNT, t.getAmount());
		values.put(TRANSACTION_NOTE, t.getNote());
		values.put(TRANSACTION_DATE, t.getDate());
		values.put(TRANSACTION_CATEGORY, t.getCategory());
		values.put(TRANSACTION_TYPE, TRANS_TYPE_INCOME);
		db.insertOrThrow(TRANSACTION_TBLNAM, null, values);
		db.close();
	}

}
