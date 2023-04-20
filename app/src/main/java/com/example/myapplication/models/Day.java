package com.example.myapplication.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Day {
    private LocalDate date;
    private List<Obaveza> obaveze = new ArrayList<>();

    public Day(LocalDate date) {
        this.date = date;
        this.obaveze = obaveze;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Obaveza> getObaveze() {
        return obaveze;
    }

    public void setObaveze(List<Obaveza> obaveze) {
        this.obaveze = obaveze;
    }
}
