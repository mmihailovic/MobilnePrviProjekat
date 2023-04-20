package com.example.myapplication.view.recycler.adapter;

import static java.lang.Math.max;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Day;
import com.example.myapplication.models.Obaveza;
import com.example.myapplication.view.fragments.DailyPlanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.function.Consumer;

public class DayAdapter extends ListAdapter<Day,DayAdapter.ViewHolder> {
    private final Consumer<Day> onObavezaClicked;
    private ViewModel viewmodel;
    public DayAdapter(@NonNull DiffUtil.ItemCallback<Day> diffCallback, Consumer<Day> onObavezaClicked, ViewModel viewModel) {
        super(diffCallback);
        this.onObavezaClicked = onObavezaClicked;
        this.viewmodel = viewModel;
    }

    @NonNull
    @Override
    public DayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day, parent, false);
        return new DayAdapter.ViewHolder(view, parent.getContext(), position -> {
            Day obaveza = getItem(position);
            onObavezaClicked.accept(obaveza);
        }, viewmodel);
    }

    @Override
    public void onBindViewHolder(@NonNull DayAdapter.ViewHolder holder, int position) {
        Day obaveza = getItem(position);
        holder.bind(obaveza);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        private ViewModel viewModel;
        private Day currentDay;

        public ViewHolder(@NonNull View itemView, Context context, Consumer<Integer> onItemClicked, ViewModel viewModel) {
            super(itemView);
            this.context = context;
            this.viewModel = viewModel;
            itemView.setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });
        }

        public void bind(Day obaveza) {
            currentDay = obaveza;
            TextView textView = (TextView)itemView.findViewById(R.id.dayTextView);
            if(textView != null) textView.setText(obaveza.getDate().getDayOfMonth() + ".");
            int highCount = 0, midCount = 0, lowCount = 0;
            for(Obaveza o: obaveza.getObaveze()) {
                if(o.getPriority() == Obaveza.HIGH)
                    highCount++;
                else if(o.getPriority() == Obaveza.MID)
                    midCount++;
                else lowCount++;
            }
            int max = max(max(highCount,midCount),lowCount);
            if(max == 0) itemView.setBackgroundColor(context.getResources().getColor(R.color.date, context.getTheme()));
            else if(max == highCount)itemView.setBackgroundColor(context.getResources().getColor(R.color.high, context.getTheme()));
            else if(max == midCount)itemView.setBackgroundColor(context.getResources().getColor(R.color.mid, context.getTheme()));
            else itemView.setBackgroundColor(context.getResources().getColor(R.color.low, context.getTheme()));
        }

        public Day getCurrentDay() {
            return currentDay;
        }
    }
}
