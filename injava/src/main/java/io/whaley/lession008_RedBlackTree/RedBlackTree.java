package io.whaley.lession008_RedBlackTree;

import io.whaley.lession007_AVL树.AVLTree3;

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

    public void remove(E element) {
        remove(node(element));
    }

    private void remove(Node<E> node) {
        if (node == null) return;
        // 度为 2，使用前驱或后继节点替换
        // 由于前驱或后继节点一定是叶子节点，可以使用前驱或后继节点的元素替换当前节点的元素
        // 然后删除前驱或后继节节点即可
        if (node.hasTwoChildren()) {
            Node<E> predecessor = predecessor(node);
            node.element = predecessor.element;
            // predecessor 为前驱节点，node 节点元素已被替换，前驱节点可以被删除
            // node 指向前驱节点，后面等待被删除
            node = predecessor;
        }
        // 度为 1，则使用其子节点替换
        Node<E> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {
            Node<E> parent = node.parent;
            // 删除的节点是根节点
            if (parent == null) {
                root = replacement;
            } else if (parent.left == node) {
                parent.left = replacement;
            } else {
                parent.right = replacement;
            }
            afterRemove(node, replacement);
            replacement.parent = parent;
        } else {
            // 删除的是惟一的一个根节点
            if (node.parent == null) {
                root = null;
                afterRemove(node, null);
            }
            // 删除的是叶子节点
            else {
                if (node == node.parent.left) {
                    node.parent.left = null;
                } else {
                    node.parent.right = null;
                }
                afterRemove(node, null);
                node.parent = null;
            }
        }
        size--;
    }
    /**
     *
     * @param node 被删除的节点
     */
    private void afterRemove(Node<E> node, Node<E> replacement) {
        // 如果删除的是红色，则直接删除
        if (isRed(node)) return;
        // 用于取代 node 节点是红色的（ ）
        if (isRed(replacement)) {
            black(replacement);
            return;
        }
        // 删除的是黑色的叶子节点
        // 删除的是根节点
        if (node.parent == null) return;
        // 删除节点的兄弟是黑色

        // 删除节点的兄弟是红色
        // 判断被删除历节点是左还是右
        boolean left = node.parent.left == null;
        Node<E> sibling = left ? node.parent.right : node.parent.left;

        if (left) {     // 被删除的节点是左边
            if (isRed(sibling)) {   // 兄弟节点是红色的
                black(sibling);
                red(node.parent);
                rotateLeft(node.parent);
                sibling = node.parent.right;
            }
            // 兄弟节点是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                boolean parentBlack = isBlack(node.parent);
                black(node.parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(node.parent, null);
                }
            } else {    // 兄弟节点至少有1个红色子节点
                // 左边是黑色，兄弟左转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = node.parent.right;
                }
                color(sibling, colorOf(node.parent));
                black(sibling.right);
                black(node.parent);
                rotateLeft(node.parent);
            }
        } else {        // 被删除的节点是右边
            if (isRed(sibling)) {   // 兄弟节点是红色的
                black(sibling);
                red(node.parent);
                rotateRight(node.parent);
                sibling = node.parent.left;
            }
            // 兄弟节点是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                boolean parentBlack = isBlack(node.parent);
                black(node.parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(node.parent, null);
                }
            } else {    // 兄弟节点至少有1个红色子节点
                // 左边是黑色，兄弟左转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = node.parent.left;
                }
                color(sibling, colorOf(node.parent));
                black(sibling.left);
                black(node.parent);
                rotateRight(node.parent);
            }
        }
    }

    /**
     * 根据元素的值查找节点
     * @param element 元素
     * @return 节点
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
     * 前驱节点：左子树上最大的节点
     * @param node 查找的节点
     * @return node 的前驱节点
     */
    private Node<E> predecessor(Node<E> node) {
        if (node == null || node.left == null)
            return null;
        node = node.left;
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    /**
     * 获取后继节点
     * @param node 查找的节点
     * @return 后继节点
     */
    private Node<E> successor(Node<E> node) {
        if (node == null || node.right == null)
            return null;
        node = node.right;
        while (node.left != null) {
            node = node.left;
        }
        return  node;
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
