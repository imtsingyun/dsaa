package io.whaley.lession007_AVL树;

import io.whaley.lession006_二叉树.tree01_二叉搜索树.BST;

public class Test {

    public static void main(String[] args) {
        BST<Integer> bst = new AVLTree<>();
        bst.add(1);
        bst.add(10);
        bst.add(7);
        bst.add(20);
        bst.add(8);
        bst.add(18);
        bst.add(11);
        bst.add(17);
        bst.add(13);
        bst.add(5);
        bst.add(9);
        bst.add(19);
        bst.add(6);
        bst.add(16);
        bst.add(3);
        bst.add(4);
        bst.add(14);
        bst.add(12);
        bst.add(15);
        bst.add(2);
        bst.add(0);
        bst.add(-10);
        bst.add(-12);


        System.out.println(bst);

    }
}
