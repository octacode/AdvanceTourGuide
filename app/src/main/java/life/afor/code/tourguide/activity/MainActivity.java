package life.afor.code.tourguide.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import life.afor.code.tourguide.R;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setClicks();
    }

    private void setClicks() {

        getTopPicsButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForPermission();
                launchIntent("topPicks");
            }
        });

        getFoodButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForPermission();
                launchIntent("food");
            }
        });

        getCafeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForPermission();
                launchIntent("coffee");
            }
        });

        getNightLifeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForPermission();
                launchIntent("drinks");
            }
        });

        getFunButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForPermission();
                launchIntent("outdoors");
            }
        });

        getShoppingButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForPermission();
                launchIntent("shops");
            }
        });
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

    private Button getTopPicsButton(){
        return (Button)findViewById(R.id.top_pics);
    }

    private Button getFoodButton(){
        return (Button)findViewById(R.id.food);
    }

    private Button getCafeButton(){
        return (Button)findViewById(R.id.cafe);
    }

    private Button getNightLifeButton(){
        return (Button)findViewById(R.id.night_life);
    }

    private Button getFunButton(){
        return (Button)findViewById(R.id.fun);
    }

    private Button getShoppingButton(){
        return (Button)findViewById(R.id.shopping);
    }

}
