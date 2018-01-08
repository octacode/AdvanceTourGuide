package life.afor.code.tourguide.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import life.afor.code.tourguide.R;
import life.afor.code.tourguide.utils.Preferences;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setClicks();
        checkEnabled();
        askForPermission();
    }

    private void setClicks() {

        getTopPicsButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchIntent("topPicks");
            }
        });

        getFoodButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchIntent("food");
            }
        });

        getCafeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchIntent("coffee");
            }
        });

        getNightLifeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchIntent("drinks");
            }
        });

        getFunButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchIntent("outdoors");
            }
        });

        getShoppingButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchIntent("shops");
            }
        });

        getSearchButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getSearchText().getText().toString().trim().length() == 0)
                    getSearchText().setError("Search text can't be left blank");
                else
                    launchIntent(getSearchText().getText().toString());
            }
        });
    }

    private void checkEnabled() {
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false, network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch (Exception ex){}
        try{
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch (Exception ex){}
        if(!gps_enabled && !network_enabled){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Location services are not turned on. Please turn it on from the settings");
            dialog.setPositiveButton("Okay!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }
    }
    private void askForPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private Intent getIntentType(String name) {
        return new Intent(this, PickerActivity.class).putExtra(Intent.EXTRA_TEXT, name);
    }

    private void launchIntent(String name) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startActivity(getIntentType(name));
        } else {
            Toast.makeText(getApplicationContext(), "Advance Tour Guide is missing permissions to access your location!", Toast.LENGTH_SHORT).show();
        }
    }

    private CardView getTopPicsButton(){
        return (CardView) findViewById(R.id.top_pics);
    }

    private CardView getFoodButton(){
        return (CardView)findViewById(R.id.food);
    }

    private CardView getCafeButton(){
        return (CardView)findViewById(R.id.cafe);
    }

    private CardView getNightLifeButton(){
        return (CardView)findViewById(R.id.night_life);
    }

    private CardView getFunButton(){
        return (CardView)findViewById(R.id.fun);
    }

    private CardView getShoppingButton(){
        return (CardView)findViewById(R.id.shopping);
    }

    private ImageView getSearchButton() {return (ImageView)findViewById(R.id.search_button);}

    private EditText getSearchText() {return (EditText)findViewById(R.id.searchtv);}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_log_out){
            Preferences.logOut(this);
            startActivity(new Intent(this, SplashActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
