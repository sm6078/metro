package org.javaacadmey.model;

import java.time.Duration;
import java.time.LocalDate;
import org.javaacadmey.exception.TicketsException;

public class Station {

    private final Metro metro;
    private final String name;

    private Station lastStation;

    private Station nextStation;

    private Duration timeFromNextStation;

    private final Line line;

    private Line changeLine;

    private final TicketOffice ticketOffice = new TicketOffice();

    public Station(Metro metro, String nameStation, Station lastStation, Station nextStation,
                   Duration timeFromNextStation, Line line, Line changeLine) {
        this.metro = metro;
        this.name = nameStation;
        this.lastStation = lastStation;
        this.nextStation = nextStation;
        this.timeFromNextStation = timeFromNextStation;
        this.line = line;
        this.changeLine = changeLine;
    }

    public Station(Metro metro, String nameStation, Line line) {
        this.metro = metro;
        this.name = nameStation;
        this.line = line;
    }

    public Station(Metro metro, String nameStation, Station lastStation, Line line) {
        this.metro = metro;
        this.name = nameStation;
        this.lastStation = lastStation;
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public Station getLastStation() {
        return lastStation;
    }

    public Station getNextStation() {
        return nextStation;
    }

    public Line getChangeLine() {
        return changeLine;
    }

    public Line getLine() {
        return line;
    }

    public TicketOffice getTicketOffice() {
        return ticketOffice;
    }

    public void setTimeFromNextStation(Duration timeFromNextStation) {
        this.timeFromNextStation = timeFromNextStation;
    }

    public void setLastStation(Station lastStation) {
        this.lastStation = lastStation;
    }

    public void setNextStation(Station nextStation) {
        this.nextStation = nextStation;
    }

    public void setChangeLine(Line changeLine) {
        this.changeLine = changeLine;
    }

    public void sellingTicket(final LocalDate date,
                              final Station startStation, final Station finalStation) {
        if (!metro.isStationInAllLines(startStation.getName())
                && !metro.isStationInAllLines(finalStation.getName())) {
            throw new TicketsException("Ошибка. Одной или обоих станция не существует: "
                    + startStation.getName() + ", " + finalStation.getName());

        }
        ticketOffice.sellTicket(date, metro.getGlobalCountStages(startStation, finalStation));
    }

    public void sellSeasonTickets(final LocalDate date) {
        ticketOffice.sellSeasonTickets(metro.generateSeasonTicket(), date, metro);
    }

    public void updateSeasonTickets(final String number, final LocalDate date) {
        ticketOffice.sellSeasonTickets(number, date, metro);
    }

    @Override
    public String toString() {
        return "Station{"
                + "name='" + name
                + '\''
                + ", changeLine=" + (changeLine == null ? null : changeLine.getName())
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Station station = (Station) o;

        return getName() != null ? getName().equals(station.getName()) : station.getName() == null;
    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }
}
