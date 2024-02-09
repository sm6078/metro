package org.javaacadmey.model;

import org.javaacadmey.exception.StationException;
import org.javaacadmey.exception.TicketsException;

public class Tickets {

    public Station getTransferStation(Line lineIn, Line lineOut) {
        for (Station station : lineIn.getListStation()) {
            if (station.getChangeLine() != null && station.getChangeLine().equals(lineOut)) {
                return station;
            }
        }
        throw new StationException("Станция пересадки между линиями: " + lineIn.getName() + " и "
                + lineOut.getName() + " не найдены");
    }

    public int countStages(final Station startStation, final Station finalStation) {
        //сделать отдельную проверку - убрать повторяемость кода
        if (startStation.equals(finalStation)) {
            throw new TicketsException("Станция начала не может быть конечной станцией");
        }
        int result = 0;
        result = countStagesFromTo(startStation, finalStation);
        if (result == -1) {
            result = countStagesToFrom(startStation, finalStation);
            if (result != -1) {
                return result;
            }
        } else {
            return result;
        }
        throw new TicketsException("Произошла ошибка. Нет пути со станции: "
                + startStation.getName() + " до станции " + finalStation.getName());
    }

    private int countStagesFromTo(Station startStation, Station finalStation) {
        int count = 0;
        while (startStation.getNextStation() != null) {
            count++;
            startStation = startStation.getNextStation();
            if (startStation.equals(finalStation)) {
                return count;
            }
        }
        return -1;
    }

    private int countStagesToFrom(Station finalStation, Station startStation) {
        int count = 0;
        while (finalStation.getLastStation() != null) {
            count++;
            finalStation = finalStation.getLastStation();
            if (finalStation.equals(startStation)) {
                return count;
            }
        }
        return -1;
    }
}
