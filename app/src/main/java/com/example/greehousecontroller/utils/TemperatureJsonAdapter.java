package com.example.greehousecontroller.utils;

import com.example.greehousecontroller.data.model.Temperature;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;


public class TemperatureJsonAdapter extends TypeAdapter<Temperature> {
    @Override
    public void write(JsonWriter out, Temperature value) throws IOException {
        out.beginObject();
        out.name("temperature");
        out.value(value.getTemperature());
        out.name("time");
        out.value(value.getTime());
        out.endObject();
    }

    @Override
    public Temperature read(JsonReader in) throws IOException {
        in.beginObject();
        double temp = 0.0;
        long time = 0;
        while(in.hasNext()){
            switch (in.nextName()){
                case "temperature":
                    temp = in.nextDouble();
                    break;
                case "time":
                    time = in.nextLong()*1000L;
                    break;
            }
        }
        in.endObject();
        return new Temperature(temp,time);
    }
}
