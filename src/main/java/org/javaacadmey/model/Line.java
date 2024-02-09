package org.javaacadmey.model;

import java.util.ArrayList;
import java.util.List;
import org.javaacadmey.exception.StationException;

public class Line {
    private final Metro metro;
    private final String name;

    private final List<Station> stations;

    public Line(Metro metro, String name) {
        this.metro = metro;
        this.name = name;
        this.stations = new ArrayList<>();
    }

    public Line(Metro metro, String color, List<Station> listStation) {
        this(metro, color);
        this.stations.addAll(listStation);
    }


    public String getName() {
        return name;
    }

    public List<Station> getListStation() {
        return stations;
    }

    public Station getLastStation() {
        return stations.get(stations.size() - 1);
    }

    public boolean checkExistedStation(final String nameStation) {
        for (Station station : stations) {
            if (station.getName().equals(nameStation)) {
                return true;
            }
        }
        return false;
    }

    private Station getNotExistedStation(final String nameStation) {
        for (Station station : stations) {
            if (station.getName().equals(nameStation)) {
                return station;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Line{"
                +
                "color='" + name + '\''
                +
                ", stations=" + stations
                +
                '}';
    }
}
