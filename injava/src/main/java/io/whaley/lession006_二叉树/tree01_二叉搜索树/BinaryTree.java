package io.whaley.lession006_二叉树.tree01_二叉搜索树;

import java.util.Comparator;

/**
 * 二叉树节口封装
 */
public class BinaryTree<E> {

    protected Comparator<E> comparator;

    protected int size;

    protected Node<E> root;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }
    ////////////////////////// 遍历 ////////////////////////// Begin
    /**
     * 1. 前序
     * @param <E>
     */


    /**
     * 2. 中序
     * @param <E>
     */

    /**
     * 3. 后序
     * @param <E>
     */
    ////////////////////////// 遍历 ////////////////////////// End
    protected static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        @Override
        public String toString() {
            return "" + element;
        }
    }
}
