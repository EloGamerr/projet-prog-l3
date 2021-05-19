package fr.prog.tablut.structures;

public class Couple<T, S> {
    private T first;
    private S second;

    public Couple(T first, S second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
}
