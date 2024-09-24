package org.javaacademy.metro;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

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

    public int getIndexStartStation(Station station) {
        List<Station> stationList
                = this.getStationSet().stream().toList();
        return IntStream.rangeClosed(0, stationList.size())
                .filter(i -> station.equals(stationList.get(i)))
                .findFirst().getAsInt();
    }

    public int getCountSectionFromTo(Station station, int startIndex) {
        int count = 1;
        List<Station> stationList
                = this.getStationSet().stream().toList();
        Station stationItem = stationList.get(startIndex);
        while (stationItem.getNext() != null) {
            if (stationItem.getNext().equals(station)) {
                return count;
            }
            stationItem = stationItem.getNext();
            count++;
        }
        return -1;
    }

    public int getCountSectionToFrom(Station station, int startIndex) {
        int count = 1;
        List<Station> stationList
                = this.getStationSet().stream().toList();
        Station stationItem = stationList.get(startIndex);
        while (stationItem.getPrev() != null) {
            if (stationItem.getPrev().equals(station)) {
                return count;
            }
            stationItem = stationItem.getPrev();
            count++;
        }
        return -1;
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
