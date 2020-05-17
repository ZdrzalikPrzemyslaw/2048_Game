package com.game.module;

public class Field {

    private int value;

    public Field(int value) {
        if (value > 0 && value % 2 == 0) {
            this.value = value;
        }
    }

    public Field() {
        value = 0;
    }

    public void setValue(int value) {
        if (value > 0 && value % 2 == 0) {
            this.value = value;
        }
    }

    public int getValue() {
        return value;
    }

}
