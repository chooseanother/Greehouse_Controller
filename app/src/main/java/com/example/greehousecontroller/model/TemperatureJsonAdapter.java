package com.example.greehousecontroller.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;


// TODO: Figure out proper location of this class, should it be in a separate helper package?
public class TemperatureJsonAdapter extends TypeAdapter<Temperature> {
    @Override
    public void write(JsonWriter out, Temperature value) throws IOException {
        out.beginObject();
        out.name("temperature");
        out.value(value.getTemperature());
        out.name("time");
        out.value(value.getTime().getTime());
        out.endObject();
    }

    @Override
    public Temperature read(JsonReader in) throws IOException {
        in.beginObject();
        Double temp = 0.0;
        Date time = new Date(0);
        while(in.hasNext()){
            switch (in.nextName()){
                case "temperature":
                    temp = in.nextDouble();
                    break;
                case "time":
                    time = new Date(in.nextLong()*1000L);
                    break;
            }
        }
        in.endObject();
        return new Temperature(temp,time);
    }
}
