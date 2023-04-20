package com.example.myapplication.view.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.myapplication.models.Day;
import com.example.myapplication.models.Obaveza;

public class DayItemDiffCallback extends DiffUtil.ItemCallback<Day> {
    @Override
    public boolean areItemsTheSame(@NonNull Day oldItem, @NonNull Day newItem) {
        return oldItem.getDate() == newItem.getDate();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Day oldItem, @NonNull Day newItem) {
        return oldItem.getDate().equals(newItem.getDate()) && oldItem.getObaveze().equals(newItem.getObaveze());
    }
}
