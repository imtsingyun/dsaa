package io.whaley.data_structure.lession009_set;

import io.whaley.data_structure.lession003_链表.single.LinkedList;
import io.whaley.data_structure.util.List;

public class ListSet<E> implements Set<E> {

    private List<E> list = new LinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(E element) {
        return list.contains(element);
    }

    @Override
    public void add(E element) {
        int index = list.indexOf(element);
        if (index != List.ELEMENT_NOT_FOUND) {
            list.set(index, element);
        } else {
            list.add(element);
        }
    }

    @Override
    public void remove(E element) {
        int index = list.indexOf(element);
        if (index != List.ELEMENT_NOT_FOUND) {
            list.remove(index);
        }
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        int size = list.size();
        for (int i = 0; i< size; i++) {
            if (visitor.visit(list.get(i))) return;
        }
    }
}
