package io.whaley.data_structure.lession003_链表.twoway;

import io.whaley.data_structure.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new DoubleLinkedList<>();
        list.add(10);
        list.add(11);
        list.add(12);
        list.add(13);
        list.add(14);

        list.insert(3, 100);

        System.out.println(list);
    }
}
