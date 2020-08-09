package org.mindidea.data_structure.lession003_链表.single;

import org.mindidea.data_structure.util.AbstractList;

/**
 * 带有虚拟头结点的链表
 * @param <E>
 */
public class LinkedListWithFakeHead<E> extends AbstractList<E> {

    private Node<E> first;

    public LinkedListWithFakeHead() {
        first = new Node<>(null, null);
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
        rangeCheckForAdd(index);
        Node<E> preNode = index == 0 ? first : node(index - 1);
        preNode.next = new Node<>(ele, preNode.next);
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
        Node<E> target = first.next;
        for (int i = 0; i < index; i++) {
            target = target.next;
        }
        return target;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> preNode = index == 0 ? first : node(index - 1);
        Node<E> old = preNode.next;
        preNode.next = old.next;
        size--;
        return old.ele;
    }

    @Override
    public int indexOf(E ele) {
        Node<E> node = first.next;
        if (ele == null) {
            for (int i = 0; i < size; i++) {
                if (node == null) return -1;
                if (node.ele == null) return i;
                node = node.next;
            }
        } else {
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
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(",[");
        Node<E> node = first.next;
        while (node != null) {
            string.append(node.ele);
            if (node.next != null) {
                string.append(", ");
            }
            node = node.next;
        }
        string.append("]");
        return string.toString();
    }
}
