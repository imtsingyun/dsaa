package io.whaley.lession003_链表.twoway;

import io.whaley.util.AbstractList;

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
            return "Node{" + "ele=" + ele + ", next=" + next + '}';
        }
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public void add(E ele) {

    }

    @Override
    public E set(int index, E ele) {
        return null;
    }

    @Override
    public void insert(int index, E ele) {

    }

    @Override
    public E remove(int index) {
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
                node = node.next;
            }
        } else {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node;
    }
}
