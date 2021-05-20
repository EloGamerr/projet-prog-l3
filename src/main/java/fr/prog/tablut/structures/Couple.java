package fr.prog.tablut.structures;

import java.util.Objects;

public class Couple<T, S> {
    private final T first;
    private final S second;

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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Couple<?, ?> couple = (Couple<?, ?>) o;
        return Objects.equals(first, couple.first) && Objects.equals(second, couple.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
    
    @Override
    public String toString() {
    	return "(" + first + ", " + second + ")";
    }
}
