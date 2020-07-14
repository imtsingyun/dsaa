package io.whaley.lession011_hash_table;

import io.whaley.lession010_map.Map;

public class HashMap<K, V> implements Map<K, V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size;
    private Node<K, V>[] table;

    private static final int DEFAULT_CAPACITY = 1<<4;

    public HashMap() {
        // 初始妆组 length 为 16
        table = new Node[DEFAULT_CAPACITY];
    }

    public HashMap(int size) throws IllegalAccessException {
        if (size < 0) {
            throw new IllegalAccessException("初始容量不能小于0");
        }
        table = new Node[size];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        if (size == 0) return;
        size = 0;
        for (int i=0; i<table.length; i++) {
            table[i] = null;
        }
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    private int index(K key) {
        if (key == null) return 0;
        // 获取 hashCode
        int hash = hash(key);
        return hash & (table.length - 1);
    }

    private int hash(K key) {
        int hash = key.hashCode();
        // hash >>> 16 表示 无符号右移 16 位
        // ^ 异或运算
        // 高 16 位和 低 16 位运算
        // 因为 hashCode 是可以用户自定义实现，多一次运算可以保证计算出的索引更加的分散
        hash = hash ^ (hash >>> 16);
        return hash;
    }

    @Override
    public Object get(Object key) {
        return null;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public void traversal(Visitor visitor) {

    }


    /************************************************************************************************
     * 红黑树节点 Begin
     ************************************************************************************************/
    protected static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;
        boolean color = RED;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
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

        public Node<K, V> sibling() {
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
            return "key=" + key + ", value=" + value;
        }
    }
    /* ***********************************************************************************************
     * 红黑树节点 End
     *  ***********************************************************************************************/

}
