package com.wonkglorg.fxutility.manager.util.fader;

public enum Direction {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, 1),
    DOWN(0, -1);

    private final int translationX;
    private final int translationY;

    Direction(int translationX, int translationY) {
        this.translationX = translationX;
        this.translationY = translationY;
    }

    public int getTranslationX() {
        return translationX;
    }

    public int getTranslationY() {
        return translationY;
    }
}
