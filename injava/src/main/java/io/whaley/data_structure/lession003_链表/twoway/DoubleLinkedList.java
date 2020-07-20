package io.whaley.data_structure.lession003_链表.twoway;

import io.whaley.data_structure.util.AbstractList;

public class DoubleLinkedList<E> extends AbstractList<E> {

    private Node<E> first;
    private Node<E> last;

    private static class Node<E> {
        E ele;
        Node<E> prev;
        Node<E> next;

        public Node(Node<E> prev, E ele, Node<E> next) {
            this.prev = prev;
            this.ele = ele;
            this.next = next;
        }

        @Override
        protected void finalize() {
            System.out.println("释放内存");
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (prev != null) {
                sb.append(prev.ele);
            } else {
                sb.append("null");
            }
            sb.append("<-").append(ele).append("->");
            if (next != null) {
                sb.append(next.ele);
            } else {
                sb.append("null");
            }
            return sb.toString();
        }
    }

    @Override
    public void clear() {
        size = 0;
        first = null;
        last = null;
    }

    @Override
    public E get(int index) {
        return node(index).ele;
    }

    @Override
    public void add(E ele) {
        insert(size, ele);
    }

    @Override
    public E set(int index, E ele) {
        Node<E> node = node(index);
        E old = node.ele;
        node.ele = ele;
        return old;
    }

    @Override
    public void insert(int index, E ele) {
        rangeCheckForAdd(index);
        // 添加到最后一个节点
        if (index == size) {
            Node<E> prev = last;
            last = new Node<>(prev, ele, null);
            if (prev == null) {
                first = last;
            } else {
                prev.next = last;
            }
        } else {
            Node<E> next = node(index);
            Node<E> prev = next.prev;
            Node<E> newNode = new Node<>(prev, ele, next);
            next.prev = newNode;

            if (prev == null) {
                first = newNode;
            } else {
                prev.next = newNode;
            }
        }
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> node = node(index);
        Node<E> prev = node.prev;
        Node<E> next = node.next;

        if (prev == null && next == null) {
            first = null;
            last = null;
        } else {
            if (prev == null) {
                first = next;
            } else {
                prev.next = next;
            }

            if (next == null) {
                last = prev;
            } else {
                next.prev = prev;
            }
        }
        return null;
    }

    @Override
    public int indexOf(E ele) {
        return 0;
    }

    /**
     * 根据 index 查找元素
     *
     * @param index 索引
     * @return 元素
     */
    private Node<E> node(int index) {
        rangeCheck(index);
        Node<E> node;
        // 从尾部开始查
        if (index > (size >> 1)) {
            node = last;
            for (int i = size; i > index; i--) {
                node = node.prev;
            }
        } else {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(",[");
        Node<E> node = first;
        while (node != null) {
            string.append(node);
            if (node.next != null) {
                string.append(", ");
            }
            node = node.next;
        }
        string.append("]");
        return string.toString();
    }
}
