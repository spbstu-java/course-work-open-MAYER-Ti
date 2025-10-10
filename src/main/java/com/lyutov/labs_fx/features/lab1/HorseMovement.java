package com.lyutov.labs_fx.features.lab1;

public class HorseMovement implements Movement {
    @Override
    public void moveToPoint(Point p) {
        System.out.println("On horse move to " + p.toString());
    }
}
