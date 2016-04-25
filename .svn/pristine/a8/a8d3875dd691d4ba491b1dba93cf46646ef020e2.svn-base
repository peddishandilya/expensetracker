package com.ponyinc.minttrack.tools;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ponyinc.minttrack.AccountStatsCursorAdapter;
import com.ponyinc.minttrack.Budget;
import com.ponyinc.minttrack.CategoryStatsCursorAdapter;
import com.ponyinc.minttrack.R;

public class StatsViewer extends ListActivity {
	private Budget budget;
	private int numberOfTransactions;
	private int numberOfCategories;
	private int numberOfAccounts;
	private TextView tvNOT, tvNOC, tvNOA;
	private Cursor transactionCursor, categoryCursor, accountCursor;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statsviewer);
		budget = new Budget(this);
		tvNOT = (TextView) findViewById(R.id.NUMTRANS);
		tvNOC = (TextView) findViewById(R.id.NUMCATS);
		tvNOA = (TextView) findViewById(R.id.NUMACCTS);
		transactionCursor = budget.getTransactions();
		categoryCursor = budget.getAllCategories();
		accountCursor = budget.getAllAccounts();
		
		getNumberOfTransactions();
		getStarRating();
		getNumberOfCategories();
		getCategoryList();
		//getListView().setEmptyView(findViewById(R.id.empty));
		getNumberOfAccounts();
		getAccountList();
		getListView().setEmptyView(findViewById(R.id.empty));
		
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		transactionCursor.close();
		categoryCursor.close();
		accountCursor.close();
		budget = null;
	}

	/**
	 * Retrieves number of transaction entries in database
	 */
	private void getNumberOfTransactions() {
		//transactionCursor = budget.getTransactions();
		numberOfTransactions = transactionCursor.getCount();
		tvNOT.setText(String.valueOf(numberOfTransactions));
	}

	/**
	 * Get rating based on number of transactions in database
	 */
	private void getStarRating() {
		//Based off of number of transactions
		if(numberOfTransactions >= 0){
			findViewById(R.id.BRONZESTAR).setVisibility(View.VISIBLE);
			findViewById(R.id.SILVERSTAR).setVisibility(View.INVISIBLE);
			findViewById(R.id.GOLDSTAR).setVisibility(View.INVISIBLE);
		}
		else if(numberOfTransactions >= 5){
			findViewById(R.id.BRONZESTAR).setVisibility(View.INVISIBLE);
			findViewById(R.id.SILVERSTAR).setVisibility(View.VISIBLE);
			findViewById(R.id.GOLDSTAR).setVisibility(View.INVISIBLE);
		}
		else if(numberOfTransactions >= 10){
			findViewById(R.id.BRONZESTAR).setVisibility(View.INVISIBLE);
			findViewById(R.id.SILVERSTAR).setVisibility(View.INVISIBLE);
			findViewById(R.id.GOLDSTAR).setVisibility(View.VISIBLE);
		}
		else{
			findViewById(R.id.BRONZESTAR).setVisibility(View.INVISIBLE);
			findViewById(R.id.SILVERSTAR).setVisibility(View.INVISIBLE);
			findViewById(R.id.GOLDSTAR).setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * Retrieves number of categories in the database
	 */
	private void getNumberOfCategories() {
		//categoryCursor = budget.getAllCategorys();
		numberOfCategories = categoryCursor.getCount();
		tvNOC.setText(String.valueOf(numberOfCategories));
	}

	/**
	 * Retrieves a list of all categories in the database
	 */
	private void getCategoryList() {
		//categoryCursor = budget.getAllCategorys();
		categoryCursor.moveToFirst();
		setListAdapter(new CategoryStatsCursorAdapter(this, categoryCursor));
	}

	/**
	 * Retrieves the number of accounts in the database
	 */
	private void getNumberOfAccounts() {
		//accountCursor = budget.getAllAccounts();
		numberOfAccounts = accountCursor.getCount();
		tvNOA.setText(String.valueOf(numberOfAccounts));
	}

	/**
	 * Retrieves a list of all accounts in the database
	 */
	private void getAccountList() {
		//accountCursor = budget.getAllAccounts();
		accountCursor.moveToFirst();
		setListAdapter(new AccountStatsCursorAdapter(this, accountCursor));
	}
}
