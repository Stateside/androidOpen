package com.stateside.stateside.appmodule.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.stateside.stateside.R;
import com.stateside.stateside.appmodule.activity.AboutActivity;
import com.stateside.stateside.appmodule.activity.MainActivity;
import com.stateside.stateside.appmodule.activity.ScheduleDetailActivity;
import com.stateside.stateside.appmodule.activity.DirectionsActivity;
import com.stateside.stateside.appmodule.fragment.adapters.ScheduleAdapter;
import com.stateside.stateside.information.Event;

import java.io.IOException;
import java.io.InputStream;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupButtons(view);
        /*getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_schedule_container, new ScheduleFragment())
                .commit();*/

        RecyclerView recyclerView = view.findViewById(R.id.recyclerSchedule);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ScheduleAdapter(getEvents(), this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
        view.findViewById(R.id.container).requestFocus();
    }

    private void setupButtons(View view){
        view.findViewById(R.id.buttonDirections).setOnClickListener(this);
        view.findViewById(R.id.imageButtonInformation).setOnClickListener(this);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("schedule.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private Event[] getEvents(){
        Gson gson = new Gson();
        Event[] events = null;
        events = gson.fromJson(loadJSONFromAsset(), Event[].class); // contains the whole reviews list
        return events;
    }

    public void display(String titleS, String descriptionS) {
        Intent intent = new Intent(getActivity(), ScheduleDetailActivity.class);
        intent.putExtra("title", titleS);
        intent.putExtra("description", descriptionS);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonDirections:
                Intent intent = new Intent(getActivity(), DirectionsActivity.class);
                startActivity(intent);
                break;
            case R.id.imageButtonInformation:
                Intent aboutIntent = new Intent(getActivity(), AboutActivity.class);
                startActivity(aboutIntent);
                break;
            default:
                break;
        }
    }
}
