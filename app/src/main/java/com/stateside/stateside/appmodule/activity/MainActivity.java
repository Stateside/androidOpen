package com.stateside.stateside.appmodule.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.stateside.stateside.R;
import com.stateside.stateside.appmodule.fragment.HomeFragment;
import com.stateside.stateside.appmodule.fragment.PrizesFragment;
import com.stateside.stateside.appmodule.fragment.RegisterFragment;

public class MainActivity extends BaseClass {

    BottomNavigationView bottomNavigationView;

    private static final String FRAGMENT_HOME = "HOME";
    private static final String FRAGMENT_PRIZES = "PRIZES";
    private static final String FRAGMENT_REGISTER = "REGISTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFragment(new HomeFragment(), FRAGMENT_HOME);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        setFragment(new HomeFragment(), FRAGMENT_HOME);
                        return true;
                    case R.id.action_prizes:
                        setFragment(new PrizesFragment(), FRAGMENT_PRIZES);
                        return true;
                    case R.id.action_register:
                        setFragment(new RegisterFragment(), FRAGMENT_REGISTER);
                        return true;
                    default:
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //setFragment(new HomeFragment(), FRAGMENT_HOME);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .commit();
    }
}
