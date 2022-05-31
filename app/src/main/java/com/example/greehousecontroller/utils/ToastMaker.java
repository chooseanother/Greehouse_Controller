package com.example.greehousecontroller.utils;

import android.content.Context;
import android.widget.Toast;

import java.util.Date;

public class ToastMaker {
    private static final long TIME_BETWEEN_TOASTS = 2000; // 2 seconds
    private static ToastMaker instance;
    private Date lastToast;

    private ToastMaker() {

    }

    public static ToastMaker getInstance() {
        if (instance == null) {
            instance = new ToastMaker();
        }
        return instance;
    }

    public void makeToast(Context context, String message) {
        if (lastToast == null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            lastToast = new Date();
        } else if (((new Date()).getTime() - lastToast.getTime()) > TIME_BETWEEN_TOASTS) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            lastToast = new Date();
        }
    }
}
