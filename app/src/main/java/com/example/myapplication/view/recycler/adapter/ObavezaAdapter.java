package com.example.myapplication.view.recycler.adapter;

import android.content.Context;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.example.myapplication.view.fragments.DetailsFragment;
import com.example.myapplication.view.fragments.EditObavezaFragment;
import com.example.myapplication.viewmodels.CalendarRecyclerViewModel;
import com.example.myapplication.viewmodels.ObavezaRecyclerViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Consumer;

public class ObavezaAdapter extends ListAdapter<Obaveza, ObavezaAdapter.ViewHolder> {
    private final Consumer<Obaveza> onObavezaClicked;
    private ViewModel viewmodel;
    public ObavezaAdapter(@NonNull DiffUtil.ItemCallback<Obaveza> diffCallback, Consumer<Obaveza> onObavezaClicked, ViewModel viewModel) {
        super(diffCallback);
        this.onObavezaClicked = onObavezaClicked;
        this.viewmodel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.obaveza_list_item, parent, false);
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
            ImageView imageView = itemView.findViewById(R.id.imageView);
            if(obaveza.getPriority() == Obaveza.HIGH) imageView.setBackgroundColor(context.getResources().getColor(R.color.high, context.getTheme()));
            else if(obaveza.getPriority() == Obaveza.MID) imageView.setBackgroundColor(context.getResources().getColor(R.color.mid, context.getTheme()));
            else imageView.setBackgroundColor(context.getResources().getColor(R.color.low, context.getTheme()));

            LocalDate date = ((ObavezaRecyclerViewModel)viewModel).getDate().getValue();
            if(LocalDate.now().isAfter(date))itemView.setBackgroundColor(context.getResources().getColor(R.color.gray, context.getTheme()));
            else if(LocalDate.now().equals(date) && LocalTime.now().compareTo(LocalTime.parse(obaveza.getEnd())) == 1) itemView.setBackgroundColor(context.getResources().getColor(R.color.gray, context.getTheme()));
            else itemView.setBackgroundColor(context.getResources().getColor(R.color.background, context.getTheme()));

//            Glide
//                    .with(context)
//                    .load(obaveza.getPicture())
//                    .circleCrop()
//                    .into(imageView);
            ((TextView) itemView.findViewById(R.id.textViewTitle)).setText(obaveza.getTitle());
            ((TextView) itemView.findViewById(R.id.textViewTime)).setText(obaveza.getStart()  + ":"+obaveza.getEnd());
            ((ImageButton) itemView.findViewById(R.id.btnEdit)).setOnClickListener(e->{
//                Toast.makeText(e.getContext(), "Obaveza id " + obaveza.getId(), Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.replaceFragmentFcv, new EditObavezaFragment(obaveza));
                transaction.addToBackStack(null);
                transaction.commit();
            });
            ((ImageButton) itemView.findViewById(R.id.btnDelete)).setOnClickListener(e->{
//                ((RecyclerViewModel)viewModel).removeCar(obaveza.getId());
                Snackbar snackbar = Snackbar.make(itemView, "Da li zelis da obrises obavezu?", Snackbar.LENGTH_LONG);
                snackbar.setAction("Da", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Akcija koja se izvršava kada se klikne na prvu opciju
                        ((ObavezaRecyclerViewModel)viewModel).removeCar(obaveza.getId());
                        ((CalendarRecyclerViewModel)((MainActivity) context).getCalendarViewModel()).removeObaveza(obaveza.getId());
                        ((RecyclerView)((MainActivity) context).getCalendarRecyclerView()).getAdapter().notifyDataSetChanged();
                    }
                });
                snackbar.show();
            });
        }
    }
}
