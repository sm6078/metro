package org.javaacademy.metro;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import org.javaacademy.metro.exception.LineAlreadyHasStationException;
import org.javaacademy.metro.exception.LineHasNotPrevStationException;
import org.javaacademy.metro.exception.LinesNotExistException;
import org.javaacademy.metro.exception.MetroAlreadyHasSuchLineException;
import org.javaacademy.metro.exception.MetroException;
import org.javaacademy.metro.exception.StationAlreadyHasNextStationException;
import org.javaacademy.metro.exception.StationNameIsAlreadyException;
import org.javaacademy.metro.exception.TransferTimeException;

/**
 * класс Метро
 */
public class Metro {
    private final String city;
    private final Set<Line> lines;

    public Metro(String city) {
        this.city = city;
        this.lines = new LinkedHashSet<>();
    }

    public Metro(String city, Set<Line> lines) {
        this.city = city;
        this.lines = lines;
    }

    public String getCity() {
        return city;
    }

    public Set<Line> getLines() {
        return lines;
    }

    /**
     * 1.4 В классе метро должна быть функция "Создать новую линию"
     * (добавляется в список линий метро). Передается цвет линии.
     * Проверка на то, что не существует линии с таким же цветом.
     */
    public void createLine(Colors color) throws MetroAlreadyHasSuchLineException {
        Line newLine = new Line(color, this);
        if (!lines.isEmpty() && lines.contains(newLine)) {
            throw new MetroAlreadyHasSuchLineException(
                    "Metro exception. A line with this color already exists.");
        }
        lines.add(newLine);
    }

    /**
     * 1.5 В классе метро должна быть функция "Создать первую станцию в линии".
     * Передается цвет линии, новое имя станции, список станций на пересадку.
     */
    public void createFirstStation(Colors color, String newNameStation,
                                   Set<Station> transferStation) throws MetroException {
        Line line = getLineByColor(color);
        if (checkNameStationInMetro(newNameStation)) {
            if (!checkLineIsEmpty(line)) {
                throw new LineAlreadyHasStationException("Error. Line already has stations.");
            }
        }
        line.getStationSet()
                .add(new Station(newNameStation, line, transferStation, this));
    }

    public void createFirstStation(Colors color, String newNameStation) throws MetroException {
        createFirstStation(color, newNameStation, null);
    }

    /**
     * -Линия с таким именем существует
     */
    public Line getLineByColor(Colors color) throws LinesNotExistException {
        return lines.stream().filter(line1 -> color.equals(line1.getColor()))
                .findFirst()
                .orElseThrow(() -> new LinesNotExistException(
                        "Error. Lines with this color do not exist in the metro."));
    }

    /**
     * -Станции с таким именем не существует во всех линиях.
     */
    private boolean checkNameStationInMetro(String newNameStation)
            throws StationNameIsAlreadyException {
        Optional<Station> stationOpt = lines.stream()
                .flatMap(p -> p.getStationSet()
                        .stream())
                .filter(station -> newNameStation.equalsIgnoreCase(station.getName()))
                .findFirst();
        if (stationOpt.isPresent()) {
            throw new StationNameIsAlreadyException(
                    "Error. The station name is already in use in this metro.");
        }
        return true;
    }

    /**
     * -Внутри линии нет станций
     * -Проверка на существование предыдущей станции.
     */
    private boolean checkLineIsEmpty(Line line) {
        return line.getStationSet().isEmpty();
    }

    /**
     * 1.6 В классе метро должна быть функция "Создать конечную станцию".
     * Передается цвет линии, новое имя станции, время перегона до данной станции
     * для предыдущей станции, список станций на пересадку.
     * Время перегона добавляется в предыдущую станцию.
     */
    public void createTerminalStation(Colors color, String newNameStation,
                                      Duration transferTimeFromPrevStation,
                                      Set<Station> transferStation) throws MetroException {
        Line line = getLineByColor(color);
        if (checkLineIsEmpty(line)) {
            throw new LineHasNotPrevStationException("Error. The line has not previous station.");
        }
        if (checkStationHasNotNextStation(line)
                && checkPositiveTransferTime(transferTimeFromPrevStation)) {
            Station lastStation = line.getStationSet().stream()
                    .skip(line.getStationSet().size() - 1)
                    .findFirst().get();
            Station newStation = new Station(newNameStation, lastStation,
                    transferTimeFromPrevStation, line, transferStation, this);
            line.getStationSet().add(newStation);
            lastStation.setNext(newStation);
        }
    }

    public void createTerminalStation(Colors color, String newNameStation,
                                      Duration transferTimeFromPrevStation)
            throws MetroException {
        createTerminalStation(color, newNameStation, transferTimeFromPrevStation, null);
    }

    /**
     * -Предыдущая станция должна не иметь следующей станции.
     */
    private boolean checkStationHasNotNextStation(Line line)
            throws StationAlreadyHasNextStationException {
        Station lastStation = line.getStationSet().stream()
                .skip(line.getStationSet().size() - 1)
                .findFirst().get();
        if (lastStation.getNext() != null) {
            throw new StationAlreadyHasNextStationException(
                    "Error. The station already has a next station.");
        }
        return true;
    }

    /**
     * Время перегона больше 0
     */
    private boolean checkPositiveTransferTime(Duration transferTime) throws TransferTimeException {
        boolean result = transferTime.compareTo(Duration.ofSeconds(0)) > 0;
        if (!result) {
            throw new TransferTimeException("Error. The transfer time must be greater than 0");
        }
        return true;
    }

    /**
     * Переопределить вывод в строку для Метро: имя города, список линий.
     */
    @Override
    public String toString() {
        return "Metro{"
                + "city='"
                + city
                + '\''
                + ", lines="
                + lines
                + '}';
    }
}