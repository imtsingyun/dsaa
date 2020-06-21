package io.whaley.lession006_二叉树.tree01_二叉搜索树;

import java.util.*;

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
        Node<E> newNode = new Node<E>(ele, parent);
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
        if (root == null) {
            return;
        }
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            System.out.println(node.ele);
            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    /**
     * 获取一个节点的前驱节点
     * @return 前驱节点: 中序遍历时的前一个节点
     */
    public Node<E> predecessor(Node<E> root) {
        if (root == null) return null;

        // 中序遍历，使用迭代
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        // 前一个节点
        Node<E> preNode;

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (node.left != null)
                queue.offer(node.left);
            queue.offer(node);
            if (node.right != null)
                queue.offer(node.right);
            
        }



        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(root, sb, "");
        return sb.toString();
    }

    private void toString(Node<E> node, StringBuilder sb, String prefix) {
        if (node == null) {
            return;
        }

        toString(node.right, sb, prefix + "  / ");
        sb.append(prefix).append(node.ele).append("\n");
        toString(node.left, sb, prefix + "  \\ ");
    }

    public void printTree() {
        if (root == null) {
            return;
        }
        int sizeOfLine = 1;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        List<E> list = new ArrayList<>();
        while (!queue.isEmpty()) {
            Node<E> poll = queue.poll();
            sizeOfLine --;
            list.add(poll.ele);

            if (poll.left != null) {
                queue.offer(poll.left);
            }
            if (poll.right != null) {
                queue.offer(poll.right);
            }
            if (sizeOfLine == 0) {
                sizeOfLine = queue.size();
                System.out.print(list);
                System.out.println();
                list = new ArrayList<>();
            }
        }
    }

    public void levelOrderTraversal(Visitor<E> visitor) {
        if (root == null || visitor == null) {
            return;
        }
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            visitor.visit(node.ele);
            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
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

    public static interface Visitor<E> {
        void visit(E ele);
    }

}
