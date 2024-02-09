package org.javaacadmey;

import java.time.Duration;
import org.javaacadmey.model.Metro;
import org.javaacadmey.model.Station;

public class TestData {
    public static final Metro METRO_TEST;

    //спортивная
    public static final Station STATION_1;
    //медведковская
    public static final Station STATION_11;
    //пацанская
    public static final Station STATION_2;
    //нижнекамская
    public static final Station STATION_21;
    //дворец культуры
    public static final Station STATION_12;

    static {
        METRO_TEST = new Metro("Пермь");
        METRO_TEST.createLine("Красная");
        METRO_TEST.createLine("синяя");
        METRO_TEST.createFirstStationInLine("Красная", "Спортивная");
        METRO_TEST.createLastStationInLine("Красная", "Медведковская", Duration.ofSeconds(141));
        METRO_TEST.createLastStationInLine("Красная", "Молодежная", Duration.ofSeconds(118));
        METRO_TEST.createLastStationInLine("Красная", "Пермь 1",
                Duration.ofMinutes(3).plusSeconds(0), METRO_TEST.getLineByName("синяя"));
        METRO_TEST.createLastStationInLine("Красная", "Пермь 2", Duration.ofSeconds(130));
        METRO_TEST.createLastStationInLine("Красная", "Дворец культуры", Duration.ofSeconds(266));
        METRO_TEST.createFirstStationInLine("Синяя", "Пацанская");
        METRO_TEST.createLastStationInLine("Синяя", "Улица Кирова", Duration.ofSeconds(90));
        METRO_TEST.createLastStationInLine("Синяя", "Тяжмаш",
                Duration.ofSeconds(107), METRO_TEST.getLineByName("красная"));
        METRO_TEST.createLastStationInLine("Синяя", "Нижнекамская", Duration.ofSeconds(199));
        METRO_TEST.createLastStationInLine("Синяя", "Соборная", Duration.ofSeconds(108));

        STATION_1 = METRO_TEST.getLineByName("Красная").getListStation().get(0);
        STATION_11 = METRO_TEST.getLineByName("Красная").getListStation().get(1);
        STATION_2 = METRO_TEST.getLineByName("синяя").getListStation().get(0);
        STATION_21 = METRO_TEST.getLineByName("синяя").getListStation().get(3);
        STATION_12 = METRO_TEST.getLineByName("Красная").getListStation().get(5);





    }
}
