package ie.aib.aibaccountopening;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class AccountType extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_type);
		
        Button startPhoto = (Button) findViewById(R.id.next);
        startPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            Intent openPhoto = new Intent(AccountType.this,StartPhotoID.class);

            AccountType.this.startActivity(openPhoto);

            }

        });
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_type, menu);
		return true;
	}

}
