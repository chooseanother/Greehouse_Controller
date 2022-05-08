package com.example.greehousecontroller.model.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.model.Pot;

import java.util.List;

public class PotAdapter extends RecyclerView.Adapter<PotAdapter.ViewHolder> {
    List<Pot> pots;
    private OnClickListener listener;

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    public PotAdapter(List<Pot> pots) {
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
        holder.name.setText(pots.get(position).getName());
        holder.currentHumidity.setText(pots.get(position).getCurrentHumidity() + " %");
        holder.minimalHumidity.setText(pots.get(position).getMinimalHumidity() + " %");
    }

    @Override
    public int getItemCount() {
        return pots.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView name;
        private TextView currentHumidity;
        private TextView minimalHumidity;
        private Button editButton;


        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.potNameTextView);
            currentHumidity = itemView.findViewById(R.id.currentHumidityTextView);
            minimalHumidity = itemView.findViewById(R.id.minimalHumidityTextView);
            editButton = itemView.findViewById(R.id.humidityEditButton);
            editButton.setOnClickListener(v ->{
                listener.OnClick(pots.get(getAdapterPosition()));
            });
        }
    }

    public interface OnClickListener{
        void OnClick(Pot pot);
    }
}
