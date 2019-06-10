package com.stateside.stateside.appmodule.activity;

import android.os.Bundle;

import com.stateside.stateside.R;
import com.stateside.stateside.appmodule.fragment.SplashFragment;
import com.stateside.stateside.appmodule.fragment.WelcomeFragment;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends BaseClass {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new SplashFragment())
                .commit();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.fragment_container, new WelcomeFragment())
                        .commitAllowingStateLoss();
            }
        }, 2000);
    }
}
