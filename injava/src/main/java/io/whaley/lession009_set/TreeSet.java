package io.whaley.lession009_set;

import io.whaley.lession008_RedBlackTree.RedBlackTree;

public class TreeSet<E> implements Set<E> {

    private RedBlackTree<E> tree = new RedBlackTree<>();

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return tree.size() == 0;
    }

    @Override
    public void clear() {
        tree.clear();
    }

    @Override
    public boolean contains(E elements) {
        return tree.contains(elements);
    }

    @Override
    public void add(E element) {
        tree.add(element);
    }

    @Override
    public void remove(E element) {
        tree.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        tree.inorderTraversal();
    }
}
