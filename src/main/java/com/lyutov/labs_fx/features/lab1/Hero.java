package com.lyutov.labs_fx.features.lab1;

public class Hero {

    private Point dislocation;
    private Movement movement;

    public Hero(Movement movement, Point p) {
        this.movement = movement;
        this.dislocation = p;
    }

    public void move(Point point) {
        movement.moveToPoint(point);
        this.dislocation = point;
    }

    public Movement getMovement() {
        return this.movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public Point getDislocation() {
        return dislocation;
    }

}
