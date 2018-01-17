package com.stateside.stateside.appmodule.fragment;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stateside.stateside.R;
import com.stateside.stateside.appmodule.fragment.adapters.ScheduleAdapter;
import com.stateside.stateside.information.Event;

import java.io.IOException;
import java.io.InputStream;

public class ScheduleFragment extends BaseFragment {
    CardView cardView;
    TextView title;
    TextView description;

    public ScheduleFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        cardView = view.findViewById(R.id.info);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.GONE);
            }
        });
        title = view.findViewById(R.id.textViewTitle);
        description = view.findViewById(R.id.textViewDescription);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerSchedule);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ScheduleAdapter(getEvents(), this));
        return view;
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
        title.setText(titleS);
        description.setText(Html.fromHtml(descriptionS));
        cardView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onBackPressed() {
        if (cardView.getVisibility() == View.VISIBLE) {
            cardView.setVisibility(View.GONE);
            return true;
        }
        return false;
    }
}
