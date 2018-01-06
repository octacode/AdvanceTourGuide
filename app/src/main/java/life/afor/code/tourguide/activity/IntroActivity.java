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

        addSlide(AppIntroFragment.newInstance("FIRST", "lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem", R.drawable.background, Color.DKGRAY));
        addSlide(AppIntroFragment.newInstance("FIRST", "lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem", R.drawable.background, Color.DKGRAY));
        addSlide(AppIntroFragment.newInstance("FIRST", "lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem", R.drawable.background, Color.DKGRAY));
        addSlide(AppIntroFragment.newInstance("FIRST", "lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem", R.drawable.background, Color.DKGRAY));
        addSlide(AppIntroFragment.newInstance("FIRST", "lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem", R.drawable.background, Color.DKGRAY));
        addSlide(AppIntroFragment.newInstance("FIRST", "lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem", R.drawable.background, Color.DKGRAY));
        addSlide(AppIntroFragment.newInstance("FIRST", "lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem", R.drawable.background, Color.DKGRAY));
        addSlide(AppIntroFragment.newInstance("FIRST", "lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem", R.drawable.background, Color.DKGRAY));
        addSlide(AppIntroFragment.newInstance("FIRST", "lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem", R.drawable.background, Color.DKGRAY));

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
