package com.stateside.stateside.appmodule.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stateside.stateside.R;

public class LocationFragment extends BaseFragment implements OnMapReadyCallback, View.OnClickListener {

    public LocationFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupButtons(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
       mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng stateside = new LatLng(9.9416735, -84.0751069);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.addMarker(new MarkerOptions().position(stateside)
                .title("Stateside"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stateside, 17));
    }

    private void setupButtons(View view){
        view.findViewById(R.id.buttonMaps).setOnClickListener(this);
        view.findViewById(R.id.buttonUber).setOnClickListener(this);
        view.findViewById(R.id.buttonWaze).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
