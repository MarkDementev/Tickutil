package tickets.utility;

import java.util.List;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

@Command(name = "tickutil", mixinStandardHelpOptions = true, version = "tickutil 1.0",
        description =
                """
                Utility finds 2 values in JSON file with array of air tickets.
                By default, utility works with Владивосток and Тель-Авив cities.
                Finds:
                1) Minimum flight time between cities for each air carrier.
                2) The difference between the average price and the median.
                """)
public final class App implements Callable<Integer> {
    @Parameters(paramLabel = "relative file path", index = "0", description = "file path to JSON file to work with")
    private String filePath;

    @Option(names = {"-c", "--cities"}, paramLabel = "cities",
            description = "two cities separated by commas to work with [default: Владивосток, Тель-Авив]",
            defaultValue = "Владивосток, Тель-Авив")
    private String cities;

    @Override
    public Integer call() throws Exception {
        List<Object> resultsList = TicketsValuesFinder.calculateValues(filePath, cities);
        System.out.print(
                "1) Minimum flight times for each carriers are " + resultsList.get(0) + "\n"
                + "2) The difference between the average price and the median = " + resultsList.get(1)
        );
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
