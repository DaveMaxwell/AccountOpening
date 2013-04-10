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

package com.example.android.wizardpager.wizard.model;

import com.example.android.wizardpager.wizard.ui.CustomerInfoFragment;



import android.content.Context;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;



import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * A page asking for a name and an email.
 */
public class CustomerInfoPage extends Page {
    public static final String NAME_DATA_KEY = "name";    
    public static final String SURNAME_DATA_KEY = "surname";
    public static final String DOB_DATA_KEY = "date of birth";
    public static final String COUNTRY_DATA_KEY = "country of birth";
    
  //public static final String EMAIL_DATA_KEY = "email";

    public CustomerInfoPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
          

    }
    

	@Override
    public Fragment createFragment() {
        return CustomerInfoFragment.create(getKey());
    }
	


    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Your name", mData.getString(NAME_DATA_KEY), getKey(), -1));
        //dest.add(new ReviewItem("Your email", mData.getString(EMAIL_DATA_KEY), getKey(), -1));
        
        dest.add(new ReviewItem("Your surname", mData.getString(SURNAME_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Your date of birth", mData.getString(DOB_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Your country of birth", mData.getString(COUNTRY_DATA_KEY), getKey(), -1));
     

        
        
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(NAME_DATA_KEY)) && !TextUtils.isEmpty(mData.getString(SURNAME_DATA_KEY)) && !TextUtils.isEmpty(mData.getString(DOB_DATA_KEY)) && !TextUtils.isEmpty(mData.getString(COUNTRY_DATA_KEY));
    }
}
