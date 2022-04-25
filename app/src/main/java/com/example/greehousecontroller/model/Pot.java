package com.example.greehousecontroller.model;

public class Pot {
    private int id;
    private int moisture;

    public Pot(int id, int moisture) {
        this.id = id;
        this.moisture = moisture;
    }

    public int getId() {
        return id;
    }

    public int getMoisture() {
        return moisture;
    }
}
