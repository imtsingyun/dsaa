package io.whaley.lession002;

public class ArrayList {

    private int size;
    private int[] elements;

    private static final int DEFAULT_CAPACITY = 16;
    private static final int ELEMENT_NOT_FOUND = -1;

    public ArrayList(int capacity) {
        // 如果 capacity 小于默认值，则使用默认值
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        elements = new int[capacity];
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public void clear() {
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(int element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    public void add(int element) {
        // 如果当前数组的使用量 超过容量的 75%，则开始进行扩容
        if (size > (elements.length * 0.75)) {
            // 扩容 25%
            int newCapacity = (int) (elements.length + (elements.length * 0.25));
            int[] old = elements;
            elements = new int[newCapacity];
            // 将原来的数据copy到新数组
            if (size >= 0) System.arraycopy(old, 0, elements, 0, size);
        }
        elements[size++] = element;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index = " + index + ", size = " + size);
        }
        return elements[index];
    }

    public int insert(int index, int element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index = " + index + ", size = " + size);
        }

        // 如果当前数组的使用量 超过容量的 75%，则开始进行扩容
        if (size > (elements.length * 0.75)) {
            // 扩容 25%
            int newCapacity = (int) (elements.length + (elements.length >> 2));
            int[] old = elements;
            elements = new int[newCapacity];
            // 将原来的数据copy到新数组
            System.arraycopy(old, 0, elements, 0, size);
        }
        for (int i = size - 1; i >= index; i--) {
            elements[i + 1] = elements[i];
        }
        elements[index] = element;
        return ++size;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index = " + index + ", size = " + size);
        }
        int old = elements[index];
        System.arraycopy(elements, index + 1, elements, index, size - index);
        size--;
        return old;
    }

    public int indexOf(int element) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == element) {
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
