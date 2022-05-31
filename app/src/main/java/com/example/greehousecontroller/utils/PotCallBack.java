package com.example.greehousecontroller.utils;

import androidx.recyclerview.widget.DiffUtil;

import com.example.greehousecontroller.data.model.Pot;

import java.util.List;

public class PotCallBack extends DiffUtil.Callback {
    private final List<Pot> old;
    private final List<Pot> newList;

    public PotCallBack(List<Pot> old, List<Pot> newList) {
        this.old = old;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return old.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition) == newList.get(newItemPosition);
    }
}
