package org.javaacademy.metro;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * класс Линия
 * для линии метро
 */
public class Line {
    private final Colors color;
    private final Set<Station> stationSet = new LinkedHashSet<>();
    private Metro metro;

    public Line(Colors color) {
        this.color = color;
    }

    public Line(Colors color, Metro metro) {
        this(color);
        this.metro = metro;
    }

    public Colors getColor() {
        return color;
    }

    public Set<Station> getStationSet() {
        return stationSet;
    }

    public Metro getMetro() {
        return metro;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Line line)) {
            return false;
        }
        return color == line.color;
    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }

    /**
     * Переопределить вывод в строку для Линии: цвет линии, список станций.
     */
    @Override
    public String toString() {
        return "Line{"
                + "color="
                + color.getColor()
                + ", stationSet="
                + stationSet
                + '}';
    }
}
