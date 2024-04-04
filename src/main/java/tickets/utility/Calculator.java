package tickets.utility;

import java.time.LocalDateTime;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.IntStream;

public class Calculator {
    public static Map<String, Long> calculateMinimumFlightTime(List<Map<String, Object>> fileParsedToList) {
        Map<String, Long> carriersWithMinimumMinutesFly = new HashMap<>();

        for (Map<String, Object> ticket : fileParsedToList) {
            carriersWithMinimumMinutesFly.put(String.valueOf(ticket.get("carrier")), null);
        }

        for (Map.Entry<String, Long> carrierInfo : carriersWithMinimumMinutesFly.entrySet()) {
            String carrierName = carrierInfo.getKey();
            Long carrierMinimalMinutesFlight = carrierInfo.getValue();

            for (Map<String, Object> ticket : fileParsedToList) {
                String ticketCarrier = String.valueOf(ticket.get("carrier"));

                if (ticketCarrier.equals(carrierName)) {
                    LocalDateTime departureDateTime = getLocalDateTimeFromTicket(ticket, "departure");
                    LocalDateTime arrivalDateTime = getLocalDateTimeFromTicket(ticket, "arrival");
                    Long ticketFlightTimeInMinutes = ChronoUnit.MINUTES.between(departureDateTime, arrivalDateTime);

                    if (carrierMinimalMinutesFlight == null || ticketFlightTimeInMinutes < carrierMinimalMinutesFlight) {
                        carriersWithMinimumMinutesFly.put(carrierName, ticketFlightTimeInMinutes);
                    }
                }
            }
        }
        return carriersWithMinimumMinutesFly;
    }

    public static Double calculateDifferenceBetweenAveragePriceMedian(List<Map<String, Object>> fileParsedToList) {
        int[] ticketsPrices = new int[fileParsedToList.size()];
        int i = 0;

        for (Map<String, Object> map : fileParsedToList) {
            ticketsPrices[i++] = (int) map.get("price");
        }
        Double averagePrice = calculateAveragePrice(ticketsPrices);
        Double medianPrice = calculateMedianPrice(ticketsPrices);

        return averagePrice - medianPrice;
    }

    private static LocalDateTime getLocalDateTimeFromTicket(Map<String, Object> ticket, String whatToGetKey) {
        int[] datesArr = new int[6];


        String[] dateString = {ticket.get("departure_date").toString().substring(6),
                ticket.get("arrival_date").toString().substring(6)};
        int year;
        Integer month;
        Integer day;
        Integer hour;
        Integer minute;

        switch (whatToGetKey) {
            case "departure" :
                year = 2000 + Integer.parseInt(dateString[0]);

                break;
            case "arrival" :
                year = 2000 + Integer.parseInt(dateString[1]);

                break;
            default :
                throw new RuntimeException();
        }
        return LocalDateTime.of(year, month, day, hour, minute);
    }

    private static Double calculateAveragePrice(int[] ticketsPrices) {
        return IntStream.of(ticketsPrices).average().getAsDouble();
    }

    private static Double calculateMedianPrice(int[] ticketsPrices) {
        Arrays.sort(ticketsPrices);

        if (ticketsPrices.length % 2 != 0) {
            return (double) ticketsPrices[ticketsPrices.length / 2];
        }
        return (double) (ticketsPrices[ticketsPrices.length / 2] + ticketsPrices[ticketsPrices.length / 2 + 1]) / 2;
    }
}
