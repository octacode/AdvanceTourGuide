package life.afor.code.tourguide.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import life.afor.code.tourguide.R;
import life.afor.code.tourguide.utils.Preferences;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Tour Guide", "An Android app which tells you about what's nearby", R.drawable.tour, getResources().getColor(R.color.intro_1)));
        addSlide(AppIntroFragment.newInstance("Select the best!", "Compare the ratings of each place and select the best", R.drawable.stars, getResources().getColor(R.color.intro_5)));
        addSlide(AppIntroFragment.newInstance("Search feature", "Search for the nearest bus station, train stop, college and almost anything", R.drawable.search, Color.DKGRAY));
        addSlide(AppIntroFragment.newInstance("Google Maps", "Google maps has been integrated into the app", R.drawable.maps, getResources().getColor(R.color.intro_4)));
        addSlide(AppIntroFragment.newInstance("Ready Set Go...", "" , R.drawable.tick, getResources().getColor(R.color.intro_6)));

        setFlowAnimation();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Preferences.setFirstRun(this);
        startActivity(new Intent(this, SplashActivity.class));
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Preferences.setFirstRun(this);
        startActivity(new Intent(this, SplashActivity.class));
    }
}
