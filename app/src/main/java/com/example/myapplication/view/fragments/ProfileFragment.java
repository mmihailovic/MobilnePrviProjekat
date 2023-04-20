package com.example.myapplication.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class ProfileFragment extends Fragment {
    private TextView studentNameTextView;
    private TextView studentEmailTextView;
    private Button changePasswordBtn;
    private Button logoutBtn;
    public ProfileFragment() {
        super(R.layout.fragment_profile);
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
        studentEmailTextView = view.findViewById(R.id.studentEmailTextView);
        studentNameTextView = view.findViewById(R.id.studentNameTextView);
        changePasswordBtn = view.findViewById(R.id.changePasswordBtn);
        logoutBtn = view.findViewById(R.id.logoutBtn);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getActivity().getPackageName(),Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME",null);
        String email = sharedPreferences.getString("EMAIL",null);

        studentEmailTextView.setText(email);
        studentNameTextView.setText(username);
    }

    private void initListeners(View view) {
        changePasswordBtn.setOnClickListener(e->{
            FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.replaceFragmentFcv, new ChangePasswordFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });
        logoutBtn.setOnClickListener(e->{
            SharedPreferences preferences = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
            preferences.edit().clear().apply();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.replaceFragmentFcv, new LoginFragment());
            transaction.commit();
        });
    }
}