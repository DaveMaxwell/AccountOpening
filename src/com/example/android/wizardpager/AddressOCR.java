package com.example.android.wizardpager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import android.app.*;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import com.abbyy.ocrsdk.*;

import com.example.android.wizardpager.wizard.ui.CustomerInfoFragment;

public class AddressOCR extends Activity {
	/** Called when the activity is first created. */
	/** Called when the activity is first created. */
	
	
	TextView tv;
	String photoPath;
	

	
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
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_ocr);
		
		tv = new TextView(this);
		//tv.setText("Beginning upload to the cloud. Please wait..\n");
		//setContentView(tv);
		
		//photoPath = getLastImageId();
		//photoPath = "/sdcard/SmallPOA.jpg";

		new Thread( new Worker() ).start();
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
				
				//String filePath = photoPath;
				//String filePath = "/sdcard/SmallPOA.jpg";
				String filePath = "/sdcard/POA.png";
				
				String outputFile = "/sdcard/POAresult.txt";
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
				} else {
					displayMessage( "Task failed" );
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
				
				reader.close();
				
				//no need to display the String
				//displayMessage( contents.toString() );
				
				//launch the Photo ID activity
				
				
				
				//end of scan
				finish();
				
				
				
			} catch ( Exception e ) {
				displayMessage( "Error: " + e.getMessage() );
				
				//launch next activity, could re-try as alternative..
				finish();
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
