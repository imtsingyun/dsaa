package io.whaley.lession003_链表;

import io.whaley.util.List;

public class MainTest {
    public static void main(String[] args) {
        List<String> strList = new LinkedList<>();
        strList.add("test1");
        strList.insert(0, "test0");
        strList.insert(1, "test2");
        strList.insert(strList.size(), "test5");
        System.out.println(strList);
        System.out.println(strList.remove(2));
        System.out.println(strList.indexOf("test4"));
        System.out.println(strList);
    }
}
