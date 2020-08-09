package org.mindidea.data_structure.tree01_二叉搜索树;

import java.util.Comparator;

/**
 * 二叉搜索树
 */
public class BST<E> extends BinaryTree<E> {

    public BST() {
    }

    public BST(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public void add(E e) {
        if (root == null) {
            root = createNode(e, null);
            size++;
            afterAdd(root);
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
        Node<E> targetNode = createNode(e, parent);
        if (compare > 0) {
            parent.right = targetNode;
        } else {
            parent.left = targetNode;
        }
        size++;
        afterAdd(targetNode);
    }

    protected void afterAdd(Node<E> node) {}

    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>) e1).compareTo(e2);
    }

    public void remove(E element) {
        remove(node(element));
    }

    private Node<E> node(E element) {
        Node<E> node = root;
        while (node != null) {
            int compare = compare(element, node.element);
            if (compare == 0)
                return node;
            if (compare > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    private void remove(Node<E> node) {
        if (node == null) return;
        size--;
        // 如果 node 的度为 2，则使用其前驱或后继节点来替换
        if (node.hasTwoChildren()) {
            Node<E> successor = successor(node);
            node.element = successor.element;
            // 指向后继节点，等待删除
            node = successor;
        }
        // 删除 node，此时 node 的度必然为 0 或 1
        // 对于度为 1 的节点，用其左子节点或右子节点来替换
        Node<E> replacement = node.left != null ? node.left : node.right;
        // 度为 1
        if (replacement != null) {
            replacement.parent = node.parent;
            // node 是度为 1 的节点，且是根节点
            if (node.parent == null) {
                root = replacement;
            } else if (node.equals(node.parent.left)) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }
        } else if (node.parent == null) {
            root = null;
        } else {
            if (node.equals(node.parent.right)) {
                node.parent.right = null;
            } else {
                node.parent.left = null;
            }
            node.parent = null;
        }
    }

    /**
     * 获取前驱节点
     * @param element 元素
     * @return 前驱节点元素
     */
    public E predecessor(E element) {
        Node<E> node = node(element);
        Node<E> predecessor = predecessor(node);
        if (predecessor != null) {
            return predecessor.element;
        }
        return null;
    }

    private Node<E> predecessor(Node<E> node) {
        if (node == null || node.left == null) return null;

        Node<E> predecessor = node.left;
        while (predecessor.right != null) {
            predecessor = predecessor.right;
        }
        return predecessor;
    }

    /**
     * 获取后继节点
     * @param element 元素
     * @return 后继节点
     */
    public E successor(E element) {
        Node<E> successor = successor(node(element));
        if (successor != null) {
            return successor.element;
        }
        return null;
    }

    private Node<E> successor(Node<E> node) {
        if (node == null || node.right == null) return null;

        Node<E> successor = node.right;
        while (successor.left != null) {
            successor = successor.left;
        }
        return successor;
    }

    boolean contains() {
        return false;
    }

    /**
     * 打印二叉树
     *
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
            sb.append(prefix).append("┌> ").append(node.element).append("\n");
            int i = sb.lastIndexOf("┌> ");
            if (i > 0) {
                sb.replace(i - 1, i, "");
            }
        } else if (direction == -1) {
            sb.append(prefix).append("└> ").append(node.element).append("\n");
            int i = sb.lastIndexOf("└> ");
            if (i > 0) {
                sb.replace(i - 1, i, "");
            }
        } else {
            sb.append(node.element).append("\n");
        }
        toString(node.left, sb, prefix + "  │", -1);
    }
}
