package com.example.greehousecontroller;


import com.example.greehousecontroller.data.model.Temperature;

import org.junit.Test;


import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.TimeZone;

public class TemperatureUnitTest {

    @Test
    public void defaultConstructor(){
        Temperature t = new Temperature();

        assertEquals(0, t.getId());
        assertEquals(0.0, t.getTemperature(), 0);
        assertEquals(0L, t.getTime());
    }

    @Test
    public void constructorNoIdZero(){
        Temperature t = new Temperature(0.0, 0L);

        assertEquals(0, t.getId());
        assertEquals(0.0, t.getTemperature(), 0);
        assertEquals(0L, t.getTime());
    }

    @Test
    public void constructorNoIdOne(){
        Temperature t = new Temperature(1.1, 1L);

        assertEquals(0, t.getId());
        assertEquals(1.1, t.getTemperature(), 1);
        assertEquals(1L, t.getTime());
    }

    @Test
    public void constructorNoIdMany(){
        double d = 3345.35;
        long l = 345256234;
        Temperature t = new Temperature(d, l);

        assertEquals(0, t.getId());
        assertEquals(d, t.getTemperature(), 1);
        assertEquals(l, t.getTime());
    }

    @Test
    public void constructorNoIdUpperBoundary(){
        double d = Double.MAX_VALUE;
        long l = Long.MAX_VALUE;
        Temperature t = new Temperature(d, l);

        assertEquals(0, t.getId());
        assertEquals(d, t.getTemperature(), 1);
        assertEquals(l, t.getTime());
    }

    @Test
    public void constructorNoIdLowerBoundary(){
        double d = Double.MIN_VALUE;
        long l = Long.MIN_VALUE;
        Temperature t = new Temperature(d, l);

        assertEquals(0, t.getId());
        assertEquals(d, t.getTemperature(), 1);
        assertEquals(l, t.getTime());
    }

    @Test
    public void constructorZero(){
        Temperature t = new Temperature(0,0.0, 0L);

        assertEquals(0, t.getId());
        assertEquals(0.0, t.getTemperature(), 0);
        assertEquals(0L, t.getTime());
    }

    @Test
    public void constructorOne(){
        Temperature t = new Temperature(1,1.1, 1L);

        assertEquals(1, t.getId());
        assertEquals(1.1, t.getTemperature(), 1);
        assertEquals(1L, t.getTime());
    }

    @Test
    public void constructorMany(){
        int i = 37273;
        double d = 3345.35;
        long l = 345256234;
        Temperature t = new Temperature(i, d, l);

        assertEquals(i, t.getId());
        assertEquals(d, t.getTemperature(), 1);
        assertEquals(l, t.getTime());
    }

    @Test
    public void constructorUpperBoundary(){
        int i = Integer.MAX_VALUE;
        double d = Double.MAX_VALUE;
        long l = Long.MAX_VALUE;
        Temperature t = new Temperature(i, d, l);

        assertEquals(i, t.getId());
        assertEquals(d, t.getTemperature(), 1);
        assertEquals(l, t.getTime());
    }

    @Test
    public void constructorLowerBoundary(){
        int i = Integer.MIN_VALUE;
        double d = Double.MIN_VALUE;
        long l = Long.MIN_VALUE;
        Temperature t = new Temperature(i, d, l);

        assertEquals(i, t.getId());
        assertEquals(d, t.getTemperature(), 1);
        assertEquals(l, t.getTime());
    }

    @Test
    public void getLocalTimeZero(){
        int id = 0;
        double measurement = 0;
        long time = 0;
        Temperature t = new Temperature(id, measurement, time);

        TimeZone localTimeZone = Calendar.getInstance().getTimeZone();

        long result = time + localTimeZone.getRawOffset() + (localTimeZone.useDaylightTime() ? localTimeZone.getDSTSavings() : 0);

        assertEquals(result, t.getLocalTime());
    }

    @Test
    public void getLocalTimeOne(){
        int id = 0;
        double measurement = 0;
        long time = 1;
        Temperature t = new Temperature(id, measurement, time);

        TimeZone localTimeZone = Calendar.getInstance().getTimeZone();

        long result = time + localTimeZone.getRawOffset() + (localTimeZone.useDaylightTime() ? localTimeZone.getDSTSavings() : 0);

        assertEquals(result, t.getLocalTime());
    }

    @Test
    public void getLocalTimeLowerBoundary(){
        int id = 0;
        double measurement = 0;
        long time = -1;
        Temperature t = new Temperature(id, measurement, time);

        TimeZone localTimeZone = Calendar.getInstance().getTimeZone();

        long result = time + localTimeZone.getRawOffset() + (localTimeZone.useDaylightTime() ? localTimeZone.getDSTSavings() : 0);

        assertEquals(result, t.getLocalTime());
    }

    // Equivalent testing
    @Test
    public void getLocalTimeMany(){
        int id = 0;
        double measurement = 0;
        long time = 45852398;
        Temperature t = new Temperature(id, measurement, time);

        TimeZone localTimeZone = Calendar.getInstance().getTimeZone();

        long result = time + localTimeZone.getRawOffset() + (localTimeZone.useDaylightTime() ? localTimeZone.getDSTSavings() : 0);

        assertEquals(result, t.getLocalTime());
    }

    // Exceptions
}
