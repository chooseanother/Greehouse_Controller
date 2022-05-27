package com.example.greehousecontroller.ui.adapter;

import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greehousecontroller.R;
import com.example.greehousecontroller.data.model.Sensor;

import java.util.List;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.ViewHolder> {
    private List<Sensor> sensors;

    public SensorAdapter(List<Sensor> sensors){
        this.sensors = sensors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.sensor_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sensor sensor = sensors.get(position);
        holder.sensor.setText(sensor.getSensor());
        //if returns true = works -> set color to green
        if (sensor.isStatus()){
            holder.status.setColorFilter(R.color.green_button_save);
        }
        //if returns false = does not work -> set color to red
        else{
            holder.status.setColorFilter(R.color.red_button_cancel);
        }
    }

    public void setSensors(List<Sensor> sensors){
        this.sensors = sensors;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return sensors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView sensor;
        private final ImageView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sensor = itemView.findViewById(R.id.sensor_name);
            status = itemView.findViewById(R.id.sensor_status);
        }
    }
}
