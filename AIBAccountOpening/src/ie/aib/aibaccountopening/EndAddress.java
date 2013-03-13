package ie.aib.aibaccountopening;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class EndAddress extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.end_address);
		// Show the Up button in the action bar.
		setupActionBar();
		

        Button next = (Button) findViewById(R.id.btnStepFive);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            Intent openPhoto = new Intent(EndAddress.this,Signature.class);

            EndAddress.this.startActivity(openPhoto);

            }

        });
		
	    String address1;
	    String address2;
	    String address3;
	    String address4;
	    String proofOfAddressType;

	    
	    EditText address1Txt = (EditText) findViewById(R.id.address1); 
	    EditText address2Txt = (EditText) findViewById(R.id.address2); 
	    EditText address3Txt = (EditText) findViewById(R.id.address3); 
	    AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.address4);
	    
		String text = null;
		try {
			text = readFile("/sdcard/addressresult.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Drivers license check, if it contains an issued on
		if(text.contains("issued on")){
			
		}
		
		
		Locale[] locales = Locale.getAvailableLocales();
		
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, CountryList);

        textView.setAdapter(adapter);
		
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.end_address, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private String readFile(String string) throws IOException {

		//File dir = Environment.getExternalStorageDirectory();
		//File photoIDResult = new File(dir, "/sdcard/result.txt");
		
		String outputFile = string;
		
		StringBuffer contents = new StringBuffer(); 
		BufferedReader reader = new BufferedReader(new FileReader(outputFile)); 
		String text = null; 
		while ((text = reader.readLine()) != null) { 
			contents.append(text) 
			.append(System.getProperty( 
					"line.separator")); 
		}
		
		return contents.toString();
	}

	public static String[] CountryList=new String[]{"Abkhazia","Afghanistan","Akrotiri and Dhekelia","Åland Islands","Albania","Algeria","American Samoa","Andorra","Angola","Anguilla",
		"Antigua and Barbuda","Argentina ","Armenia ","Aruba","Ascension Island",
		"Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados",
		"Belarus","Belgium","Belize","Benin ","Bermuda","Bhutan","Bolivia"," Bosnia","Botswana","Brazil",
		"Brunei"," Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Canada","Cape Verde",
		"Cayman Islands","Central African Republic","Chad","Chile","China","ChristmasIsland",
		"Cocos","Colombia","Comoros","Congo","Cook Islands","Costa Rica","Côte d'Ivoire",
		"Croatia","Cuba","Cyprus","Czech","Denmark","Djibouti","Dominica","Ecuador","Egypt",
		"El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia","Falkland Islands",
		"Faroe Islands","Fiji","Finland","France","Gabon","Gambia","Georgia","Germany",
		"Ghana","Gibraltar","Greece","Greenland","Grenada","Guam","Guatemala","Guernsey","Guinea",
		"Guinea-Bissau","Guyana","Haiti","Honduras","Hong Kong","Hungary","Iceland","India","Indonesia",
		"Iran","Iraq","Ireland","Isle of Man","Israel","Italy","Jamaica","Japan","Jersey",
		"Jordan","Kazakhstan","Kenya","Kiribati","Korea","Kosovo","Kuwait","Kyrgyzstan","Laos","Latvia",
		"Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macao",
		"Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands",
		"Mauritania","Mauritius","Mayotte","Mexico","Micronesia","Moldova","Monaco","Mongolia",
		"Montenegro","Montserrat","Morocco","Mozambique","Myanmar","Nagorno-Karabakh","Namibia","Nauru",
		"Nepal","Netherlands","New Caledonia","New Zealand","Nicaragua","Niger","Nigeria","Niue","Norfolk Island",
		"Norway","Oman","Pakistan","Palau","Palestine","Panama","Papua New Guinea","Paraguay","Peru",
		"Philippines","Pitcairn","Poland","Portugal","Pridnestrovie","Puerto Rico","Qatar",
		"Romania","Russia","Rwanda","Saint-Barthélemy","Saint Helena","Saint Kitts and Nevis",
		"Saint Lucia","Saint Martin","Saint-Pierre and	Miquelon",
		"Saint Vincent and the Grenadines","Samoa","San Marino","São Tomé and Príncipe",
		"Saudi Arabia","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Slovakia",
		"Slovenia","Solomon	Islands","Somalia","Somaliland","South Africa","South Ossetia",
		"Spain","SriLanka","Sudan","Suriname","Svalbard","Swaziland","Sweden",
		"Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Timor",
		"Togo","Tokelau","Tonga","Trinidad and Tobago","Tristan da Cunha",
		"Tunisia","Turkey","Turkmenistan","Turks and Caicos","Tuvalu","Uganda",
		"Ukraine","United Arab Emirates","United Kingdom","United States",
		"Uruguay","Uzbekistan","Vanuatu","Vatican City","Venezuela","Vietnam",
		"Virgin Islands","Wallis and Futuna","Western Sahara","Yemen",
		"Zambia","Zimbabwe"};

}
