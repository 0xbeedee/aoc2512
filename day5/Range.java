
class Range implements Comparable<Range> {

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

    @Override
    public int compareTo(Range other) {
        // override to enable clean sorting
        return Long.compare(this.start, other.start);
    }
}
