package com.example.myapplication.view.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Obaveza;
import com.example.myapplication.view.MainActivity;
import com.example.myapplication.view.fragments.EditObavezaFragment;
import com.example.myapplication.viewmodels.CalendarRecyclerViewModel;
import com.example.myapplication.viewmodels.ObavezaDetailsRecyclerViewModel;
import com.example.myapplication.viewmodels.ObavezaRecyclerViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class ObavezaDetailsAdapter extends ListAdapter<Obaveza, ObavezaDetailsAdapter.ViewHolder> {
    private final Consumer<Obaveza> onObavezaClicked;
    private ViewModel viewmodel;
    public ObavezaDetailsAdapter(@NonNull DiffUtil.ItemCallback<Obaveza> diffCallback, Consumer<Obaveza> onObavezaClicked, ViewModel viewModel) {
        super(diffCallback);
        this.onObavezaClicked = onObavezaClicked;
        this.viewmodel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.obaveza_details_item, parent, false);
        return new ViewHolder(view, parent.getContext(), position -> {
            Obaveza obaveza = getItem(position);
            onObavezaClicked.accept(obaveza);
        }, viewmodel);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Obaveza obaveza = getItem(position);
        holder.bind(obaveza);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        private ViewModel viewModel;

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

        public void bind(Obaveza obaveza) {
            ((TextView) itemView.findViewById(R.id.titleDetailTextView)).setText(obaveza.getTitle());
            ((TextView) itemView.findViewById(R.id.timeTextViewDetails)).setText(obaveza.getStart()  + ":"+obaveza.getEnd());
            ((TextView) itemView.findViewById(R.id.descriptionTextView)).setText(obaveza.getDescription());

            LocalDate date = ((ObavezaDetailsRecyclerViewModel)viewModel).getDate().getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd. yyyy.");
            String dateString = date.format(formatter);
            ((TextView) itemView.findViewById(R.id.dateTextView2)).setText(dateString);

            ((Button) itemView.findViewById(R.id.btnEditObaveza)).setOnClickListener(e->{
//                Toast.makeText(e.getContext(), "Obaveza id " + obaveza.getId(), Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.replaceFragmentFcv, new EditObavezaFragment(obaveza));
                transaction.addToBackStack(null);
                transaction.commit();
            });
            ((Button) itemView.findViewById(R.id.btnDeleteObaveza)).setOnClickListener(e->{
//                ((RecyclerViewModel)viewModel).removeCar(obaveza.getId());
                Snackbar snackbar = Snackbar.make(itemView, "Da li zelis da obrises obavezu?", Snackbar.LENGTH_LONG);
                snackbar.setAction("Da", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Akcija koja se izvršava kada se klikne na prvu opciju
                        ((ObavezaDetailsRecyclerViewModel)viewModel).removeCar(obaveza.getId());
                        ((ObavezaRecyclerViewModel)((MainActivity) context).getDailyPlanViewModel()).removeCar(obaveza.getId());
                        ((CalendarRecyclerViewModel)((MainActivity) context).getCalendarViewModel()).removeObaveza(obaveza.getId());
                        ((RecyclerView)((MainActivity) context).getCalendarRecyclerView()).getAdapter().notifyDataSetChanged();
                    }
                });
                snackbar.show();
            });


        }



    }
}
