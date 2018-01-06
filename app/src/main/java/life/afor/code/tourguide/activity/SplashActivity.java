package life.afor.code.tourguide.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import life.afor.code.tourguide.R;
import life.afor.code.tourguide.utils.Preferences;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 1750;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Preferences.getFirstRun(SplashActivity.this))
                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                else {
                    if(!Preferences.isLoggedIn(SplashActivity.this)) {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    else
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }
        }, SPLASH_TIME_OUT);

    }

}
