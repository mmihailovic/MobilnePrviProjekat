package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.view.fragments.BottomNavigationFragment;
import com.example.myapplication.view.fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private final String SECOND_FRAGMENT_TAG = "secondFragment";
    private final String FIRST_FRAGMENT_TAG = "firstFragment";
//    private ViewPager viewPager;
    private ViewModel dailyPlanViewModel;
    private ViewModel calendarViewModel;
    private ViewModel obavezaDetailsViewModel;
    private ViewModel obavezaEditViewModel;
    private RecyclerView calendarRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return false;
        });
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SharedPreferences preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String username = preferences.getString("USERNAME",null);
        if(username == null) {
            transaction.add(R.id.replaceFragmentFcv, new LoginFragment(), FIRST_FRAGMENT_TAG);
        }
        else {
            transaction.add(R.id.replaceFragmentFcv, new BottomNavigationFragment(), FIRST_FRAGMENT_TAG);
        }
        transaction.commit();
    }

//    public ViewPager getViewPager() {
//        return viewPager;
//    }
//
//    public void setViewPager(ViewPager viewPager) {
//        this.viewPager = viewPager;
//    }

    public ViewModel getDailyPlanViewModel() {
        return dailyPlanViewModel;
    }

    public void setDailyPlanViewModel(ViewModel dailyPlanViewModel) {
        this.dailyPlanViewModel = dailyPlanViewModel;
    }

    public ViewModel getCalendarViewModel() {
        return calendarViewModel;
    }

    public void setCalendarViewModel(ViewModel calendarViewModel) {
        this.calendarViewModel = calendarViewModel;
    }

    public RecyclerView getCalendarRecyclerView() {
        return calendarRecyclerView;
    }

    public void setCalendarRecyclerView(RecyclerView calendarRecyclerView) {
        this.calendarRecyclerView = calendarRecyclerView;
    }

    public ViewModel getObavezaDetailsViewModel() {
        return obavezaDetailsViewModel;
    }

    public void setObavezaDetailsViewModel(ViewModel obavezaDetailsViewModel) {
        this.obavezaDetailsViewModel = obavezaDetailsViewModel;
    }

    public ViewModel getObavezaEditViewModel() {
        return obavezaEditViewModel;
    }

    public void setObavezaEditViewModel(ViewModel obavezaEditViewModel) {
        this.obavezaEditViewModel = obavezaEditViewModel;
    }
}