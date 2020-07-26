package io.whaley.data_structure.lession012_heap;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 二叉堆
 *
 * @param <E>
 */
public class BinaryHeap<E> implements Heap<E> {
    // 二叉堆使用数组存储
    private E[] elements;
    // 元素数量
    private int size;

    private static final int DEFAULT_CAPACITY = 10;
    private Comparator<E> comparator;

    public BinaryHeap(Comparator<E> comparator) {
        this.comparator = comparator;
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public BinaryHeap() {
        this(null);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        ensureCapacity();
        // 在 index = size 的位置添加元素，同时 size++
        elements[size++] = element;
        // 将最后一个元素上虑
        shiftUp(size - 1);
    }

    private void shiftUp(int index) {
        E e = elements[index];
        while (index > 0) {
            // 父节点的所索
            int pIndex = (index - 1) >> 2;
            E p = elements[pIndex];
            if (compare(e, p) <= 0) return;
            // 交换
            elements[index] = p;
            index = pIndex;
        }
        elements[index] = e;
    }

    private void ensureCapacity() {
        // 如果当前数组的使用量 超过容量的 75%，则开始进行扩容
        if (size >= (elements.length * 0.75)) {
            // 扩容 25%
            int newCapacity = (int) (elements.length + (elements.length * 0.25));
            Object[] old = elements;
            elements = (E[]) new Object[newCapacity];
            // 将原来的数据copy到新数组
            if (size >= 0) System.arraycopy(old, 0, elements, 0, size);
        }
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E replace(E element) {
        return null;
    }

    private int compare(E e1, E e2) {
        return comparator != null ? comparator.compare(e1, e2) : ((Comparable<E>) e1).compareTo(e2);
    }

    private void emptyCheck() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }
    }

    private void elementNotNullCheck(E element) {
        if (element == null)
            throw new IllegalArgumentException("element must not be null");
    }

    public void printAsTree() {
        int lineNum = 1;//首先遍历第一行
        int lines = (int) (Math.log(size) / Math.log(2)) + 1;//lines是堆的层数
        int spaceNum = (int) (Math.pow(2, lines) - 1);
        for (int i = 0; i < size; ) { //因为在[1...size]左闭右闭区间存数据，data[0]不存数据
            //每层都是打印这个区间[2^(层数-1) ... (2^层数)-1]。如果堆里的数不够(2^层数)-1个，那就打印到size。所以取min((2^层数)-1,size).
            for (int j = (int) Math.pow(2, lineNum - 1) -1;
                 j < Math.min(size, (int) Math.pow(2, lineNum) - 1);
                 j++) {

                printSpace(spaceNum); //打印spaceNum个空格
                System.out.printf("%3s", elements[j]);//打印数据
                System.out.printf("%3s", "");//图片中绿色方框
                printSpace(spaceNum);//打印spaceNum个空格
                i++;//每打印一个元素就 + 1
            }
            lineNum++;
            spaceNum = spaceNum / 2;
            System.out.println();
        }
    }

    public void printSpace(int n) {//打印n个空格(在这里用‘\t’来代替)
        for (int i = 0; i < n; i++) {
            System.out.printf("%3s", "");
        }
    }

    public Integer root() {
        return 0;
    }

    public Integer right(Integer index) {
        index = (index << 1) + 2;
        return index >= size ? null : index;
    }

    public Integer left(Integer index) {
        index = (index << 1) + 1;
        return index >= size ? null : index;
    }

    public E string(Integer index) {
        return elements[index];
    }
}
