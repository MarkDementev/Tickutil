package tickets.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class Parser {
    public static final String EMPTY_FILE_ERROR = "JSON file is empty, nothing to work with!";

    public static List<Map<String, Object>> parseToList(String fileData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> parsedList = mapper.readValue(fileData, new TypeReference<>() { });

        return checkIsEmptyFile(parsedList);
    }

    private static List<Map<String, Object>> checkIsEmptyFile(List<Map<String, Object>> parsedList) {
        if (parsedList == null || parsedList.size() == 0) {
            throw new IllegalArgumentException(EMPTY_FILE_ERROR);
        }

        return parsedList;
    }
}
