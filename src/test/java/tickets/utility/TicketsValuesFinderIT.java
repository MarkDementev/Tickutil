package tickets.utility;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.nio.file.Paths;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static tickets.utility.Parser.EMPTY_FILE_ERROR;
import static tickets.utility.Parser.UNKNOWN_CITIES_ERROR;
import static tickets.utility.Parser.FILE_STRUCTURE_ERROR;
import static tickets.utility.Parser.NO_CITIES_INFO_IN_FILE;
import static tickets.utility.TicketsValuesFinder.UNKNOWN_EXTENSION_ERROR;

public class TicketsValuesFinderIT {
    public static final String DEFAULT_TICKETS_PATH =
            "./src/test/resources/fixtures/tickets.json";
    public static final String ANOTHER_TICKETS_PATH =
            "./src/test/resources/fixtures/another_tickets.json";
    private static final String FILE_DOESNT_EXIST_PATH =
            "./src/test/resources/fixtures/this_file_not_exist.json";
    private static final String FILE_UNKNOWN_EXTENSION_PATH =
            "./src/test/resources/fixtures/unknown_extension_file.oth";
    private static final String EMPTY_FILE_PATH =
            "./src/test/resources/fixtures/empty_file.json";
    private static final String FILE_ERROR_STRUCTURE_PATH =
            "./src/test/resources/fixtures/without_price_ticket.json";
    private static final String FILE_TO_MOSCOW_ONLY_TICKET_PATH =
            "./src/test/resources/fixtures/ticket_to_moscow.json";
    public static final String DEFAULT_CITIES = "VVO TLV";
    public static final String FIRST_INCORRECT_CITIES_INPUT = "VVO";
    public static final String SECOND_INCORRECT_CITIES_INPUT = "VVO TLV TLV";

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

    @Test
    public void testNoFile() {
        assertThatThrownBy(() -> TicketsValuesFinder.calculateValues(FILE_DOESNT_EXIST_PATH))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("'" + Paths.get(FILE_DOESNT_EXIST_PATH).toAbsolutePath().normalize()
                        + "' does not exist.\nCheck it!");
    }

    @Test
    public void testUnknownExtensionFile() {
        assertThatThrownBy(() -> TicketsValuesFinder.calculateValues(FILE_UNKNOWN_EXTENSION_PATH))
                .isInstanceOf(IOException.class)
                .hasMessageContaining(UNKNOWN_EXTENSION_ERROR);
    }

    @Test
    public void testIncorrectCitiesInput() {
        assertThatThrownBy(() -> TicketsValuesFinder.calculateValues(DEFAULT_TICKETS_PATH,
                FIRST_INCORRECT_CITIES_INPUT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(UNKNOWN_CITIES_ERROR);
        assertThatThrownBy(() -> TicketsValuesFinder.calculateValues(DEFAULT_TICKETS_PATH,
                SECOND_INCORRECT_CITIES_INPUT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(UNKNOWN_CITIES_ERROR);
    }

    @Test
    public void testEmptyFile() {
        assertThatThrownBy(() -> TicketsValuesFinder.calculateValues(EMPTY_FILE_PATH))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(EMPTY_FILE_ERROR);
    }

    @Test
    public void testErrorStructureFile() {
        assertThatThrownBy(() -> TicketsValuesFinder.calculateValues(FILE_ERROR_STRUCTURE_PATH))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(FILE_STRUCTURE_ERROR);
    }

    @Test
    public void testNotNeededCitiesFile() {
        assertThatThrownBy(() -> TicketsValuesFinder.calculateValues(FILE_TO_MOSCOW_ONLY_TICKET_PATH, DEFAULT_CITIES))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(NO_CITIES_INFO_IN_FILE);
    }
}
