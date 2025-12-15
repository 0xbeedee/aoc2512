
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lib {

    // Parses the ID ranges in the database and merges the mergeable ones into bigger ranges.
    public static List<Range> parseAndMergeRanges(List<String> rangeLines) {
        List<Range> rangeList = new ArrayList<>();

        // parse and sort the ranges
        for (String range : rangeLines) {
            String[] leftRight = range.trim().split("-");
            long lower = Long.parseLong(leftRight[0]);
            long upper = Long.parseLong(leftRight[1]);
            rangeList.add(new Range(lower, upper));
        }
        Collections.sort(rangeList);

        // merge the ranges
        List<Range> mergedList = new ArrayList<>();
        mergedList.add(rangeList.get(0)); // first element of old list included by default
        for (Range range : rangeList.subList(1, rangeList.size())) {
            Range newRange = mergedList.get(mergedList.size() - 1);
            if (range.getStart() <= newRange.getEnd() + 1) {
                // the old range's start is within the new range's bounds => expand the new range
                newRange.setEnd(Math.max(range.getEnd(), newRange.getEnd()));
            } else {
                // the old range is outside any of the new ranges
                mergedList.add(range);
            }
        }

        return mergedList;
    }

    // Checks wether a value is withing any of the ranges out our disposal.
    public static boolean isFresh(List<Range> ranges, long value) {
        for (Range range : ranges) {
            if (value >= range.getStart() && value <= range.getEnd()) {
                return true;
            }
        }
        return false;
    }

    // Counts the number of fresh IDs contained within the database.
    public static long countFreshIDs(List<Range> ranges) {
        long numFreshIDs = 0;
        for (Range range : ranges) {
            numFreshIDs += range.size();
        }
        return numFreshIDs;
    }
}
