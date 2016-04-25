package com.ponyinc.minttrack.help;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.ponyinc.minttrack.R;

/** Help screen for the Home activity
 */
public class HelpHome extends Activity
{
	 public void onCreate(Bundle savedInstanceState) 
	    {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.helph);
	       TextView text = (TextView)findViewById(R.id.help1);
	       TextView text2 = (TextView)findViewById(R.id.help2);
	       TextView text3 = (TextView)findViewById(R.id.help3);
	       TextView text4 = (TextView)findViewById(R.id.help4);
	       
	       text.setText("Help for Home Tab\n");
	       text2.setText("Components");
	       text3.setText("·Income Total - the amount of the income total.\n" +
	       		"·Expense Total - the amount of the income total.\n" +
	       		"·Grant total- the amount of the income total.\n" +
	       		"·Recent transactions - a list of the last four transactions.");
	       text4.setText("Recent Transactions items:\n" +
	       		"·Date - the date of the transaction.\n" +
	       		"·Kind of transaction - income, expese or transfer.\n" +
	       		"·Amount - the amount of the transaction.");
	       
	    }
	
}
