package io.whaley.data_structure.lession009_set;

/**
 * 集合：Set
 * @param <E>
 */
public interface Set<E> {
    int size();
    boolean isEmpty();
    void clear();
    boolean contains(E elements);
    void add(E element);
    void remove(E element);
    void traversal(Visitor<E> visitor);

    public static abstract class Visitor<E> {
        boolean stop;
        abstract boolean visit(E element);
    }
}
