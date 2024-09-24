package org.javaacademy.metro;

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
//        metro.createLine(Colors.GREEN);
//        metro.createFirstStation(Colors.GREEN, "Московская");
//        metro.createTerminalStation(Colors.GREEN, "Фрунзенская", Duration.ofMinutes(0).plus(Duration.ofSeconds(48)));
        metro.createFirstStation(Colors.RED, "Спортивная");
        metro.createTerminalStation(Colors.RED, "Медведковская", Duration.ofMinutes(2).plus(Duration.ofSeconds(21)));
        metro.createTerminalStation(Colors.RED, "Молодежная", Duration.ofMinutes(1).plus(Duration.ofSeconds(58)));
        Set<Station> stationSet = metro.getLineByColor(Colors.BLUE).getStationSet();

        metro.createTerminalStation(Colors.RED,
                "Пермь 1",
                Duration.ofMinutes(3));
        metro.createTerminalStation(Colors.RED, "Пермь 2", Duration.ofMinutes(2).plus(Duration.ofSeconds(10)));
        metro.createTerminalStation(Colors.RED, "Дворец Культуры", Duration.ofMinutes(4).plus(Duration.ofSeconds(26)));

        metro.createFirstStation(Colors.BLUE, "Пацанская");
        metro.createTerminalStation(Colors.BLUE, "Улица Кирова", Duration.ofMinutes(1).plus(Duration.ofSeconds(30)));
        metro.createTerminalStation(Colors.BLUE, "Тяжмаш", Duration.ofMinutes(1).plus(Duration.ofSeconds(47)), "Пермь 1");
        metro.createTerminalStation(Colors.BLUE, "Нижнекамская", Duration.ofMinutes(3).plus(Duration.ofSeconds(19)));
        metro.createTerminalStation(Colors.BLUE, "Соборная", Duration.ofMinutes(1).plus(Duration.ofSeconds(48)));
        //stationSet.addAll(metro.getLineByColor(Colors.GREEN).getStationSet());

        metro.createTransferStation("Пермь 1", "");


        System.out.println(metro);
        System.out.println(metro.countNumberSectionBetweenStations("Дворец Культуры", "Пацанская"));
        System.out.println(metro.countNumberSectionBetweenStations("Пацанская", "Дворец Культуры"));
    }
}
