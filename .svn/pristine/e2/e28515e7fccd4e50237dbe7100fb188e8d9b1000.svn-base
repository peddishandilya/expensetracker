package com.ponyinc.minttrack.basetypes.transactiontypes;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.ponyinc.minttrack.Constants;
import com.ponyinc.minttrack.MintData;
import com.ponyinc.minttrack.basetypes.Transaction;

public class TransferTransaction extends Transaction implements Constants{

	public void update(MintData md, Transaction t) {
		SQLiteDatabase db = md.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TRANSACTION_TOACCOUNT, t.getToAccount());
		values.put(TRANSACTION_FROMACCOUNT, t.getFromAccount());
		values.put(TRANSACTION_AMOUNT, t.getAmount());
		values.put(TRANSACTION_CATEGORY, t.getCategory());
		values.put(TRANSACTION_NOTE, t.getNote());
		values.put(TRANSACTION_DATE, t.getDate());
		values.put(TRANSACTION_TYPE, TRANS_TYPE_TRANSFER);
		db.update(TRANSACTION_TBLNAM, values,  _ID + "=" + t.getId(), null);
		db.close();
	}

	public void create(MintData md, Transaction t) {
		SQLiteDatabase db = md.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TRANSACTION_TOACCOUNT, t.getToAccount());
		values.put(TRANSACTION_FROMACCOUNT, t.getFromAccount());
		values.put(TRANSACTION_AMOUNT, t.getAmount());
		values.put(TRANSACTION_CATEGORY, t.getCategory());
		values.put(TRANSACTION_NOTE, t.getNote());
		values.put(TRANSACTION_DATE, t.getDate());
		values.put(TRANSACTION_TYPE, TRANS_TYPE_TRANSFER);
		db.insertOrThrow(TRANSACTION_TBLNAM, null, values);
		db.close();
	}

}
