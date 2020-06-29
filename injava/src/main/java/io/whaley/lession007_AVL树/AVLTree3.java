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
            // TODO:
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
            if (compare > 0) {
                node = node.right;
            } else if (compare < 0) {
                node = node.left;
            } else {
                // 两个元素相等，暂不处理
                return;
            }
        }
        if (compare > 0) {
            targetNode.right = createNode(newEle, targetNode);
        } else if (compare < 0) {
            targetNode.left = createNode(newEle, targetNode);
        }
        size++;
        // TODO
    }
    /* ***********************************************************************************************
     * 添加一个新的元素 END
     * ***********************************************************************************************/

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
        int height;     // 树的高度

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
    }
    /* ***********************************************************************************************
     * AVL-tree 节点 End
     * ***********************************************************************************************/


}
