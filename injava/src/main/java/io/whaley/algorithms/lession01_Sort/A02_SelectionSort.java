package io.whaley.algorithms.lession01_Sort;

public class A02_SelectionSort {
    public static void main(String[] args) {

    }

    /**
     * 不稳定：
     * 7, 5, 10, 2, 5, 2
     * 10 和最后一个2交换后，两个 2 的相对位置发生了变换
     */
    private void selectionSort(Integer[] arr) {
        for (int end = arr.length - 1; end > 0; end--) {
            int maxIndex = 0;
            for (int begin = 1; begin <= end; begin++) {
                if (arr[begin] >= arr[maxIndex]) {
                    maxIndex = begin;
                }
            }
        }
    }
}