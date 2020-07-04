package io.whaley.lession008_RedBlackTree;

import java.util.Comparator;

public class RedBlackTree<E> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private Node<E> root;
    private int size;

    private Comparator<E> comparator;

    public RedBlackTree() {}

    public RedBlackTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }


    /************************************************************************************************
     * 添加元素后的处理逻辑 Begin
     ************************************************************************************************/
    private void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;
        // 添加的是根节点
        if (parent == null) {
            black(node);    // 染成黑色
            return;
        }
        // 父节点是黑色的，无需处理
        if (isBlack(parent)) return;
        // 叔父节点
        Node<E> uncle = parent.sibling();
        // 祖父节点
        Node<E> grand = parent.parent;
        // 叔父节点是红色
        if (isRed(uncle)) {
            // 把父节点和叔父节点都染成黑色
            black(parent);
            black(uncle);
            // 把祖父节点当成是新添加的节点
            red(grand);
            afterAdd(grand);
            return;
        }

        // 叔父节点不是红色

    }
    /* ***********************************************************************************************
     * 添加元素后的处理逻辑 End
     * ***********************************************************************************************/

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
