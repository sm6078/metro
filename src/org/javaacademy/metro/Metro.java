package org.javaacademy.metro;

import org.javaacademy.metro.exception.*;

import java.time.Duration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

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
        checkIsAlready(checkIsMetroStationWithName(newNameStation), );


            if (!checkLineIsEmpty(line)) {
                throw new LineAlreadyHasStationException("Error. Line already has stations.");

        }
        line.getStationSet()
                .add(new Station(newNameStation, line, transferStation, this));
    }

    public void createFirstStation(Colors color, String newNameStation) throws MetroException {
        createFirstStation(color, newNameStation, null);
    }

    public void createFirstStation(String newNameStation, Colors color, String ... transferStationName) throws MetroException {
        Set<Station> transferStation = new HashSet<>();
        for (String nameStation : transferStationName) {
            Optional<Station> stationOpt = searchStationByName(nameStation);
            if (!stationOpt.isPresent()) {
                throw new StationNameNotFoundException(
                        String.format("Station with name  %s not found", nameStation));
            }
            transferStation.add(stationOpt.get());
        }
        createFirstStation(color, newNameStation, transferStation);
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
    private boolean checkIsMetroStationWithName(String newNameStation) {
        Optional<Station> stationOpt = searchStationByName(newNameStation);
        return stationOpt.isEmpty();
    }

    private void checkIsAlready(boolean found, MetroException exception, String textError) throws StationNameIsAlreadyException {
        if (found) {
            throw new (
                    "Error. The station name is already in use in this metro.");
        }
    }

    /*private void checkIsNot(boolean found)  {
        if (!found) {
            throw new StationNameIsAlreadyException(
                    "Error. The station name is already in use in this metro.");
        }
    }*/

    /**
     * -Станции с таким именем существует во всех линиях.
     */
    private boolean checkStationsWithNameExist(String newNameStation)
            throws StationNameIsAlreadyException {
        Optional<Station> stationOpt = searchStationByName(newNameStation);
        if (stationOpt.isPresent()) {
            throw new StationNameIsAlreadyException(
                    "Error. The station name is already in use in this metro.");
        }
        return true;
    }

    private Optional<Station> searchStationByName(String newNameStation) {
        Optional<Station> stationOpt = lines.stream()
                .flatMap(p -> p.getStationSet()
                        .stream())
                .filter(station -> newNameStation.equalsIgnoreCase(station.getName()))
                .findFirst();
        return stationOpt;
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
                                      Set<Station> transferStation,
                                      Duration transferTimeFromPrevStation
    ) throws MetroException {
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
        createTerminalStation(color, newNameStation, null, transferTimeFromPrevStation);
    }

    //    ??
    public void createTerminalStation(Colors color, String newNameStation,
                                      Duration transferTimeFromPrevStation,
                                      String... transferStationName) throws MetroException {
        createTerminalStation(color, newNameStation, new HashSet<>(), transferTimeFromPrevStation);
        Set<Station> transferStation = new HashSet<>();
        for (String nameStation : transferStationName) {
            Optional<Station> stationOpt = searchStationByName(nameStation);
            if (!stationOpt.isPresent()) {
                throw new StationNameNotFoundException(
                        String.format("Station with name  %s not found", nameStation));
            }
            transferStation.add(stationOpt.get());
        }
        Station station = searchStationByName(newNameStation).get();
        station.getTransferStation().addAll(transferStation);
    }

    public void createTransferStation(String stationWhereTransfer, String transferStation) {
        Optional<Station> station = searchStationByName(stationWhereTransfer);
        if (!station.isPresent()) {
            System.out.println("тут надо сдлеать ошибку");
        } else {
            Set<Station> set = new HashSet<>();
            set.add(searchStationByName("Тяжмаш").get());
            station.get().setTransferStation(set);
        }
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
     * 2.1 В классе метро должна быть функция определения станции на пересадку.
     * На вход линия с которой начнется поездка, линия на которую нужно пересесть.
     * Возвращается станция пересадки.
     */
    public Station searchTransferStation(Colors startLineColor, Colors endLineColor) throws LinesNotExistException, RouteException {
        Line startLine = getLineByColor(startLineColor);
        Line endLine = getLineByColor(endLineColor);
        for (Station station : startLine.getStationSet()) {
            if (station.getTransferStation() != null) {
                for (Station station1 : station.getTransferStation()) {
                    if (station1.getLine().equals(endLine)) {
                        System.out.println("______");
                        System.out.println(station);
                        return station;
                    }
                }
            }
        }
        /*Optional<Station> stationOpt = lines.stream()
                .flatMap(p -> p.getStationSet()
                        .stream())
                .filter(station -> newNameStation.equalsIgnoreCase(station.getName()))
                .findFirst();*/
//        Optional<Station> stationOpt = startLine.getStationSet().stream()
//                .filter(station -> station.getTransferStation() != null)
//                .flatMap(station -> station.getTransferStation().stream())
//                        .filter(station1 -> endLine.equals(station1.getLine()))
//                        }
        throw new RouteException(String.format(
                //There is no transfer between the %s line and the %s line.
                "Error route. There is no transfer between %s line and %s line.",
                startLineColor.getTranslateColor(), endLineColor.getTranslateColor()));
    }


    /**
     * 2.2 Создать вспомогательный метод в классе метро,
     * который считает количество перегонов между двумя станциями(начальной и конечной)
     * внутри одной линии.
     */
    public int countNumberSectionBetweenStationsOneLineByNameStation(String nameStation1, String nameStation2)
            throws StationNameNotFoundException, RouteException {

        Station station1 = searchStationByName(nameStation1).orElseThrow(() ->
                new StationNameNotFoundException(String.format("%s %s %s",
                        "Error. The station name ",
                        nameStation1, "is not in this metro.")));

        Station station2 = searchStationByName(nameStation2).orElseThrow(() ->
                new StationNameNotFoundException(String.format("%s %s %s",
                        "Error. The station name ",
                        nameStation2, "is not in this metro.")));
        return countNumberSectionByStation(station1, station2);
    }


    private int countNumberSectionByStation(Station station1, Station station2) throws RouteException {
        Line line = station1.getLine();
        int indexStartStation = line.getIndexStartStation(station1);
        int countSection = line.getCountSectionFromTo(station2, indexStartStation);
        if (countSection == -1) {
            countSection = line.getCountSectionToFrom(station2, indexStartStation);
        }
        if (countSection == -1) {
            throw new RouteException(String.format(
                    "Error. No route from station %s to station %s.",
                    station1.getName(), station2.getName()));
        }
        return countSection;
    }

    public int countNumberSectionBetweenStations(String nameStation1, String nameStation2) throws StationNameNotFoundException, RouteException, LinesNotExistException {
        int countNumberSection = 0;
        Station station1 = searchStationByName(nameStation1).orElseThrow(() ->
                new StationNameNotFoundException(String.format("%s %s %s",
                        "Error. The station name ",
                        nameStation1, "is not in this metro.")));

        Station station2 = searchStationByName(nameStation2).orElseThrow(() ->
                new StationNameNotFoundException(String.format("%s %s %s",
                        "Error. The station name ",
                        nameStation2, "is not in this metro.")));
        if (station1.getLine().equals(station2.getLine())) {
            countNumberSection = countNumberSectionByStation(station1, station2);
        } else {
            Station transferStation = searchTransferStation(
                    station1.getLine().getColor(), station2.getLine().getColor());
            countNumberSection = countNumberSectionBetweenStationsOtherLine(station1, station2, transferStation);
        }
        return countNumberSection;
    }

    private int countNumberSectionBetweenStationsOtherLine(Station station1, Station station2, Station transferStation) throws RouteException {
        Station tempStationNext = transferStation.getNext();
        transferStation.setNext(null);
        Station tempStationPrev = transferStation.getPrev();
        transferStation.setPrev(null);
        int countStationBeforeTransfer = countNumberSectionByStation(station1, transferStation);
        transferStation.setNext(tempStationNext);
        transferStation.setPrev(tempStationPrev);
        Set<Station> transferStations = transferStation.getTransferStation();
        Station transferStationNewLine = transferStations.stream()
                .filter(station -> station2.getLine().equals(station.getLine())).findFirst().get();
        int countStationAfterTransfer = 0;
        if (!transferStationNewLine.equals(station2)) {
            tempStationNext = station2.getNext();
            station2.setNext(null);
            tempStationPrev = station2.getPrev();
            station2.setPrev(null);
            countStationAfterTransfer = countNumberSectionByStation(
                    transferStationNewLine, station2);
            station2.setNext(tempStationNext);
            station2.setPrev(tempStationPrev);
        }
        return countStationBeforeTransfer + countStationAfterTransfer;
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