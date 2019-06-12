package com.stateside.stateside.appmodule.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.stateside.stateside.R;

public class ScheduleDetailActivity extends BaseClass implements View.OnClickListener {
    Toolbar toolbar;
    TextView tvTitle, tvDescription;
    ImageView imageViewSpeaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_schedule);

        tvTitle = findViewById(R.id.textViewTitle);
        tvDescription = findViewById(R.id.textViewSubject);
        imageViewSpeaker = findViewById(R.id.imageViewSpeaker);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        String title = getIntent().getExtras().getString("title");
        String description = getIntent().getExtras().getString("description");
        String resource = getIntent().getExtras().getString("resource");

        tvTitle.setText(title);
        tvDescription.setText(Html.fromHtml(description));

        int resID = getResources().getIdentifier(resource , "drawable", getPackageName());
        imageViewSpeaker.setImageResource(resID);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonFacebook:

                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
