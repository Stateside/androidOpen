package com.stateside.stateside.appmodule.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.stateside.stateside.R;
import com.stateside.stateside.appmodule.activity.AboutActivity;
import com.stateside.stateside.information.NewUserResponse;
import com.stateside.stateside.networking.JSONClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends BaseFragment implements View.OnClickListener {

    private String AUTH= "d7e2b639fdfc4cfdfad5f6ba3d7dcdca";
    public static String EMAIL = "EMAIL";

    private EditText editTextFullName;
    private EditText editTextJobTitle;
    private EditText editTextLevel;
    private EditText editTextEmail;
    private EditText editTextPhone;

    private SharedPreferences sharedPreferences;
    private static final String REGISTER_PREFERENCES = "Register";

    public RegisterFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupButtons(view);
    }

    private void setupButtons(View view){
        view.findViewById(R.id.buttonRegister).setOnClickListener(this);
        view.findViewById(R.id.imageButtonInformation).setOnClickListener(this);
        editTextFullName = view.findViewById(R.id.editTextFullName);
        editTextJobTitle = view.findViewById(R.id.editTextJobTitle);
        editTextLevel = view.findViewById(R.id.editTextLevel);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPhone = view.findViewById(R.id.editTextPhone);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonRegister:
                register();
                break;
            case R.id.imageButtonInformation:
                Intent aboutIntent = new Intent(getActivity(), AboutActivity.class);
                startActivity(aboutIntent);
                break;
            default:
                break;
        }
    }

    private void register() {
        JSONClient.getRetrofit().newUser(AUTH,
                        editTextFullName.getText().toString(),
                        editTextJobTitle.getText().toString(),
                        editTextLevel.getText().toString(),
                        editTextEmail.getText().toString(),
                        editTextPhone.getText().toString())
                .enqueue(new Callback<NewUserResponse>() {
                    @Override
                    public void onResponse(Call<NewUserResponse> call, Response<NewUserResponse> response) {
                        if (response.isSuccessful()) {
                            getSharedPreferences().edit()
                                    .putString(EMAIL, editTextEmail.getText().toString())
                                    .apply();
                        } else {
                            Toast.makeText(getContext(), "Some problems on the backend", Toast.LENGTH_SHORT);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewUserResponse> call, Throwable t) {

                    }
                });
    }

    private SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(REGISTER_PREFERENCES, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }
}
