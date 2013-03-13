package ie.aib.accountopening;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PhotoID extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_id);
		// Show the Up button in the action bar.
		setupActionBar();

		
	    String surname;
	    String firstName;
	    String dateOfBirth;
	    String countryOfBirth;
	    String proofOfIdentityType;
		
	    
	    EditText firstNameTxt = (EditText) findViewById(R.id.firstName); 
	    EditText surnameTxt = (EditText) findViewById(R.id.surname); 
	    EditText dateOfBirthTxt = (EditText) findViewById(R.id.dateOfBirth); 
	    AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.countryOfBirth);
	    
		String text = null;
		try {
			text = readFile("/sdcard/result.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Drivers license check, if it contains an issued on
		if(text.contains("issued on")){
			
			//System.out.println(text);
			
			String word = "name(s)";
			String last = "3.";
			
			int endIndex = 0;
			int startIndex = 0;
			
			String characters = text.replaceAll("[^\\x00-\\x7F]", "");
			System.out.println(characters);
			
		    for (int i = characters.length(); (i = characters.lastIndexOf(word, i - 1)) != -1; ) {
		    	startIndex = i;
			}
		    
		    for (int i = characters.length(); (i = characters.lastIndexOf(last, i - 1)) != -1; ) {
				endIndex = i;
			}
			
		    firstName = text.substring(startIndex, endIndex); 
		    
			System.out.println(firstName);
			
		}
		
		//Passport check, if it contains MRZ line then it must be a passport
		if(text.contains("<<<<<")) {
		proofOfIdentityType = "Passport";
		
		String word = "P<";
		String last = "<<";
		
		int startIndex = 0;
		int endIndex;
		
		for (int i = text.length(); (i = text.lastIndexOf(word, i - 1)) != -1; ) {
			startIndex = i;
		    
		}
	    int index = text.lastIndexOf(last);
	    endIndex = index;
	    
	    //Extract only the MRZ line from the passport
	    String MRZ = text.substring(startIndex, endIndex); 
	    
	    //Remove any possible spaces
	    MRZ = MRZ.replace(" ", "");
	    
	    //System.out.println(MRZ);
	    
	    
		for (int i = MRZ.length(); (i = MRZ.lastIndexOf(last, i - 1)) != -1; ) {
			endIndex = i;
		    
		}
	    
	    surname = MRZ.substring(5, endIndex); 
	    surnameTxt.setText(surname);

	    startIndex = endIndex + 2;
	    last = "<<<<";
	    
	    for (int i = MRZ.length(); (i = MRZ.lastIndexOf(last, i - 1)) != -1; ) {
			endIndex = i;
		}
	    
	    firstName = MRZ.substring(startIndex, endIndex); 
	    firstNameTxt.setText(firstName);
	    //System.out.println(firstName);
	    
	    for (int i = MRZ.length(); (i = MRZ.lastIndexOf(last, i - 1)) != -1; ) {
			startIndex = i;
		}
	    endIndex = MRZ.length() - 1;
	   
	    String MRZLineTwo = MRZ.substring(startIndex, endIndex); 	
	    
	    //remove carriage returns and << from MRZ
	    MRZLineTwo = MRZLineTwo.replace("<", "");
	    MRZLineTwo = MRZLineTwo.replaceAll("[\n\r]", "");;
	   
	    
	    dateOfBirth = MRZLineTwo.substring(13, 19); 
	    

	    String year = dateOfBirth.substring(0, 2); 
	    String month = dateOfBirth.substring(2, 4);
	    String day = dateOfBirth.substring(4, 6);
	    
	    dateOfBirth = day + "-" + month + "-" + year;
	    dateOfBirthTxt.setText(dateOfBirth);
	    
	    countryOfBirth = MRZLineTwo.substring(10, 13); 
	    textView.setText(countryOfBirth);
		}
	    
	    //System.out.println(countryOfBirth);
		
		
		Locale[] locales = Locale.getAvailableLocales();
		
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, CountryList);

        textView.setAdapter(adapter);
        
        
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

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo_id, menu);
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

}
