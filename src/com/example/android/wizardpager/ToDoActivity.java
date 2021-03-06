package com.example.android.wizardpager;

import static com.microsoft.windowsazure.mobileservices.MobileServiceQueryOperations.*;

import java.net.MalformedURLException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.wizardpager.wizard.model.CustomerAddressPage;
import com.example.android.wizardpager.wizard.model.CustomerInfoPage;
import com.example.android.wizardpager.wizard.ui.CustomerInfoFragment;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import com.microsoft.windowsazure.mobileservices.MobileServiceUser;
import com.microsoft.windowsazure.mobileservices.MobileServiceAuthenticationProvider;
import com.microsoft.windowsazure.mobileservices.UserAuthenticationCallback;

public class ToDoActivity extends Activity {

	/**
	 * Mobile Service Client reference
	 */
	private MobileServiceClient mClient;

	/**
	 * Mobile Service Table used to access data
	 */
	private MobileServiceTable<ToDoItem> mToDoTable;

	/**
	 * Adapter to sync the items list with the view
	 */
	private ToDoItemAdapter mAdapter;

	/**
	 * EditText containing the "New ToDo" text
	 */
	private EditText mTextNewToDo;

	/**
	 * Progress spinner to use for table operations
	 */
	private ProgressBar mProgressBar;

	/**
	 * Initializes the activity
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do);
		
		mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);
		
		// Initialize the progress bar
		mProgressBar.setVisibility(ProgressBar.GONE);
		
		//mTextNewToDo = (EditText) findViewById(R.id.textNewToDo);
		
		//mPage.getData().putString(CustomerInfoPage.SURNAME_DATA_KEY, surname);
		//String test = CustomerAddressPage.ADDRESS1_DATA_KEY;
		

		
		
		try {
			// Create the Mobile Service Client instance, using the provided
			// Mobile Service URL and key
			mClient = new MobileServiceClient(
					"https://accountopening.azure-mobile.net/",
					"gVUzGqLkrgShIgyhkGNJvPUtBMDeXI39",
					this).withFilter(new ProgressFilter());
			
			authenticate();
			
			//changes
			//addItem(mTextNewToDo);

		} catch (MalformedURLException e) {
			createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
		}
		
		
		
	}
	
	
	private void createTable() {


		// Get the Mobile Service Table instance to use
		mToDoTable = mClient.getTable(ToDoItem.class);

		
		mTextNewToDo = (EditText) findViewById(R.id.textNewToDo);
		
		String Name = CustomerInfoPage.NAME_DATA_KEY;
		String Surname = CustomerInfoPage.SURNAME_DATA_KEY;
		String DOB = CustomerInfoPage.DOB_DATA_KEY;
		String CountryOfBirth = CustomerInfoPage.COUNTRY_DATA_KEY;
		
		String Address1 = CustomerAddressPage.ADDRESS1_DATA_KEY;
		String Address2 = CustomerAddressPage.ADDRESS2_DATA_KEY;
		String Address3 = CustomerAddressPage.ADDRESS3_DATA_KEY;
		String Address4 = CustomerAddressPage.ADDRESS4_DATA_KEY;
		
		String text = "Name: " + Name + "\nSurname: " + Surname + "\nDOB: " + DOB + "\nCountry of Birth: " + CountryOfBirth + "\nAddress 1: " + Address1 + "\nAddress 2: " + Address2 + "\nAddress 3: " + Address3 + "\nAddress 4: " + Address4;
		
		mTextNewToDo.setText(text);



		// Create an adapter to bind the items with the view
		mAdapter = new ToDoItemAdapter(this, R.layout.row_list_to_do);
		ListView listViewToDo = (ListView) findViewById(R.id.listViewToDo);
		listViewToDo.setAdapter(mAdapter);


		// Load the items from the Mobile Service
		refreshItemsFromTable();
		
		addItem(mTextNewToDo);
		
		Intent openComplete = new Intent(ToDoActivity.this, Complete.class);
		ToDoActivity.this.startActivity(openComplete);


	}

	
/*	*//**
	 * Initialises the activity menu
	 *//*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	*//**
	 * Select an option from the menu
	 *//*
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_refresh) {
			refreshItemsFromTable();
		}
		
		return true;
	}
*/
	/**
	 * Mark an item as completed
	 * 
	 * @param item
	 *            The item to mark
	 */
	public void checkItem(ToDoItem item) {
		if (mClient == null) {
			return;
		}

		// Set the item as completed and update it in the table
		item.setComplete(true);
		
		mToDoTable.update(item, new TableOperationCallback<ToDoItem>() {

			public void onCompleted(ToDoItem entity, Exception exception, ServiceFilterResponse response) {
				if (exception == null) {
					if (entity.isComplete()) {
						mAdapter.remove(entity);
					}
				} else {
					createAndShowDialog(exception, "Error");
				}
			}

		});
	}

	/**
	 * Add a new item
	 * 
	 * @param view
	 *            The view that originated the call
	 */
	public void addItem(View view) {
		if (mClient == null) {
			return;
		}

		// Create a new item
		ToDoItem item = new ToDoItem();

		item.setText(mTextNewToDo.getText().toString());
		item.setComplete(true);
		
		// Insert the new item
		mToDoTable.insert(item, new TableOperationCallback<ToDoItem>() {

			public void onCompleted(ToDoItem entity, Exception exception, ServiceFilterResponse response) {
				
				if (exception == null) {
					if (!entity.isComplete()) {
						mAdapter.add(entity);
						
						Intent openComplete = new Intent(ToDoActivity.this, Complete.class);
						ToDoActivity.this.startActivity(openComplete);
						
					}
				} else {
					createAndShowDialog(exception, "Error");
				}

			}
		});

		mTextNewToDo.setText("");
	}

	/**
	 * Refresh the list with the items in the Mobile Service Table
	 */
	private void refreshItemsFromTable() {

		// Get the items that weren't marked as completed and add them in the
		// adapter
		mToDoTable.where().field("complete").eq(val(false)).execute(new TableQueryCallback<ToDoItem>() {

			public void onCompleted(List<ToDoItem> result, int count, Exception exception, ServiceFilterResponse response) {
				if (exception == null) {
					mAdapter.clear();

					for (ToDoItem item : result) {
						mAdapter.add(item);
					}

				} else {
					createAndShowDialog(exception, "Error");
				}
			}
		});
	}
	
	private void authenticate() {


		// Login using the Google provider.
		mClient.login(MobileServiceAuthenticationProvider.Google,
		        new UserAuthenticationCallback() {


		            @Override
		            public void onCompleted(MobileServiceUser user,
		                    Exception exception, ServiceFilterResponse response) {


		                if (exception == null) {

		                	//Didn't like the fact the dialog below didn't disappear so toast instead
		                    //createAndShowDialog(String.format("You are now logged in - %1$2s",user.getUserId()), "Success");
		                	Context context = getApplicationContext();
		                	CharSequence text = "Upload complete";
		                	int duration = Toast.LENGTH_SHORT;

		                	Toast toast = Toast.makeText(context, text, duration);
		                	toast.show();
		                	
		                	
		                	createTable();
		                } else {
		                    createAndShowDialog("You must log in. Login Required", "Error");
		                }
		            }
		        });


		}

	/**
	 * Creates a dialog and shows it
	 * 
	 * @param exception
	 *            The exception to show in the dialog
	 * @param title
	 *            The dialog title
	 */
	private void createAndShowDialog(Exception exception, String title) {
		createAndShowDialog(exception.toString(), title);
	}

	/**
	 * Creates a dialog and shows it
	 * 
	 * @param message
	 *            The dialog message
	 * @param title
	 *            The dialog title
	 */
	private void createAndShowDialog(String message, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(message);
		builder.setTitle(title);
		builder.create().show();
	}
	
	private class ProgressFilter implements ServiceFilter {
		
		@Override
		public void handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback,
				final ServiceFilterResponseCallback responseCallback) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.VISIBLE);
				}
			});
			
			nextServiceFilterCallback.onNext(request, new ServiceFilterResponseCallback() {
				
				@Override
				public void onResponse(ServiceFilterResponse response, Exception exception) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.GONE);
						}
					});
					
					if (responseCallback != null)  responseCallback.onResponse(response, exception);
				}
			});
		}
	}
}
