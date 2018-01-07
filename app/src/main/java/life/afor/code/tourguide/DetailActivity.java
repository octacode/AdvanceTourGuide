package life.afor.code.tourguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String present = getIntent().getStringExtra("presentLocation");
        String dest = getIntent().getStringExtra("toLocation");
        Toast.makeText(this, present+" "+dest, Toast.LENGTH_SHORT).show();
    }
}
