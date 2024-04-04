package tickets.utility;

import java.util.Arrays;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Calculator {
    public static Map<String, String> calculateMinimumFlightTime(List<Map<String, Object>> fileParsedToList) {

    }

    public static Double calculateDifferenceBetweenAveragePriceMedian(List<Map<String, Object>> fileParsedToList) {
        int[] ticketsPrices = new int[fileParsedToList.size()];
        int i = 0;

        for (Map<String, Object> map : fileParsedToList) {
            ticketsPrices[i++] = (int) map.get("price");
        }
        Double averagePrice = findAveragePrice(ticketsPrices);
        Double medianPrice = findMedianPrice(ticketsPrices);

        return averagePrice - medianPrice;
    }

    private static Double findAveragePrice(int[] ticketsPrices) {
        return IntStream.of(ticketsPrices).average().getAsDouble();
    }

    private static Double findMedianPrice(int[] ticketsPrices) {
        Arrays.sort(ticketsPrices);

        if (ticketsPrices.length % 2 != 0) {
            return (double) ticketsPrices[ticketsPrices.length / 2];
        }
        return (double) (ticketsPrices[ticketsPrices.length / 2] + ticketsPrices[ticketsPrices.length / 2 + 1]) / 2;
    }
}