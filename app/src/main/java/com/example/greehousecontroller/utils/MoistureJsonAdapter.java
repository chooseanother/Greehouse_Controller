package com.example.greehousecontroller.utils;

import com.example.greehousecontroller.data.model.Moisture;
import com.example.greehousecontroller.data.model.Temperature;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class MoistureJsonAdapter extends TypeAdapter<Moisture> {
    @Override
    public void write(JsonWriter out, Moisture value) throws IOException {
        out.beginObject();
        out.name("humidity");
        out.value(value.getMoisture());
        out.name("time");
        out.value(value.getTime());
        out.endObject();
    }

    @Override
    public Moisture read(JsonReader in) throws IOException {
        in.beginObject();
        double moisture = 0.0;
        long time = 0;
        while(in.hasNext()){
            switch (in.nextName()){
                case "moisture":
                    moisture = in.nextDouble();
                    break;
                case "time":
                    time = in.nextLong()*1000L;
                    break;

            }
        }
        in.endObject();
        return new Moisture(moisture,time);
    }
}
