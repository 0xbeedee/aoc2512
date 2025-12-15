
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class Main {

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("database.txt"));

            int splitIdx = lines.indexOf(""); // get index of empty line
            List<String> ranges = lines.subList(0, splitIdx);
            List<String> ids = lines.subList(splitIdx + 1, lines.size());

            List<Range> parsedRanges = Lib.parseAndMergeRanges(ranges);

            int numFreshIngredients = 0;
            for (String id : ids) {
                long numericID = Long.parseLong(id);
                if (Lib.isInRange(parsedRanges, numericID)) {
                    numFreshIngredients++;
                }
            }

            System.out.printf("[PHASE 1] The number of fresh ingredients is: %d\n", numFreshIngredients);
        } catch (IOException e) {
            System.out.printf("Error reading file: %s\n", e.getMessage());
        }

    }
}
