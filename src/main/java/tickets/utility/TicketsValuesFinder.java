package tickets.utility;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicketsValuesFinder {
    private static final String DEFAULT_CITIES = "Владивосток, Тель-Авив";
    public static final String UNKNOWN_EXTENSION_ERROR = "There is wrong filename extension.\nUtility works only with" +
            " JSON files!";

    public static List<Object> calculateValues(String filePath) throws Exception {
        return calculateValues(filePath, DEFAULT_CITIES);
    }

    public static List<Object> calculateValues(String filePath, String cities) throws Exception {
        List<Map<String, String>> fileParsedToList = getData(filePath, cities);
        List<Object> resultsList = new ArrayList<>();

        resultsList.add(Calculator.calculateMinimumFlightTime(fileParsedToList));
        resultsList.add(Calculator.calculateDifferenceBetweenAveragePriceMedian(fileParsedToList));

        return resultsList;
    }

    private static List<Map<String, String>> getData(String filePath, String cities) throws Exception {
        Path fileAbsolutePath = getAbsolutePath(filePath);
        checkFileExistance(fileAbsolutePath);
        checkFileExtension(filePath);
        String fileData = pathToData(fileAbsolutePath);

        return Parser.parseToList(fileData, cities);
    }

    private static Path getAbsolutePath(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }

    private static void checkFileExistance(Path absoluteFilePath) throws IOException {
        if (!Files.exists(absoluteFilePath)) {
            throw new IOException("'" + absoluteFilePath + "' does not exist.\nCheck it!");
        }
    }

    private static void checkFileExtension(String filePath) throws Exception {
        String fileExtension = filePath.substring(filePath.lastIndexOf('.') + 1).toLowerCase();

        if (!fileExtension.equals("json")) {
            throw new Exception(UNKNOWN_EXTENSION_ERROR);
        }
    }

    private static String pathToData(Path absoluteFilePath) throws IOException {
        return Files.readString(absoluteFilePath);
    }
}
