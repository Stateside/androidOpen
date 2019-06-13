package com.stateside.stateside.appmodule.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.stateside.stateside.R;
import com.stateside.stateside.appmodule.activity.AboutActivity;
import com.stateside.stateside.information.NewUserResponse;
import com.stateside.stateside.networking.JSONClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends BaseFragment implements View.OnClickListener {

    public static String ID = "ID";

    private EditText editTextFullName;
    private EditText editTextJobTitle;
    private EditText editTextLevel;
    private EditText editTextEmail;
    private EditText editTextPhone;

    ConstraintLayout quizDone;
    LinearLayout quiz;

    private SharedPreferences sharedPreferences;
    public static final String REGISTER_PREFERENCES = "REGISTER";

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
        setupViews(view);
        setupButtons(view);
        validateRegistration();
    }

    private void validateRegistration() {
        if(getSharedPreferences().getLong(ID, 0) == 0) {
            quiz.setVisibility(View.VISIBLE);
            quizDone.setVisibility(View.GONE);
        } else {
            quiz.setVisibility(View.GONE);
            quizDone.setVisibility(View.VISIBLE);
        }
    }

    private void setupViews(View view) {
        quizDone = view.findViewById(R.id.quizDone);
        quiz = view.findViewById(R.id.quiz);
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
        if (!editTextFullName.getText().toString().isEmpty() ||
                !editTextJobTitle.getText().toString().isEmpty() ||
                !editTextLevel.getText().toString().isEmpty() ||
                !editTextEmail.getText().toString().isEmpty() ||
                !editTextPhone.getText().toString().isEmpty()) {
            hideKeyboard();
            JSONClient.getRetrofit().newUser(
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
                                        .putLong(ID, response.body().getId())
                                        .apply();
                                validateRegistration();
                            } else {
                                Toast.makeText(getContext(), "Ooops Something went wrong, try again later", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<NewUserResponse> call, Throwable t) {
                            Toast.makeText(getContext(), "Ooops Something went wrong, try again later", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(REGISTER_PREFERENCES, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isAcceptingText()) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
