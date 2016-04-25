package com.ponyinc.minttrack.tools;

import static android.provider.BaseColumns._ID;
import static com.ponyinc.minttrack.Constants.ACCOUNT_ACTIVE;
import static com.ponyinc.minttrack.Constants.ACCOUNT_NAME;
import static com.ponyinc.minttrack.Constants.ACCOUNT_TOTAL;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ponyinc.minttrack.Budget;
import com.ponyinc.minttrack.R;

public class AccountManager extends Activity {
	private Budget budget;
	private Spinner accountSpinner;
	private TextView nameText, balText;
	private Button saveButton, newAccount, editAccount;
	private CheckBox activateCb;
	private TextView tvAccountName, tvBalance, tvActive;
	
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
	private boolean newFromEntryTab = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.ponyinc.minttrack.R.layout.acctmgr);
		budget = new Budget(this);
		
		setWidgets();
		Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			newFromEntryTab = extras.getBoolean("newFromEntryTab");
			if(newFromEntryTab)
			{
				setWidgetVisiblity(New);
			}
		}
		else
		{
			findViewById(com.ponyinc.minttrack.R.id.new_acct).setOnClickListener(newAccountListener);
			findViewById(com.ponyinc.minttrack.R.id.edit_acct).setOnClickListener(editAccountListener);
			
			fillAccountDropDown(accountSpinner);
			accountSpinner.setOnItemSelectedListener(spinnerListener);
		}
		
		findViewById(com.ponyinc.minttrack.R.id.save_acct).setOnClickListener(saveAccountListener);

	}
	
	private void setWidgets(){
		newAccount = (Button)findViewById(com.ponyinc.minttrack.R.id.new_acct);
		editAccount = (Button)findViewById(com.ponyinc.minttrack.R.id.edit_acct);
		accountSpinner = (Spinner)findViewById(com.ponyinc.minttrack.R.id.acct_spinner);
		tvAccountName = (TextView)findViewById(com.ponyinc.minttrack.R.id.tv_acctname);
		nameText = (EditText)findViewById(com.ponyinc.minttrack.R.id.acct_name);
		tvBalance = (TextView)findViewById(com.ponyinc.minttrack.R.id.tv_balance);
		balText = (EditText)findViewById(com.ponyinc.minttrack.R.id.acct_bal);
		tvActive = (TextView)findViewById(com.ponyinc.minttrack.R.id.tv_active);
		activateCb = (CheckBox)findViewById(com.ponyinc.minttrack.R.id.active_acct);
		saveButton = (Button)findViewById(com.ponyinc.minttrack.R.id.save_acct);
		//Toast
		lInflator = getLayoutInflater();
		layout = lInflator.inflate(R.layout.customtoast,
				(ViewGroup) findViewById(R.id.custom_toast_layout));
		warningText = (TextView) layout.findViewById(R.id.warning_text);
		toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		setWidgetVisiblity(Default);
	}
	
	/**OnClickListener for New Account button**/
	View.OnClickListener newAccountListener = new View.OnClickListener()
	{	   
		public void onClick(View v) 
		{
			setWidgetVisiblity(New);
		}
	};
	   
	/**OnClickListener for Edit Account button**/
	View.OnClickListener editAccountListener = new View.OnClickListener()
	{	   
		public void onClick(View v) 
		{
			setWidgetVisiblity(Update);
		}
	};
   
	/**OnClickListener for Save Account button**/
	View.OnClickListener saveAccountListener = new View.OnClickListener()
	{	   
		public void onClick(View v) 
		{	
			String name = String.valueOf(nameText.getText());
			String balance = String.valueOf(balText.getText());
			
			if(!name.equals(""))
			{
				if(balance == null || balance.equals(""))
					balance = "0.00";
				if(!newAccount.isEnabled())
				{
					budget.addAccount(name, Double.parseDouble(balance), activateCb.isChecked());
					if(newFromEntryTab) {finish();}
				}
				else if(!editAccount.isEnabled())
				{
					SimpleCursorAdapter s = (SimpleCursorAdapter) accountSpinner.getAdapter();
					Cursor spinCoursor = s.getCursor();
					
					spinCoursor.moveToPosition(accountSpinner.getSelectedItemPosition());
				
					budget.EditAccountName(spinCoursor.getInt(spinCoursor.getColumnIndex(_ID)), name);
					budget.EditAccountTotal(spinCoursor.getInt(spinCoursor.getColumnIndex(_ID)), Double.parseDouble(balance));
					
					if(activateCb.isChecked() == true)
						budget.ReactivateAccount(spinCoursor.getInt(spinCoursor.getColumnIndex(_ID)));
					
					else if(activateCb.isChecked() == false)
						budget.DeactivateAccount(spinCoursor.getInt(spinCoursor.getColumnIndex(_ID)));
				}
				fillAccountDropDown(accountSpinner);
				
				setWidgetVisiblity(Default);
			}
			//No name set
			else{
				warningText.setText("Please enter a name.");
				toast.show();
			}
				
		}
	};

	AdapterView.OnItemSelectedListener spinnerListener = new OnItemSelectedListener(){

		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			
			SimpleCursorAdapter s = (SimpleCursorAdapter) accountSpinner.getAdapter();
			Cursor spinCoursor = s.getCursor();
			
	//		spinCoursor.moveToPosition(accountSpinner.getSelectedItemPosition());
			spinCoursor.moveToPosition(arg2);
			Cursor cursor = budget.getAccount(spinCoursor.getInt(spinCoursor.getColumnIndex(_ID)));
			cursor.moveToFirst();
			
			String name = cursor.getString(cursor.getColumnIndex(ACCOUNT_NAME));
			String amount = cursor.getString(cursor.getColumnIndex(ACCOUNT_TOTAL));
			String activity = cursor.getString(cursor.getColumnIndex(ACCOUNT_ACTIVE));
			
			nameText.setText(name);
			balText.setText(amount);
			if(activity.equals("active"))
				activateCb.setChecked(true);
			else
				activateCb.setChecked(false);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// nothing needed here
			
		}
	};
	
	/** Fill in Account drop down
	 * @param s Spinner to be used to fill drop down*/
	public void fillAccountDropDown(Spinner s) {
		Cursor cursor = budget.getAllAccounts();
		SimpleCursorAdapter s1 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, cursor, new String[] {
				ACCOUNT_NAME, _ID }, new int[] { android.R.id.text1,
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
				balText.setText("");
				nameText.setText("");
				activateCb.setChecked(true);
				
				newAccount.setEnabled(true);
				editAccount.setEnabled(true);
				
				accountSpinner.setVisibility(View.GONE);
				tvAccountName.setVisibility(View.GONE);
				nameText.setVisibility(View.GONE);
				tvBalance.setVisibility(View.GONE);
				balText.setVisibility(View.GONE);
				saveButton.setVisibility(View.GONE);
				tvActive.setVisibility(View.GONE);
				activateCb.setVisibility(View.GONE);
				break;
			}	
			case(Update):
			{
				spinnerListener.onItemSelected(accountSpinner, null, 0, 0);
				newAccount.setEnabled(true); //
				editAccount.setEnabled(false);//
				accountSpinner.setVisibility(View.VISIBLE);//
				tvAccountName.setVisibility(View.VISIBLE);
				nameText.setVisibility(View.VISIBLE);
				tvBalance.setVisibility(View.VISIBLE);
				balText.setVisibility(View.VISIBLE);
				saveButton.setVisibility(View.VISIBLE);
				tvActive.setVisibility(View.VISIBLE);
				activateCb.setVisibility(View.VISIBLE);
				break;
			}
			case(New):
			{
				balText.setText("");
				nameText.setText("");
				activateCb.setChecked(true);
				newAccount.setEnabled(false);//
				editAccount.setEnabled(true);//
				accountSpinner.setVisibility(View.GONE);//
				tvAccountName.setVisibility(View.VISIBLE);
				nameText.setVisibility(View.VISIBLE);
				tvBalance.setVisibility(View.VISIBLE);
				balText.setVisibility(View.VISIBLE);
				saveButton.setVisibility(View.VISIBLE);
				tvActive.setVisibility(View.VISIBLE);
				activateCb.setVisibility(View.VISIBLE);
				
				if(newFromEntryTab)
					editAccount.setVisibility(View.GONE);
				break;
			}
			default:
		}
				
		
	}
}
