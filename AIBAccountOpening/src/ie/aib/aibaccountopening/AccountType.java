package ie.aib.aibaccountopening;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AccountType extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_type);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_type, menu);
		return true;
	}

}
