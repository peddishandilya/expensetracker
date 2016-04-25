
package com.ponyinc.minttrack;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * This class implements the about us screen displayed after clicking the Information button in the options menu
 */
public class AboutUs extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);
	       
		TextView txtversion = (TextView)findViewById(R.id.version);
		TextView txtnames = (TextView)findViewById(R.id.names);
		TextView txtwebsite = (TextView)findViewById(R.id.website);
		 
		txtversion.setText("Version 1.1");
		txtnames.setText("Stephan Krach\n" +
		"Christopher Wilkins\n" +
		"Jeff Titus\n" +
		"Pablo Bajo Laso");
		txtwebsite.setText("www.minttrack.com");
	}
}