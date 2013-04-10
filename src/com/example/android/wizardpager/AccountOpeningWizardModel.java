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

package com.example.android.wizardpager;

import com.example.android.wizardpager.wizard.model.AbstractWizardModel;
import com.example.android.wizardpager.wizard.model.BranchPage;
import com.example.android.wizardpager.wizard.model.CustomerAddressPage;
import com.example.android.wizardpager.wizard.model.CustomerInfoPage;
//import com.example.android.wizardpager.wizard.model.MultipleFixedChoicePage;
import com.example.android.wizardpager.wizard.model.PageList;
import com.example.android.wizardpager.wizard.model.SingleFixedChoicePage;

import android.content.Context;

public class AccountOpeningWizardModel extends AbstractWizardModel {
    public AccountOpeningWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                new BranchPage(this, "Select Account Type")
                        .addBranch("Personal Bank Account", 
                        new SingleFixedChoicePage(this, "Do you accept the Terms & Conditions?")
                                .setChoices("Yes")
                                .setRequired(true),
                                
                                new SingleFixedChoicePage(this, "Would you like to receive your Terms & Conditions via E-Mail?")
                        .setChoices("Yes", "No")
                        .setValue("Yes")
                        		)
                                        
                                        
                                            

/*                                new BranchPage(this, "Do you accept the Terms & Conditions of this new Account Opening?")
                                        .addBranch("Yes",
                                                new SingleFixedChoicePage(this, "Would you like to receive your Terms & Conditions via E-Mail?")
                                                        .setChoices("Yes", "No"))
                                        .addBranch("No")
                                        .setValue("No")),*/
                                        
		                .addBranch("Student Plus Account",
		                        new SingleFixedChoicePage(this, "Do you accept the Terms & Conditions?")
                        .setChoices("Yes")
                        .setRequired(true),
                        
                new SingleFixedChoicePage(this, "Would you like to receive your Terms & Conditions via E-Mail?")
                .setChoices("Yes", "No")
                .setValue("Yes"))

                .setRequired(true),
	                                                                                              

/*                new CustomerInfoPage(this, "Your details")
                        .setRequired(true)
        );*/

        
                new CustomerInfoPage(this, "Your details")
                .setRequired(false),
        
        new CustomerAddressPage(this, "Your address")
        .setRequired(false)
        

        
        
        );
    }
}
