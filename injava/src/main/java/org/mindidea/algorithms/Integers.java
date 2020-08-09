package org.mindidea.algorithms;

public class Integers {

    public static Integer[] random(int count, int min, int max) {
        if (count <= 0 || min > max) return null;
        Integer[] array = new Integer[count];
        int delta = max - min + 1;
        for (int i = 0; i < count; i++) {
            array[i] = min + (int) (Math.random() * delta);
        }
        return array;
    }

}
