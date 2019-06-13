package com.stateside.stateside.appmodule.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.stateside.stateside.R;
import com.stateside.stateside.appmodule.activity.AboutActivity;
import com.stateside.stateside.appmodule.activity.MainActivity;
import com.stateside.stateside.appmodule.activity.ScheduleDetailActivity;
import com.stateside.stateside.appmodule.activity.DirectionsActivity;
import com.stateside.stateside.appmodule.fragment.adapters.ScheduleAdapter;
import com.stateside.stateside.information.CurrentEvent;
import com.stateside.stateside.information.Event;
import com.stateside.stateside.networking.JSONClient;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.stateside.stateside.appmodule.fragment.RegisterFragment.ID;
import static com.stateside.stateside.appmodule.fragment.RegisterFragment.REGISTER_PREFERENCES;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    final public static String CHECKED_IN = "CHECKED_IN";

    private ConstraintLayout checkinScreen;
    private SharedPreferences sharedPreferences;
    private TextView tvCheckinTitle, tvCheckinSubtitle;
    private Button buttonCheckin;
    private Button buttonCheckinMain;
    private ScheduleAdapter adapter;

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

        checkinScreen = view.findViewById(R.id.checkinDialog);
        tvCheckinSubtitle = view.findViewById(R.id.tvCheckinSubtitle);
        tvCheckinTitle = view.findViewById(R.id.tvCheckinTitle);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerSchedule);

        adapter = new ScheduleAdapter(getEvents(), this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
        view.findViewById(R.id.container).requestFocus();
        validateCheckin();

        getCurrentEvent();
    }

    private void validateCheckin() {
        if(getSharedPreferences().getBoolean(CHECKED_IN, false)) {
            buttonCheckinMain.setVisibility(View.GONE);
        } else {

            Calendar startTime = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mma");

            try {
                startTime.setTime(dateFormat.parse("06/12/2018 00:00AM"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar currentTime = new GregorianCalendar();
            if (currentTime.after(startTime)) {
                buttonCheckinMain.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setupButtons(View view) {
        buttonCheckin = view.findViewById(R.id.buttonCheckin);
        buttonCheckin.setOnClickListener(this);
        view.findViewById(R.id.imageButtonCheckingClose).setOnClickListener(this);

        view.findViewById(R.id.buttonDirections).setOnClickListener(this);
        view.findViewById(R.id.imageButtonInformation).setOnClickListener(this);

        buttonCheckinMain = view.findViewById(R.id.buttonCheckinMain);
        buttonCheckinMain.setOnClickListener(this);
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

    public void display(String titleS, String descriptionS, String resource) {
        Intent intent = new Intent(getActivity(), ScheduleDetailActivity.class);
        intent.putExtra("title", titleS);
        intent.putExtra("description", descriptionS);
        intent.putExtra("resource", resource);
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
            case R.id.buttonCheckinMain:
                if(getSharedPreferences().getLong(ID, 0) != 0) {
                    checkinScreen.setVisibility(View.VISIBLE);
                } else {
                    BottomNavigationView bottomNavigationView = ((MainActivity)getActivity()).findViewById(R.id.bottom_navigation);
                    View registerAction = bottomNavigationView.findViewById(R.id.action_register);
                    registerAction.performClick();
                }
                break;
            case R.id.buttonCheckin:
                if(!getSharedPreferences().getBoolean(CHECKED_IN, false)) {
                    requestQRScanner();
                } else {
                    checkinScreen.setVisibility(View.GONE);
                }

                break;
            case R.id.imageButtonCheckingClose:
                checkinScreen.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            if(scanResult.getContents() != null && scanResult.getContents().equals("https://qrco.de/mobilenight")) {
                checkin();
            }
        }
    }

    private void checkin() {
        JSONClient.getRetrofit().checkIn(getSharedPreferences().getLong(ID, 0))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 200) {
                            getSharedPreferences().edit().putBoolean(CHECKED_IN, true).apply();
                            validateCheckin();
                            displaySuccess();
                        } else {
                            Toast.makeText(getContext(), "Ooops Something went wrong, try again later", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "Ooops Something went wrong, try again later", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void displaySuccess() {
        tvCheckinTitle.setText("ALL SET!");
        tvCheckinSubtitle.setText("You were successfully checked in! \nWe hope you enjoy the event :)");
        buttonCheckin.setText("CONTINUE");
        checkinScreen.setVisibility(View.VISIBLE);
    }

    private void requestQRScanner() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan QR Code");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    private SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(REGISTER_PREFERENCES, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }


    private void getCurrentEvent() {
        JSONClient.getRetrofit().getCurrentAgenda()
                .enqueue(new Callback<CurrentEvent>() {
                    @Override
                    public void onResponse(Call<CurrentEvent> call, Response<CurrentEvent> response) {
                        if(response.code() == 200) {
                            adapter.setCurrentEvent(response.body().getCurrent());
                        } else {
                            Toast.makeText(getContext(), "Ooops Something went wrong, try again later", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CurrentEvent> call, Throwable t) {
                        Toast.makeText(getContext(), "Ooops Something went wrong, try again later", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
