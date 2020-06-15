package io.whaley.lession006_二叉树.tree01_二叉搜索树;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 二叉搜索树
 * @param <E>
 */
public class BinarySearchTree<E> {

    private int size;

    private Node<E> root;

    private Comparator<E> comparator;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0 ;
    }

    public void clear() {

    }

    public void add(E ele) {
        eleNotNullCheck(ele);

        // 添加根节点
        if (root == null) {
            root = new Node<>(ele, null);
            size ++;
            return;
        }

        Node<E> node = root;
        Node<E> parent = root;
        int compare = 0;
        while (node != null) {
            compare = compare(ele, node.ele);
            parent = node;
            if (compare > 0) {
                //
                node = node.right;
            } else if (compare < 0) {
                //
                node = node.left;
            } else {
                return;
            }
        }
        Node<E> newNode = new Node<>(ele, parent);
        if (compare > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size ++;
    }


    public void remove(E ele) {

    }

    public boolean contains(E ele) {

        return false;
    }

    /**
     * > 0 ： e1 > e2
     * @param e1
     * @param e2
     * @return
     */
    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>)e1).compareTo(e2);
    }

    private void eleNotNullCheck(E ele) {
        if (ele == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    private static class Node<E> {
        E ele;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E ele, Node<E> parent) {
            this.ele = ele;
            this.parent = parent;
        }
    }

    public void preorderTraversal() {
        preorderTraversal(root);
    }

    /**
     * 前序遍历： 根 _ 左 _ 右
     * @param root 根节点
     */
    private void preorderTraversal(Node<E> root) {
        if (root == null) {
            return;
        }
        System.out.println(root.ele);
        preorderTraversal(root.left);
        preorderTraversal(root.right);
    }

    public void inorderTraversal() {
        inorderTraversal(root);
    }

    /**
     * 中序遍历： 左 _ 根 _ 右
     * @param root 根节点
     */
    private void inorderTraversal(Node<E> root) {
        if (root == null) {
            return;
        }
        inorderTraversal(root.left);
        System.out.println(root.ele);
        inorderTraversal(root.right);
    }

    public void postorderTraversal() {
        postorderTraversal(root);
    }

    /**
     * 后序遍历： 左 _ 右 _ 根
     * @param root 根节点
     */
    private void postorderTraversal(Node<E> root) {
        if (root == null) {
            return;
        }
        postorderTraversal(root.left);
        postorderTraversal(root.right);
        System.out.println(root.ele);
    }

    public void levelOrderTraversal() {
        levelOrderTraversal(root);
    }

    /**
     * 层序遍历
     * @param root 根节点
     */
    private void levelOrderTraversal(Node<E> root) {
        Queue<Node> queue = new LinkedBlockingQueue<>();
    }
}
