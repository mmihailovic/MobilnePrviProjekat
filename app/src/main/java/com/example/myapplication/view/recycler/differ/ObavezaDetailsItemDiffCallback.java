package com.example.myapplication.view.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.myapplication.models.Obaveza;

public class ObavezaDetailsItemDiffCallback extends DiffUtil.ItemCallback<Obaveza>{
    @Override
    public boolean areItemsTheSame(@NonNull Obaveza oldItem, @NonNull Obaveza newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Obaveza oldItem, @NonNull Obaveza newItem) {
        return oldItem.getStart().equals(newItem.getStart()) && oldItem.getEnd().equals(newItem.getEnd())
                && oldItem.getDescription().equals(newItem.getDescription()) && oldItem.getPriority() == newItem.getPriority()
                && oldItem.getTitle().equals(newItem.getTitle());
    }
}
