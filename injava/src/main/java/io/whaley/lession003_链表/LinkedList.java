package io.whaley.lession003_链表;

import io.whaley.util.AbstractList;

public class LinkedList<E> extends AbstractList<E> {

    private Node<E> first;

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

    @Override
    public void clear() {
        size = 0;
        first = null;
    }

    @Override
    public E get(int index) {
        return node(index).ele;
    }

    @Override
    public void add(E ele) {
        insert(size, ele);
    }

    /**
     * 覆盖 index 位置
     *
     * @param index 位置
     * @param ele   元素
     * @return E
     */
    @Override
    public E set(int index, E ele) {
        Node<E> targetNode = node(index);
        E old = targetNode.ele;
        targetNode.ele = ele;
        return old;
    }

    public void insert(int index, E ele) {
        if (index == 0) {
            first = new Node<>(ele, first);
        } else {
            Node<E> preNode = node(index - 1);
            preNode.next = new Node<>(ele, preNode.next);
        }
        size++;
    }

    /**
     * 返回指定索引上的 节点
     *
     * @param index 索引
     * @return 节点
     */
    private Node<E> node(int index) {
        rangeCheck(index);
        Node<E> target = first;
        for (int i = 0; i < index; i++) {
            target = target.next;
        }
        return target;
    }

    @Override
    public E remove(int index) {
        Node<E> old = first;
        if (index == 0) {
            first = first.next;
        } else {
            Node<E> preNode = node(index);
            old = preNode.next;
            preNode.next = old.next;
        }
        return old.ele;
    }

    @Override
    public int indexOf(E ele) {
        if (ele == null) {
            Node<E> node = first;
            for (int i = 0; i < size; i++) {
                if (node == null) return -1;
                if (node.ele == null) return i;
                node = node.next;
            }
        } else {
            Node<E> node = first;
            for (int i = 0; i < size; i++) {
                if (node == null) return -1;
                if (ele.equals(node.ele)) return i;
                node = node.next;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return "LinkedList{" + "first=" + first + ", size=" + size + '}';
    }
}
