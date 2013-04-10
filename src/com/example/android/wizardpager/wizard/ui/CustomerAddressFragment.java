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
import java.io.FileReader;
import java.io.IOException;

import com.example.android.wizardpager.Address;
import com.example.android.wizardpager.R;
import com.example.android.wizardpager.wizard.model.CustomerAddressPage;
import com.example.android.wizardpager.wizard.model.CustomerInfoPage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class CustomerAddressFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private CustomerAddressPage mPage;
    private TextView mAddress1View;
    private TextView mAddress2View;
    private TextView mAddress3View;
    private TextView mAddress4View;
    
    //private TextView mEmailView;

    public static CustomerAddressFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        CustomerAddressFragment fragment = new CustomerAddressFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CustomerAddressFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = new Intent();
        intent.setClass(getActivity(), Address.class);
        getActivity().startActivity(intent);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (CustomerAddressPage) mCallbacks.onGetPage(mKey);
        

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.customer_address, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mAddress1View = ((TextView) rootView.findViewById(R.id.your_address1));
        mAddress1View.setText(mPage.getData().getString(CustomerAddressPage.ADDRESS1_DATA_KEY));

        //mEmailView = ((TextView) rootView.findViewById(R.id.your_email));
        //mEmailView.setText(mPage.getData().getString(CustomerInfoPage.EMAIL_DATA_KEY));
        
        mAddress2View = ((TextView) rootView.findViewById(R.id.your_address2));
        mAddress2View.setText(mPage.getData().getString(CustomerAddressPage.ADDRESS2_DATA_KEY));
        
        mAddress3View = ((TextView) rootView.findViewById(R.id.your_address3));
        mAddress3View.setText(mPage.getData().getString(CustomerAddressPage.ADDRESS3_DATA_KEY));
        
        mAddress4View = ((TextView) rootView.findViewById(R.id.your_address4));
        mAddress4View.setText(mPage.getData().getString(CustomerAddressPage.ADDRESS4_DATA_KEY));
        

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
    
   public void OCR(){
	    String address1;
	    String address2;
	    String address3;
	    
	    TextView address1Txt = mAddress1View; 
	    TextView address2Txt = mAddress2View; 
	    TextView address3Txt = mAddress3View; 
	    TextView address4Txt = mAddress4View;
	    
	    String address4 = "Ireland";
	    mPage.getData().putString(CustomerAddressPage.ADDRESS4_DATA_KEY, address4);
	    address4Txt.append(address4);
	    
	    //String Surname = CustomerInfoPage.SURNAME_DATA_KEY;
	    
	    String proofOfAddressType;
		
	    //mPage.getData().putString(CustomerAddressPage.ADDRESS1_DATA_KEY, address1);
	    //mPage.getData().putString(CustomerAddressPage.ADDRESS2_DATA_KEY, address2);
	    //mPage.getData().putString(CustomerAddressPage.ADDRESS3_DATA_KEY, address3);
	    //mPage.getData().putString(CustomerAddressPage.ADDRESS4_DATA_KEY, address4);
	    
	    
		String text = null;
		try {
			text = readFile("/sdcard/POAresult.txt");
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String salutation = "Mr";
		
		
		
		//Drivers license check, if it contains an issued on
		if(text.contains(salutation)){
			
			//address2Txt.append(salutation);
			
			String word = salutation;
			//String last = "County";
			String carriageReturn = "\n";
			
			int startIndex = 0;
			int endIndex = 0;
			
			int endPoint = 0;
			
			endPoint = text.indexOf(word);
			
			text = text.substring(endPoint, text.length()); 
			
			for (int i = text.length(); (i = text.lastIndexOf(word, i - 1)) != -1; ) {
				startIndex = i;
			    
			}
			
			for (int i = text.length(); (i = text.lastIndexOf(carriageReturn, i - 1)) != -1; ) {
				endIndex = i;
			}
			
		    //int index = text.lastIndexOf(last);
		    //endIndex = index;
		    
		    address1 = text.substring(startIndex, endIndex); 
		  
		    mPage.getData().putString(CustomerAddressPage.ADDRESS1_DATA_KEY, address1);
		    address1Txt.append(address1);

		    //start from the end of the last occurance
		    //startIndex = endIndex;
		    //address2 = text.substring(startIndex, endIndex); 
		    
		    //int startPoint = address1.length();
		    
		    //startPoint = text.indexOf(address1 + startPoint);
		    //startPoint = startPoint + 1;
		    
		    
		    text = text.substring(endIndex, text.length()); 
		    
		    String county = "County";
		    
		    startIndex = 0;
			
			for (int i = text.length(); (i = text.lastIndexOf(county, i - 1)) != -1; ) {
				endIndex = i;
			}
			
		    address2 = text.substring(startIndex, endIndex); 
		    
		    //remove carriage returns from the end of string
		    address2 = address2.substring(1, address2.length() - 1);
			  
		    mPage.getData().putString(CustomerAddressPage.ADDRESS2_DATA_KEY, address2);
		    address2Txt.append(address2);
		    
		    
		    //last part
		    
		    text = text.substring(endIndex, text.length()); 
		    //int endAddress = text.lastIndexOf(carriageReturn);
		    int endAddress = 0;
		    
			for (int i = text.length(); (i = text.lastIndexOf(carriageReturn, i - 1)) != -1; ) {
				endAddress = i;
			}
		    
		    address3 = text.substring(startIndex, endAddress); 
			  
		    mPage.getData().putString(CustomerAddressPage.ADDRESS3_DATA_KEY, address3);
		    address3Txt.append(address3);
		    
		}
	   
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
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
    


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        OCR();

        mAddress1View.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                    int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(CustomerAddressPage.ADDRESS1_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
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
        
        mAddress2View.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                    int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(CustomerAddressPage.ADDRESS2_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });
        
        mAddress3View.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                    int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(CustomerAddressPage.ADDRESS3_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });
        
        mAddress4View.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                    int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(CustomerAddressPage.ADDRESS4_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });
        
        
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        // In a future update to the support library, this should override setUserVisibleHint
        // instead of setMenuVisibility.
        if (mAddress1View != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (!menuVisible) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }
}
