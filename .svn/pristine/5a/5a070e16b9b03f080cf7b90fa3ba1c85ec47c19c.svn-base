package com.ponyinc.minttrack.tabs;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import com.ponyinc.minttrack.AboutUs;
import com.ponyinc.minttrack.AuditCursorAdapter;
import com.ponyinc.minttrack.Budget;
import com.ponyinc.minttrack.MintTrack;
import com.ponyinc.minttrack.R;
import com.ponyinc.minttrack.backuprestore.BackupManager;
import com.ponyinc.minttrack.help.HelpAudit;

/** This class is used to represent the listactivity for reviewing transactions
*	@author Christopher C. Wilkins
*/
public class AuditActivity extends ListActivity {

	public Budget budget;
	public Button btnToDate, btnFromDate, btnQuery, btnEdit, btnDelete;
	private int toYear;
	private int toMonth;
	private int toDay;
	private int fromYear;
	private int fromMonth;
	private int fromDay;
	static final int TODATE_DIALOG_ID = 0;
	static final int FROMDATE_DIALOG_ID = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audit);
		initializeButtons();
		createOnClickListeners();
		budget = new Budget(this);
		// get the current date for both date buttons
		toYear = c.get(Calendar.YEAR);
		toMonth = c.get(Calendar.MONTH);
		toDay = c.get(Calendar.DAY_OF_MONTH);
		fromYear = c.get(Calendar.YEAR);
		fromMonth = c.get(Calendar.MONTH);
		fromDay = c.get(Calendar.DAY_OF_MONTH);
		getListView().setEmptyView(findViewById(R.id.empty));
		updateDisplay();
	}

	/** the callback received when the user "sets" the date in the dialog*/
	private DatePickerDialog.OnDateSetListener ToDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			toYear = year;
			toMonth = monthOfYear;
			toDay = dayOfMonth;
			updateDisplay();
		}
	};
	/** the callback received when the user "sets" the date in the dialog*/
	private DatePickerDialog.OnDateSetListener FromDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) 
		{
			fromYear = year;
			fromMonth = monthOfYear;
			fromDay = dayOfMonth;
			updateDisplay();
		}
	};
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) 
		{
		case TODATE_DIALOG_ID:
			return new DatePickerDialog(this, ToDateSetListener, toYear, toMonth,
					toDay);
		case FROMDATE_DIALOG_ID:
			return new DatePickerDialog(this, FromDateSetListener, toYear, toMonth,
					toDay);
		default:
			return null;
		}
	}
	private void updateDisplay() {
		btnToDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(toMonth + 1).append("-").append(toDay).append("-")
				.append(toYear).append(" "));
		btnFromDate.setText(new StringBuilder()
		// Month is 0 based so add 1
		.append(fromMonth + 1).append("-").append(fromDay).append("-")
		.append(fromYear).append(" "));
	}
	/** Method used to inflate popup menu at bottom
	*	@return boolean Whether the menu is successfully populated
	* 	@param Menu The many object that you want to populate 
	*/
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.menu, menu);
	    return true;
	}
	/** Method used to populate the ListActivity from a database Cursor
	*	@param Cursor A cursor containing the rows of the transaction database that you want to display
	*/
	private void showEvents(Cursor cursor) {
		setListAdapter(new AuditCursorAdapter(this, cursor));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		final long clickItemID = id;
		if(btnEdit == null || btnDelete == null){
		btnEdit = (Button) v.findViewById(R.id.editTransactionBtn);
		btnDelete = (Button) v.findViewById(R.id.deleteTransactionBtn);
		btnEdit.setVisibility(View.VISIBLE);
		btnDelete.setVisibility(View.VISIBLE);
			btnEdit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					switchTabSpecial(clickItemID);
				}
			});
			btnDelete.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					budget.deleteTransaction(clickItemID);
					executeQuery();
;				}
			});
		}
		else{
			btnEdit.setVisibility(View.GONE);
			btnDelete.setVisibility(View.GONE);
			btnEdit = (Button) v.findViewById(R.id.editTransactionBtn);
			btnDelete = (Button) v.findViewById(R.id.deleteTransactionBtn);
			btnEdit.setVisibility(View.VISIBLE);
			btnDelete.setVisibility(View.VISIBLE);
			btnEdit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					switchTabSpecial(clickItemID);
				}
			});
			btnDelete.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					budget.deleteTransaction(clickItemID);
					executeQuery();
				}
			});
		}
    }
	
	/* Handles item selections */
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
	    		executeIntent(HelpAudit.class);
	    		return true;
	    	case (R.id.info):
	    		executeIntent(AboutUs.class);
	    		return true;
	    }
		
	    return false;
	}
	
	private void executeIntent(Class<?> cls)
	{
		 Intent i = new Intent(this, cls);
	     startActivity(i);
	}

	/**
	 * Method is used to switch tabs nicely
	 */
	private void switchTabSpecial(long l){
		MintTrack ParentActivity = (MintTrack) this.getParent();
		ParentActivity.setTransactionID(l);
		ParentActivity.switchTabSpecial(1);
	}
	/**
	 * Method is used to initialize button object
	 */
	private void initializeButtons(){
		btnToDate = (Button) findViewById(R.id.btnToDate);
		btnFromDate = (Button) findViewById(R.id.btnFromDate);
		btnQuery = (Button) findViewById(R.id.btnQuery);
	}
	
	/**
	 * Method is used to initialize all of the button onClickListeners
	 */
	private void createOnClickListeners(){
		btnToDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(TODATE_DIALOG_ID);
			}
		});
		btnFromDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(FROMDATE_DIALOG_ID);
			}
		});
		btnQuery.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				executeQuery();
			}
		});
	}
	
	private void executeQuery()
	{
		String toDate = toYear + String.format("%02d", toMonth+1) + String.format("%02d", toDay);
		String fromDate = fromYear + String.format("%02d", fromMonth+1) + String.format("%02d", fromDay);
		long td = Integer.parseInt(toDate);
		long fd = Integer.parseInt(fromDate);
		
		/*
		 * Check to make sure from date is less than too date make sure no date
		 * is greater than the current date
		 */
		if(td >= fd)
		{
			showEvents(budget.getTransactions(fromDate,toDate));
		}
	}
}