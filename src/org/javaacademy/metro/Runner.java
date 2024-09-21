package org.javaacademy.metro;

import org.javaacademy.metro.exception.LineAlreadyHasStationException;
import org.javaacademy.metro.exception.LinesNotExistException;
import org.javaacademy.metro.exception.MetroAlreadyHasSuchLineException;
import org.javaacademy.metro.exception.MetroException;

import java.time.Duration;
import java.util.Set;

public class Runner {

    /**
     * 1.8 Создать Runner и в нем создать метро для Перми, описанное выше. Ожидаемый вывод для метро:
     * Metro{city='Пермь', lines=[Line{color='Красная', stations=[Station{name='Спортивная', changeLines=null'},
     * Station{name='Медведковская', changeLines=null'}, Station{name='Молодежная', changeLines=null'},
     * Station{name='Пермь 1', changeLines=Синяя'}, Station{name='Пермь 2', changeLines=null'},
     * Station{name='Дворец Культуры', changeLines=null'}]}, Line{color='Синяя',
     * stations=[Station{name='Пацанская', changeLines=null'},
     * Station{name='Улица Кирова', changeLines=null'}, Station{name='Тяжмаш', changeLines=Красная'},
     * Station{name='Нижнекамская', changeLines=null'}, Station{name='Соборная', changeLines=null'}]}]}
     */
    public static void main(String[] args) throws MetroException {
        Metro metro = new Metro("Пермь");
        metro.createLine(Colors.RED);
        metro.createLine(Colors.BLUE);
        //metro.createLine(Colors.GREEN);
        //metro.createFirstStation(Colors.GREEN, "Московская");
        metro.createTerminalStation(Colors.GREEN, "Фрунзенская", Duration.ofMinutes(0).plus(Duration.ofSeconds(48)));
        metro.createFirstStation(Colors.RED, "Спортивная");
//        metro.createFirstStation(Colors.RED, "Спортивная2");
//        metro.createFirstStation(Colors.BLUE, "Спортивная3");
        metro.createTerminalStation(Colors.RED, "Медведковская", Duration.ofMinutes(2).plus(Duration.ofSeconds(21)));
        metro.createTerminalStation(Colors.RED, "Молодежная", Duration.ofMinutes(1).plus(Duration.ofSeconds(58)));
//        metro.createFirstStation(Colors.BLUE, "Пацанская");
//        metro.createTerminalStation(Colors.BLUE, "Улица Кирова", Duration.ofMinutes(1).plus(Duration.ofSeconds(30)));
//        Set<Station> stationSet = metro.getLineByColor(Colors.BLUE).orElseThrow().getStationSet();
//        stationSet.addAll(metro.getLineByColor(Colors.GREEN).orElseThrow().getStationSet());
//        metro.createTerminalStation(Colors.RED,
//                "Пермь 1",
//                Duration.ofMinutes(3),
//                stationSet);
        System.out.println(metro);
    }
}
