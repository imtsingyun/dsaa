package io.whaley.lession007_AVL树;

public class AVLTree3<E> {

    private int size;       // 树的大小
    private Node<E> root;   // 树的根节点

    /************************************************************************************************
     * 创建一个新的节点
     * @param element 节点的元素
     * @param parent 父节点
     * @return 新节点
     ************************************************************************************************/
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

    /************************************************************************************************
     * 添加元素后处理是否需要进行旋转
     * 判断逻辑：
     *      从新曾节点的父节点开始往上层层判断是否失衡
     * @param node 新添加的元素
     ************************************************************************************************/
    private void afterAdd(Node<E> node) {
        if (node == null) return;
        Node<E> parent = node.parent;
        if (parent == null)
            return;
        while (parent != null) {
            if (isBalance(parent)) {
                // 更新高度
                updateHeight(parent);
            } else {
                reBalance(parent);
                break;
            }
            parent = parent.parent;
        }
    }

    private void reBalance(Node<E> grand) {
        Node<E> parent = grand.tallerChild();
        Node<E> child = parent.tallerChild();

        if (parent.isLeft()) {
            if (child.isRight()) {  // LR
                rotateLeft(parent);
            }
            rotateRight(grand);     // LL
        } else {
            if (child.isLeft()) {   // RL
                rotateRight(parent);
            }
            rotateLeft(grand);      // RR
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
        updateHeight(grand);
        updateHeight(parent);
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
        updateHeight(grand);
        updateHeight(parent);
    }

    private void updateHeight(Node<E> node) {
        node.updateHeight();
    }
    /**
     * 判断节点是否平衡
     * @param node 被判断的节点
     * @return boolean
     */
    private boolean isBalance(Node<E> node) {
        return Math.abs(node.balanceFactor()) <= 1;
    }
    /************************************************************************************************
     * 删除元素
     * @param element 删除的元素
     ************************************************************************************************/
    public void remove(E element) {
        remove(node(element));
    }

    /**
     * 删除节点，分三种情况
     * 1. 度为 2
     * 2. 度为 1
     * 3. 度为 0
     * @param node 要删除的节点
     */
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
            replacement.parent = parent;
        } else {
            // 删除的是惟一的一个根节点
            if (node.parent == null) {
                root = null;
            }
            // 删除的是叶子节点
            else {
                if (node == node.parent.left) {
                    node.parent.left = null;
                } else {
                    node.parent.right = null;
                }
                node.parent = null;
            }
        }
        size--;
    }
    /* ***********************************************************************************************
     * 删除元素 END
     * ***********************************************************************************************/

    public E predecessor(E element) {
        Node<E> predecessor = predecessor(node(element));
        if (predecessor != null) {
            return predecessor.element;
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
     * 比较两个元素的大小
     * @param e1 first element
     * @param e2 second element
     * @return if result > 0 then el > e2, if result < 0 then e1 < e2, else e1 = e2
     */
    private int compare(E e1, E e2) {
        // 使用 JDK 自带的比较器
        return ((Comparable<E>) e1).compareTo(e2);
    }
    /************************************************************************************************
     * AVL-tree 节点 Begin
     ************************************************************************************************/
    private static final class Node<E> {
        E element;      // 节点上存储的元素
        Node<E> left;   // 左指针
        Node<E> right;  // 右指针
        Node<E> parent; // 父节点指针
        int height = 1;     // 树的高度

        /**
         * 添加构造器，初始化节点
         *
         * @param element 元素
         * @param parent  父节点
         */
        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        /**
         * 判断是否有两个子节点
         * @return boolean
         */
        public boolean hasTwoChildren() {
            return this.left != null && this.right != null;
        }

        /**
         * 判断是否为叶子节点
         * @return boolean
         */
        public boolean isLeaf() {
            return this.left == null && this.right == null;
        }

        public boolean isRight() {
            return parent != null && parent.right == this;
        }

        public boolean isLeft() {
            return parent != null && parent.left == this;
        }

        public void updateHeight() {
            int leftHeight = this.left != null ? this.left.height : 0;
            int rightHeight = this.right != null ? this.right.height : 0;
            this.height = 1 + Math.max(leftHeight, rightHeight);
        }

        public Node<E> tallerChild() {
            int leftHeight = this.left != null ? this.left.height : 0;
            int rightHeight = this.right != null ? this.right.height : 0;
            if (leftHeight > rightHeight) {
                return this.left;
            }
            return right;
        }

        public int balanceFactor() {
            int leftHeight = this.left == null ? 0 : this.left.height;
            int rightHeight = this.right == null ? 0 : this.right.height;
            return leftHeight - rightHeight;
        }
    }
    /* ***********************************************************************************************
     * AVL-tree 节点 End
     * ***********************************************************************************************/

    public static void main(String[] args) {
        AVLTree3<Integer> tree3 = new AVLTree3();
        tree3.add(1);
        tree3.add(8);
        tree3.add(9);
//        tree3.add(7);
//        tree3.add(10);
//        tree3.add(11);
//        tree3.add(6);
//        tree3.add(5);
//        tree3.add(4);
//
//        tree3.add(12);
//        tree3.add(2);
//        tree3.add(13);
//
//        tree3.add(20);
        System.out.println(tree3);
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
