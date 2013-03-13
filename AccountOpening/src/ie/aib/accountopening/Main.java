package ie.aib.accountopening;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {

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
        
        Button startPhoto = (Button) findViewById(R.id.takePhoto);
        startPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            Intent openPhoto = new Intent(Main.this,Photo.class);

            Main.this.startActivity(openPhoto);

            }

        });
        
        return true;
    }
    
    
}
