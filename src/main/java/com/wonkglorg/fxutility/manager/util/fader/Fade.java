package com.wonkglorg.fxutility.manager.util.fader;

public enum Fade {
    IN(1),
    OUT(-1);
    private final int value;

    Fade(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
