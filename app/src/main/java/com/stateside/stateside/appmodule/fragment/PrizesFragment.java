package com.stateside.stateside.appmodule.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.stateside.stateside.R;
import com.stateside.stateside.appmodule.activity.AboutActivity;
import com.stateside.stateside.information.GanadorResponse;
import com.stateside.stateside.information.NewUserResponse;
import com.stateside.stateside.networking.JSONClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.stateside.stateside.appmodule.fragment.RegisterFragment.ID;

public class PrizesFragment extends BaseFragment implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    private static String NAME_PREFERENCES= "Prize";
    private static String PHONE= "Phone";
    private RelativeLayout haventstart;
    private RelativeLayout prizenotfound;
    private RelativeLayout won;

    public PrizesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prizes, container, false);
        view.findViewById(R.id.buttonRefresh1).setOnClickListener(this);
        view.findViewById(R.id.buttonRefresh2).setOnClickListener(this);
        view.findViewById(R.id.imageButtonInformation).setOnClickListener(this);
        haventstart = view.findViewById(R.id.haventstart);
        prizenotfound = view.findViewById(R.id.prizenotfound);
        won = view.findViewById(R.id.won);

        //haventstart.setVisibility(View.VISIBLE);
        updateVisibilities();
        return view;
    }

    private void updateVisibilities() {
        JSONClient.getRetrofit().getWinner()
                .enqueue(new Callback<GanadorResponse>() {
                    @Override
                    public void onResponse(Call<GanadorResponse> call, Response<GanadorResponse> response) {
                        processGanador(response.body());
                    }

                    @Override
                    public void onFailure(Call<GanadorResponse> call, Throwable t) {

                    }
                });
    }

    private void processGanador(GanadorResponse body) {
        haventstart.setVisibility(View.GONE);
        prizenotfound.setVisibility(View.GONE);
        won.setVisibility(View.GONE);

        long winnerId = getSharedPreferences().getLong(ID, 0);

        if(body.getId() == -1) {
            haventstart.setVisibility(View.VISIBLE);
        } else if(body.getId() == winnerId) {
            won.setVisibility(View.VISIBLE);
        } else {
            prizenotfound.setVisibility(View.VISIBLE);
        }
    }

    private SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(NAME_PREFERENCES, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRefresh1:
                updateVisibilities();
                break;
            case R.id.buttonRefresh2:
                updateVisibilities();
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
