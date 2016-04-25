package com.ponyinc.minttrack.tabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.ponyinc.minttrack.AboutUs;
import com.ponyinc.minttrack.Budget;
import com.ponyinc.minttrack.R;
import com.ponyinc.minttrack.backuprestore.BackupManager;
import com.ponyinc.minttrack.help.HelpTools;
import com.ponyinc.minttrack.tools.AccountManager;
import com.ponyinc.minttrack.tools.CategoryManager;
import com.ponyinc.minttrack.tools.StatsViewer;
import com.ponyinc.minttrack.tools.TipCalculator;

/**This class implements a set of tools for the user including Account manager, Category manager, 
 * 		and Tip calculator
 * @author Pablo BajoLaso and Jeff Titus
 */
public class ToolActivity extends Activity implements OnClickListener
{

	Budget budget;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool);
		budget = new Budget(this);
       
        // Set up click listeners for all the buttons
		View stats_button = findViewById(R.id.stats_button);
		stats_button.setOnClickListener(statsListener);
		
        View tip_button = findViewById(R.id.tip_button);
        tip_button.setOnClickListener(tipCalcListener);
        
        View account_button = findViewById(R.id.manage_accounts);
        account_button.setOnClickListener(manAcctListener);
        
        View category_button = findViewById(R.id.manage_cats);
        category_button.setOnClickListener(manCatListener);
	}
	
	/** Statistics Viewer Button Listener **/
	View.OnClickListener statsListener = new View.OnClickListener() {
		
		public void onClick(View v) 
		{
			executeStatisticsViewer();
		}
	};
	 
	/**Manage Accounts Button Listener**/
	View.OnClickListener manAcctListener = new View.OnClickListener() {
			
		public void onClick(View v) 
		{
			executeAccountManager();
		}
	};
	
	/**Manage Categories Button Listener**/
	View.OnClickListener manCatListener = new View.OnClickListener() {
		
		public void onClick(View v) 
		{
			executeCategoryManager();
		}
	};
	
	/**Tip Calculator Button Listener**/
	View.OnClickListener tipCalcListener = new View.OnClickListener() {
		
		public void onClick(View v) 
		 {
			executeTipCalculator();
		 }
	};
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	@Override
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

	/** Executes help functionality **/
	private void executeHelpIntent()
	{
		 Intent i = new Intent(this, HelpTools.class);
	     startActivity(i);
	}
	/** Executes Information screen **/
	private void executeInfoIntent()
	{
		 Intent i = new Intent(this, AboutUs.class);
	     startActivity(i);
	}
	/** Executes Tip Calculator Functionality **/
	private void executeTipCalculator()
	{
		Intent calcIntent = new Intent(this, TipCalculator.class);
		startActivity(calcIntent);
	}
	/** Executes Account Manager Functionality **/
	private void executeAccountManager()
	{
		Intent accountIntent = new Intent(this, AccountManager.class);
		startActivity(accountIntent);
	}
	/** Executes Category manager Functionality **/
	private void executeCategoryManager()
	{
		Intent categoryIntent = new Intent(this, CategoryManager.class);
		startActivity(categoryIntent);
	}
	/** Executes statistics viewer functionality **/
	private void executeStatisticsViewer()
	{
		Intent statsIntent = new Intent(this, StatsViewer.class);
		startActivity(statsIntent);
	}

	public void onClick(View arg0) {
		
	}
			 
	
}
