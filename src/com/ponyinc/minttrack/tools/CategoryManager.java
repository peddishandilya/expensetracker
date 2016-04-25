package com.ponyinc.minttrack.tools;


import static android.provider.BaseColumns._ID;
import static com.ponyinc.minttrack.Constants.CATEGORY_ACTIVE;
import static com.ponyinc.minttrack.Constants.CATEGORY_NAME;
import static com.ponyinc.minttrack.Constants.CATEGORY_TYPE;
import static com.ponyinc.minttrack.Constants.REASON_TYPE_INCOME;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ponyinc.minttrack.Budget;
import com.ponyinc.minttrack.R;

public class CategoryManager extends Activity {
	private Budget budget;
	private Spinner categorySpinner, categoryTypeSpinner;
	private TextView nameText;
	private Button saveCategory, newCategory, editCategory;
	private CheckBox activateCb;
	private TextView tvCategoryName, tvType, tvActive;
	
	/**mode for manage account*/
	private static final int Default = 1;
	/**mode for editing account	*/
	private static final int Update = 2;
	/**mode for creating new account*/
	private static final int New = 3;
	
	//Toast variables
	private LayoutInflater lInflator;
	private View layout;
	private TextView warningText;
	private Toast toast;
	private String type;
	private boolean newFromEntryTab = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.ponyinc.minttrack.R.layout.catmgr);
		budget = new Budget(this);

		setWidgets();
		Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			newFromEntryTab = extras.getBoolean("newFromEntryTab");
			if(newFromEntryTab)
			{ 
				setWidgetVisiblity(New);
			
				type = extras.getString("categoryType");
				//New income category
				if(type.equalsIgnoreCase("income"))
				{
					categoryTypeSpinner.setSelection(0);
				}
				//New expense category
				else
				{
					categoryTypeSpinner.setSelection(1);
				}
			}
		}
		else
		{	
			findViewById(com.ponyinc.minttrack.R.id.new_cat).setOnClickListener(newCategoryListener);
			findViewById(com.ponyinc.minttrack.R.id.edit_cat).setOnClickListener(editCategoryListener);
		
			fillCatDropDown(categorySpinner,REASON_TYPE_INCOME);
		    categorySpinner.setOnItemSelectedListener(spinnerListener);
		}
		
		findViewById(com.ponyinc.minttrack.R.id.save_cat).setOnClickListener(saveCategoryListener);	
		fillCatTypeSpinner();
	}

	/**
	 * Fill the spinner for category types
	 */
	private void fillCatTypeSpinner() {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, com.ponyinc.minttrack.R.array.cattype_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    categoryTypeSpinner.setAdapter(adapter);
	}


	/**
	 * Initializes all widgets
	 */
	private void setWidgets(){
		newCategory = (Button)findViewById(com.ponyinc.minttrack.R.id.new_cat);
		editCategory = (Button)findViewById(com.ponyinc.minttrack.R.id.edit_cat);
		categorySpinner = (Spinner)findViewById(com.ponyinc.minttrack.R.id.cat_spinner);
		tvCategoryName = (TextView)findViewById(com.ponyinc.minttrack.R.id.tv_catname);
		nameText = (EditText)findViewById(com.ponyinc.minttrack.R.id.cat_name);
		tvType = (TextView)findViewById(com.ponyinc.minttrack.R.id.tv_cattype);
		categoryTypeSpinner = (Spinner)findViewById(com.ponyinc.minttrack.R.id.cat_type);
		saveCategory = (Button)findViewById(com.ponyinc.minttrack.R.id.save_cat);
		tvActive = (TextView)findViewById(com.ponyinc.minttrack.R.id.tv_catactive);
		activateCb = (CheckBox) findViewById(com.ponyinc.minttrack.R.id.active_cat);
		//Toast
		lInflator = getLayoutInflater();
		layout = lInflator.inflate(R.layout.customtoast, (ViewGroup) findViewById(R.id.custom_toast_layout));
		warningText = (TextView) layout.findViewById(R.id.warning_text);
		toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		setWidgetVisiblity(Default);
	}
	
	/**OnClickListener for New Category button**/
	View.OnClickListener newCategoryListener = new View.OnClickListener()
	{	   
		public void onClick(View v) 
		{
			setWidgetVisiblity(New);
		}
	};
	   
	/**OnClickListener for Edit Category button**/
	View.OnClickListener editCategoryListener = new View.OnClickListener()
	{	   
		public void onClick(View v) 
		{	
			setWidgetVisiblity(Update);
		}
	};
   
	/**OnClickListener for Save Category button**/
	View.OnClickListener saveCategoryListener = new View.OnClickListener()
	{	   
		public void onClick(View v) 
		{
			String name = String.valueOf(nameText.getText());
			if(!name.equals(""))
			{
				//If a new category is being created
				if(!newCategory.isEnabled()){
					int type = categoryTypeSpinner.getSelectedItemPosition();
						budget.addCategory(String.valueOf(nameText.getText()), 0.0, type, activateCb.isChecked());
						//Go back to entry screen if this was an addition from the entry tab
						if(newFromEntryTab) {finish();}				
				}
				//On update of pre-existing category
				else
				{
					SimpleCursorAdapter s1 = (SimpleCursorAdapter) categorySpinner.getAdapter();
					
					Cursor catCursor = s1.getCursor();
					catCursor.moveToPosition(categorySpinner.getSelectedItemPosition());
	
					budget.EditCategoryName(catCursor.getInt(catCursor.getColumnIndex(_ID)), String.valueOf(nameText.getText()));
					budget.EditCategoryType(catCursor.getInt(catCursor.getColumnIndex(_ID)), categoryTypeSpinner.getSelectedItemPosition());
					
					if(activateCb.isChecked() == true)
						budget.ReactivateCategory(catCursor.getInt(catCursor.getColumnIndex(_ID)));
					
					else if(activateCb.isChecked() == false)
						budget.DeactivateCategory(catCursor.getInt(catCursor.getColumnIndex(_ID)));
				}
	
				fillCatDropDown(categorySpinner, REASON_TYPE_INCOME);
				setWidgetVisiblity(Default);
			}
			//No name set
			else{
				warningText.setText("Please enter a name.");
				toast.show();
			}
		}
	};
	/**
	 * 
	 */
	AdapterView.OnItemSelectedListener spinnerListener = new OnItemSelectedListener(){

		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			
			SimpleCursorAdapter s = (SimpleCursorAdapter) categorySpinner.getAdapter();
			Cursor spinCoursor = s.getCursor();
			
			spinCoursor.moveToPosition(arg2);
			Cursor cursor = budget.getCategory(spinCoursor.getInt(spinCoursor.getColumnIndex(_ID)));
			cursor.moveToFirst();
			
			String name = cursor.getString(cursor.getColumnIndex(CATEGORY_NAME));
			int type = cursor.getInt(cursor.getColumnIndex(CATEGORY_TYPE));
			String activity = cursor.getString(cursor.getColumnIndex(CATEGORY_ACTIVE));
			
			nameText.setText(name);
			if(type == REASON_TYPE_INCOME){
				categoryTypeSpinner.setSelection(0);
			}
			else {
				categoryTypeSpinner.setSelection(1);
			}
			
			if(activity.equals("active"))
				activateCb.setChecked(true);
			else
				activateCb.setChecked(false);
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// nothing needed here
			
		}
	};
	/** Fill in category drop down
	 *  @param s Spinner to be used to fill drop down
	 *  @param type fill for income(0) or expense(1)*/
	public void fillCatDropDown(Spinner s, int type) {
		Cursor cursor = budget.getAllCategories();
		SimpleCursorAdapter s1 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, cursor, new String[] {
						CATEGORY_NAME, _ID }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		s1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(s1);
	}
	/**
	 * @param mode which visibility setting is used
	 * @see Default
	 * @see Update
	 * @see New
	 */
	private void setWidgetVisiblity(int mode)
	{
		switch(mode)
		{
			case(Default):
			{
				newCategory.setEnabled(true);
				editCategory.setEnabled(true);
					
				nameText.setText("");				
				
				categorySpinner.setVisibility(View.GONE);
				tvCategoryName.setVisibility(View.GONE);
				nameText.setVisibility(View.GONE);
				tvType.setVisibility(View.GONE);
				categoryTypeSpinner.setVisibility(View.GONE);
				saveCategory.setVisibility(View.GONE);
				tvActive.setVisibility(View.GONE);
				activateCb.setVisibility(View.GONE);
				break;
			}	
			case(Update):
			{
				spinnerListener.onItemSelected(categorySpinner, null, 0, 0);
				newCategory.setEnabled(true);
				editCategory.setEnabled(false);
				
				categorySpinner.setVisibility(View.VISIBLE);
				tvCategoryName.setVisibility(View.VISIBLE);
				nameText.setVisibility(View.VISIBLE);
				tvType.setVisibility(View.VISIBLE);
				categoryTypeSpinner.setVisibility(View.VISIBLE);
				saveCategory.setVisibility(View.VISIBLE);
				tvActive.setVisibility(View.VISIBLE);
				activateCb.setVisibility(View.VISIBLE);
				break;
			}
			case(New):
			{
				nameText.setText("");
			
				newCategory.setEnabled(false);
				editCategory.setEnabled(true);
								
				categorySpinner.setVisibility(View.GONE);
				tvCategoryName.setVisibility(View.VISIBLE);
				nameText.setVisibility(View.VISIBLE);
				tvType.setVisibility(View.VISIBLE);
				categoryTypeSpinner.setVisibility(View.VISIBLE);
				saveCategory.setVisibility(View.VISIBLE);
				tvActive.setVisibility(View.VISIBLE);
				activateCb.setVisibility(View.VISIBLE);
				
				if(newFromEntryTab)
					editCategory.setVisibility(View.GONE);
				break;
			}
			default:
		}			
	}
}
