package com.ponyinc.minttrack.help;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.ponyinc.minttrack.R;
/** Help screen for the Entry activity
 */
public class HelpEntry extends Activity
{
	 public void onCreate(Bundle savedInstanceState) 
	    {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.helpentry);
	       TextView text = (TextView)findViewById(R.id.help1);
	       TextView text2 = (TextView)findViewById(R.id.helpmode);
	       TextView text3 = (TextView)findViewById(R.id.helpincome);
	       TextView text4 = (TextView)findViewById(R.id.itemsi);
	       TextView text5 = (TextView)findViewById(R.id.helpitemsi);
	       TextView text6 = (TextView)findViewById(R.id.helpexpense);
	       TextView text7 = (TextView)findViewById(R.id.itemse);
	       TextView text8 = (TextView)findViewById(R.id.helpitemse);
	       TextView text9 = (TextView)findViewById(R.id.helptransfer);
	       TextView text10 = (TextView)findViewById(R.id.transferi);
	       TextView text11 = (TextView)findViewById(R.id.helptransferi);
	       
	       text.setText("Help for Entry Tab");
	       text2.setText("Modes");
	       text3.setText("  Income – Financial gain accrued to a particular account.");
	       text4.setText(" Input Items:");
	       text5.setText(" ·Amount – the amount of the income transaction.\n"+
						" ·Date – the date the transaction occurred.\n"+
						" ·Payment Type to– Account money is deposited to.\n"+
						" ·Reason – A reason or category of the transaction.\n"+
						" ·Note – to provide more info about the transaction.\n");
		   text6.setText("  Expense – Financial loss logged into the system.");
		   text7.setText("  Input Items:");
		   text8.setText(" ·Amount – the amount of the income transaction.\n"+
						" ·Date – the date the transaction occurred.\n"+
						" ·Payment Type from– Account money is deposited from.\n"+
						" ·Reason – A reason or category of the transaction.\n"+
						" ·Note – to provide more info about the transaction.\n");
		   text9.setText("  Transfer – Moving money from one account to another account.");
		   text10.setText("  Input Items:");
		   text11.setText(" ·Amount –the amount of the income transaction.\n"+
					" ·Date –the date the transaction occurred.\n"+
					" ·Payment Type to –Account money is deposited to.\n"+
					" ·Payment Type from –Account money is deposited from.\n"+
					" ·Note –to provide more info about the transaction.\n");
	    }
	 
}