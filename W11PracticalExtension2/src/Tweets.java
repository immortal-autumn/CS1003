public class Tweets<T> implements Comparable<Tweets<T>> {

    private T key;
    private int value;
    public Tweets(T key, int value) {
        this.key = key;
        this.value = value;
    }

    public T getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(Tweets<T> other) {
        return Integer.compare(other.value, this.value);
    }
}
