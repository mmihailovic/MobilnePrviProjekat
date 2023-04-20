package com.example.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.models.Day;
import com.example.myapplication.models.Obaveza;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CalendarRecyclerViewModel extends ViewModel {
    private final MutableLiveData<List<Day>> cars = new MutableLiveData<>();
    private ArrayList<Day> carList = new ArrayList<>();

    public CalendarRecyclerViewModel() {
        LocalDate date = LocalDate.now().withDayOfMonth(1);
        int redniBrojDanaUNedelji = LocalDate.now().withDayOfMonth(1).getDayOfWeek().getValue();
        int brojDanaUMesecu = YearMonth.now().lengthOfMonth();
        date = date.minusDays(redniBrojDanaUNedelji - 1);

        for(int i = -84;i < 84 + brojDanaUMesecu + redniBrojDanaUNedelji - 1; i++) {
            Day day = new Day(date.plusDays(i));
            if(date.plusDays(i).equals(LocalDate.now())) {
                day.getObaveze().add(new Obaveza(5, "19:30", "20:00", "Masinsko zadaci", "Domaci", Obaveza.LOW));
                day.getObaveze().add(new Obaveza(6, "18:30", "19:30", "Mobilne", "Domaci", Obaveza.HIGH));
            }
            carList.add(day);
        }
        carList.get(0).getObaveze().add(new Obaveza(0,"14:30","15:30","Mobilne","Domaci",Obaveza.HIGH));
        carList.get(0).getObaveze().add(new Obaveza(1,"15:30","16:30","Masinsko","Domaci",Obaveza.LOW));
        carList.get(5).getObaveze().add(new Obaveza(2,"15:30","16:30","Veb","Domaci",Obaveza.HIGH));
        carList.get(10).getObaveze().add(new Obaveza(3,"22:50","23:50","Interakcija","Domaci",Obaveza.MID));
        carList.get(15).getObaveze().add(new Obaveza(4,"19:30","20:00","Masinsko zadaci","Domaci",Obaveza.LOW));

        ArrayList<Day> listToSubmit = new ArrayList<>(carList);
        cars.setValue(listToSubmit);
    }

    public LiveData<List<Day>> getObaveze() {
        return cars;
    }

    public void setObaveze(List<Day> obaveze) {
        cars.setValue(obaveze);
    }

    public List<Day> getCarList() {
        return carList;
    }

    public void addObaveza(int id, String start, String end,String title,String description, int priority,Obaveza obaveza, LocalDate date) {
        Obaveza car = new Obaveza(id, start,end,title,description,priority);
        for(int i = 0;i < carList.size(); i++) {
            if(carList.get(i).getDate().equals(date)) {
                carList.get(i).getObaveze().add(car);
            }
        }
        List<Day> days = new ArrayList<>(carList);
        setObaveze(days);
    }

    public void removeObaveza(int id) {
        for(int i = 0;i < carList.size(); i++) {
            for(int j = 0;j < carList.get(i).getObaveze().size();j++) {
                if(carList.get(i).getObaveze().get(j).getId() == id) {
                    carList.get(i).getObaveze().remove(j);
                    break;
                }
            }
        }
        List<Day> days = new ArrayList<>(carList);
        setObaveze(days);
    }

    public void updateObaveza(Obaveza o, int id) {
        for(int i = 0;i < carList.size(); i++) {
            for(int j = 0;j < carList.get(i).getObaveze().size();j++) {
                if(carList.get(i).getObaveze().get(j).getId() == id) {
//                    carList.get(i).getObaveze().remove(j);
//                    carList.get(i).getObaveze().add(o);
                    carList.get(i).getObaveze().get(j).setStart(o.getStart());
                    carList.get(i).getObaveze().get(j).setEnd(o.getEnd());
                    carList.get(i).getObaveze().get(j).setTitle(o.getTitle());
                    carList.get(i).getObaveze().get(j).setPriority(o.getPriority());
                    carList.get(i).getObaveze().get(j).setDescription(o.getDescription());
                    break;
                }
            }
        }
        List<Day> days = new ArrayList<>(carList);
        setObaveze(days);
    }
}
