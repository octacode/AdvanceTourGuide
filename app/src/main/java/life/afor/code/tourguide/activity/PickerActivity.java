package life.afor.code.tourguide.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import life.afor.code.tourguide.R;
import life.afor.code.tourguide.adapter.PickerAdapter;
import life.afor.code.tourguide.app.model.FoursquareGroup;
import life.afor.code.tourguide.app.model.FoursquareJSON;
import life.afor.code.tourguide.app.model.FoursquareResponse;
import life.afor.code.tourguide.app.model.FoursquareResults;
import life.afor.code.tourguide.app.model.FoursquareVenue;
import life.afor.code.tourguide.app.refs.FoursquareService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PickerActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;
    private String foursquareBaseURL = "https://api.foursquare.com/v2/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        getSupportActionBar().setTitle(getIntent().getStringExtra(Intent.EXTRA_TEXT));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {
                String userLL = mLastLocation.getLatitude() + "," + mLastLocation.getLongitude();
                double userLLAcc = mLastLocation.getAccuracy();

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
                        FoursquareVenue fv = frs.get(0);
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
                        List<FoursquareResults> frs = fg.getResults();
                        Collections.sort(frs, new Comparator<FoursquareResults>() {
                            @Override
                            public int compare(FoursquareResults foursquareResults, FoursquareResults t1) {
                                return Double.compare(t1.getVenue().getRating(), foursquareResults.getVenue().getRating());
                            }
                        });
                        RecyclerView recv = (RecyclerView)findViewById(R.id.recv);
                        PickerAdapter pickerAdapter = new PickerAdapter(frs);
                        recv.setLayoutManager(new LinearLayoutManager(PickerActivity.this));
                        recv.setAdapter(pickerAdapter);
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
                return foursquare.searchTopPicks(
                        getString(R.string.foursquare_client_id),
                        getString(R.string.foursquare_client_secret),
                        userLL,
                        userLLAcc);
            case "food":
                return foursquare.searchFood(
                        getString(R.string.foursquare_client_id),
                        getString(R.string.foursquare_client_secret),
                        userLL,
                        userLLAcc);
            case "coffee":
                return foursquare.searchCoffee(
                        getString(R.string.foursquare_client_id),
                        getString(R.string.foursquare_client_secret),
                        userLL,
                        userLLAcc);
            case "drinks":
                return foursquare.searchNightLife(
                        getString(R.string.foursquare_client_id),
                        getString(R.string.foursquare_client_secret),
                        userLL,
                        userLLAcc);
            case "outdoors":
                return foursquare.searchFun(
                        getString(R.string.foursquare_client_id),
                        getString(R.string.foursquare_client_secret),
                        userLL,
                        userLLAcc);
            case "shops":
                return foursquare.searchShopping(
                        getString(R.string.foursquare_client_id),
                        getString(R.string.foursquare_client_secret),
                        userLL,
                        userLLAcc);
            default:
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
}
