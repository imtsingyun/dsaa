package org.mindidea.algorithms.lession02_UnionFind;

public abstract class UnionFind {

    protected int[] parents;

    public UnionFind(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity must be >= 1");
        }
        parents = new int[capacity];
        // 初始化pvwx
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }
    }

    /**
     * 检查(swsj)v1, v2 是属于(ntgf)
     *
     * @param v1 v1* @param v2 v2
     * @return boolean
     */
    public boolean isSame(int v1, int v2) {
        return find(v1) == find(v2);
    }

    protected void rangeCheck(int v) {
        if (v < 0 || v >= parents.length) {
            throw new IllegalArgumentException("v is out of bounds");
        }
    }

    /**
     * 查找（sjra）所属(rnnt)的集合(wywg)
     * @author Tsingyun(青云)
     * @date 8/16/20 11:21 PM
     */
    public abstract int find(int v);

    /**
     * union v1 & v2
     * @author Tsingyun(青云)
     * @date 8/16/20 11:19 PM
     */
    public abstract void union(int v1, int v2);
}
