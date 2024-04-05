package tickets.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
    public static final String[] CORRECT_JSON_KEYS_STRUCTURE = {"origin", "origin_name", "destination",
            "destination_name", "departure_date", "departure_time", "arrival_date", "arrival_time", "carrier",
            "stops", "price"};
    public static final String UNKNOWN_CITIES_ERROR = "There is something wrong in cities input!";
    public static final String EMPTY_FILE_ERROR = "JSON file is empty, nothing to parse!";
    public static final String FILE_STRUCTURE_ERROR = "There is an error in the structure of the JSON file." +
            " One of the values/fields for the ticket is missing!";
    public static final String NO_CITIES_INFO_IN_FILE = "There is no information about the necessary" +
            " cities in this file!";

    public static List<Map<String, String>> parseToList(String fileData, String cities)
            throws JsonProcessingException {
        String[] citiesArr = cities.split(" ");

        if (citiesArr.length != 2) {
            throw new IllegalArgumentException(UNKNOWN_CITIES_ERROR);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(fileData);
        checkIsEmptyFile(jsonNode);
        JsonNode ticketsNode = jsonNode.get("tickets");
        List<Map<String, String>> parsedList = new ArrayList<>();

        if (ticketsNode.isArray()) {
            for (JsonNode ticketNode : ticketsNode) {
                Map<String, String> ticketMap = new HashMap<>();

                for (String key : CORRECT_JSON_KEYS_STRUCTURE) {
                    try {
                        ticketMap.put(key, ticketNode.get(key).asText());
                    } catch (NullPointerException e) {
                        throw new IllegalArgumentException(FILE_STRUCTURE_ERROR);
                    }
                }

                if (ticketMap.get("origin").equals(citiesArr[0]) &&
                        ticketMap.get("destination").equals(citiesArr[1])) {
                    parsedList.add(ticketMap);
                }
            }
        }

        if (parsedList.size() == 0) {
            throw new IllegalArgumentException(NO_CITIES_INFO_IN_FILE);
        }
        return parsedList;
    }

    private static void checkIsEmptyFile(JsonNode jsonNode) {
        if (jsonNode == null || jsonNode.size() == 0) {
            throw new IllegalArgumentException(EMPTY_FILE_ERROR);
        }
    }
}
