package tickets.utility;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class Calculator {
    public static Map<String, Long> calculateMinimumFlightTime(List<Map<String, String>> fileParsedToList) {
        Map<String, Long> carriersWithMinimumMinutesFly = new HashMap<>();

        for (Map<String, String> ticket : fileParsedToList) {
            carriersWithMinimumMinutesFly.put(ticket.get("carrier"), null);
        }

        for (Map.Entry<String, Long> carrierInfo : carriersWithMinimumMinutesFly.entrySet()) {
            String carrierName = carrierInfo.getKey();

            for (Map<String, String> ticket : fileParsedToList) {
                if (ticket.get("carrier").equals(carrierName)) {
                    LocalDateTime[] dateTimes = getLocalDateTimesFromTicket(ticket);
                    Long ticketFlightTimeInMinutes = ChronoUnit.MINUTES.between(dateTimes[0], dateTimes[1]);

                    if (carrierInfo.getValue() == null || carrierInfo.getValue() > ticketFlightTimeInMinutes) {
                        carriersWithMinimumMinutesFly.put(carrierName, ticketFlightTimeInMinutes);
                    }
                }
            }
        }
        return carriersWithMinimumMinutesFly;
    }

    public static Double calculateDifferenceBetweenAveragePriceMedian(List<Map<String, String>> fileParsedToList) {
        int[] ticketsPrices = new int[fileParsedToList.size()];
        int i = 0;

        for (Map<String, String> map : fileParsedToList) {
                ticketsPrices[i++] = Integer.parseInt(map.get("price"));
        }
        Double averagePrice = calculateAveragePrice(ticketsPrices);
        Double medianPrice = calculateMedianPrice(ticketsPrices);

        return averagePrice - medianPrice;
    }

    private static LocalDateTime[] getLocalDateTimesFromTicket(Map<String, String> ticket) {
        String[] departureDatesArr;
        String[] arrivalDatesArr;
        String[] departureTimesArr;
        String[] arrivalTimesArr;
        LocalDateTime[] resultsArr = new LocalDateTime[2];

        departureDatesArr = ticket.get("departure_date").split("\\.");
        arrivalDatesArr = ticket.get("arrival_date").split("\\.");
        departureTimesArr = ticket.get("departure_time").split(":");
        arrivalTimesArr = ticket.get("arrival_time").split(":");

        resultsArr[0] = LocalDateTime.of(Integer.parseInt(
                "20" + departureDatesArr[2]),
                Integer.parseInt(departureDatesArr[1]),
                Integer.parseInt(departureDatesArr[0]),
                Integer.parseInt(departureTimesArr[0]),
                Integer.parseInt(departureTimesArr[1])
        );
        resultsArr[1] = LocalDateTime.of(
                Integer.parseInt("20" + arrivalDatesArr[2]),
                Integer.parseInt(arrivalDatesArr[1]),
                Integer.parseInt(arrivalDatesArr[0]),
                Integer.parseInt(arrivalTimesArr[0]),
                Integer.parseInt(arrivalTimesArr[1])
        );

        return resultsArr;
    }

    private static Double calculateAveragePrice(int[] ticketsPrices) {
        return Arrays.stream(ticketsPrices)
                .mapToDouble(Double::valueOf)
                .average()
                .orElseThrow(IllegalArgumentException::new);
    }

    private static Double calculateMedianPrice(int[] ticketsPrices) {
        Arrays.sort(ticketsPrices);

        if (ticketsPrices.length % 2 != 0) {
            return (double) ticketsPrices[ticketsPrices.length / 2];
        }
        return (double) (ticketsPrices[ticketsPrices.length / 2 - 1] + ticketsPrices[ticketsPrices.length / 2]) / 2;
    }
}
