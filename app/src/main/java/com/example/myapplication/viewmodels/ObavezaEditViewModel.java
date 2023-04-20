package com.example.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.models.Obaveza;

import java.time.LocalDate;

public class ObavezaEditViewModel extends ViewModel {
    private final MutableLiveData<Obaveza> obaveza = new MutableLiveData<>();
    private final MutableLiveData<LocalDate> date = new MutableLiveData<>();


    public void setObaveza(Obaveza obaveza) {
        this.obaveza.setValue(obaveza);
    }

    public LiveData<Obaveza> getObaveza() {
        return obaveza;
    }

    public void setDate(LocalDate date) {
        this.date.setValue(date);
    }

    public LiveData<LocalDate> getDate() {
        return date;
    }
}
