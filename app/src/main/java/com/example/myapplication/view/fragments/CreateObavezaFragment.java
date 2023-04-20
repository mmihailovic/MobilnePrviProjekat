package com.example.myapplication.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.models.Obaveza;
import com.example.myapplication.view.MainActivity;
import com.example.myapplication.viewmodels.CalendarRecyclerViewModel;
import com.example.myapplication.viewmodels.ObavezaRecyclerViewModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CreateObavezaFragment extends Fragment {
    private TextView dateTextView;
    private RadioGroup radioGroup;
    private RadioButton lowRadioBtn;
    private RadioButton midRadioBtn;
    private RadioButton highRadioBtn;
    private TextView titleTextView;
    private TextView timeTextView;
    private TextView descriptionTextView;
    private Button saveBtn;
    private Button cancelBtn;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd. yyyy.");

    public CreateObavezaFragment() {
        super(R.layout.fragment_create_obaveza);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        MainActivity mainActivity = (MainActivity) this.getActivity();
        LocalDate date = ((ObavezaRecyclerViewModel)mainActivity.getDailyPlanViewModel()).getDate().getValue();
        dateTextView.setText(date.format(formatter));
    }

    private void init(View view) {
        initView(view);
        initListeners(view);
    }

    private void initView(View view) {
        lowRadioBtn = view.findViewById(R.id.radioButtonLowCreate);
        midRadioBtn = view.findViewById(R.id.radioButtonMidCreate);
        highRadioBtn = view.findViewById(R.id.radioButtonHighCreate);
        titleTextView = view.findViewById(R.id.titleCreateEditText);
        timeTextView = view.findViewById(R.id.timeCreateEditText);
        descriptionTextView = view.findViewById(R.id.descriptionCreateEditText);
        saveBtn = view.findViewById(R.id.btnCreate);
        cancelBtn = view.findViewById(R.id.btnCancelCreate);
        radioGroup = view.findViewById(R.id.radioGroupCreate);
        dateTextView = view.findViewById(R.id.dateTextView5);
    }

    private void initListeners(View view) {
        lowRadioBtn.setOnClickListener(e->{
            if(lowRadioBtn.isChecked()) lowRadioBtn.setAlpha(1.0f);
            else lowRadioBtn.setAlpha(0.5f);

            if(midRadioBtn.isChecked()) midRadioBtn.setAlpha(1.0f);
            else midRadioBtn.setAlpha(0.5f);

            if(highRadioBtn.isChecked()) highRadioBtn.setAlpha(1.0f);
            else highRadioBtn.setAlpha(0.5f);
        });

        midRadioBtn.setOnClickListener(e->{
            if(lowRadioBtn.isChecked()) lowRadioBtn.setAlpha(1.0f);
            else lowRadioBtn.setAlpha(0.5f);

            if(midRadioBtn.isChecked()) midRadioBtn.setAlpha(1.0f);
            else midRadioBtn.setAlpha(0.5f);

            if(highRadioBtn.isChecked()) highRadioBtn.setAlpha(1.0f);
            else highRadioBtn.setAlpha(0.5f);
        });
        highRadioBtn.setOnClickListener(e->{
            if(lowRadioBtn.isChecked()) lowRadioBtn.setAlpha(1.0f);
            else lowRadioBtn.setAlpha(0.5f);

            if(midRadioBtn.isChecked()) midRadioBtn.setAlpha(1.0f);
            else midRadioBtn.setAlpha(0.5f);

            if(highRadioBtn.isChecked()) highRadioBtn.setAlpha(1.0f);
            else highRadioBtn.setAlpha(0.5f);
        });
        saveBtn.setOnClickListener(e->{
            Obaveza obaveza = new Obaveza();
            if(highRadioBtn.isChecked())
                obaveza.setPriority(Obaveza.HIGH);
            else if(midRadioBtn.isChecked())
                obaveza.setPriority(Obaveza.MID);
            else if(lowRadioBtn.isChecked())
                obaveza.setPriority(Obaveza.LOW);

            MainActivity mainActivity = (MainActivity)this.getActivity();
            ObavezaRecyclerViewModel viewModel = (ObavezaRecyclerViewModel)mainActivity.getDailyPlanViewModel();
            LocalDate date = viewModel.getDate().getValue();
            String[] time = timeTextView.getText().toString().split("-");
            LocalTime start = LocalTime.parse(time[0]);
            LocalTime end = LocalTime.parse(time[1]);
            boolean poklapa = false;
            for(Obaveza o: viewModel.getObaveze().getValue()) {
                LocalTime oStart = LocalTime.parse(o.getStart());
                LocalTime oEnd = LocalTime.parse(o.getEnd());

                if (start.isBefore(oEnd) && oStart.isBefore(end)) {
                    // Vremenski intervali se preklapaju
                    Toast.makeText(mainActivity, "Vreme se poklapa sa drugom obavezom", Toast.LENGTH_SHORT).show();
                    poklapa = true;
                    break;
                }
            }
            if(!poklapa) {
                obaveza.setTitle(titleTextView.getText().toString());
                obaveza.setDescription(descriptionTextView.getText().toString());
                obaveza.setStart(time[0]);
                obaveza.setEnd(time[1]);
                Toast.makeText(mainActivity, "Uspesno ste izmenili podatke", Toast.LENGTH_SHORT).show();
                ((ObavezaRecyclerViewModel) viewModel).addCar(obaveza);
                ((CalendarRecyclerViewModel) ((MainActivity) this.getActivity()).getCalendarViewModel()).addObaveza(obaveza, date);
                ((RecyclerView) ((MainActivity) this.getActivity()).getCalendarRecyclerView()).getAdapter().notifyDataSetChanged();
                this.getActivity().onBackPressed();
            }
        });
        cancelBtn.setOnClickListener(e->{
            this.getActivity().onBackPressed();
        });
    }
}