package ie.aib.aibaccountopening;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class Main extends Activity {
	
	private Boolean poi_check = false;
	private Boolean poa_check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Boolean networkAvailable;
        
    	Context context = getApplicationContext();
    	CharSequence text = "You are not online! Please connect to the internet";
    	int duration = 8000;

    	Toast toast = Toast.makeText(context, text, duration);
    	
    	networkAvailable = checkInternetConnection();


        if(networkAvailable!=true)
        {
        	toast.show();
        }
        else
        {
            //everythings ok
        }
        
    	if (poa_check & poi_check) {
            Intent openAccountType = new Intent(Main.this,AccountType.class);
            Main.this.startActivity(openAccountType);
    	}
        
        CheckBox poi = ( CheckBox ) findViewById( R.id.poi_chkbox );
        poi.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    // perform logic
                	if (poa_check) {
                        Intent openAccountType = new Intent(Main.this,AccountType.class);
                        Main.this.startActivity(openAccountType);
                	}
                	poi_check = true;
                	
                }

            }
        });

        CheckBox poa = ( CheckBox ) findViewById( R.id.poa_chkbox );
        poa.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    // perform logic
                	if (poi_check) {
                        Intent openAccountType = new Intent(Main.this,AccountType.class);
                        Main.this.startActivity(openAccountType);
                	}
                	poi_check = true;
                }

            }
        });

        
    }

    public boolean checkInternetConnection() {
        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() &&    conMgr.getActiveNetworkInfo().isConnected()) {
              return true;
        } else {
              System.out.println("Internet Connection Not Present");
            return false;
        }
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
}
