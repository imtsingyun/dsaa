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

    public void clear() {
        root = null;
        size = 0;
    }

    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<E>(element, parent);
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
        public E element;
        public Node<E> left;
        public Node<E> right;
        public Node<E> parent;

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

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        @Override
        public String toString() {
            return "" + element;
        }
    }
}
