package io.whaley.util;

import java.util.Comparator;

/**
 * 二叉搜索树
 */
public class BST<E> extends BinaryTree<E> {

    public BST() {}

    public BST(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    void add(E e) {
        if (root == null) {
            root = new Node<>(e, null);
            size ++;
            return;
        }
        // 指针：用于遍历
        Node<E> node = root;
        // 要插入的节点的父节点
        Node<E> parent = null;
        // 大于 0 表示插入到 右节点
        // 小于 0 表示插入到 左节点
        int compare = 0;
        while (node != null) {
            compare = compare(e, node.element);
            parent = node;
            if (compare > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        Node<E> targetNode = new Node<>(e, parent);
        if (compare > 0) {
            parent.right = targetNode;
        } else {
            parent.left = targetNode;
        }
        size ++;
    }

    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>)e1).compareTo(e2);
    }

    void remove() {

    }

    boolean contains() {
        return false;
    }

    /**
     * 打印二叉树
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        toString(root, sb, "", 0);

        return sb.toString();
    }

    private void toString(Node<E> node, StringBuilder sb, String prefix, int direction) {
        if (node == null) return;
        toString(node.right, sb, prefix + "  │", 1);
        if (direction == 1) {
            sb.append(prefix + "┌> ").append(node.element).append("\n");
            int i = sb.lastIndexOf("┌> ");
            if (i > 0) {
                sb.replace(i - 1, i, "");
            }
        } else if (direction == -1) {
            sb.append(prefix + "└> ").append(node.element).append("\n");
            int i = sb.lastIndexOf("└> ");
            if (i > 0) {
                sb.replace(i - 1, i, "");
            }
        } else {
            sb.append(node.element).append("\n");
        }
        toString(node.left, sb, prefix + "  │", -1);

//        System.out.println(sb.toString());
    }
}
