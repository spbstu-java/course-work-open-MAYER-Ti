package com.lyutov.labs_fx.features.lab1;

public class LegsMovement implements Movement {
    @Override
    public void moveToPoint(Point p) {
        System.out.println("Legs move to " + p.toString());
    }
}
