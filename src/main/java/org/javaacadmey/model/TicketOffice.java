package org.javaacadmey.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TicketOffice {

    private static final int COEFFICIENT = 5;
    private static final int SINGLE_PAYMENT = 20;

    private static final int COST_SEASON_TICKETS = 3000;
    private final Map<LocalDate, BigInteger> allTicketMap = new HashMap<>();

    public Map<LocalDate, BigInteger> getAllTicketMap() {
        return allTicketMap;
    }

    public void sellTicket(final LocalDate date, final int countStages) {
        BigInteger sum = new BigInteger(String.valueOf(countStages))
                .multiply(BigInteger.valueOf(COEFFICIENT))
                .add(BigInteger.valueOf(SINGLE_PAYMENT));
        sell(date, sum);
    }

    private void sell(final LocalDate date, final BigInteger sum) {
        if (allTicketMap.putIfAbsent(date, sum) != null) {
            allTicketMap.merge(date, sum, BigInteger::add);
        }
    }

    public void sellSeasonTickets(final String number, final LocalDate buyDate, final Metro metro) {
        if (metro.getSeasonsTicketsMap().putIfAbsent(number, buyDate.plusMonths(1)) != null) {
            metro.getSeasonsTicketsMap().merge(number, buyDate.plusMonths(1),
                    (finishDate, val) -> finishDate.plusMonths(1));
        }
        sell(buyDate, BigInteger.valueOf(COST_SEASON_TICKETS));
    }
}
