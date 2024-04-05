package tickets.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    public static final String EMPTY_FILE_ERROR = "JSON file is empty, nothing to parse!";
    public static final String FILE_STRUCTURE_ERROR = "There is an error in the structure of the JSON file." +
            " One of the values/fields for the ticket is missing!";
    public static final String UNKNOWN_CITIES_ERROR = "There is something wrong in cities input!";
    public static final String NO_CITIES_INFO_IN_FILE = "There is no information about the necessary" +
            " cities in this file!";

    public static List<Map<String, String>> parseToList(String fileData, String cities)
            throws JsonProcessingException {
        List<Map<String, String>> parsedList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(fileData);
        checkIsEmptyFile(jsonNode);
        JsonNode ticketsNode = jsonNode.get("tickets");

        if (ticketsNode.isArray()) {
            for (JsonNode ticketNode : ticketsNode) {
                Map<String, String> ticketMap = new HashMap<>();

                ticketMap.put("origin_name", ticketNode.get("origin_name").asText());
                ticketMap.put("destination_name", ticketNode.get("destination_name").asText());
                ticketMap.put("departure_date", ticketNode.get("departure_date").asText());
                ticketMap.put("departure_time", ticketNode.get("departure_time").asText());
                ticketMap.put("arrival_date", ticketNode.get("arrival_date").asText());
                ticketMap.put("arrival_time", ticketNode.get("arrival_time").asText());
                ticketMap.put("carrier", ticketNode.get("carrier").asText());
                ticketMap.put("price", ticketNode.get("price").asText());

                if (ticketMap.containsValue(null)) {
                    throw new IllegalArgumentException(FILE_STRUCTURE_ERROR);
                }
                parsedList.add(ticketMap);
            }
        }
//        return removeUselessCities(parsedList, cities);
        return parsedList;
    }

    private static void checkIsEmptyFile(JsonNode jsonNode) {
        if (jsonNode == null || jsonNode.size() == 0) {
            throw new IllegalArgumentException(EMPTY_FILE_ERROR);
        }
    }

//    private static List<Map<String, Object>> removeUselessCities(
//            List<Map<String, Object>> parsedJSONBeforeUselessCitiesRemoving, String cities) {
//        String[] citiesArr = cities.split(", ");
//
//        if (citiesArr.length != 2) {
//            throw new IllegalArgumentException(UNKNOWN_CITIES_ERROR);
//        }
//
//        List<Map<String, Object>> clearedList = parsedJSONBeforeUselessCitiesRemoving.stream()
//                .filter(map ->
//                        (map.get("origin_name").toString().equals(citiesArr[0])
//                                && map.get("destination_name").toString().equals(citiesArr[1]))
//                                ||
//                        (map.get("origin_name").toString().equals(citiesArr[1])
//                                && map.get("destination_name").toString().equals(citiesArr[0]))
//                ).toList();
//
//        if (clearedList.size() == 0) {
//            throw new IllegalArgumentException(NO_CITIES_INFO_IN_FILE);
//        }
//        return clearedList;
//    }
}
