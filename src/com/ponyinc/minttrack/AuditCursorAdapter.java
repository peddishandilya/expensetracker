package com.ponyinc.minttrack;

import static com.ponyinc.minttrack.Constants.TRANSACTION_AMOUNT;
import static com.ponyinc.minttrack.Constants.TRANSACTION_DATE;
import static com.ponyinc.minttrack.Constants.TRANSACTION_NOTE;
import static com.ponyinc.minttrack.Constants.TRANSACTION_TYPE;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
/** This class is a custom CursorAdapter that handles the joins of the Account/Transaction/Category for proper output on the AuditTab/HomeTab
*	@author Christopher C. Wilkins
*/
public final class AuditCursorAdapter extends CursorAdapter {
	/** Secondary Constructor
	*	@param	context The context that the adapter will be used
	*	@param	c		A Cursor containing the prejoined database Cursor
	*/
	public AuditCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
	public void bindView(View view, Context context, Cursor cursor) {              
    	TextView dateView = (TextView) view.findViewById(R.id.transactionDate);
    	TextView tranTypeView = (TextView) view.findViewById(R.id.transactionType);
    	TextView amountView =  (TextView) view.findViewById(R.id.transactionAmount);
    	TextView categoryView = (TextView) view.findViewById(R.id.transactionCategory);
    	TextView noteView =  (TextView) view.findViewById(R.id.transactionNote);
    	TextView ToAccountView = (TextView) view.findViewById(R.id.transactionToAccount);
    	TextView FromAccountView = (TextView) view.findViewById(R.id.transactionFromAccount);
    	java.util.Date transactionDate;
    	DateFormat df = new SimpleDateFormat("yyyyMMdd");
    	NumberFormat nf = NumberFormat.getInstance();
    	String millis = cursor.getString(cursor.getColumnIndex(TRANSACTION_DATE));
    	double dblAmount = cursor.getDouble(cursor.getColumnIndex(TRANSACTION_AMOUNT));
    	int iTranType = cursor.getInt(cursor.getColumnIndex(TRANSACTION_TYPE));
    	String strCategory = cursor.getString(cursor.getColumnIndex("CATNAME"));
    	String strToAccount = cursor.getString(cursor.getColumnIndex("ACT1NAME"));
    	String strFromAccount = cursor.getString(cursor.getColumnIndex("ACT2NAME"));
    	String strNote = cursor.getString(cursor.getColumnIndex(TRANSACTION_NOTE));
    	Button deleteBtn = (Button) view.findViewById(R.id.deleteTransactionBtn);
    	Button editBtn = (Button) view.findViewById(R.id.editTransactionBtn);
    	
    	editBtn.setVisibility(View.GONE);
    	deleteBtn.setVisibility(View.GONE);
    	
    	//transaction date
    	try {
			transactionDate = df.parse(millis);
			transactionDate.setMonth(transactionDate.getMonth());
			dateView.setText(DateFormat.getDateInstance(DateFormat.LONG).format(transactionDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	if(iTranType == 0){
    		tranTypeView.setText("Income");
    		ToAccountView.setText(strToAccount);
    		FromAccountView.setText("N/A");
    	}
    	else if(iTranType == 1){
    		tranTypeView.setText("Expense");
    		FromAccountView.setText(strFromAccount);
    		ToAccountView.setText("N/A");
    	}
    	else if(iTranType == 2){
    		tranTypeView.setText("Transfer");
    		ToAccountView.setText(strToAccount);
    		FromAccountView.setText(strFromAccount);
    	}
    	
    	//transaction amount
    	nf.getCurrency();
    	amountView.setText("$" + nf.format(dblAmount));
    	
    	//transaction category
    	categoryView.setText(strCategory);
    	//transaction note
    	noteView.setText(strNote);
    }

    @Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {        
    	LayoutInflater ContextLayoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

    	View view = ContextLayoutInflater.inflate(R.layout.audititem, null);

        bindView(view, context, cursor);
        
        return view;
    }
}
