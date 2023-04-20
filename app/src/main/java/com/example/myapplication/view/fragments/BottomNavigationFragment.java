package com.example.myapplication.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.view.viewpager.PagerAdapter;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationFragment extends Fragment {

    private ViewPager viewPager;

    public BottomNavigationFragment() {
        super(R.layout.fragment_bottom_navigation);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        initViewPager(view);
        initNavigation(view);
    }

    private void initViewPager(View view) {
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));
//        ((MainActivity)this.getActivity()).setViewPager(viewPager);
    }

    private void initNavigation(View view) {
        ((BottomNavigationView)view.findViewById(R.id.bottomNavigation)).setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                // setCurrentItem metoda viewPager samo obavesti koji je Item trenutno aktivan i onda metoda getItem u adapteru setuje odredjeni fragment za tu poziciju
                case R.id.navigation_1: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_1, false); break;
                case R.id.navigation_2: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_2, false); break;
                case R.id.navigation_3: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_3, false); break;
            }
            return true;
        });
    }
}