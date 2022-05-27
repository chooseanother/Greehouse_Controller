package com.example.greehousecontroller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import android.content.Context;

import com.example.greehousecontroller.data.model.UserInfo;

@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    Context mockContext;

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void readStringFromContext_LocalizedString(){
        when(mockContext.getString(R.string.greenhouse_id)).thenReturn("test");

        UserInfo userInfo = new UserInfo();
        userInfo.setGreenhouseID(mockContext.getString(R.string.greenhouse_id));
        String result = userInfo.getGreenhouseID();
        assertEquals("test",result);
    }
}