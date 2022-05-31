package com.example.greehousecontroller.utils;

import com.example.greehousecontroller.data.model.CO2;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class CO2JsonAdapter extends TypeAdapter<CO2> {
    @Override
    public void write(JsonWriter out, CO2 value) throws IOException {
        out.beginObject();
        out.name("co2Measurement");
        out.value(value.getCo2Measurement());
        out.name("time");
        out.value(value.getTime());
        out.endObject();
    }

    @Override
    public CO2 read(JsonReader in) throws IOException {
        in.beginObject();
        double co2Measurement = 0.0;
        long time = 0;
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "co2Measurement":
                    co2Measurement = in.nextDouble();
                    break;
                case "time":
                    time = in.nextLong() * 1000L;
                    break;
            }
        }
        in.endObject();
        return new CO2(co2Measurement, time);
    }
}
