package life.afor.code.tourguide.activity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import life.afor.code.tourguide.R;
import life.afor.code.tourguide.app.model.FoursquareResults;
import life.afor.code.tourguide.app.model.WeatherReport;

public class DetailActivity extends AppCompatActivity {

    FoursquareResults foursquareResults;
    String baseUrl = "http://api.openweathermap.org/data/2.5/weather?lat=";
    TextView weatherDesc, minTemp, maxTemp, windSpeed, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String present = getIntent().getStringExtra("presentLocation");
        foursquareResults = (FoursquareResults) getIntent().getSerializableExtra("toLocation");
        double lat = foursquareResults.getVenue().getLocation().lat;
        double lng = foursquareResults.getVenue().getLocation().lng;
        baseUrl = baseUrl + lat + "&lon=" + lng + "&appid=c6867299216e9be87c062428b6315b07";
        weatherDesc = findViewById(R.id.description);
        minTemp = findViewById(R.id.min_temp);
        maxTemp = findViewById(R.id.max_temp);
        windSpeed = findViewById(R.id.wind_speed);
        address = findViewById(R.id.address);
        address.setText("Address: "+foursquareResults.getVenue().getLocation().address);
        try {
            String report = new WeatherTask().execute().get();
            WeatherReport weatherReport = getWeatherDataFromJSON(report);
            weatherDesc.setText("Description: "+weatherReport.getDescription());
            minTemp.setText("Minimum Temperature: "+weatherReport.getMinTemp());
            maxTemp.setText("Maximum Temperature: "+weatherReport.getMaxTemp());
            windSpeed.setText("Wind Speed: "+weatherReport.getSpeed());
        } catch (InterruptedException | JSONException | ExecutionException e) {
            e.printStackTrace();
        }
        setMyActionBar();
    }

    private void setMyActionBar() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(foursquareResults.getVenue().getName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        return true;
    }

    private class WeatherTask extends AsyncTask<Void, Void, String>{
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String forecastJsonStr = null;
            URL url;

            try {
                url = new URL(baseUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                StringBuffer buffer;
                String line;

                try (InputStream inputStream = urlConnection.getInputStream()) {
                    buffer = new StringBuffer();
                    if (inputStream == null) {
                        forecastJsonStr = null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        forecastJsonStr = null;
                    }
                    forecastJsonStr = buffer.toString();
                    Log.d(getClass().getSimpleName(), forecastJsonStr);
                }
                catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
                }
            }
            return forecastJsonStr;
        }
    }

    private WeatherReport getWeatherDataFromJSON(String weatherJSON) throws JSONException{
        if(weatherJSON != null){
            JSONObject weatherReport = new JSONObject(weatherJSON);
            JSONArray weatherArr = weatherReport.getJSONArray("weather");
            String description = weatherArr.getJSONObject(0).getString("description");
            JSONObject main = weatherReport.getJSONObject("main");
            String minTemp = main.getString("temp_min");
            String maxTemp = main.getString("temp_max");
            JSONObject wind = weatherReport.getJSONObject("wind");
            String speed = wind.getString("speed");
            return new WeatherReport(description, maxTemp, minTemp, speed);
        }
        return null;
    }
}
