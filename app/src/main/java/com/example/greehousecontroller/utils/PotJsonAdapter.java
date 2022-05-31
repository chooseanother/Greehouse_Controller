package com.example.greehousecontroller.utils;

import com.example.greehousecontroller.data.model.Pot;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class PotJsonAdapter extends TypeAdapter<Pot> {
    @Override
    public void write(JsonWriter out, Pot value) throws IOException {
        out.beginObject();
        out.name("latestMoisture");
        out.value(0.0);
        out.name("name");
        out.value(value.getName());
        out.name("lowerMoistureThreshold");
        out.value(value.getLowerMoistureThreshold());
        out.name("id");
        out.value(0);
        out.name("moistureSensorId");
        out.value(value.getMoistureSensorId());

        out.endObject();
    }


    @Override
    public Pot read(JsonReader in) throws IOException {
        String name = "";
        double lowerMoistureThreshold = 0.0;
        int id = 0;
        int moistureSensorId = 0;
        double latestMoisture = 0.0;
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "name":
                    name = in.nextString();
                    break;
                case "lowerMoistureThreshold":
                    lowerMoistureThreshold = in.nextDouble();
                    break;
                case "id":
                    id = in.nextInt();
                    break;
                case "moistureSensorId":
                    moistureSensorId = in.nextInt();
                    break;
                case "latestMoisture":
                    latestMoisture = in.nextDouble();
                    break;
            }
        }
        in.endObject();
        return new Pot(id, name, moistureSensorId, latestMoisture, lowerMoistureThreshold);
    }
}
