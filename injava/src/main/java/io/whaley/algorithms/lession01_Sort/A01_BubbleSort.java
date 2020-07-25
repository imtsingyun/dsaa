package io.whaley.algorithms.lession01_Sort;

import io.whaley.algorithms.Integers;

/**
 * 冒泡排序
 */
public class A01_BubbleSort {
    public static void main(String[] args) {
        A01_BubbleSort obj = new A01_BubbleSort();
//        Integer[] arr = new int[]{10, 29, 9, 19, 5, 30, 21, 99, 53};
//        Integer[] arr = new int[]{5, 9, 10, 19, 21, 29, 30, 53, 99};
        Integer[] arr = Integers.random(10, 1, 100);
        obj.sort(arr);

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ", ");
        }
    }

    public void sort(Integer[] arr) {
        for (int i = 0; i < arr.length; i++) {
            boolean isSorted = false;
            for (int j = 1; j < arr.length - i; j++) {
                if (arr[j - 1] > arr[j]) {
                    int temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                    isSorted = true;
                }
            }
            if (!isSorted) {
                break;
            }
        }
    }

    public void sort2(Integer[] arr) {
        for (int end = arr.length - 1; end > 0; end--) {
            int sortedIndex = end;
            for (int begin = 1; begin <= end; begin++) {
                if (arr[begin] < arr[begin - 1]) {
                    int tmp = arr[begin];
                    arr[begin] = arr[begin - 1];
                    arr[begin - 1] = tmp;
                    sortedIndex = begin;
                }
            }
            end = sortedIndex;
        }
    }
}
