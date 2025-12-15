
public class Range implements Comparable<Range> {

    private long start;
    private long end;

    Range(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    // Gets the size of the range (i.e., how many integer IDs can fit within it).
    public long size() {
        return end - start + 1;
    }

    @Override
    public int compareTo(Range other) {
        // override to enable clean sorting
        return Long.compare(this.start, other.start);
    }
}
