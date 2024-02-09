package org.javaacadmey;

import static org.javaacadmey.TestData.METRO_TEST;

import java.time.LocalDate;
import org.javaacadmey.model.Metro;
import org.javaacadmey.model.Station;




public class Runner {
    public static void main(String[] args) {
        System.out.println("Tests task 1:");
        System.out.println("-------------");
        test1();
        System.out.println("==============");

        System.out.println("Tests task 2:");
        System.out.println("-------------");
        test2();
        System.out.println("==============");


    }

    public static void test1() {
        System.out.println(METRO_TEST);
    }

    public static void test2() {
        Metro metro = METRO_TEST;

        //спортивная
        Station station1 = metro.getLineByName("Красная").getListStation().get(0);
        //медведковская
        Station station11 = metro.getLineByName("Красная").getListStation().get(1);


        //пацанская
        Station station2 = metro.getLineByName("синяя").getListStation().get(0);
        //нижнекамская
        Station station21 = metro.getLineByName("синяя").getListStation().get(3);

        station1.sellingTicket(LocalDate.of(2024, 02, 05), station1, station11);
        System.out.println(station1.getTicketOffice().getTicketMap());
        System.out.println("----");
        station1.sellingTicket(LocalDate.of(2024, 02, 05), station1, station11);
        System.out.println(station1.getTicketOffice().getTicketMap());
        System.out.println("----");
        //дворец культуры
        Station station12 = metro.getLineByName("Красная").getListStation().get(5);
        station1.sellingTicket(LocalDate.of(2024, 02, 06), station1, station11);
        station1.sellingTicket(LocalDate.of(2024, 02, 06), station1, station12);
        station1.sellingTicket(LocalDate.of(2024, 02, 06), station1, station2);
        System.out.println(station1.getTicketOffice().getTicketMap());
    }
}

