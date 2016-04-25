
package com.ponyinc.minttrack.help;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.ponyinc.minttrack.R;

/** Help screen for the audit activity
 */
public class HelpAudit extends Activity
{
	 public void onCreate(Bundle savedInstanceState) 
	    {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.helpaudit);
	       TextView text = (TextView)findViewById(R.id.help1);
	       TextView text2 = (TextView)findViewById(R.id.help2);
	       TextView text3 = (TextView)findViewById(R.id.help3);
	       TextView text4 = (TextView)findViewById(R.id.help4);
	       
	       text.setText("Help for Audit Tab");
	       text2.setText("Components: All the transactions");
	       text3.setText("·Transactions items");
	       text4.setText("Date - the date of the transaction.\n"+
	       		"Type - the type of the transaction.\n"+
	       		"Amount - the amount of the transaction.\n"+
	       		"Reason - the reason of the transaction.\n"+
	       		"Note - a complementary note of the transaction.");
	       
	    }
	
}