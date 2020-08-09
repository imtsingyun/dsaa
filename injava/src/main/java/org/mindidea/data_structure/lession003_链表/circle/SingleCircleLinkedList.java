package org.mindidea.data_structure.lession003_链表.circle;

import org.mindidea.data_structure.util.AbstractList;

/**
 * 单向循环链表
 */
public class SingleCircleLinkedList<E> extends AbstractList<E> {
    // 链表中的元素数
    private Node<E> first;

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return node(index).ele;
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
        rangeCheckForAdd(index);

        Node<E> prev = node(index - 1);
        Node<E> node = new Node<>(ele, prev.next);
        prev.next = node;

        size ++;
    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(E ele) {
        return 0;
    }

    private Node<E> node(int index) {
        rangeCheck(index);
        Node<E> point = first;
        for (int i=0;i<index; i++) {
            point = point.next;
        }
        return point;
    }

    private static class Node<E> {
        E ele;
        Node<E> next;

        public Node(E ele, Node<E> next) {
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
}
