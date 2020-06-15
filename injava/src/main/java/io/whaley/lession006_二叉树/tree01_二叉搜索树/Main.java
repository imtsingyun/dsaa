package io.whaley.lession006_二叉树.tree01_二叉搜索树;

import java.util.Comparator;

public class  Main {


    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        tree.add(7);
        tree.add(4);
        tree.add(9);
        tree.add(2);
        tree.add(5);
        tree.add(8);
        tree.add(11);
        tree.add(1);
        tree.add(3);
        tree.add(10);
        tree.add(12);
//        tree.preorderTraversal();
        System.out.println("--------------------------------");
//        tree.inorderTraversal();
//        tree.postorderTraversal();
        tree.levelOrderTraversal();

        BinarySearchTree<Person> personTree = new BinarySearchTree<>(Comparator.comparingInt(Person::getAge));
        personTree.add(new Person("C", 20));
        personTree.add(new Person("A", 15));
        personTree.add(new Person("B", 10));
        personTree.add(new Person("D", 30));
        personTree.add(new Person("D", 25));
        personTree.add(new Person("D", 35));
        personTree.add(new Person("D", 55));
//        personTree.preorderTraversal();

        BinarySearchTree<Car> carTree = new BinarySearchTree<Car>();
        carTree.add(new Car("A", 100.02));
        carTree.add(new Car("B", 101.02));
    }
}
