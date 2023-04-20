package com.example.myapplication.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.application.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ChangePasswordFragment extends Fragment {
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button changePasswordBtn;
    public ChangePasswordFragment() {
        super(R.layout.fragment_change_password);
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
        passwordEditText = view.findViewById(R.id.changePasswordEditText);
        confirmPasswordEditText = view.findViewById(R.id.confirmChangePasswordEditText);
        changePasswordBtn = view.findViewById(R.id.confirmChangePasswordBtn);
    }

    private void initListeners(View view) {
        changePasswordBtn.setOnClickListener(e->{
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();
            if(!password.equals(MyApplication.password) && password.equals(confirmPassword) && validPassword(password)) {
                writeNewPassword(password);
                Toast.makeText(getActivity(), "Promenjena sifra", Toast.LENGTH_LONG).show();
                this.getActivity().onBackPressed();
            }
        });
    }

    private void writeNewPassword(String password) {
        try {
            FileOutputStream outputStream = new FileOutputStream(MyApplication.file);
            outputStream.write(password.getBytes());
            outputStream.close();
            MyApplication.password = password;
            Log.d("password",password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validPassword(String password) {
        if(password.length() < 5)
            return false;
        int bigLetters = 0;
        int numOfDigits = 0;
        for(int i = 0;i < password.length(); i++) {
            char c = password.charAt(i);
            if(Character.isDigit(c))
                numOfDigits++;
            else if(Character.isUpperCase(c))
                bigLetters++;
            else if(c == '~' || c == '#' || c == '^' || c == '|' || c == '$' || c == '%'
                    || c == '&' || c == '*' || c == '!')
                return false;
        }
        return bigLetters > 0 && numOfDigits > 0;
    }
}