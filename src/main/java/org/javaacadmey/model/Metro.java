package org.javaacadmey.model;

import org.javaacadmey.exception.LineException;
import org.javaacadmey.exception.StationException;
import org.javaacadmey.exception.TicketsException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class Metro {
    private final String cityName;
    private final List<Line> lines = new ArrayList<>();

    private final Tickets tickets;

    private static int countSeasonTickets = 0;
    private static int LIMIT = 9999;

    private static List<String> list;

    static {
        for ()
    }

    public Metro(String cityName) {
        this.cityName = cityName;
        this.tickets = new Tickets();
    }

    public void createLine(String inputNameLine) {
        String nameLine = getFormatNameLine(inputNameLine);
        if (!checkExistedLine(nameLine)) {
            lines.add(new Line(this, nameLine));
        }
    }

    private boolean checkExistedLine(String nameLine) {
        for (Line line : lines) {
            if (line.getName().equals(nameLine)) {
                throw new LineException("Line with name " + nameLine + " already exists.");
            }
        }
        return false;
    }

    /**
     * Formatting name for new Line
     *
     * @return name after format
     */
    private String getFormatNameLine(final String inputName) {
        return inputName.substring(0, 1).toUpperCase() + inputName.substring(1).toLowerCase();
    }

    public void createFirstStationInLine(String nameLine, String stationName,
                                         List<Station> changeStation) {
        Line line = getLineByName(nameLine);
        if (!isLineEmpty(line)) {
            throw new LineException("Error created first station in line: "
                    + line.getName() + ". Line is not empty.");
        } else if (!isStationInAllLines(stationName)) {
            line.getListStation().add(new Station(this, stationName, line));
        }
    }

    public void createFirstStationInLine(String nameLine, String stationName) {
        Line line = getLineByName(nameLine);
        if (!isLineEmpty(line)) {
            throw new LineException("Error created first station in line: "
                    + line.getName() + ". Line is not empty.");
        } else if (!isStationInAllLines(stationName)) {
            line.getListStation().add(new Station(this, stationName, line));
        }
    }

    //проверили, существует ли станция с таким именем уже
    public boolean isStationInAllLines(String nameStation) {
        for (Line line : lines) {
            if (line.checkExistedStation(nameStation)) {
                return true;
                //throw new LineException("Station with name " + nameStation + " already exists.");
            }
        }
        return false;
    }

    private boolean isLineEmpty(Line line) {
        return line.getListStation().isEmpty();
    }

    /**
     * return Line by name
     */
    public Line getLineByName(String nameLine) {
        return getNotExistedLine(getFormatNameLine(nameLine));
    }

    /**
     * check not existed Line by name in list of line
     * if station not found get LinException
     * if station found @return line
     */
    private Line getNotExistedLine(String nameLine) {
        for (Line item : lines) {
            if (item.getName().equals(nameLine)) {
                return item;
            }
        }
        throw new LineException("Линия метро " + nameLine + " не найдена в метро.");
    }

    public void createLastStationInLine(String nameLine, String nameStation,
                                        Duration timeFromLastStation, Line changeLine) {
        Line line = getLineByName(nameLine);
        if (isStationInAllLines(nameStation)) {
            throw new LineException("Station with name " + nameStation + " already exists.");
        } else if (!timeFromLastStation.isNegative()
                && line.getLastStation() != null
                && line.getLastStation().getNextStation() == null
        ) {
            Station lastStation = line.getLastStation();
            Station station = new Station(this, nameStation, lastStation, null,
                    null, line, changeLine);
            line.getListStation().add(station);
            lastStation.setNextStation(station);
            lastStation.setTimeFromNextStation(timeFromLastStation);
        } else {
            throw new StationException("Error creat last station with name: "
                    + nameStation);
        }
    }

    public void createLastStationInLine(String nameLine, String nameStation,
                                        Duration timeFromLastStation) {
        Line line = getLineByName(nameLine);
        if (isStationInAllLines(nameStation)) {
            throw new LineException("Станция с именем " + nameStation + " уже существует.");
        } else if (!timeFromLastStation.isNegative()
                && line.getLastStation() != null
                && line.getLastStation().getNextStation() == null
        ) {
            Station lastStation = line.getLastStation();
            Station station = new Station(this, nameStation, lastStation, line);
            line.getListStation().add(station);
            lastStation.setNextStation(station);
            lastStation.setTimeFromNextStation(timeFromLastStation);
        } else {
            throw new StationException("Ошибка создания последней станции с именем: "
                    + nameStation);
        }
    }

    public void createSubwayCrossing(String nameLine1, String nameStation1,
                                     String nameLine2, String nameStation2) {
        Line line1 = getLineByName(nameLine1);
        Station station1 = line1.getStationByName(nameStation1);
        Line line2 = getLineByName(nameLine2);
        Station station2 = line2.getStationByName(nameStation2);
        station1.setChangeLine(line2);
        station2.setChangeLine(line1);
    }

    public int getGlobalCountStages(final Station startStation, final Station finalStation) {
        if (!isStationInAllLines(startStation.getName()) && !isStationInAllLines(startStation.getName())) {
            throw new TicketsException("Ошибка. Одна или несколько станций не существует");
        }
        if (isOneLine(startStation, finalStation)) {
            return tickets.countStages(startStation, finalStation);
        } else {
            Station changeStation = tickets.getTransferStation(startStation.getLine(), finalStation.getLine());
            int count = tickets.countStages(startStation, changeStation);
            Line line = changeStation.getChangeLine();
            changeStation = tickets.getTransferStation(line, startStation.getLine());
            count = count + tickets.countStages(finalStation, changeStation);
            return count;
        }
    }

    private boolean isOneLine(Station station1, Station station2) {
        return station1.getLine().equals(station2.getLine());
    }

    public static String generateSeasonTicket() {

    }

    @Override
    public String toString() {
        return "Metro{"
                + "cityName='" + cityName
                + '\''
                + ", lines=" + lines
                +
                '}';
    }
}
