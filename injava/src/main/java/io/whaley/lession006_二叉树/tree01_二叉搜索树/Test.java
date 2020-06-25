package io.whaley.lession006_二叉树.tree01_二叉搜索树;

public class Test {

    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        bst.add(10);
        bst.add(20);
        bst.add(1);
        bst.add(8);
        bst.add(18);
        bst.add(11);
        bst.add(13);
        bst.add(5);
        bst.add(9);
        bst.add(6);
        bst.add(3);
        bst.add(4);
        bst.add(2);
        bst.add(12);
        bst.add(15);

        BST<Car> carBST = new BST<>();
        carBST.add(new Car("BMW", 122));
        carBST.add(new Car("DZ", 123));
        carBST.add(new Car("JL", 98));
        carBST.add(new Car("BC", 144));
        carBST.add(new Car("HQ", 88));

        System.out.println(carBST);
    }
}
