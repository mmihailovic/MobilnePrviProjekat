package com.example.myapplication.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.models.Obaveza;
import com.example.myapplication.view.MainActivity;
import com.example.myapplication.view.recycler.adapter.ObavezaDetailsAdapter;
import com.example.myapplication.view.recycler.differ.ObavezaItemDiffCallback;
import com.example.myapplication.viewmodels.ObavezaDetailsRecyclerViewModel;
import com.example.myapplication.viewmodels.ObavezaRecyclerViewModel;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DetailsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ObavezaDetailsRecyclerViewModel recyclerViewModel;
    private TextView dateTextView;

    private ObavezaDetailsAdapter obavezeAdapter;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd. yyyy.");

    public DetailsFragment() {
        super(R.layout.fragment_details);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewModel = new ViewModelProvider(this).get(ObavezaDetailsRecyclerViewModel.class);
        MainActivity mainActivity = (MainActivity)this.getActivity();
        mainActivity.setObavezaDetailsViewModel(recyclerViewModel);
        List<Obaveza> obaveze = new ArrayList<>(((ObavezaRecyclerViewModel)mainActivity.getDailyPlanViewModel()).getCarList());
        recyclerViewModel.setObaveze(obaveze);
        recyclerViewModel.setCarList((ArrayList<Obaveza>) obaveze);
        recyclerViewModel.setDate(((ObavezaRecyclerViewModel)mainActivity.getDailyPlanViewModel()).getDate().getValue());
        Log.d("setovano","true");
        init(view);
    }

    private void init(View view) {
        initView(view);
        initListeners(view);
        initObservers();
        initRecycler();
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.detailsRecyclerView);
        dateTextView = view.findViewById(R.id.dateTextView2);
    }

    private void initListeners(View view) {
    }
    private void initRecycler() {
        obavezeAdapter = new ObavezaDetailsAdapter(new ObavezaItemDiffCallback(), car -> {
            Toast.makeText(this.getActivity(), car.getId() + "", Toast.LENGTH_SHORT).show(); // TODO: detaljan prikaz obaveze
        },recyclerViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(obavezeAdapter);
    }
    private void initObservers() {
        recyclerViewModel.getObaveze().observe(this, cars -> {
            Log.d("Observer","brisanje");
            obavezeAdapter.submitList(cars);
        });
//        recyclerViewModel.getDate().observe(this, date->{
//            dateTextView.setText(recyclerViewModel.getDate().getValue().format(formatter));
//        });
    }
}