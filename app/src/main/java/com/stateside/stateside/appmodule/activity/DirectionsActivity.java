package com.stateside.stateside.appmodule.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stateside.stateside.R;

public class DirectionsActivity extends BaseClass implements OnMapReadyCallback, View.OnClickListener {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
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
        findViewById(R.id.imageButtonInformation).setOnClickListener(this);

        findViewById(R.id.buttonMaps).setOnClickListener(this);
        findViewById(R.id.buttonUber).setOnClickListener(this);
        findViewById(R.id.buttonWaze).setOnClickListener(this);
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

    @Override
    public void onStart() {
        super.onStart();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng stateside = new LatLng(9.9416735, -84.0751069);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.addMarker(new MarkerOptions().position(stateside)
                .title("Stateside"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stateside, 17));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonInformation:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.buttonMaps:
                String map = "http://maps.google.com/maps?q=" + "stateside+costa+rica";
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                startActivity(i);
                break;
            case R.id.buttonUber:
                String uber = "https://m.uber.com/ul/?action=setPickup&dropoff[latitude]=9.9416735&dropoff[longitude]=-84.0751069&dropoff[nickname]=Stateside";
                Intent iU = new Intent(Intent.ACTION_VIEW, Uri.parse(uber));
                startActivity(iU);
                break;
            case R.id.buttonWaze:
                String waze = "https://waze.com/ul?ll=9.9416735, -84.0751069";
                Intent iW = new Intent(Intent.ACTION_VIEW, Uri.parse(waze));
                startActivity(iW);
                break;
            default:
                break;
        }
    }
}