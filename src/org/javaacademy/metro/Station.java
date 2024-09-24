package org.javaacademy.metro;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * класс Станция
 * для станции метро
 */
public class Station {
    private final String name;
    private Station prev;
    private Station next;
    private Duration transferTime;
    private final Line line;
    private Set<Station> transferStation;
    private final Metro metro;

    public Station(String name, Station prev, Duration transferTime, Line line, Metro metro) {
        this.name = name;
        this.prev = prev;
        this.transferTime = transferTime;
        this.line = line;
        this.metro = metro;
    }

    public Station(String name, Station prev, Duration transferTime, Line line,
                   Set<Station> transferStation, Metro metro) {
        this.name = name;
        this.prev = prev;
        this.transferTime = transferTime;
        this.line = line;
        this.transferStation = transferStation;
        this.metro = metro;
    }

    public Station(String name, Line line, Set<Station> transferStation, Metro metro) {
        this.name = name;
        this.line = line;
        this.transferStation = transferStation;
        this.metro = metro;
    }

    public Station(String name, Station prev, Station next, Duration transferTime, Line line,
                   Set<Station> transferStation, Metro metro) {
        this.name = name;
        this.prev = prev;
        this.next = next;
        this.transferTime = transferTime;
        this.line = line;
        this.transferStation = transferStation;
        this.metro = metro;
    }

    public String getName() {
        return name;
    }

    public Station getPrev() {
        return prev;
    }

    public Station getNext() {
        return next;
    }

    public Duration getTransferTime() {
        return transferTime;
    }

    public Line getLine() {
        return line;
    }

    public Set<Station> getTransferStation() {
        return transferStation;
    }

    public void setTransferStation(Set<Station> transferStation) {
        this.transferStation = transferStation;
    }

    public Metro getMetro() {
        return metro;
    }

    public void setPrev(Station prev) {
        this.prev = prev;
    }

    public void setNext(Station next) {
        this.next = next;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Station station)) {
            return false;
        }

        return Objects.equals(name, station.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    /**
     * Переопределить вывод в строку для Станции:
     * имя станции, переходы на линии (цветы линии)
     */
    @Override
    public String toString() {
        String time = transferTime == null ? "0" : formatTransferTime(transferTime.getSeconds());
        String colorChangeLine =
                transferStation == null ? null : transferLineColor(transferStation).toString();
        return "Station{"
                + "name='"
                + name
                + '\''
                + ", changeLines!!="
                + colorChangeLine
                + ", "
                + "transferTime: "
                + time
                + '}';
    }

    private String formatTransferTime(long transferTime) {
        long minute = transferTime / 60;
        long seconds = transferTime - (minute * 60);
        return String.format("%s.%s", minute, seconds);
    }

    private List<String> transferLineColor(Set<Station> transferStation) {
        Set<Line> transferLines = transferStation.stream()
                .map(Station::getLine)
                .collect(Collectors.toSet());
        return transferLines.stream()
                .map(p -> p.getColor().getColor())
                .toList();
    }
}