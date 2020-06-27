package io.whaley.lession008_RedBlackTree;

import java.util.Comparator;

public class RedBlackTree<E> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private Comparator<E> comparator;

    public RedBlackTree() {}

    public RedBlackTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }


    /************************************************************************************************
     * 辅助方法 Begin
     ************************************************************************************************/
    private Node<E> color(Node<E> node, boolean color) {
        if (node == null)
            return null;
        node.color = color;
        return node;
    }

    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }

    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : node.color;
    }
    /* ***********************************************************************************************
     * 辅助方法 End
     * ***********************************************************************************************/

    /************************************************************************************************
     * 红黑树节点 Begin
     ************************************************************************************************/
    protected static class Node<E> {
        public E element;
        public Node<E> left;
        public Node<E> right;
        public Node<E> parent;
        public int height =1;
        public boolean color = RED;

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

        public Node<E> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }
        @Override
        public String toString() {
            return "" + element;
        }
    }
    /* ***********************************************************************************************
     * 红黑树节点 End
     *  ***********************************************************************************************/
}
