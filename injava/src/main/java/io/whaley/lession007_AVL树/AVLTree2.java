package io.whaley.lession007_AVL树;

import io.whaley.lession006_二叉树.tree01_二叉搜索树.BinaryTree;

import java.util.Comparator;

public class AVLTree2<E> {

    public static void main(String[] args) {
        AVLTree2<Integer> tree2 = new AVLTree2<>();
        tree2.add(10);
        tree2.add(1);
        tree2.add(2);
        tree2.add(9);
        tree2.add(12);
        tree2.add(5);

        System.out.println(tree2);
    }

    // 比较器
    protected Comparator<E> comparator;

    public AVLTree2() {
    }

    public AVLTree2(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    private Node<E> root;
    private int size;

    private static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;
        int height;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        /**
         * 是否有两个子节点
         * @return boolean
         */
        public boolean hasTwoChildren() {
            return this.right != null && this.left != null;
        }
        /**
         * 判断是否为叶子节点
         * @return boolean
         */
        public boolean isLeaf() {
            return this.right == null && this.left == null;
        }
    }

    /**
     * 构造一个树节点
     *
     * @param element 树的元素
     * @param parent  父节点
     * @return 新节点
     */
    private Node<E> createNode(E element, Node<E> parent) {
        return new Node<E>(element, parent);
    }

    /**
     * 添加元素
     *
     * @param element 元素
     */
    public void add(E element) {
        if (root == null) {
            root = createNode(element, null);
            size++;
            return;
        }
        Node<E> node = root;
        int compare = 0;
        Node<E> target = root;
        while (node != null) {
            compare = compare(element, node.element);
            target = node;
            if (compare > 0) {
                node = node.right;
            } else if (compare < 0) {
                node = node.left;
            }
        }
        Node<E> newNode = createNode(element, target);
        if (compare > 0) {
            target.right = newNode;
        } else {
            target.left = newNode;
        }
        size++;
    }

    private int compare(E e1, E e2) {
        // 使用用户自定义的比较器
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        // 使用 JDK 自带的比较器
        return ((Comparable<E>) e1).compareTo(e2);
    }

    // 后继节点
    public E successor(E element) {
        Node<E> success = success(node(element));
        if (success == null)
            return null;
        return success.element;
    }

    // 后继节点，即右子树中最小的节点(右边最左的元素)
    private Node<E> success(Node<E> node) {
        if (node == null || node.right == null)
            return null;

        Node<E> successor = node.right;
        while (successor.left != null) {
            successor = successor.left;
        }
        return successor;
    }

    // 前驱节点
    public E predecessor(E element) {
        Node<E> predecessor = predecessor(node(element));
        if (predecessor == null)
            return null;
        return predecessor.element;
    }
    // 前驱节点：左子树中最大的节点（最靠右的节点）
    private Node<E> predecessor(Node<E> node) {
        if (node == null || node.left == null)
            return null;
        Node<E> predecessor = node.left;
        while (predecessor.right != null) {
            predecessor = predecessor.right;
        }
        return predecessor;
    }

    /**
     * 根据元素获取节点
     * @param element 元素
     * @return node
     */
    private Node<E> node(E element) {
        Node<E> node = root;
        while (node != null) {
            int compare = compare(element, node.element);
            if (compare == 0) {
                return node;
            }
            if (compare > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    /**
     * 删除节点
     * @param node
     */
    private void remove(Node<E> node) {
        if (node == null)
            return;
        size--;
        // 如果度为 2，则使用前驱或后继节点来替换
        if (node.hasTwoChildren()) {
            Node<E> predecessor = predecessor(node);
            // 使用前驱节点的值覆盖该节点的值，然后删除前驱节点
            node.element = predecessor.element;
            // 指向后继节点，等待删除
            node = predecessor;
        }
        // 此时节点的度必然为 0 或 1
        // 对于度为 1 的节点，用其左子节点或右子节点来替换
        Node<E> replacement = node.left == null ? node.right : node.left;
        if (replacement != null) {
            Node<E> parent = node.parent;
            // 如果是根节点
            if (parent == null) {
                root = replacement;
            } else if (parent.left == node) {
                parent.left = replacement;
            } else {
                parent.right = replacement;
            }
            replacement.parent = parent;
        }
        // 删除的元素是唯一的一个根节点
        else if (node.parent == null) {
            root = null;
        }
        // 叶子节点
        else {
            if (node == node.parent.right) {
                node.parent.right = null;
            } else {
                node.parent.left = null;
            }
            node.parent = null;
        }
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
