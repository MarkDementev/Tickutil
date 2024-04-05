package tickets.utility;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketsValuesFinderTest {
    public static final String DEFAULT_TICKETS_PATH =
            "./src/test/resources/fixtures/tickets.json";
    public static final String ANOTHER_TICKETS_PATH =
            "./src/test/resources/fixtures/another_tickets.json";
    public static final String DEFAULT_CITIES = "VVO TLV";

    @Test
    public void testDefaultTickets() throws Exception {
        List<Object> correctResult = new LinkedList<>();

        Map<String, Long> correctResultMap = new HashMap<>();
        correctResultMap.put("SU", 360L);
        correctResultMap.put("S7", 390L);
        correctResultMap.put("TK", 350L);
        correctResultMap.put("BA", 485L);

        correctResult.add(correctResultMap);
        correctResult.add(460.0);

        List<Object> withoutCitiesResult = TicketsValuesFinder.calculateValues(DEFAULT_TICKETS_PATH);
        List<Object> withCitiesResult = TicketsValuesFinder.calculateValues(DEFAULT_TICKETS_PATH, DEFAULT_CITIES);

        assertEquals(withoutCitiesResult, correctResult);
        assertEquals(withCitiesResult, correctResult);
    }

    @Test
    public void testAnotherTickets() throws Exception {
        List<Object> correctResult = new LinkedList<>();

        Map<String, Long> correctResultMap = new HashMap<>();
        correctResultMap.put("Aeroflot", 350L);
        correctResultMap.put("Fantasy carrier", 390L);

        correctResult.add(correctResultMap);
        correctResult.add(0.0);

        List<Object> result = TicketsValuesFinder.calculateValues(ANOTHER_TICKETS_PATH, DEFAULT_CITIES);

        assertEquals(result, correctResult);
    }
}
