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

import com.example.android.wizardpager.wizard.ui.CustomerAddressFragment;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * A page asking for a name and an email.
 */
public class CustomerAddressPage extends Page {
    public static final String ADDRESS1_DATA_KEY = "address 1";    
    public static final String ADDRESS2_DATA_KEY = "address 2";
    public static final String ADDRESS3_DATA_KEY = "address 3";
    public static final String ADDRESS4_DATA_KEY = "address 4";

    public CustomerAddressPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
        
        
    }

    @Override
    public Fragment createFragment() {
        return CustomerAddressFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Address 1", mData.getString(ADDRESS1_DATA_KEY), getKey(), -1));        
        dest.add(new ReviewItem("Address 2", mData.getString(ADDRESS2_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Address 3", mData.getString(ADDRESS3_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Address 4", mData.getString(ADDRESS4_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(ADDRESS1_DATA_KEY)) && !TextUtils.isEmpty(mData.getString(ADDRESS2_DATA_KEY)) && !TextUtils.isEmpty(mData.getString(ADDRESS3_DATA_KEY)) && !TextUtils.isEmpty(mData.getString(ADDRESS4_DATA_KEY));
    }
}
