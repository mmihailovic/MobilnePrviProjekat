package com.example.myapplication.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.application.MyApplication;

import java.io.FileInputStream;
import java.io.IOException;

public class LoginFragment extends Fragment {
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView emailNullTextView;
    private TextView usernameNullTextView;
    private TextView passwordNullTextView;
    private Button loginButton;
    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        initView(view);
        initListeners(view);
    }

    private void initView(View view) {
        emailEditText = view.findViewById(R.id.emailEditText);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginBtn);
        emailNullTextView = view.findViewById(R.id.emailNullTextView);
        usernameNullTextView = view.findViewById(R.id.usernameNullTextView);
        passwordNullTextView = view.findViewById(R.id.passwordNullTextView);
    }

    private void initListeners(View view) {
        loginButton = view.findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(e->{
            Log.d("Password", MyApplication.password);
            usernameNullTextView.setVisibility(View.INVISIBLE);
            passwordNullTextView.setVisibility(View.INVISIBLE);
            emailNullTextView.setVisibility(View.INVISIBLE);
            boolean login = true;
            String username = usernameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if(username == null || username.length() == 0)
            {
                login = false;
                usernameNullTextView.setVisibility(View.VISIBLE);
            }
            if(email == null || email.length() == 0) {
                login = false;
                emailNullTextView.setVisibility(View.VISIBLE);
            }
            if(password == null || password.length() == 0) {
                login = false;
                passwordNullTextView.setVisibility(View.VISIBLE);
            }
            if(!login) return;
            if(passwordEditText.getText().toString().equals(MyApplication.password)) {
                SharedPreferences preferences = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
                preferences.edit().putString("USERNAME", usernameEditText.getText().toString()).apply();
                preferences.edit().putString("EMAIL", emailEditText.getText().toString()).apply();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.replaceFragmentFcv, new BottomNavigationFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
            else {
                Toast.makeText(getActivity(), "Pogresna sifra!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}