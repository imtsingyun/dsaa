package io.whaley.lession002_动态数组;

public class ArrayList<E> {

    private int size;
    private Object[] elements;

    private static final int DEFAULT_CAPACITY = 16;
    private static final int ELEMENT_NOT_FOUND = -1;

    public ArrayList(int capacity) {
        // 如果 capacity 小于默认值，则使用默认值
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        elements = new Object[capacity];
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public void clear() {
        for (int i=0; i<size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    public void add(E element) {
        // 如果当前数组的使用量 超过容量的 75%，则开始进行扩容
        if (size > (elements.length * 0.75)) {
            // 扩容 25%
            int newCapacity = (int) (elements.length + (elements.length * 0.25));
            Object[] old = elements;
            elements = new Object[newCapacity];
            // 将原来的数据copy到新数组
            if (size >= 0) System.arraycopy(old, 0, elements, 0, size);
        }
        elements[size++] = element;
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index = " + index + ", size = " + size);
        }
        return (E) elements[index];
    }

    public E insert(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index = " + index + ", size = " + size);
        }

        // 如果当前数组的使用量 超过容量的 75%，则开始进行扩容
        if (size > (elements.length * 0.75)) {
            // 扩容 25%
            int newCapacity = (int) (elements.length + (elements.length >> 2));
            Object[] old = elements;
            elements = new Object[newCapacity];
            // 将原来的数据copy到新数组
            System.arraycopy(old, 0, elements, 0, size);
        }
        Object old = elements[index];
        for (int i = size - 1; i >= index; i--) {
            elements[i + 1] = elements[i];
        }
        elements[index] = element;
        return (E) old;
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index = " + index + ", size = " + size);
        }
        Object old = elements[index];
        System.arraycopy(elements, index + 1, elements, index, size - index);
        elements[--size] = null;
        return (E) old;
    }

    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(elements[i])) {
                return i;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(", [");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(", ");
            }
            string.append(elements[i]);
            // 下面的方法比上面的方法多了一步减法运算
//            if (i != size - 1) {
//                string.append(", ");
//            }
        }
        string.append("]");
        return string.toString();
    }
}
