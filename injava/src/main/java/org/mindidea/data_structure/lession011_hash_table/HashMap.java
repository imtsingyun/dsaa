package org.mindidea.data_structure.lession011_hash_table;

import org.mindidea.data_structure.lession010_map.Map;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class HashMap<K, V> implements Map<K, V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size;
    private Node<K, V>[] table;

    private static final int DEFAULT_CAPACITY = 1 << 4;

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
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
    }

    @Override
    public V put(K key, V value) {
        int index = index(key);
        Node<K, V> root = table[index];
        if (root == null) {
            root = new Node<>(key, value, null);
            table[index] = root;
            size++;
            afterPut(root);
            return null;
        }
        // 将新增元素添加到 红黑树上
        Node<K, V> node = root;
        Node<K, V> parent;
        int compare;
        int h1 = key == null ? 0 : key.hashCode();
        do {
            compare = compare(key, node.key, h1, node.hash);
            parent = node;
            if (compare > 0) {
                node = node.right;
            } else if (compare < 0) {
                node = node.left;
            } else {
                // 相等则替换旧的元素
                node.key = key;
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        } while (node != null);
        Node<K, V> newNode = new Node<>(key, value, parent);
        if (compare > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        afterPut(newNode);
        return null;
    }

    private int compare(K k1, K k2, int h1, int h2) {
        // 先比较 hash 值是否相等
        int result = h1 - h2;
        // hash 值不相等，表示 key 不同
        if (result != 0) return result;

        // hash 值相等且 k1 和 k2 同一个 key，则替换
        if (Objects.equals(k1, k2)) {
            return 0;
        }

        // hash 值相等，k1 和 k2 不是同一个 key
        // 比较类名
        if (k1 != null && k2 != null) {
            String k1Class = k1.getClass().getName();
            String k2Class = k2.getClass().getName();
            result = k1Class.compareTo(k2Class);

            if (result != 0) return result;

            if (k1 instanceof Comparable) {
                return ((Comparable) k1).compareTo(k2);
            }
        }
        return System.identityHashCode(k1) - System.identityHashCode(k2);
    }

    /**
     * 添加元素后处理红黑树的性质
     *
     * @param node 新增的节点
     */
    private void afterPut(Node<K, V> node) {
        if (node == null) return;
        // 1. node 节点是根节点，直接将其染成黑色
        Node<K, V> parent = node.parent;
        if (parent == null) {
            black(node);
            return;
        }
        // 2. 父节点是黑色的无需处理
        if (isBlack(parent)) return;
        // 叔父节点 uncle
        Node<K, V> uncle = parent.sibling();
        // 祖父节点 grandParent
        Node<K, V> grandParent = parent.parent;
        // 3. 父节点是红色的，祖父节点必然是黑色
        // 3.1 叔父节点是红色的: 把父节点和叔父节点染成黑色，祖父节点染成红色
        if (isRed(uncle)) {
            black(parent);
            black(uncle);
            red(grandParent);
            // 将父节点当作新增节点
            afterPut(grandParent);
            return;
        }
        // 3.2 叔父节点是黑色的
        if (parent.isLeftChild()) {
            // 祖父节点染成红色
            red(grandParent);
            // 新增的节点是左子节点
            if (node.isLeftChild()) {
                black(parent);
            } else {
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grandParent);
        } else {
            // 祖父节点染成红色
            red(grandParent);
            // 新增的节点是左子节点
            if (node.isLeftChild()) {
                black(parent);
                rotateRight(parent);
            } else {
                black(node);
            }
            rotateLeft(grandParent);
        }
    }

    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;

        if (grand.isLeft()) {
            grand.parent.left = parent;
        } else if (grand.isRight()) {
            grand.parent.right = parent;
        } else {
            table[index(grand.key)] = parent;
        }
        parent.right = grand;
        grand.left = child;

        parent.parent = grand.parent;
        grand.parent = parent;
        if (child != null) {
            child.parent = grand;
        }
    }

    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;

        if (grand.isLeft()) {
            grand.parent.left = parent;
        } else if (grand.isRight()) {
            grand.parent.right = parent;
        } else {
            table[index(grand.key)] = parent;
        }

        parent.left = grand;
        grand.right = child;

        parent.parent = grand.parent;
        grand.parent = parent;
        if (child != null) {
            child.parent = grand;
        }
    }

    private int index(K key) {
        if (key == null) return 0;
        // 获取 hashCode
        int hash = hash(key);
        // table.length 需要是 2的n次方，所以 (table.length-1) 的二进制全是 1
        // 2^1 - 1 = 1
        // 2^2 - 1 = 11
        // 2^3 - 1 = 111
        // 2^4 - 1 = 1111
        // 和 hash 进行 与运算
        // hash：         1011001110110
        // &（数组长度-1）  0000000001111
        //                0000000000110
        // 计算的结果肯定小于数组长度
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
    public V get(K key) {
        Node<K, V> node = node(key);
        return node == null ? null : node.value;
    }

    private Node<K, V> node(K key) {
        Node<K, V> node = table[index(key)];
        int h1 = key == null ? 0 : key.hashCode();
        while (node != null) {
            int compare = compare(key, node.key, h1, node.hash);
            if (compare == 0) return node;
            if (compare > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    public V remove(Node<K, V> node) {
        if (node == null) return null;
        size--;
        V value = node.value;
        // 度为 2，使用前驱或后继节点替换
        if (node.hasTwoChildren()) {
            Node<K, V> predecessor = predecessor(node);
            node.value = predecessor.value;
            node = predecessor;
        }

        Node<K, V> replacement = node.left != null ? node.left : node.right;
        // 度为 1, 用左子节点或右子节点有值替换，最终删除的是子节点
        if (replacement != null) {
            node.value = replacement.value;
            node = replacement;
        }
        // 度为 0
        // 删除的是惟一的一个根节点
        if (node.parent == null) {
            table[index(node.key)] = null;
            afterRemove(node);
        }
        // 删除的是叶子节点
        else {
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
            afterRemove(node);
            node.parent = null;
        }
        return value;
    }

    /**
     * 删除后维护红黑树的特性
     * @param node 被删除的节点
     */
    private void afterRemove(Node<K, V> node) {
        // 1. 如果 node 是红色的, 直接删除
        if (isRed(node)) {
           return;
        }
        // 2. 删除的是黑色的叶子节点
        // 删除的是根节点
        if (node.parent == null) return;

        // 判断被删除历节点是左还是右
        boolean left = node.parent.left == null;
        Node<K, V> sibling = left ? node.parent.right : node.parent.left;

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
                    afterRemove(node.parent);
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
        } else {
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
                    afterRemove(node.parent);
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

    // 获取前驱节点
    private Node<K, V> predecessor(Node<K, V> node) {
        if (node == null) return null;
        Node<K, V> left = node.left;

        if (left != null) {
            while (left.right!=null) {
                left = left.right;
            }
            return left;
        }

        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }

        return node.parent;
    }

    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;
        Node<K, V> right = node.right;
        if (right != null) {
            while (right.left != null) {
                right = right.left;
            }
            return right;
        }

        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (size == 0) return false;

        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (Objects.equals(value, node.value)) return true;

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
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
        int hash;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;
        boolean color = RED;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.hash = key == null ? 0 : key.hashCode();
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

    /************************************************************************************************
     * 辅助方法 Begin
     ************************************************************************************************/
    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null)
            return null;
        node.color = color;
        return node;
    }

    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        if (node == null) {
            return false;
        }
        return colorOf(node) == RED;
    }

    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }
    /* ***********************************************************************************************
     * 辅助方法 End
     * ***********************************************************************************************/

    public static void main(String[] args) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("tom", 100);
        hashMap.put("jack", 80);
        hashMap.put(null, 50);

        System.out.println(hashMap.get("tom"));
        System.out.println(hashMap.get("jack"));
        System.out.println(hashMap.get(null));
    }
}
