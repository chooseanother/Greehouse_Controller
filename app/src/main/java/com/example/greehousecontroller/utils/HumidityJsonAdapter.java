package com.example.greehousecontroller.utils;

import com.example.greehousecontroller.data.model.Humidity;
import com.example.greehousecontroller.data.model.Temperature;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class HumidityJsonAdapter extends TypeAdapter<Humidity> {
    @Override
    public void write(JsonWriter out, Humidity value) throws IOException {
        out.beginObject();
        out.name("humidity");
        out.value(value.getHumidity());
        out.name("time");
        out.value(value.getTime());
        out.endObject();
    }

    @Override
    public Humidity read(JsonReader in) throws IOException {
        in.beginObject();
        double humidity = 0.0;
        long time = 0;
        while(in.hasNext()){
            switch (in.nextName()){
                case "humidity":
                    humidity = in.nextDouble();
                    break;
                case "time":
                    time = in.nextLong()*1000L;
                    break;
            }
        }
        in.endObject();
        return new Humidity(humidity,time);
    }
}
