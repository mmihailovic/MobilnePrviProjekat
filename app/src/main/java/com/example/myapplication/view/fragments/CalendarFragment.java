package com.example.myapplication.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.Day;
import com.example.myapplication.models.Obaveza;
import com.example.myapplication.view.MainActivity;
import com.example.myapplication.view.recycler.adapter.DayAdapter;
import com.example.myapplication.view.recycler.differ.DayItemDiffCallback;
import com.example.myapplication.viewmodels.CalendarRecyclerViewModel;
import com.example.myapplication.viewmodels.ObavezaRecyclerViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CalendarFragment extends Fragment{
    private TextView monthView;
    private RecyclerView recyclerView;
    private CalendarRecyclerViewModel calendarRecyclerViewModel;
    private DayAdapter dayAdapter;

    public CalendarFragment() {
        // Required empty public constructor
        super(R.layout.fragment_calendar);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarRecyclerViewModel = new ViewModelProvider(this).get(CalendarRecyclerViewModel.class);
        ((MainActivity)this.getActivity()).setCalendarViewModel(calendarRecyclerViewModel);
        init(view);
    }

    private void init(View view) {
        initView(view);
        initRecycler(view);
        initObservers(view);
    }

    private void initView(View view) {
        monthView = view.findViewById(R.id.monthTextView);
        recyclerView = view.findViewById(R.id.calendarRecyclerView);
        ((MainActivity)this.getActivity()).setCalendarRecyclerView(recyclerView);
        LocalDate danasnjiDatum = LocalDate.now();
        String mesec = danasnjiDatum.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        String godina = String.valueOf(danasnjiDatum.getYear());
        monthView.setText(mesec + " " + godina +".");
    }

    private void initRecycler(View view) {
        dayAdapter = new DayAdapter(new DayItemDiffCallback(), car -> {
//            Toast.makeText(this.getActivity(), car.getDate() + "", Toast.LENGTH_SHORT).show();
            BottomNavigationView bottomNavigationView = this.getActivity().findViewById(R.id.bottomNavigation);
//            ((MainActivity)this.getActivity()).getViewPager().setCurrentItem(PagerAdapter.FRAGMENT_2);
            MainActivity mainActivity = ((MainActivity)this.getActivity());
            ObavezaRecyclerViewModel viewModel = (ObavezaRecyclerViewModel) mainActivity.getDailyPlanViewModel();
            List<Obaveza> listToSubmit = new ArrayList<>(car.getObaveze());
            viewModel.setObaveze(listToSubmit);
            viewModel.setDate(car.getDate());
            viewModel.setCarList((ArrayList<Obaveza>) listToSubmit);
            bottomNavigationView.findViewById(R.id.navigation_2).performClick();
            },calendarRecyclerViewModel);
        int position = 0;
        for(int i = 0;i < calendarRecyclerViewModel.getCarList().size(); i++) {
            if(calendarRecyclerViewModel.getCarList().get(i).getDate().isEqual(LocalDate.now())) {
                position = i;
            }
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),7, GridLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(dayAdapter);
        recyclerView.scrollToPosition(position);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                Map<String,Integer> days = new HashMap<>();
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();

                for (int i = firstVisiblePosition; i <= lastVisiblePosition; i++) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(i);
                    Day day = ((DayAdapter.ViewHolder)viewHolder).getCurrentDay();
                    String key = day.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + String.valueOf(day.getDate().getYear())+".";
                    if(days.containsKey(key)) {
                        days.replace(key,days.get(key) + 1);
                    }
                    else {
                        days.put(key,1);
                    }
                }
                LocalDate now = LocalDate.now();
                String date = now.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + String.valueOf(now.getYear());
                int max = 0;
                for(Map.Entry<String,Integer> m:days.entrySet()) {
                    if(m.getValue() > max) {
                        max = m.getValue();
                        date = m.getKey();
                    }
                }
                monthView.setText(date);

            }
        });
    }

    private void initObservers(View view) {
        calendarRecyclerViewModel.getObaveze().observe(this, cars -> {
            dayAdapter.submitList(cars);
        });
    }

}