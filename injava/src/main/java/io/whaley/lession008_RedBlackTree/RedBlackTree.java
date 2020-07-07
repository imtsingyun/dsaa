package io.whaley.lession008_RedBlackTree;

import java.util.Comparator;

public class RedBlackTree<E> {

    public static void main(String[] args) {
        RedBlackTree<Integer> red = new RedBlackTree<>();
        red.add(10);
        red.add(12);
        red.add(14);
        red.add(4);
        red.add(3);

        System.out.println(red.toString());
    }

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private Node<E> root;
    private int size;

    private Comparator<E> comparator;

    public RedBlackTree() {
    }

    public RedBlackTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    private Node<E> createNode(E element, Node<E> parent) {
        return new Node<E>(element, parent);
    }

    /************************************************************************************************
     * 添加一个新的元素
     * @param element 节点的元素
     ************************************************************************************************/
    public void add(E element) {
        // 添加的第一个节点做为根节点
        if (root == null) {
            root = createNode(element, null);
            size++;
            afterAdd(root);
            return;
        }
        // 通过遍历查找合适的位置插入元素
        findPosition(element);
    }

    /**
     * 查找插入新元素的位置
     *
     * @param newEle 新元素
     */
    private void findPosition(E newEle) {
        Node<E> node = root;
        int compare = 0;                       // 比较结果, >0 表示插入右边，<0 表示插入左边
        Node<E> targetNode = root;             // 新节点的父节点
        while (node != null) {
            // 比较两个元素的大小
            compare = compare(newEle, node.element);
            targetNode = node;
            if (compare > 0) {
                node = node.right;
            } else if (compare < 0) {
                node = node.left;
            } else {
                // 两个元素相等，暂不处理
                return;
            }
        }
        Node<E> newNode = createNode(newEle, targetNode);
        if (compare > 0) {
            targetNode.right = newNode;
        } else if (compare < 0) {
            targetNode.left = newNode;
        }
        size++;
        afterAdd(newNode);
    }
    /* ***********************************************************************************************
     * 添加一个新的元素 END
     * ***********************************************************************************************/

    /**
     * 比较两个元素的大小
     *
     * @param e1 first element
     * @param e2 second element
     * @return if result > 0 then el > e2, if result < 0 then e1 < e2, else e1 = e2
     */
    private int compare(E e1, E e2) {
        // 使用 JDK 自带的比较器
        return ((Comparable<E>) e1).compareTo(e2);
    }

    /************************************************************************************************
     * 添加元素后的处理逻辑 Begin
     * 1. 根节点
     * 2. 父节点是黑色
     * 3. 叔父节点是红色
     * 4. 叔父节点是黑色
     ************************************************************************************************/
    private void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;
        // 1. 添加的是根节点 ///////////////////////////////////////////////////////////////
        if (parent == null) {
            black(node);    // 染成黑色
            return;
        }
        // 2. 父节点是黑色的，无需处理 //////////////////////////////////////////////////////
        if (isBlack(parent)) return;
        // 叔父节点
        Node<E> uncle = parent.sibling();
        // 祖父节点
        Node<E> grand = parent.parent;
        // 3. 叔父节点是红色 ///////////////////////////////////////////////////////////////
        if (isRed(uncle)) {
            // 把父节点和叔父节点都染成黑色
            black(parent);
            black(uncle);
            // 把祖父节点当成是新添加的节点
            red(grand);
            afterAdd(grand);
            return;
        }
        // 叔父节点不是红色 ///////////////////////////////////////////////////////////////
        if (parent.isLeftChild()) {
            red(grand);
            if (node.isLeftChild()) {
                black(parent);
            } else {
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else {
            red(grand);
            if (node.isLeftChild()) {
                black(node);
                rotateRight(parent);
            } else {
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    private void rotateRight(Node<E> grand) {
        Node<E> parent = grand.left;
        Node<E> child = parent.right;

        if (grand.isLeft()) {
            grand.parent.left = parent;
        } else if (grand.isRight()) {
            grand.parent.right = parent;
        } else {
            root = parent;
        }
        parent.right = grand;
        grand.left = child;

        parent.parent = grand.parent;
        grand.parent = parent;
        if (child != null) {
            child.parent = grand;
        }
    }

    private void rotateLeft(Node<E> grand) {
        Node<E> parent = grand.right;
        Node<E> child = parent.left;

        if (grand.isLeft()) {
            grand.parent.left = parent;
        } else if (grand.isRight()) {
            grand.parent.right = parent;
        } else {
            root = parent;
        }

        parent.left = grand;
        grand.right = child;

        parent.parent = grand.parent;
        grand.parent = parent;
        if (child != null) {
            child.parent = grand;
        }
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
        if (node == null) {
            return false;
        }
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

        public boolean isRight() {
            return parent != null && parent.right == this;
        }

        public boolean isLeft() {
            return parent != null && parent.left == this;
        }

        @Override
        public String toString() {
            return "" + element;
        }
    }
    /* ***********************************************************************************************
     * 红黑树节点 End
     *  ***********************************************************************************************/

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
        String color = "B";
        if (node.color == RED) {
            color = "R";
        }
        toString(node.right, sb, prefix + "  │", 1);
        if (direction == 1) {
            sb.append(prefix).append("┌> ").append("[").append(color).append("]").append(node.element).append("\n");
            int i = sb.lastIndexOf("┌> ");
            if (i > 0) {
                sb.replace(i - 1, i, "");
            }
        } else if (direction == -1) {
            sb.append(prefix).append("└> ").append("[").append(color).append("]").append(node.element).append("\n");
            int i = sb.lastIndexOf("└> ");
            if (i > 0) {
                sb.replace(i - 1, i, "");
            }
        } else {
            sb.append("[").append(color).append("]").append(node.element).append("\n");
        }
        toString(node.left, sb, prefix + "  │", -1);
    }
}
