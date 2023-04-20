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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ObavezaDetailsRecyclerViewModel extends ViewModel {
    public static int counter = 11;

    private final MutableLiveData<List<Obaveza>> cars = new MutableLiveData<>();
    private final MutableLiveData<LocalDate> date = new MutableLiveData<>();
    private ArrayList<Obaveza> carList = new ArrayList<>();

    public ObavezaDetailsRecyclerViewModel() {
//        for (int i = 0; i <= 10; i++) {
//            Obaveza obaveza = new Obaveza(i,"14:30","15:30","Mobilne","Domaci",Obaveza.HIGH,"https://electric-fun.com/wp-content/uploads/2020/01/sony-car-796x418-1.jpg");
//            carList.add(obaveza);
//        }
        // We are doing this because cars.setValue in the background is first checking if the reference on the object is same
        // and if it is it will not do notifyAll. By creating a new list, we get the new reference everytime
//        Obaveza obaveza1 = new Obaveza(0,"23:50","23:51","Mobilne","Domaci",Obaveza.HIGH,"https://electric-fun.com/wp-content/uploads/2020/01/sony-car-796x418-1.jpg");
//        carList.add(obaveza1);
//        Obaveza obaveza2 = new Obaveza(1,"09:30","10:30","Veb","Domaci",Obaveza.MID,"https://electric-fun.com/wp-content/uploads/2020/01/sony-car-796x418-1.jpg");
//        carList.add(obaveza2);
//        Obaveza obaveza3 = new Obaveza(2,"22:15","22:30","Interakcija","Domaci",Obaveza.LOW,"https://electric-fun.com/wp-content/uploads/2020/01/sony-car-796x418-1.jpg");
//        carList.add(obaveza3);
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

    public void filterCars(String filter) {
//        List<Obaveza> filteredList = carList.stream().filter(car -> car.getManufacturer().toLowerCase().startsWith(filter.toLowerCase())).collect(Collectors.toList());
//        cars.setValue(filteredList);
    }

    public int addCar(String start, String end, String title, String description, int priority, String pictureUrl) {
        int id = counter++;
        Obaveza car = new Obaveza(id, "14:30","15:30","Mobilne","Domaci",Obaveza.HIGH);
        carList.add(car);
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
}
