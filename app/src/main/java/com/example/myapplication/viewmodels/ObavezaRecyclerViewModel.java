package com.example.myapplication.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.models.Obaveza;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ObavezaRecyclerViewModel extends ViewModel {
    public static int counter = 11;

    private final MutableLiveData<List<Obaveza>> cars = new MutableLiveData<>();
    private final MutableLiveData<LocalDate> date = new MutableLiveData<>();
    private ArrayList<Obaveza> carList = new ArrayList<>();

    public ObavezaRecyclerViewModel() {
        carList.sort(new Comparator<Obaveza>() {
            @Override
            public int compare(Obaveza obaveza, Obaveza t1) {
                return LocalTime.parse(obaveza.getStart()).compareTo(LocalTime.parse(t1.getStart()));
            }
        });
        ArrayList<Obaveza> listToSubmit = new ArrayList<>(carList.stream().filter((car)-> LocalTime.now().compareTo(LocalTime.parse(car.getEnd())) == -1).collect(Collectors.toList()));
        cars.setValue(listToSubmit);
    }

    public LiveData<List<Obaveza>> getObaveze() {
        return cars;
    }

    public void setObaveze(List<Obaveza> obaveze) {
        List<Obaveza> oba = new ArrayList<>(obaveze);
        oba.sort(new Comparator<Obaveza>() {
            @Override
            public int compare(Obaveza obaveza, Obaveza t1) {
                return LocalTime.parse(obaveza.getStart()).compareTo(LocalTime.parse(t1.getStart()));
            }
        });
        this.cars.setValue(oba);
        for(Obaveza o: cars.getValue()) {
            Log.d("Obaveza", o.getTitle());
        }
    }

    public LiveData<LocalDate> getDate() { return date; }
    public void setDate(LocalDate date) {this.date.setValue(date);}

    public List<Obaveza> getCarList() {
        return carList;
    }

    public void setCarList(ArrayList<Obaveza> carList) {
        this.carList = carList;
    }

    public int addCar(String start, String end,String title,String description, int priority,Obaveza obaveza) {
        int id = counter++;
        Obaveza car = new Obaveza(id, start,end,title,description,priority);
        carList.add(car);
        carList.sort(new Comparator<Obaveza>() {
            @Override
            public int compare(Obaveza obaveza, Obaveza t1) {
                return LocalTime.parse(obaveza.getStart()).compareTo(LocalTime.parse(t1.getStart()));
            }
        });
        ArrayList<Obaveza> listToSubmit = new ArrayList<>(carList);
        cars.setValue(listToSubmit);
        return id;
    }

    public void removeCar(int id) {
        Log.d("poziv brisanja", String.valueOf(id));
        Optional<Obaveza> carObject = carList.stream().filter(obaveza-> obaveza.getId() == id).findFirst();
        if (carObject.isPresent()) {
            Log.d("Brisanje",String.valueOf(id));
            carList.remove(carObject.get());
            ArrayList<Obaveza> listToSubmit = new ArrayList<>(carList);
            setObaveze(listToSubmit);
        }
    }

    public void updateObaveza(Obaveza o, int id) {
        Optional<Obaveza> carObject = carList.stream().filter(obaveza-> obaveza.getId() == id).findFirst();
        if (carObject.isPresent()) {
//            carList.remove(carObject.get());
//            carList.add(o);
            carObject.get().setStart(o.getStart());
            carObject.get().setEnd(o.getEnd());
            carObject.get().setTitle(o.getTitle());
            carObject.get().setPriority(o.getPriority());
            carObject.get().setDescription(o.getDescription());
            ArrayList<Obaveza> listToSubmit = new ArrayList<>(carList);
            setObaveze(listToSubmit);
        }
    }

}
