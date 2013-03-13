package ie.aib.aibaccountopening;


import java.io.BufferedReader;
import java.io.FileReader;

import android.app.*;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;
import com.abbyy.ocrsdk.*;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class AddressOCR extends Activity {

	TextView tv;
	String photoPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_ocr);
		// Show the Up button in the action bar.
		setupActionBar();
		
		tv = new TextView(this);
		tv.setText("Beginning upload to the cloud. Please wait..\n");
		setContentView(tv);
		
		photoPath = getLastImageId();

		new Thread( new Worker() ).start();
		
	}
	

	
    private String getLastImageId(){
        final String[] imageColumns = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA };
        final String imageOrderBy = MediaStore.Images.Media._ID+" DESC";
        Cursor imageCursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, imageOrderBy);
        if(imageCursor.moveToFirst()){
            int id = imageCursor.getInt(imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
            String fullPath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            imageCursor.close();
            return fullPath;
        }else{
            return "None saved";
        }
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
		getMenuInflater().inflate(R.menu.photo_ocr, menu);
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
	
	class Worker implements Runnable {


		public void run() {
			try {
				Thread.sleep(1000);
				displayMessage( "Starting.." );
				Client restClient = new Client();

				// Name of application you created
				restClient.applicationId = "AccountOpening";
				// You should get e-mail from ABBYY Cloud OCR SDK service with the application password
				restClient.password = "SRiR+Ygp0LjMW2LoiOGutUTx";
				
				String filePath = photoPath;
				
				String outputFile = "/sdcard/addressresult.txt";
				String language = "English"; // Comma-separated list: Japanese,English or German,French,Spanish etc.
				
				ProcessingSettings settings = new ProcessingSettings();
				settings.setOutputFormat( ProcessingSettings.OutputFormat.txt );
				settings.setLanguage(language);
				
				displayMessage( "Uploading.." );
				Task task = restClient.processImage(filePath, settings);
				
				// If you want to process business cards, uncomment this
				/*
				BusCardSettings busCardSettings = new BusCardSettings();
				busCardSettings.setLanguage(language);
				busCardSettings.setOutputFormat(BusCardSettings.OutputFormat.xml);
				Task task = restClient.processBusinessCard(filePath, busCardSettings);
				*/
				
				while( task.isTaskActive() ) {
					Thread.sleep(2000);
					
					displayMessage( "Waiting.." );
					task = restClient.getTaskStatus(task.Id);
				}
				
				if( task.Status == Task.TaskStatus.Completed ) {
					displayMessage( "Downloading.." );
					restClient.downloadResult(task, outputFile);
				} else if( task.Status == Task.TaskStatus.NotEnoughCredits ) {
					displayMessage( "Not enough credits to process task. Add more pages to your application's account." );
					
					Intent myIntent = new Intent(AddressOCR.this, EndAddress.class);
					AddressOCR.this.startActivity(myIntent);
					
				} else {
					displayMessage( "Task failed" );
					
					Intent myIntent = new Intent(AddressOCR.this, EndAddress.class);
					AddressOCR.this.startActivity(myIntent);
				}
				
				displayMessage( "Ready" );

				
				StringBuffer contents = new StringBuffer(); 
				BufferedReader reader = new BufferedReader(new FileReader(outputFile)); 
				String text = null; 
				while ((text = reader.readLine()) != null) { 
					contents.append(text) 
					.append(System.getProperty( 
							"line.separator")); 
				}
				
				//no need to display the String
				//displayMessage( contents.toString() );
				
				//launch the Photo ID activity
				Intent myIntent = new Intent(AddressOCR.this, EndAddress.class);
				AddressOCR.this.startActivity(myIntent);
				
				
			} catch ( Exception e ) {
				displayMessage( "Error: " + e.getMessage() );
				
				//launch next activity, could re-try as alternative..
				Intent myIntent = new Intent(AddressOCR.this, EndAddress.class);
				AddressOCR.this.startActivity(myIntent);
			}
		}

		private void displayMessage( String text )
		{
			tv.post( new MessagePoster( text ) );
		}

		class MessagePoster implements Runnable {
			public MessagePoster( String message )
			{
				_message = message;
			}

			public void run() {
				tv.append( _message + "\n" );
				setContentView( tv );
			}

			private final String _message;
		}
	}
}
