package org.javaacadmey.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TicketOffice {

    private static final int COEFFICIENT = 5;
    private static final int SINGLE_PAYMENT = 20;
    private final Map<LocalDate, BigInteger> ticketMap = new HashMap<>();

    public Map<LocalDate, BigInteger> getTicketMap() {
        return ticketMap;
    }

    public void sellTicket(LocalDate date, int countStages) {
        BigInteger sum = new BigInteger(String.valueOf(countStages))
                .multiply(BigInteger.valueOf(COEFFICIENT))
                .add(BigInteger.valueOf(SINGLE_PAYMENT));
        if (ticketMap.putIfAbsent(date, sum) != null) {
            ticketMap.merge(date, sum, BigInteger::add);
        }
    }
}
