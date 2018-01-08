package life.afor.code.tourguide.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import life.afor.code.tourguide.R;
import life.afor.code.tourguide.adapter.PickerAdapter;
import life.afor.code.tourguide.app.model.FoursquareGroup;
import life.afor.code.tourguide.app.model.FoursquareJSON;
import life.afor.code.tourguide.app.model.FoursquareResponse;
import life.afor.code.tourguide.app.model.FoursquareResults;
import life.afor.code.tourguide.app.model.FoursquareVenue;
import life.afor.code.tourguide.app.refs.FoursquareService;
import life.afor.code.tourguide.utils.RecyclerItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PickerActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;
    private String foursquareBaseURL = "https://api.foursquare.com/v2/";
    private ProgressBar progressBar;
    private TextView locationTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        locationTv = (TextView)findViewById(R.id.location);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {
                final String userLL = mLastLocation.getLatitude() + "," + mLastLocation.getLongitude();
                double userLLAcc = mLastLocation.getAccuracy();
                locationTv.setText(userLL);
                try {
                    Address location = new fetchAddress().execute(userLL).get();
                    locationTv.setText(location.getAddressLine(0));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(foursquareBaseURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final FoursquareService foursquare = retrofit.create(FoursquareService.class);

                Call<FoursquareJSON> stpcall = foursquare.snapToPlace(
                        getString(R.string.foursquare_client_id),
                        getString(R.string.foursquare_client_secret),
                        userLL,
                        userLLAcc
                );

                stpcall.enqueue(new Callback<FoursquareJSON>() {
                    @Override
                    public void onResponse(Call<FoursquareJSON> call, Response<FoursquareJSON> response) {
                        FoursquareJSON fjson = response.body();
                        FoursquareResponse fr = fjson.getResponse();
                        List<FoursquareVenue> frs = fr.getVenues();
                    }

                    @Override
                    public void onFailure(Call<FoursquareJSON> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "We can't connect to Foursquare's servers!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

                getCall(foursquare, userLL, userLLAcc).enqueue(new Callback<FoursquareJSON>() {
                    @Override
                    public void onResponse(Call<FoursquareJSON> call, Response<FoursquareJSON> response) {
                        FoursquareJSON fjson = response.body();
                        FoursquareResponse fr = fjson.getResponse();
                        FoursquareGroup fg = fr.getGroup();
                        final List<FoursquareResults> frs = fg.getResults();
                        Collections.sort(frs, new Comparator<FoursquareResults>() {
                            @Override
                            public int compare(FoursquareResults foursquareResults, FoursquareResults t1) {
                                return Double.compare(t1.getVenue().getRating(), foursquareResults.getVenue().getRating());
                            }
                        });
                        for(int i=0; i<frs.size(); i++) {
                            String ll = frs.get(i).getVenue().getLocation().lat+","+frs.get(i).getVenue().getLocation().lng;
                            String location = ll;
                            try {
                                location = new fetchAddress().execute(ll).get().getAddressLine(0);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                            frs.get(i).getVenue().getLocation().address = location;
                        }

                        progressBar.setVisibility(View.GONE);
                        RecyclerView recv = (RecyclerView)findViewById(R.id.recv);
                        PickerAdapter pickerAdapter = new PickerAdapter(frs, PickerActivity.this);
                        recv.setLayoutManager(new LinearLayoutManager(PickerActivity.this));
                        recv.setAdapter(pickerAdapter);
                        recv.addOnItemTouchListener(new RecyclerItemClickListener(PickerActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent i = new Intent(PickerActivity.this, DetailActivity.class);
                                i.putExtra("presentLocation", userLL);
                                i.putExtra("toLocation", frs.get(position));
                                startActivity(i);
                            }
                        }));
                    }

                    @Override
                    public void onFailure(Call<FoursquareJSON> call, Throwable t) {

                    }
                });
            }
        }
    }

    private Call<FoursquareJSON> getCall(FoursquareService foursquare, String userLL, double userLLAcc){
        switch(getIntent().getStringExtra(Intent.EXTRA_TEXT)) {
            case "topPicks":
                getSupportActionBar().setTitle("Top Picks");
                return foursquare.searchTopPicks(
                        getString(R.string.foursquare_client_id),
                        getString(R.string.foursquare_client_secret),
                        userLL,
                        userLLAcc);
            case "food":
                getSupportActionBar().setTitle("Food");
                return foursquare.searchFood(
                        getString(R.string.foursquare_client_id),
                        getString(R.string.foursquare_client_secret),
                        userLL,
                        userLLAcc);
            case "coffee":
                getSupportActionBar().setTitle("Cafe");
                return foursquare.searchCoffee(
                        getString(R.string.foursquare_client_id),
                        getString(R.string.foursquare_client_secret),
                        userLL,
                        userLLAcc);
            case "drinks":
                getSupportActionBar().setTitle("Night Life");
                return foursquare.searchNightLife(
                        getString(R.string.foursquare_client_id),
                        getString(R.string.foursquare_client_secret),
                        userLL,
                        userLLAcc);
            case "outdoors":
                getSupportActionBar().setTitle("Outdoors");
                return foursquare.searchFun(
                        getString(R.string.foursquare_client_id),
                        getString(R.string.foursquare_client_secret),
                        userLL,
                        userLLAcc);
            case "shops":
                getSupportActionBar().setTitle("Shops");
                return foursquare.searchShopping(
                        getString(R.string.foursquare_client_id),
                        getString(R.string.foursquare_client_secret),
                        userLL,
                        userLLAcc);
            default:
                getSupportActionBar().setTitle(getIntent().getStringExtra(Intent.EXTRA_TEXT));
                return foursquare.searchPlaces(
                        getString(R.string.foursquare_client_id),
                        getString(R.string.foursquare_client_secret),
                        userLL,
                        userLLAcc,
                        getIntent().getStringExtra(Intent.EXTRA_TEXT));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "We can't connect to Google's servers!", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private class fetchAddress extends AsyncTask<String, Void, Address>{
        @Override
        protected Address doInBackground(String... strings) {
            String ll[] = strings[0].split(",");
            double lat = Double.parseDouble(ll[0]);
            double lng = Double.parseDouble(ll[1]);

            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(PickerActivity.this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(lat,lng, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses.get(0);
        }
    }
}