package life.afor.code.advancetourguide.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import life.afor.code.advancetourguide.R;

public class MainActivity extends AppCompatActivity {

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

            }
        });

        getFoodButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        getCafeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        getNightLifeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        getFunButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        getShoppingButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private Button getTopPicsButton(){
        return (Button)findViewById(R.id.top_pics);
    }

    private Button getFoodButton(){
        return (Button)findViewById(R.id.top_pics);
    }

    private Button getCafeButton(){
        return (Button)findViewById(R.id.top_pics);
    }

    private Button getNightLifeButton(){
        return (Button)findViewById(R.id.top_pics);
    }

    private Button getFunButton(){
        return (Button)findViewById(R.id.top_pics);
    }

    private Button getShoppingButton(){
        return (Button)findViewById(R.id.top_pics);
    }

}
