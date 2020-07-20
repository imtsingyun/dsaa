package io.whaley.data_structure.lession003_链表.single;

import io.whaley.data_structure.util.List;

public class MainTest {
    public static void main(String[] args) {
        List<String> strList = new LinkedListWithFakeHead<>();
        strList.add("test1");
        strList.insert(0, "test0");
        strList.insert(1, "test2");
        strList.insert(strList.size(), "test5");
        System.out.println(strList);
        System.out.println(strList.remove(0));
        System.out.println(strList.indexOf("test5"));
        System.out.println(strList);
    }
}
