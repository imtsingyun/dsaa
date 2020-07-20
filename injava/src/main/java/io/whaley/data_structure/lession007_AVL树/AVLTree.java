package io.whaley.data_structure.lession007_AVL树;

import io.whaley.data_structure.tree01_二叉搜索树.BST;

import java.util.Comparator;

public class AVLTree<E> extends BST<E> {

    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * 从当前新增的节点的父节点开始找第一个不平衡的节点
     * @param node 当前新增的节点
     */
    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;
        while (parent != null) {
            // 是否失衡
            if (isBalance(parent)) {
                // 更新高度
                updateHeight(parent);
            } else {
                // 恢复平衡(parent是失去平衡的节点）
                rebalance(parent);
                break;
            }
            parent = parent.parent;
        }
    }

    /**
     * @param grand 高度最低的失衡节点
     */
    private void rebalance(Node<E> grand) {
        // 失衡节点的子节点中高度较高的节点
        Node<E> parent = ((AVLNode<E>)grand).tallerChild();
        // parent 的子节点中高度较高的节点
        Node<E> node = ((AVLNode<E>)parent).tallerChild();
        // 如果失衡节点中较高的子节点是左子节点
        if (parent.isLeftChild()) {     // L
            // 需要右旋转
            if (node.isLeftChild()) {   // LL
                rotateRight(grand);
            } else {                    // LR
                rotateLeft(parent);
                rotateRight(grand);
            }
        } else {                        // R
            if (node.isLeftChild()) {   // RL
                rotateRight(parent);
                rotateLeft(grand);
            } else {                    // RR
                rotateLeft(grand);
            }
        }
    }

    private void rotateLeft(Node<E> g) {
        Node<E> gp = g.parent;
        Node<E> p = ((AVLNode<E>)g).tallerChild();

        g.right = p.left;
        if (p.left !=null) {
            p.left.parent = g;
        }

        if (gp == null) {
            root = p;
        } else {
            if (gp.right == g) {
                gp.right = p;
            } else {
                gp.left = p;
            }
        }
        p.parent = gp;

        p.left = g;
        g.parent = p;

        updateHeight(g);
        updateHeight(p);
    }

    /**
     * 右旋
     * @param g 需要右旋的节点
     */
    private void rotateRight(Node<E> g) {
        // 右旋节点的父节点
        Node<E> gp = g.parent;
        // 右旋节点中较高的子节点（用来替换右旋节点的位置）
        Node<E> p = ((AVLNode<E>)g).tallerChild();
        // 右旋节点的 left 指向替换右旋节点的 right
        g.left = p.right;
        if (p.right != null) {
            // 如果替换右旋节点的节点的 right 不为空，则修改其父节点为 右旋节点
            p.right.parent = g;
        }
        // 如果右旋节点的父节点为 null，表示右旋节点是根节点
        if (gp == null) {
            root = p;
        } else {
            // 如果不是根节点，判断右旋节点是其父节点的方向
            if (gp.right == g) {
                gp.right = p;
            } else {
                gp.left = p;
            }
        }
        // 替换节点的父节点指向右旋节点的父节点
        p.parent = gp;
        // 替换节点的 right 指向右旋节点（被替换节点）
        p.right = g;
        // 被替换节点的父节点指向替换节点
        g.parent = p;
        // 更新替换节点和被替换节点的高度
        updateHeight(p);
        updateHeight(g);
    }

    private void updateHeight(Node<E> node) {
        ((AVLNode<E>)node).updateHeight();
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode(element, parent);
    }

    private boolean isBalance(Node<E> node) {
        return Math.abs(((AVLNode)node).balanceFactor()) <= 1;
    }

    private class AVLNode<E> extends Node<E> {

        public int height =1;

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AVLNode)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode)right).height;
            return leftHeight - rightHeight;
        }

        public void updateHeight() {
            int leftHeight = left == null ? 0 : ((AVLNode)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode)right).height;
            height = 1 + Math.max(leftHeight, rightHeight);
        }

        public Node<E> tallerChild() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            if (leftHeight > rightHeight) {
                return left;
            } else if (leftHeight < rightHeight) {
                return right;
            }
            return isLeftChild() ? left : right;
        }
    }
}
