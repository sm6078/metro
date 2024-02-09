package org.javaacadmey;

import static org.javaacadmey.TestData.METRO_TEST;
import static org.javaacadmey.TestData.STATION_1;
import static org.javaacadmey.TestData.STATION_11;
import static org.javaacadmey.TestData.STATION_12;
import static org.javaacadmey.TestData.STATION_2;

import java.time.LocalDate;

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

        System.out.println("Tests task 3:");
        System.out.println("-------------");
        test3();
        System.out.println("==============");


    }

    public static void test1() {
        System.out.println(METRO_TEST);
    }

    public static void test2() {
        STATION_1.sellingTicket(LocalDate.of(2024, 02, 05), STATION_1, STATION_11);
        System.out.println(STATION_1.getTicketOffice().getAllTicketMap());
        System.out.println("----");
        STATION_1.sellingTicket(LocalDate.of(2024, 02, 05), STATION_1, STATION_11);
        System.out.println(STATION_1.getTicketOffice().getAllTicketMap());
        System.out.println("----");

        STATION_1.sellingTicket(LocalDate.of(2024, 02, 06), STATION_1, STATION_11);
        STATION_1.sellingTicket(LocalDate.of(2024, 02, 06), STATION_1, STATION_12);
        STATION_1.sellingTicket(LocalDate.of(2024, 02, 06), STATION_1, STATION_2);
        System.out.println(STATION_1.getTicketOffice().getAllTicketMap());
    }

    public static void test3() {
        STATION_2.sellingTicket(LocalDate.of(2024, 01, 01), STATION_2, STATION_11);
        STATION_2.sellSeasonTickets(LocalDate.of(2024, 02, 05));
        System.out.println(STATION_1.getTicketOffice().getAllTicketMap());
        System.out.println(STATION_2.getTicketOffice().getAllTicketMap());
        METRO_TEST.printTotalIncome();
        System.out.println(METRO_TEST.checkSeasonsTicketsMap("a0001", LocalDate.now()));
        System.out.println(METRO_TEST.checkSeasonsTicketsMap("a0001", LocalDate.of(2024, 03, 06)));
        STATION_2.updateSeasonTickets("a0001", LocalDate.of(2024, 02, 10));
        System.out.println(METRO_TEST.checkSeasonsTicketsMap("a0001", LocalDate.of(2024, 03, 06)));
        System.out.println(METRO_TEST.checkSeasonsTicketsMap("a0002", LocalDate.now()));

    }
}

