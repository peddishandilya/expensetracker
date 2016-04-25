package com.ponyinc.minttrack.tabs;

import static android.provider.BaseColumns._ID;
import static com.ponyinc.minttrack.Constants.ACCOUNT_NAME;
import static com.ponyinc.minttrack.Constants.CATEGORY_NAME;
import static com.ponyinc.minttrack.Constants.REASON_TYPE_EXPENSE;
import static com.ponyinc.minttrack.Constants.REASON_TYPE_INCOME;
import static com.ponyinc.minttrack.Constants.TRANSACTION_AMOUNT;
import static com.ponyinc.minttrack.Constants.TRANSACTION_CATEGORY;
import static com.ponyinc.minttrack.Constants.TRANSACTION_DATE;
import static com.ponyinc.minttrack.Constants.TRANSACTION_FROMACCOUNT;
import static com.ponyinc.minttrack.Constants.TRANSACTION_NOTE;
import static com.ponyinc.minttrack.Constants.TRANSACTION_TOACCOUNT;
import static com.ponyinc.minttrack.Constants.TRANSACTION_TYPE;
import static com.ponyinc.minttrack.Constants.TRANS_TYPE_EXPENSE;
import static com.ponyinc.minttrack.Constants.TRANS_TYPE_INCOME;
import static com.ponyinc.minttrack.Constants.TRANS_TYPE_TRANSFER;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ponyinc.minttrack.AboutUs;
import com.ponyinc.minttrack.Budget;
import com.ponyinc.minttrack.MintTrack;
import com.ponyinc.minttrack.R;
import com.ponyinc.minttrack.backuprestore.BackupManager;
import com.ponyinc.minttrack.help.HelpEntry;
import com.ponyinc.minttrack.tools.AccountManager;
import com.ponyinc.minttrack.tools.CategoryManager;
/**
 * This class represents the entry tab and all of it's pieces and interactions
 * @author Stephan Krach & Christopher Wilkins
 */
public class EntryActivity extends Activity {
	private Button mPickDate;
	private Button mSave, mIncomeButton, mExpenseButton, mTransButton, mCancelButton;
	private TextView mtxtPay_To, mtxtPay_From, mtxt_Reason, mWarning;
	private EditText mAmount;
	private EditText mNotes;
	private Spinner mCategory;
	private Spinner mPaymentType_To;
	private Spinner mPaymentType_From;
	private ImageView ivAddTo, ivAddFrom, ivAddCategory;
	private int mYear, mMonth, mDay;
	private String [] oldEntryInfo = new String[6];
	
	private boolean isUpdate = false;
	private boolean isFromManagerState = false;
	private long trans_ID;
	
	private Budget budget;

	private static final int DATE_DIALOG_ID = 0;
	
	private static final int INCOME_MODE = 0;
	private static final int EXPENSE_MODE = 1;
	private static final int TRANSFER_MODE = 2;
	
	//Toast variables
	private LayoutInflater lInflator;
	private View layout;
	private TextView warningText;
	private Toast toast;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.entry);
		budget = new Budget(this);

		SetWidgets();
		
		setListeners();
		
		fillCatDropDown(mCategory, REASON_TYPE_INCOME);
		fillAccountDropDown(mPaymentType_To);
		fillAccountDropDown(mPaymentType_From);
		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		
		// display the current date (this method is below)
		updateDate();
	}
	
	//Create menu
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	public void onResume()
	{
		super.onResume();
	
		//Only reset if user is not coming from the category or account managers
		if(!isFromManagerState)
			ResetTab();
		else
			isFromManagerState = false;
		
		fillCatDropDown(mCategory, REASON_TYPE_INCOME);
		fillAccountDropDown(mPaymentType_To);
		fillAccountDropDown(mPaymentType_From);
		
		setWidgetMode(INCOME_MODE);
		
		MintTrack mt = (MintTrack) this.getParent();
		if(mt.getTransactionID() >= 0){
			isUpdate = true; //set to update new
			trans_ID = mt.getTransactionID();
			setEntryTab(trans_ID);
			mt.setTransactionID(-1);
			mCancelButton.setVisibility(View.VISIBLE);
			mWarning.setText("You are editing a transaction");
			mWarning.setVisibility(View.VISIBLE);
		}
	}

	/** updates the date in the TextView*/
	private void updateDate() {
		mPickDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append((isUpdate ? mMonth : mMonth + 1)).append("-").append(mDay).append("-")
				.append(mYear).append(" "));
	}

	/** the callback received when the user "sets" the date in the dialog*/
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDate();
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}
	
	/** Fill in category drop down
	 *  @param s Spinner to be used to fill drop down
	 *  @param type fill for income(0) or expense(1)*/
	public void fillCatDropDown(Spinner s, int type) {
		Cursor cursor = budget.getCategorys(type);
		SimpleCursorAdapter s1 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, cursor, new String[] {
						CATEGORY_NAME, _ID }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		s1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(s1);
	}
	
	/** Fill in Account drop down
	 * @param s Spinner to be used to fill drop down*/
	public void fillAccountDropDown(Spinner s) {
		Cursor cursor = budget.getActiveAccounts();
		SimpleCursorAdapter s1 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, cursor, new String[] {
				ACCOUNT_NAME, _ID }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		s1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(s1);
	}
	/**Set all widgets for this view */
	private void SetWidgets()
	{
		mSave = (Button) findViewById(R.id.saveButton);
		mPickDate = (Button) findViewById(R.id.pickDate);
		mCancelButton = (Button) findViewById(R.id.cancelButton);
		mCategory = (Spinner) findViewById(R.id.reason1);
		mPaymentType_To = (Spinner) findViewById(R.id.paytype_To);
		mPaymentType_From = (Spinner) findViewById(R.id.paytype_From);
		mAmount = (EditText) findViewById(R.id.entry1);
		mNotes = (EditText) findViewById(R.id.notes);
		mIncomeButton = (Button) findViewById(R.id.incomeButton);
		mExpenseButton = (Button) findViewById(R.id.expenseButton);
		mTransButton = (Button) findViewById(R.id.transferButton);
		mtxtPay_To = (TextView) findViewById(R.id.txt_type_to);
		ivAddTo = (ImageView) findViewById(R.id.add_to_account_button);
		mtxtPay_From = (TextView) findViewById(R.id.txt_type_from);
		ivAddFrom = (ImageView) findViewById(R.id.add_from_account_button);
		mtxt_Reason = (TextView) findViewById(R.id.txt_reason);
		ivAddCategory = (ImageView) findViewById(R.id.add_category_button);
		mWarning = (TextView) findViewById(R.id.Warning);
		mWarning.setVisibility(View.VISIBLE);
		mIncomeButton.setEnabled(false);
		mPaymentType_From.setVisibility(View.GONE);
		mtxtPay_From.setVisibility(View.GONE);
		//Toast
		lInflator = getLayoutInflater();
		layout = lInflator.inflate(R.layout.customtoast,
				(ViewGroup) findViewById(R.id.custom_toast_layout));
		warningText = (TextView) layout.findViewById(R.id.warning_text);
		toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
	}
	/** Handles item selections
	 * @param item 
	 * @return True if help selected False if not*/
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId())
	    {
		    case(R.id.backup):
		    	BackupManager.openDB();
				BackupManager bm = new BackupManager();
	    		bm.exportToXml();
	    		BackupManager.closeDB();
				return true;
		    case(R.id.export):
				BackupManager.openDB();
				BackupManager bmh = new BackupManager();
				bmh.exportToHtml();
				BackupManager.closeDB();
				return true;
	    	case (R.id.help):
	    		executeHelpIntent();
	    		return true;
	    	case (R.id.info):
	    		executeInfoIntent();
	    		return true;
	    }
	    return false;
	}
	/** Starts Help*/
	private void executeHelpIntent()
	{
		 Intent i = new Intent(this, HelpEntry.class);
	     startActivity(i);
	}
	
	/** Starts Help*/
	private void executeInfoIntent()
	{
		 Intent i = new Intent(this, AboutUs.class);
	     startActivity(i);
	}
	
	/**
	 * Reset all widgets to default
	 */
	void ResetTab()
	{
		mNotes.setText("");
		mAmount.setText("");
		mCategory.setSelection(0);
		mPaymentType_From.setSelection(0);
		mPaymentType_To.setSelection(0);
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mIncomeButton.setVisibility(View.VISIBLE);
		mExpenseButton.setVisibility(View.VISIBLE);
		mTransButton.setVisibility(View.VISIBLE);
		mIncomeButton.setEnabled(false);
		mExpenseButton.setEnabled(true);
		mTransButton.setEnabled(true);
		mCancelButton.setVisibility(View.GONE);
		isUpdate = false;
		trans_ID = -1;
		updateDate();
	}
	
	/**
	 * Get transaction and fill entry tab
	 * @param trans_ID id to be used to fill tab
	 */
	private void setEntryTab(long trans_ID)
	{
		Cursor cursor_trans = budget.getTransaction(trans_ID);
		cursor_trans.moveToFirst();
		
		String amount = cursor_trans.getString(cursor_trans.getColumnIndexOrThrow(TRANSACTION_AMOUNT));
		String note = cursor_trans.getString(cursor_trans.getColumnIndex(TRANSACTION_NOTE));
		int from_id = cursor_trans.getInt(cursor_trans.getColumnIndex(TRANSACTION_FROMACCOUNT));
		int to_id = cursor_trans.getInt(cursor_trans.getColumnIndex(TRANSACTION_TOACCOUNT));
		int cat_id = cursor_trans.getInt(cursor_trans.getColumnIndex(TRANSACTION_CATEGORY));
		String date = cursor_trans.getString(cursor_trans.getColumnIndex(TRANSACTION_DATE));
		int trans_type = cursor_trans.getInt(cursor_trans.getColumnIndex(TRANSACTION_TYPE));
		
		SimpleCursorAdapter from = (SimpleCursorAdapter) mPaymentType_From.getAdapter();
		SimpleCursorAdapter to = (SimpleCursorAdapter) mPaymentType_To.getAdapter();
		SimpleCursorAdapter reason = (SimpleCursorAdapter) mCategory.getAdapter();
		
		Cursor To_acc_cursor = to.getCursor();
		To_acc_cursor.moveToFirst();
		Cursor cat_cursor = reason.getCursor();
		cat_cursor.moveToFirst();
		Cursor From_acc_cursor = from.getCursor();
		From_acc_cursor.moveToFirst();
		
		mAmount.setText(amount);
		mNotes.setText(note);
		mMonth = Integer.parseInt(date.substring(4, 6));
		mDay = Integer.parseInt(date.substring(6));
		mYear = Integer.parseInt(date.substring(0, 4));
		mCategory.setSelection(setCursorPosition(cat_cursor, cat_id));
		mPaymentType_From.setSelection(setCursorPosition(From_acc_cursor, from_id));
		mPaymentType_To.setSelection(setCursorPosition(To_acc_cursor, to_id));
		if(trans_type == TRANS_TYPE_INCOME)
			setWidgetMode(INCOME_MODE);
		else if(trans_type == TRANS_TYPE_EXPENSE)
			setWidgetMode(EXPENSE_MODE);
		else if(trans_type == TRANS_TYPE_TRANSFER)
			setWidgetMode(TRANSFER_MODE);
		updateDate();
		oldEntryInfo = getEntryInfo();
	}
	
	/**
	 * Sets the cursor to the position of the element and returns the position index
	 * @param c cursor object
	 * @param id id to search for
	 * @return position index of the found element
	 */
	public int setCursorPosition(Cursor c, int id)
	{
		for(; c.getPosition() < c.getCount(); c.moveToNext())
		{
			if(c.getInt(c.getColumnIndex(_ID)) == id)
			{
				return c.getPosition();
			}	
		}
		return 0;
	}
	
	/**
	 * Sets the widgets for a specific entry mode
	 * @param mode 0 = Income; 1 = Expense; 2 = Transfer
	 */
	public void setWidgetMode(int mode)
	{
		switch(mode)
		{
			case(INCOME_MODE):
			{
				mIncomeButton.setEnabled(false);
				
				if(isUpdate){
					mExpenseButton.setVisibility(View.INVISIBLE);
					mTransButton.setVisibility(View.INVISIBLE);
				}
				mExpenseButton.setEnabled(true);
				mTransButton.setEnabled(true);
				mPaymentType_From.setVisibility(View.GONE);
				mtxtPay_From.setVisibility(View.GONE);
				mPaymentType_To.setVisibility(View.VISIBLE);
				mtxtPay_To.setVisibility(View.VISIBLE);
				mCategory.setVisibility(View.VISIBLE);
				mtxt_Reason.setVisibility(View.VISIBLE);
				
				ivAddTo.setVisibility(View.VISIBLE);
				ivAddFrom.setVisibility(View.GONE);
				ivAddCategory.setVisibility(View.VISIBLE);
				
				fillCatDropDown(mCategory, REASON_TYPE_INCOME);
				if(!isUpdate)
					mWarning.setVisibility(View.GONE);
				break;
			}
			
			case(EXPENSE_MODE):
			{
				mExpenseButton.setEnabled(false);
				
				if(isUpdate){
					mIncomeButton.setVisibility(View.INVISIBLE);
					mTransButton.setVisibility(View.INVISIBLE);
				}
				mIncomeButton.setEnabled(true);
				mTransButton.setEnabled(true);
				mPaymentType_From.setVisibility(View.VISIBLE);
				mtxtPay_From.setVisibility(View.VISIBLE);
				mPaymentType_To.setVisibility(View.GONE);
				mtxtPay_To.setVisibility(View.GONE);
				mCategory.setVisibility(View.VISIBLE);
				mtxt_Reason.setVisibility(View.VISIBLE);
				
				ivAddTo.setVisibility(View.GONE);
				ivAddFrom.setVisibility(View.VISIBLE);
				ivAddCategory.setVisibility(View.VISIBLE);
				
				fillCatDropDown(mCategory, REASON_TYPE_EXPENSE);
				if(!isUpdate)
					mWarning.setVisibility(View.GONE);
				break;
			}
			
			case(TRANSFER_MODE):
			{
				mTransButton.setEnabled(false);
				
				if(isUpdate){
					mExpenseButton.setVisibility(View.INVISIBLE);
					mIncomeButton.setVisibility(View.INVISIBLE);
				}
				mIncomeButton.setEnabled(true);
				mExpenseButton.setEnabled(true);				
				mPaymentType_From.setVisibility(View.VISIBLE);
				mtxtPay_From.setVisibility(View.VISIBLE);
				mPaymentType_To.setVisibility(View.VISIBLE);
				mtxtPay_To.setVisibility(View.VISIBLE);
				mCategory.setVisibility(View.GONE);
				mtxt_Reason.setVisibility(View.GONE);
				
				ivAddTo.setVisibility(View.VISIBLE);
				ivAddFrom.setVisibility(View.VISIBLE);
				ivAddCategory.setVisibility(View.GONE);
				
				if(!isUpdate)
					mWarning.setVisibility(View.GONE);
				break;
			}
			default:
		}
	}
	public void setListeners()
	{
		mCancelButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v)
			{
				ResetTab();
				//Get rid of message when done editing
				mWarning.setVisibility(View.GONE);
			}
		});
		mSave.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v)
			{
				try{
					SimpleCursorAdapter s1 = (SimpleCursorAdapter) mPaymentType_To.getAdapter();
					SimpleCursorAdapter s2 = (SimpleCursorAdapter) mCategory.getAdapter();
					SimpleCursorAdapter s3 = (SimpleCursorAdapter) mPaymentType_From.getAdapter();
					
					Cursor To_acc_cursor = s1.getCursor();
					Cursor cat_cursor = s2.getCursor();
					Cursor From_acc_cursor = s3.getCursor();
					
					To_acc_cursor.moveToPosition(mPaymentType_To.getSelectedItemPosition());
					cat_cursor.moveToPosition(mCategory.getSelectedItemPosition());
					From_acc_cursor.moveToPosition(mPaymentType_From.getSelectedItemPosition());

					String S_amount = String.valueOf(mAmount.getText());
					
					//Formatting
					//No value entered - invalid
					if(S_amount.equals("")){
						warningText.setText("You have not entered an amount.");
						toast.show();
					}
					//Amount string does not contain a decimal point
					else if(!S_amount.contains(".")){
						S_amount = S_amount + ".00";
					}
					//Amount string only contains a decimal point
					else if(S_amount.endsWith(".") && S_amount.length()==1){
						S_amount = "0.00";
					}
					//Amount string ends with a decimal point
					else if(S_amount.endsWith(".")){
						S_amount = S_amount + ".00";
					}
					
					//Entered 0
					if(S_amount.equals("0.00")){
						warningText.setText("Please enter a non-zero amount.");
						toast.show();
					}
					//Valid value entered - process
					else if (isValid(S_amount))
					{
						if(isUpdate) //update old
						{
							if(!mIncomeButton.isEnabled())
								budget.updateIncome(trans_ID, oldEntryInfo, getEntryInfo());
							else if(!mExpenseButton.isEnabled())
							{
								if(!budget.updateExpense(trans_ID, oldEntryInfo, getEntryInfo()))
								{
									warningText.setText("Funds not sufficient to perform this expense transaction");
									toast.show();
								}
							}
							else if(!mTransButton.isEnabled())
							{
								if(!budget.updateTransfer(trans_ID, oldEntryInfo, getEntryInfo()))
								{
									warningText.setText("Funds not sufficient to perform this transfer transaction");
									toast.show();
								}
							}
							
							ResetTab();
						}
						else//create new
						{
							if(!mIncomeButton.isEnabled())
								budget.Income(getEntryInfo());
							else if(!mExpenseButton.isEnabled())
							{
								if(!budget.Expense(getEntryInfo()))
								{
									warningText.setText("Funds not sufficient to perform this expense transaction.");
									toast.show();
								}
							}
							else if(!mTransButton.isEnabled())
							{
								if(!budget.Transfer(getEntryInfo()))
								{
									warningText.setText("Funds not sufficient to perform this transfer transaction.");
									toast.show();
								}
							}
							
							ResetTab();
						}
						//Get rid of message when done editing
						mWarning.setVisibility(View.GONE);
					}
					//Bad value entered - invalid
					else{
						warningText.setText("Please enter a valid amount");
						toast.show();
					}
					
				}catch(Exception e){
					e.printStackTrace();
					warningText.setText("Something went wrong. Check the log for details.");
					toast.show();
				}
			}
			
		});
		mIncomeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) 
			{
				setWidgetMode(INCOME_MODE);
			}
		});
		mExpenseButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) 
			{
				setWidgetMode(EXPENSE_MODE);
			}
		});
		mTransButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) 
			{
				setWidgetMode(TRANSFER_MODE);
			}
		});
		mPickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		ivAddTo.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				isFromManagerState = true;
				switchActivity(1);
			}
		});
		ivAddFrom.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				isFromManagerState = true;
				switchActivity(1);
			}
		});
		ivAddCategory.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				isFromManagerState = true;
				switchActivity(2);
			}
		});
	}
	
	protected void switchActivity(int i) 
	{
		switch(i)
		{
		//Adding an account
		case 1:
			Intent accountIntent = new Intent(this, AccountManager.class);
			accountIntent.putExtra("newFromEntryTab", true);
			startActivity(accountIntent);
			break;
		case 2:
			//Adding an expense category
			if(!mExpenseButton.isEnabled())
			{
				Intent expenseCategoryIntent = new Intent(this, CategoryManager.class);
				expenseCategoryIntent.putExtra("categoryType", "expense");
				expenseCategoryIntent.putExtra("newFromEntryTab", true);
				startActivity(expenseCategoryIntent);
			}
			//Adding income category - defaults to income category when in transfer mode
			else
			{
				Intent incomeCategoryIntent = new Intent(this, CategoryManager.class);
				incomeCategoryIntent.putExtra("categoryType", "income");
				incomeCategoryIntent.putExtra("newFromEntryTab", true);
				startActivity(incomeCategoryIntent);
			}
			break;
		}
	}

	/**
     * Checks to see if str is valid input
     * @param str String entered by the user
     * @return true if string is OK, or false if not
     */
    public boolean isValid(String str){
    	if(str == null || str.equalsIgnoreCase(""))
    		return false;
    	if(str.contains(".") && str.indexOf('.') < str.lastIndexOf('.'))
			return false;
    	for(int c = 0; c < str.length(); c++){
    		if((str.charAt(c) <= 46 || str.charAt(c) >= 58) && (str.charAt(c) != '.'))
    			return false;
    	}
    	return true;
    }
    
    /**
	 * Gather all info currently displayed in Entry activity
	 * @return array with entry activity info
	 */
	public String[] getEntryInfo()
	{
		String entryInfo[] = new String[6];
		
		/*return new Transaction(
			(String.valueOf(mYear) + 
				(isUpdate ? (mMonth < 10 ? "0"+String.valueOf(mMonth):String.valueOf(mMonth)) : (mMonth+1 < 10 ? "0"+String.valueOf(mMonth+1):String.valueOf(mMonth+1))) + 
				(mDay < 10 ? "0"+String.valueOf(mDay) : String.valueOf(mDay))),
			mPaymentType_To.getSelectedItemId(),
			mPaymentType_From.getSelectedItemId(),
			Double.parseDouble(mAmount.getText().toString()),
			mCategory.getSelectedItemId(),
			mNotes.getText().toString()
		)*/
		
		//Amount
		entryInfo[0] = (mAmount.getText().toString() != null ? mAmount.getText().toString() : "");
		//Date
		entryInfo[1] = (String.valueOf(mYear) + 
				(isUpdate ? (mMonth < 10 ? "0"+String.valueOf(mMonth):String.valueOf(mMonth)) : (mMonth+1 < 10 ? "0"+String.valueOf(mMonth+1):String.valueOf(mMonth+1))) + 
				(mDay < 10 ? "0"+String.valueOf(mDay) : String.valueOf(mDay)));
		//From
		entryInfo[2] = (String.valueOf(mPaymentType_From.getSelectedItemId()) != null ? String.valueOf(mPaymentType_From.getSelectedItemId()) : "");
		//To
		entryInfo[3] = (String.valueOf(mPaymentType_To.getSelectedItemId()) != null ? String.valueOf(mPaymentType_To.getSelectedItemId()) : "");
		//Category
		entryInfo[4] = (String.valueOf(mCategory.getSelectedItemId()) != null ? String.valueOf(mCategory.getSelectedItemId()) : "");	
		//Note
		entryInfo[5] = (mNotes.getText().toString() != null ? mNotes.getText().toString() : "");	
		return entryInfo;
	}
	
}
