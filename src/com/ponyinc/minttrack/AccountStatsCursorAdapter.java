package com.ponyinc.minttrack;

import static com.ponyinc.minttrack.Constants.ACCOUNT_ACTIVE;
import static com.ponyinc.minttrack.Constants.ACCOUNT_NAME;
import static com.ponyinc.minttrack.Constants.ACCOUNT_TOTAL;

import java.text.DecimalFormat;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public final class AccountStatsCursorAdapter extends CursorAdapter {

	DecimalFormat df = new DecimalFormat("0.00");
	public AccountStatsCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor){
		TextView accountName = (TextView) view.findViewById(R.id.accountName);
		TextView accountAmount = (TextView) view.findViewById(R.id.accountTotal);
		TextView accountActivity = (TextView) view.findViewById(R.id.accountActivity);
		
		String strAccountName = cursor.getString(cursor.getColumnIndex(ACCOUNT_NAME));
		accountName.setText(strAccountName);
		String strAccountAmount = df.format(cursor.getDouble(cursor.getColumnIndex(ACCOUNT_TOTAL)));
		accountAmount.setText(strAccountAmount);
		String strAccountActivity = cursor.getString(cursor.getColumnIndex(ACCOUNT_ACTIVE));

		if(strAccountActivity.equalsIgnoreCase("active"))
		{
			accountActivity.setTextColor(Color.GREEN);
			accountActivity.setText("Active");
		}
		else
		{
			accountActivity.setTextColor(Color.RED);
			accountActivity.setText("Inactive");
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater ContextLayoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

    	View view = ContextLayoutInflater.inflate(R.layout.accountitem, null);

        bindView(view, context, cursor);
        
        return view;
	}

}
