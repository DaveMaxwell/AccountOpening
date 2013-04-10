/*
 * Copyright 2012 Roman Nurik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.wizardpager.wizard.ui;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.example.android.wizardpager.Photo;
import com.example.android.wizardpager.R;
import com.example.android.wizardpager.wizard.model.CustomerInfoPage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerInfoFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private CustomerInfoPage mPage;
    TextView mNameView;
    TextView mSurnameView;
    TextView mDOBView;
    TextView mCountryView;
    
    String Name;
    String Surname;
    String DOB;
    String CountryOfBirth;
    
   
    
    //private TextView mEmailView;
    
    public static CustomerInfoFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);
        

        CustomerInfoFragment fragment = new CustomerInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CustomerInfoFragment() {   	
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
    	Intent intent = new Intent();
        intent.setClass(super.getActivity(), Photo.class);
        super.getActivity().startActivity(intent);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (CustomerInfoPage) mCallbacks.onGetPage(mKey);

    }
    


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	    	
        View rootView = inflater.inflate(R.layout.fragment_page_customer_info, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mNameView = ((TextView) rootView.findViewById(R.id.your_name));
        mSurnameView = ((TextView) rootView.findViewById(R.id.your_surname));
        mDOBView = ((TextView) rootView.findViewById(R.id.your_dob));
        mCountryView = ((TextView) rootView.findViewById(R.id.your_country));
      //mEmailView = ((TextView) rootView.findViewById(R.id.your_email));
        
        
        mNameView.setText(mPage.getData().getString(CustomerInfoPage.NAME_DATA_KEY));
        //mEmailView.setText(mPage.getData().getString(CustomerInfoPage.EMAIL_DATA_KEY));
        mSurnameView.setText(mPage.getData().getString(CustomerInfoPage.SURNAME_DATA_KEY));
        mDOBView.setText(mPage.getData().getString(CustomerInfoPage.DOB_DATA_KEY));
        mCountryView.setText(mPage.getData().getString(CustomerInfoPage.COUNTRY_DATA_KEY));

        
        

        return rootView;
    }
  

    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;

       
    }
    

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;

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
		reader.close();
		return contents.toString();
	}
	

    


	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        OCR();
        

        
        mNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                    int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {     	
                mPage.getData().putString(CustomerInfoPage.NAME_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
                Name = editable.toString();
            }
        });

/*        mEmailView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                    int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(CustomerInfoPage.EMAIL_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });*/
        
        mSurnameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                    int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(CustomerInfoPage.SURNAME_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
                Surname = editable.toString();
            }
        });
        
        mDOBView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                    int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(CustomerInfoPage.DOB_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
                DOB = editable.toString();
            }
        });
        
        mCountryView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                    int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(CustomerInfoPage.COUNTRY_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
                CountryOfBirth = editable.toString();
            }
        });
        

            
    }
    
    public void OCR(){
    	
	    String surname;
	    String firstName;
	    String dateOfBirth;
	    String countryOfBirth;
	    String proofOfIdentityType;
		
	    
	    TextView firstNameTxt = mNameView; 
	    TextView surnameTxt = mSurnameView; 
	    TextView dateOfBirthTxt = mDOBView; 
	    TextView textView = mCountryView;
	    
	    
	    
		String text = null;
		try {
			text = readFile("/sdcard/POIresult.txt");
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
	    surnameTxt.append(surname);
	    mPage.getData().putString(CustomerInfoPage.SURNAME_DATA_KEY, surname);

	    startIndex = endIndex + 2;
	    last = "<<<<";
	    
	    for (int i = MRZ.length(); (i = MRZ.lastIndexOf(last, i - 1)) != -1; ) {
			endIndex = i;
		}
	    
	    firstName = MRZ.substring(startIndex, endIndex); 
	    
	    firstNameTxt.append(firstName);
	   
        mPage.getData().putString(CustomerInfoPage.NAME_DATA_KEY, firstName);

	    
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
	    dateOfBirthTxt.append(dateOfBirth);
	    
       
        mPage.getData().putString(CustomerInfoPage.DOB_DATA_KEY, dateOfBirth);
        
	    
	    countryOfBirth = MRZLineTwo.substring(10, 13); 
	    textView.append(countryOfBirth);
	    
	    mPage.getData().putString(CustomerInfoPage.COUNTRY_DATA_KEY, countryOfBirth);
		}
		
		
		
    	
    	
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        // In a future update to the support library, this should override setUserVisibleHint
        // instead of setMenuVisibility.
        if (mNameView != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (!menuVisible) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }
}
