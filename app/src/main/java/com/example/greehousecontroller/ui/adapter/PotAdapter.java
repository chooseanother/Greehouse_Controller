package com.example.greehousecontroller.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.model.Pot;
import com.example.greehousecontroller.utils.PotCallBack;
import com.example.greehousecontroller.utils.PotClickCallBack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PotAdapter extends RecyclerView.Adapter<PotAdapter.ViewHolder> {
    private List<Pot> pots;
    private PotClickCallBack callBack;

    public PotAdapter(List<Pot> pots) {
        this.pots = pots;
    }

    public void setOnClickListener(PotClickCallBack listener) {
        this.callBack = listener;
    }

    public void setPots(List<Pot> potts) {
        List<Pot> old = pots;
        List<Pot> newList = new ArrayList<>(potts);
        PotCallBack callback = new PotCallBack(old, newList);
        DiffUtil.DiffResult results = DiffUtil.calculateDiff(callback);
        this.pots = potts;
        results.dispatchUpdatesTo(this);
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
        holder.name.setText(pots.get(position).getName());
        DecimalFormat df = new DecimalFormat("0.0");
        holder.currentHumidity.setText(pots.get(position).getCurrentMoisture() + " %");
        holder.minimalHumidity.setText(df.format(pots.get(position).getLowerMoistureThreshold()) + " %");
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.OnClick(pots.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pots.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView currentHumidity;
        private final TextView minimalHumidity;
        private final ImageView edit;


        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.potItemNameTextView);
            currentHumidity = itemView.findViewById(R.id.potItemCurrentMoistureMeasurementTextView);
            minimalHumidity = itemView.findViewById(R.id.potItemMinimalMoistureMeasurementTextView);
            edit = itemView.findViewById(R.id.potItemEdit);
            edit.setOnClickListener(v -> {
                //listener.OnClick(pots.get(getAdapterPosition()));
            });
        }
    }
}
