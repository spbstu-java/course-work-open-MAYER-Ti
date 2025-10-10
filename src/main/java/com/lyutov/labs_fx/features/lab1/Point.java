package com.lyutov.labs_fx.features.lab1;

public class Point {
    public String name;
    public double lat;
    public double lon;

    Point (String name, double lat, double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String toString() {
        return String.format("{%s - широта: %.4f, долгота: %.4f}", name, lat, lon);
    }
}
