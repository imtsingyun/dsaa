package org.mindidea._001_linkedlist;

/**
 * 反转二叉树
 * https://leetcode.com/problems/invert-binary-tree/
 * 226. Invert Binary Tree
 * 交换所有的左右节点
 * Example:
 *
 * Input:
 *
 *      4
 *    /   \
 *   2     7
 *  / \   / \
 * 1   3 6   9
 * Output:
 *
 *      4
 *    /   \
 *   7     2
 *  / \   / \
 * 9   6 3   1
 *
 */
public class _226_InvertBinaryTree {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        String str = "\t7\n/\n4";
        System.out.println(str);
//        solution.invertTree();
    }
}

class Solution {
    /**
     * 使用中序遍历
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        preorderTraversal(root);

        return root;
    }

    private void preorderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.left = right;
        root.right = left;
        preorderTraversal(left);
        preorderTraversal(right);
    }

    public static class TreeNode {
        private int val;
        private TreeNode left;
        private TreeNode right;
        public TreeNode() {

        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}