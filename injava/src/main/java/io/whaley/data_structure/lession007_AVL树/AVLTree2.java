package io.whaley.data_structure.lession007_AVL树;

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
        tree2.add(6);
        tree2.add(7);
        tree2.add(4);
        tree2.add(3);
        tree2.add(8);
        tree2.add(11);
        tree2.add(18);
        tree2.add(20);
        tree2.add(39);
        tree2.add(28);
        tree2.add(58);
        tree2.add(68);
        tree2.add(78);
        tree2.add(88);
        tree2.add(98);

        tree2.remove(98);
        tree2.remove(88);
        tree2.remove(78);
        tree2.remove(68);
        tree2.remove(58);
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
        int height = 1;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        /**
         * 获取平衡因子: 左右节点高度差
         * @return int
         */
        public int balanceFactor() {
            int leftHeight = this.left == null ? 0 : this.left.height;
            int rightHeight = this.right == null ? 0 : this.right.height;
            return leftHeight - rightHeight;
        }

        /**
         * 更新節點的高度
         */
        public void updateHeight() {
            // 獲取左右子樹的高度
            int leftHeight = this.left == null ? 0 : this.left.height;
            int rightHeight = this.right == null ? 0 : this.right.height;
            // 當前節點的高度是其最高子树的高度 + 1
            height = 1 + Math.max(leftHeight, rightHeight);
        }

        /**
         * 获取高度较大的子树
         * @return 子树
         */
        public Node<E> tallerChild() {
            // 獲取左右子樹的高度
            int leftHeight = this.left == null ? 0 : this.left.height;
            int rightHeight = this.right == null ? 0 : this.right.height;
            if (leftHeight > rightHeight) {
                return left;
            } else if (leftHeight < rightHeight) {
                return right;
            }
            return isLeftChild() ? left : right;
        }

        /**
         * 判断是否为左子节点
         * @return boolean
         */
        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
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
            afterAdd(root);
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
        afterAdd(newNode);
        size++;
    }

    /**
     * 添加元素后处理平衡问题
     * @param node 添加的元素
     */
    private void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;
        while (parent != null) {
            if (isBalance(parent)) {
                // 更新高度
                updateHeight(parent);
            } else {
                // 不平衡: 找到的第一个不平衡的节点
                reBalance(parent);
                break;
            }
            parent = parent.parent;
        }
    }

    /**
     * 修复平衡
     * grand 祖父节点（失衡节点）
     * parent 父节点
     * child 子节点
     */
    private void reBalance(Node<E> grand) {
        // 通过判断子树的高度差，来确定引起失衡的方向
        Node<E> parent = grand.tallerChild();
        // 引起失衡的子树
        Node<E> child = parent.tallerChild();

        if (parent.isLeftChild()) {     // L
            if (!child.isLeftChild()) { // LR
                rotateLeft(parent);
            }
            rotateRight(grand);         // LR
        } else {                        // R
            if (child.isLeftChild()) {  // RL
                rotateRight(parent);
            }
            rotateLeft(grand);          // RR
        }
    }

    /**
     * 右旋 ->
     * 涉及的三个角色：祖父节点、父节点、子节点
     * @param grand 祖父节点
     */
    private void rotateRight(Node<E> grand) {
        // 父节点：祖父节点的 左子节点
        Node<E> parent = grand.left;
        // 子节点：方该节点在旋转后会被调到 grand 的左子节点
        Node<E> child = parent.right;
        // 1. parent 取代 grand 的位置
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else {
            root = parent;
        }
        parent.parent = grand.parent;
        // 2. grand 成为 parent 的右子节点
        parent.right = grand;
        grand.parent = parent;
        // 3. child 成为 grand 的左子节点
        grand.left = child;
        if (child != null) {
            child.parent = grand;
        }
        updateHeight(grand);
        updateHeight(parent);
    }

    private void rotateLeft(Node<E> grand) {
        Node<E> parent = grand.right;
        Node<E> child = parent.left;
        //********************************************
        if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else {
            root = parent;
        }
        parent.parent = grand.parent;
        //********************************************
        parent.left = grand;
        grand.parent = parent;
        //********************************************
        grand.right = child;
        if (child != null) {
            child.parent = grand;
        }
        updateHeight(grand);
        updateHeight(parent);
    }

    /**
     * 删除节点后
     * @param node 被删除节点的父节点
     */
    private void afterRemove(Node<E> node) {
        Node<E> parent = node.parent;
        while (parent != null) {
            if (isBalance(parent)) {
                // 更新高度
                updateHeight(parent);
            } else {
                // 不平衡
                reBalance(parent);
            }
            parent = parent.parent;
        }
    }
    // 更新高度
    private void updateHeight(Node<E> node) {
        node.updateHeight();
    }

    /**
     * 
     * 判断树是否失衡
     * @param node 节点
     * @return boolean
     */
    private boolean isBalance(Node<E> node) {
        return Math.abs(node.balanceFactor()) <= 1;
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

    public void remove(E element) {
        remove(node(element));
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
            afterRemove(node);
            replacement.parent = parent;
        }
        // 删除的元素是唯一的一个根节点
        else if (node.parent == null) {
            root = null;
            afterRemove(node);
        }
        // 叶子节点
        else {
            if (node == node.parent.right) {
                node.parent.right = null;
            } else {
                node.parent.left = null;
            }
            afterRemove(node);
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
