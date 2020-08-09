package org.mindidea.data_structure.util;

/**
 * 线性表接口
 * @param <E>
 */
public interface List<E> {

    static final int ELEMENT_NOT_FOUND = -1;

    /**
     * 清空
     */
    void clear();

    /**
     * 元素的数量
     */
    int size();

    /**
     * 判断是否为空
     */
    boolean isEmpty();

    /**
     * 判断是否包含某个元素
     * @param ele 元素
     * @return Boolean
     */
    boolean contains(E ele);

    /**
     * 获取某个元素
     * @param index 下标
     * @return 元素
     */
    E get(int index);

    /**
     * 添加元素
     * @param ele 元素
     */
    void add(E ele);

    /**
     * 设置index位置处的元素
     * @param index 位置
     * @param ele 元素
     * @return 原来的元素
     */
    E set(int index, E ele);

    /**
     * 在某个位置处插入一个元素
     * @param index 位置
     * @param ele 元素
     */
    void insert(int index, E ele);

    /**
     * 删除 index 位置处的元素
     * @param index 小标
     * @return 元素
     */
    E remove(int index);

    /**
     * 查看元素的下标
     * @param ele 元素
     * @return 下标
     */
    int indexOf(E ele);
}
