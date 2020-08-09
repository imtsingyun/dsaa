package org.mindidea.data_structure.lession009_set;

public class Main {
    public static void main(String[] args) {
        Set<Integer> set = new ListSet<>();
        set.add(10);
        set.add(100);
        set.add(100);

        set.traversal(new Set.Visitor<Integer>() {
            @Override
            boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
    }
}
