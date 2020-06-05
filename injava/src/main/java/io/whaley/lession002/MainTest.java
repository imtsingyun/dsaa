package io.whaley.lession002;

public class MainTest {
    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        System.out.println(list);

        list.insert(1, 100);
        list.insert(1, 101);
        list.insert(1, 102);
        list.insert(1, 103);
        list.insert(1, 104);
        System.out.println(list);


    }
}
