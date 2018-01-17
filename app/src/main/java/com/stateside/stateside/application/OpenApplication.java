package com.stateside.stateside.application;

import android.app.Application;

import com.stateside.stateside.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class OpenApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Montserrat-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
