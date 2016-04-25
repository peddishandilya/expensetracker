package com.ponyinc.minttrack;

import static com.ponyinc.minttrack.Constants.CATEGORY_ACTIVE;
import static com.ponyinc.minttrack.Constants.CATEGORY_NAME;
import static com.ponyinc.minttrack.Constants.CATEGORY_TOTAL;
import static com.ponyinc.minttrack.Constants.CATEGORY_TYPE;

import java.text.DecimalFormat;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public final class CategoryStatsCursorAdapter extends CursorAdapter {

	DecimalFormat df = new DecimalFormat("0.00");
	public CategoryStatsCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor){
		TextView categoryName = (TextView) view.findViewById(R.id.categoryName);
		TextView categoryAmount = (TextView) view.findViewById(R.id.categoryTotal);
		TextView categoryType = (TextView) view.findViewById(R.id.categoryType);
		TextView categoryActivity = (TextView) view.findViewById(R.id.categoryActivity);
		
		String strCategoryName = cursor.getString(cursor.getColumnIndex(CATEGORY_NAME));
		categoryName.setText(strCategoryName);
		String strCategoryAmount = df.format(cursor.getDouble(cursor.getColumnIndex(CATEGORY_TOTAL)));
		categoryAmount.setText(strCategoryAmount);
		int intCategoryType = cursor.getInt(cursor.getColumnIndex(CATEGORY_TYPE));
		String strCategoryActive = cursor.getString(cursor.getColumnIndex(CATEGORY_ACTIVE));
	
		switch(intCategoryType){
		case 0:
			categoryType.setText("Income");
			break;
		case 1:
			categoryType.setText("Expense");
			break;
		}

		if(strCategoryActive.equalsIgnoreCase("active"))
		{
			categoryActivity.setTextColor(Color.GREEN);
			categoryActivity.setText("Active");
		}
		else
		{
			categoryActivity.setTextColor(Color.RED);
			categoryActivity.setText("Inactive");
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater ContextLayoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

    	View view = ContextLayoutInflater.inflate(R.layout.categoryitem, null);

        bindView(view, context, cursor);
        
        return view;
	}

}
