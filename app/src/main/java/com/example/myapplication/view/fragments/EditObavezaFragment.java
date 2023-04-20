package com.example.myapplication.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Obaveza;
import com.example.myapplication.view.MainActivity;
import com.example.myapplication.viewmodels.CalendarRecyclerViewModel;
import com.example.myapplication.viewmodels.ObavezaEditViewModel;
import com.example.myapplication.viewmodels.ObavezaRecyclerViewModel;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EditObavezaFragment extends Fragment {
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
    private ObavezaEditViewModel obavezaEditViewModel;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd. yyyy.");
    private Obaveza obaveza;
    public EditObavezaFragment(Obaveza o) {
        super(R.layout.fragment_edit_obaveza);
        this.obaveza = o;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        obavezaEditViewModel = new ViewModelProvider(this).get(ObavezaEditViewModel.class);
        obavezaEditViewModel.setObaveza(obaveza);;
        MainActivity mainActivity = (MainActivity)this.getActivity();
        obavezaEditViewModel.setDate(((ObavezaRecyclerViewModel)mainActivity.getDailyPlanViewModel()).getDate().getValue());
        mainActivity.setObavezaEditViewModel(obavezaEditViewModel);
        init(view);
    }

    private void init(View view) {
        initView(view);
        initListeners(view);
        initObservers(view);
    }

    private void initView(View view) {
        lowRadioBtn = view.findViewById(R.id.radioButtonLow);
        midRadioBtn = view.findViewById(R.id.radioButtonMid);
        highRadioBtn = view.findViewById(R.id.radioButtonHigh);
        titleTextView = view.findViewById(R.id.titleEditText);
        timeTextView = view.findViewById(R.id.timeEditText);
        descriptionTextView = view.findViewById(R.id.descriptionEditText);
        saveBtn = view.findViewById(R.id.btnSave);
        cancelBtn = view.findViewById(R.id.btnCancel);
        radioGroup = view.findViewById(R.id.radioGroup);
        dateTextView = view.findViewById(R.id.dateTextView4);
        if(obaveza != null) obavezaEditViewModel.setObaveza(obaveza);
        else Log.e("Obaveza",String.valueOf(obaveza));
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
            Obaveza obaveza = obavezaEditViewModel.getObaveza().getValue();
            MainActivity mainActivity = (MainActivity)this.getActivity();
            ObavezaRecyclerViewModel viewModel = (ObavezaRecyclerViewModel)mainActivity.getDailyPlanViewModel();
            String[] time = timeTextView.getText().toString().split("-");
            LocalTime start = LocalTime.parse(time[0]);
            LocalTime end = LocalTime.parse(time[1]);
            boolean poklapa = false;
            for(Obaveza o: viewModel.getObaveze().getValue()) {
                LocalTime oStart = LocalTime.parse(o.getStart());
                LocalTime oEnd = LocalTime.parse(o.getEnd());

                if (start.isBefore(oEnd) && oStart.isBefore(end) && o.getId() != obaveza.getId()) {
                    // Vremenski intervali se preklapaju
                    Toast.makeText(mainActivity, "Vreme se poklapa sa drugom obavezom", Toast.LENGTH_SHORT).show();
                    poklapa = true;
                    break;
                }
            }
            if(!poklapa) {
                if(highRadioBtn.isChecked())
                    obaveza.setPriority(Obaveza.HIGH);
                else if(midRadioBtn.isChecked())
                    obaveza.setPriority(Obaveza.MID);
                else if(lowRadioBtn.isChecked())
                    obaveza.setPriority(Obaveza.LOW);
                obaveza.setTitle(titleTextView.getText().toString());
                obaveza.setDescription(descriptionTextView.getText().toString());
                obaveza.setStart(time[0]);
                obaveza.setEnd(time[1]);
                Toast.makeText(mainActivity, "Uspesno ste izmenili podatke", Toast.LENGTH_SHORT).show();
                ((ObavezaRecyclerViewModel) viewModel).updateObaveza(obaveza, obaveza.getId());
                ((CalendarRecyclerViewModel) ((MainActivity) this.getActivity()).getCalendarViewModel()).updateObaveza(obaveza, obaveza.getId());
                ((RecyclerView) ((MainActivity) this.getActivity()).getCalendarRecyclerView()).getAdapter().notifyDataSetChanged();
                this.getActivity().onBackPressed();
            }
        });
        cancelBtn.setOnClickListener(e->{
            this.getActivity().onBackPressed();
        });
    }

    private void initObservers(View view) {
        obavezaEditViewModel.getDate().observe(this, date->{
            dateTextView.setText(obavezaEditViewModel.getDate().getValue().format(formatter));
        });
        obavezaEditViewModel.getObaveza().observe(this,date->{
            Obaveza o = obavezaEditViewModel.getObaveza().getValue();
            titleTextView.setText(o.getTitle());
            timeTextView.setText(o.getStart() + "-" + o.getEnd());
            descriptionTextView.setText(o.getDescription());
            lowRadioBtn.performClick();
            midRadioBtn.setChecked(false);
            highRadioBtn.setChecked(false);
            if(o.getPriority() == Obaveza.LOW)
                lowRadioBtn.performClick();
            else if(o.getPriority() == Obaveza.MID)
                midRadioBtn.performClick();
            else if(o.getPriority() == Obaveza.HIGH)
                highRadioBtn.performClick();
        });
    }
}
