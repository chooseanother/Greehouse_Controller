package com.example.greehousecontroller.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.model.Pot;

import java.util.List;

public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.ViewHolder> {
    List<Pot> pots;

    public HomeFragmentAdapter(List<Pot> pots) {
        this.pots = pots;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pot_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.id.setText("Pot # " + pots.get(position).getId());
        holder.moistureMeasurement.setText(pots.get(position).getMoisture() + " %");
    }

    @Override
    public int getItemCount() {
        return pots.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView id;
        private TextView moistureMeasurement;


        ViewHolder(View itemView){
            super(itemView);
            id = itemView.findViewById(R.id.potNumberTextView);
            moistureMeasurement = itemView.findViewById(R.id.moistureMeasurementTextView);
        }
    }
}
