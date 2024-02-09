package org.javaacadmey.model;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.javaacadmey.exception.LineException;
import org.javaacadmey.exception.MetroException;
import org.javaacadmey.exception.StationException;
import org.javaacadmey.exception.TicketsException;

public class Metro {
    private final String cityName;
    private final List<Line> lines = new ArrayList<>();

    private final Tickets tickets;
    private static int LIMIT = 10000;

    private final Map<String, LocalDate> seasonsTicketsMap = new HashMap<>();

    private static final Queue<String> listSeasonTickets;

    static {
        listSeasonTickets = new LinkedList<>();
        for (int i = 1; i < LIMIT; i++) {
            listSeasonTickets.add(String.format("a%04d", i));
        }
    }

    public Metro(String cityName) {
        this.cityName = cityName;
        this.tickets = new Tickets();
    }

    public Map<String, LocalDate> getSeasonsTicketsMap() {
        return seasonsTicketsMap;
    }

    public boolean checkSeasonsTicketsMap(final String number, final LocalDate dateCheck) {
        LocalDate finishDateSeasonsTickets = seasonsTicketsMap.get(number);
        if (finishDateSeasonsTickets == null) {
            throw new MetroException("Произошла ошибка. Абонемент с номером: '"
                    + number + "' не найден.");
        } else {
            return dateCheck.isBefore(finishDateSeasonsTickets.plusDays(1))
                    && dateCheck.isAfter(LocalDate.now().minusDays(1));
        }
    }

    public void printTotalIncome() {
        Map<LocalDate, BigInteger> totalIncomeMap = new HashMap<>();
        for (Line line : lines) {
            for (Station station : line.getListStation()) {
                for (Map.Entry<LocalDate, BigInteger> entry
                        : station.getTicketOffice().getAllTicketMap().entrySet()) {
                    if (totalIncomeMap.putIfAbsent(entry.getKey(), entry.getValue()) != null) {
                        totalIncomeMap.merge(entry.getKey(), entry.getValue(), BigInteger::add);
                    }
                }
            }
        }
        System.out.println(totalIncomeMap);
    }

    public void createLine(final String inputNameLine) {
        String nameLine = getFormatNameLine(inputNameLine);
        if (!checkExistedLine(nameLine)) {
            lines.add(new Line(this, nameLine));
        }
    }

    private boolean checkExistedLine(final String nameLine) {
        for (Line line : lines) {
            if (line.getName().equals(nameLine)) {
                throw new LineException("Линия с именем " + nameLine + " уже существует.");
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

    public void createFirstStationInLine(final String nameLine, final String stationName,
                                         List<Station> changeStation) {
        Line line = getLineByName(nameLine);
        if (!isLineEmpty(line)) {
            throw new StationException("Ошибка создания первой станции. "
                    + line.getName() + ". Линия не пустая.");
        } else if (!isStationInAllLines(stationName)) {
            line.getListStation().add(new Station(this, stationName, line));
        }
    }

    public void createFirstStationInLine(final String nameLine, final String stationName) {
        Line line = getLineByName(nameLine);
        if (!isLineEmpty(line)) {
            throw new StationException("Ошибка создания первой станции. "
                    + line.getName() + ". Линия не пустая.");
        } else if (!isStationInAllLines(stationName)) {
            line.getListStation().add(new Station(this, stationName, line));
        }
    }

    public boolean isStationInAllLines(final String nameStation) {
        for (Line line : lines) {
            if (line.checkExistedStation(nameStation)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLineEmpty(final Line line) {
        return line.getListStation().isEmpty();
    }

    /**
     * return Line by name
     */
    public Line getLineByName(final String nameLine) {
        return getNotExistedLine(getFormatNameLine(nameLine));
    }

    /**
     * check not existed Line by name in list of line
     * if station not found get LinException
     * if station found @return line
     */
    private Line getNotExistedLine(final String nameLine) {
        for (Line item : lines) {
            if (item.getName().equals(nameLine)) {
                return item;
            }
        }
        throw new LineException("Линия метро " + nameLine + " не найдена в метро.");
    }

    public void createLastStationInLine(final String nameLine, final String nameStation,
                                        Duration timeFromLastStation, Line changeLine) {
        Line line = getLineByName(nameLine);
        if (isStationInAllLines(nameStation)) {
            throw new LineException("Станция с именем " + nameStation + " уже существует.");
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
            throw new StationException("Ошибка создания конечной станции с именем: "
                    + nameStation);
        }
    }

    public void createLastStationInLine(final String nameLine, final String nameStation,
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

    public int getGlobalCountStages(final Station startStation, final Station finalStation) {
        if (!isStationInAllLines(startStation.getName())
                && !isStationInAllLines(startStation.getName())) {
            throw new TicketsException("Ошибка. Одна или несколько станций не существует");
        }
        if (isOneLine(startStation, finalStation)) {
            return tickets.countStages(startStation, finalStation);
        } else {
            Station changeStation = tickets.getTransferStation(startStation.getLine(),
                    finalStation.getLine());
            int count = tickets.countStages(startStation, changeStation);
            Line line = changeStation.getChangeLine();
            changeStation = tickets.getTransferStation(line, startStation.getLine());
            count = count + tickets.countStages(finalStation, changeStation);
            return count;
        }
    }

    private boolean isOneLine(final Station station1, final Station station2) {
        return station1.getLine().equals(station2.getLine());
    }


    public String generateSeasonTicket() {
        String result = listSeasonTickets.poll();
        if (result == null) {
            throw new TicketsException("Абонементы закончились, продажа невозможна");
        }
        return result;
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
