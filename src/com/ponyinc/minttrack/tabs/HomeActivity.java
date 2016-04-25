package com.ponyinc.minttrack.tabs;

import static com.ponyinc.minttrack.Constants.REASON_TYPE_EXPENSE;
import static com.ponyinc.minttrack.Constants.REASON_TYPE_INCOME;
import static com.ponyinc.minttrack.Constants.TRANSACTION_AMOUNT;
import static com.ponyinc.minttrack.Constants.TRANSACTION_DATE;
import static com.ponyinc.minttrack.Constants.TRANSACTION_TYPE;
import static com.ponyinc.minttrack.Constants.TRANS_TYPE_EXPENSE;
import static com.ponyinc.minttrack.Constants.TRANS_TYPE_INCOME;
import static com.ponyinc.minttrack.Constants.TRANS_TYPE_TRANSFER;

import java.text.NumberFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.ponyinc.minttrack.AboutUs;
import com.ponyinc.minttrack.Budget;
import com.ponyinc.minttrack.R;
import com.ponyinc.minttrack.backuprestore.BackupManager;
import com.ponyinc.minttrack.help.HelpHome;

/**
 * This class implements a summary screen to the MintTrack application. This
 * summary screen displays an income, expense, and grand total of transactions
 * as well as a short list of the last four transactions entered.
 * 
 * @author Jeff Titus
 **/
public class HomeActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		Budget budget = new Budget(this);

		Cursor AccountCursor = budget.getAllAccounts();
		Cursor CategoryCursor = budget.getAllCategories();

		if (AccountCursor.getCount() == 0) {
			budget.addAccount("Savings", 0.00, true);
			budget.addAccount("Checking", 0.00, true);
			budget.addAccount("Pocket Money", 0.00, true);
		}
		if (CategoryCursor.getCount() == 0) {
			budget.addCategory("Job 1", 0.00, REASON_TYPE_INCOME, true);
			budget.addCategory("Food", 0.00, REASON_TYPE_EXPENSE, true);
			budget.addCategory("Bills", 0.00, REASON_TYPE_EXPENSE, true);
			budget.addCategory("Gas", 0.00, REASON_TYPE_EXPENSE, true);
		}

		CategoryCursor.close();
		AccountCursor.close();
		
		budget.getCategoryDB().close();
		budget.getAccountDB().close();
		
		budget.getCategoryCursor().close();
		budget.getAccountCursor().close();

		updateDisplay();
	}

	@Override
	public void onResume() {
		super.onResume();
		updateDisplay();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case(R.id.backup):
			BackupManager.openDB();
			BackupManager bmx = new BackupManager();
    		bmx.exportToXml();
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

	/** Executes help functionality **/
	private void executeHelpIntent() {
		Intent i = new Intent(this, HelpHome.class);
		startActivity(i);
	}

	/** Executes Information screen **/
	private void executeInfoIntent() {
		Intent i = new Intent(this, AboutUs.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	/**
	 * Updates the display.<br>
	 * Called in <b>onCreate()</b> and <b>onResume()</b>
	 **/
	private void updateDisplay() {
		Budget budget = new Budget(this);
		Cursor TransactionsCursor = budget.getTransactions();
		TransactionsCursor.requery();
		double d_inTotal = 0;
		double d_exTotal = 0;
		TransactionsCursor.moveToFirst();
		// Search through transactions table and sum up all income and expenses
		while (!TransactionsCursor.isAfterLast()) {
			if (TransactionsCursor.getInt(TransactionsCursor
					.getColumnIndex(TRANSACTION_TYPE)) == TRANS_TYPE_INCOME)
				d_inTotal += TransactionsCursor.getDouble(TransactionsCursor
						.getColumnIndex(TRANSACTION_AMOUNT));
			else if (TransactionsCursor.getInt(TransactionsCursor
					.getColumnIndex(TRANSACTION_TYPE)) == TRANS_TYPE_EXPENSE)
				d_exTotal += TransactionsCursor.getDouble(TransactionsCursor
						.getColumnIndex(TRANSACTION_AMOUNT));
			TransactionsCursor.moveToNext();
		}
		// Updates individual data fields
		displayIncomeTotal(d_inTotal);
		displayExpenseTotal(d_exTotal);
		displayGrandTotal(d_inTotal - d_exTotal);
		displayRecentTransactions(TransactionsCursor);

		TransactionsCursor.close();
		budget.getTransactionDB().close();
		budget.getTransactionCursor().close();
	}

	/**
	 * Displays the total value in the income text field
	 * 
	 * @param iT
	 *            Income total
	 **/
	private void displayIncomeTotal(double iT) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
		String s_incomeTotal = nf.format(iT);
		TextView tv_iTotal = (TextView) findViewById(R.id.in_total);
		tv_iTotal.setText(s_incomeTotal);
	}

	/**
	 * Displays the total value in the expense text field
	 * 
	 * @param eT
	 *            Expense total
	 **/
	private void displayExpenseTotal(double eT) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
		String s_expenseTotal = nf.format(eT);
		TextView tv_eTotal = (TextView) findViewById(R.id.ex_total);
		tv_eTotal.setText(s_expenseTotal);
	}

	/**
	 * Displays the total value in the grand total text field
	 * 
	 * @param gT
	 *            Grand total
	 **/
	private void displayGrandTotal(double gT) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
		String s_grandTotal = nf.format(gT);
		TextView tv_gTotal = (TextView) findViewById(R.id.gr_total);
		tv_gTotal.setText(s_grandTotal);
	}

	/**
	 * Returns the transaction type in string form
	 * 
	 * @param transRefNum
	 *            Transaction reference number
	 * @return The transaction type as a String
	 */
	private String getTransactionString(int transRefNum) {
		String retValue = null;

		switch (transRefNum) {
		case TRANS_TYPE_INCOME:
			retValue = "Income";
			break;
		case TRANS_TYPE_EXPENSE:
			retValue = "Expense";
			break;
		case TRANS_TYPE_TRANSFER:
			retValue = "Transfer";
			break;
		default:
			retValue = "Unknown";
			break;
		}

		return retValue;
	}

	/**
	 * Displays recent transactions at the bottom of the home tab
	 * 
	 * @param TransactionsCursor
	 *            Iterator used to parse database entries
	 */
	private void displayRecentTransactions(Cursor TransactionsCursor) {
		TextView tv_date1 = (TextView) findViewById(R.id.date1);
		TextView tv_type1 = (TextView) findViewById(R.id.type1);
		TextView tv_amt1 = (TextView) findViewById(R.id.amount1);
		TextView tv_date2 = (TextView) findViewById(R.id.date2);
		TextView tv_type2 = (TextView) findViewById(R.id.type2);
		TextView tv_amt2 = (TextView) findViewById(R.id.amount2);
		TextView tv_date3 = (TextView) findViewById(R.id.date3);
		TextView tv_type3 = (TextView) findViewById(R.id.type3);
		TextView tv_amt3 = (TextView) findViewById(R.id.amount3);
		TextView tv_date4 = (TextView) findViewById(R.id.date4);
		TextView tv_type4 = (TextView) findViewById(R.id.type4);
		TextView tv_amt4 = (TextView) findViewById(R.id.amount4);

		// Clean up display before generation
		tv_date1.setText("");
		tv_type1.setText("");
		tv_amt1.setText("");
		tv_date2.setText("");
		tv_type2.setText("");
		tv_amt2.setText("");
		tv_date3.setText("");
		tv_type3.setText("");
		tv_amt3.setText("");
		tv_date4.setText("");
		tv_type4.setText("");
		tv_amt4.setText("");

		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);

		// Cursor moved to first item in the table
		TransactionsCursor.moveToLast();
		// Set display information for first list item
		if (!TransactionsCursor.isBeforeFirst()) {

			tv_date1.setText(getFormattedDate(TransactionsCursor
					.getString(TransactionsCursor
							.getColumnIndex(TRANSACTION_DATE))));
			tv_type1.setText(getTransactionString(TransactionsCursor
					.getInt(TransactionsCursor.getColumnIndex(TRANSACTION_TYPE))));
			tv_amt1.setText(nf.format(TransactionsCursor
					.getDouble(TransactionsCursor
							.getColumnIndex(TRANSACTION_AMOUNT))));
			TransactionsCursor.moveToPrevious();
		}
		// Set display information for second list item
		if (!TransactionsCursor.isBeforeFirst()) {
			tv_date2.setText(getFormattedDate(TransactionsCursor
					.getString(TransactionsCursor
							.getColumnIndex(TRANSACTION_DATE))));
			tv_type2.setText(getTransactionString(TransactionsCursor
					.getInt(TransactionsCursor.getColumnIndex(TRANSACTION_TYPE))));
			tv_amt2.setText(nf.format(TransactionsCursor
					.getDouble(TransactionsCursor
							.getColumnIndex(TRANSACTION_AMOUNT))));
			TransactionsCursor.moveToPrevious();
		}
		// Set display information for third list item
		if (!TransactionsCursor.isBeforeFirst()) {
			tv_date3.setText(getFormattedDate(TransactionsCursor
					.getString(TransactionsCursor
							.getColumnIndex(TRANSACTION_DATE))));
			tv_type3.setText(getTransactionString(TransactionsCursor
					.getInt(TransactionsCursor.getColumnIndex(TRANSACTION_TYPE))));
			tv_amt3.setText(nf.format(TransactionsCursor
					.getDouble(TransactionsCursor
							.getColumnIndex(TRANSACTION_AMOUNT))));
			TransactionsCursor.moveToPrevious();
		}
		// Set display information for fourth list item
		if (!TransactionsCursor.isBeforeFirst()) {

			tv_date4.setText(getFormattedDate(TransactionsCursor
					.getString(TransactionsCursor
							.getColumnIndex(TRANSACTION_DATE))));

			tv_type4.setText(getTransactionString(TransactionsCursor
					.getInt(TransactionsCursor.getColumnIndex(TRANSACTION_TYPE))));

			tv_amt4.setText(nf.format(TransactionsCursor
					.getDouble(TransactionsCursor
							.getColumnIndex(TRANSACTION_AMOUNT))));
		}
	}

	/**
	 * Returns the date string in a date-like format (mm/dd/yyyy)
	 * 
	 * @param str
	 *            An unformatted date string
	 * @return String that shows the date of the transaction in mm/dd/yyyy
	 *         format
	 */
	private String getFormattedDate(String str) {
		return str.substring(4, 6) + "/" + str.substring(6) + "/"
				+ str.substring(0, 4);
	}
}
