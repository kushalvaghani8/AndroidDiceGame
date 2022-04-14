package com.example.androiddicegame;

import java.io.Serializable;

public class Dice implements Serializable {

    private int mDice;

    public Dice(int dice) {
        mDice = dice;
    }

    public int getmDice() {
        return mDice;
    }

}