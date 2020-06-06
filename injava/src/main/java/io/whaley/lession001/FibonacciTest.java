package io.whaley.lession001;

/**
 * Fibonacci 算法测试
 */
public class FibonacciTest {
    
    /**
     *
     * 0 1 1 2 3 5 8 13 21 34 55 89
     * @param args
     */
    public static void main(String[] args) {
        int n = 43;
        long start = System.currentTimeMillis();
        int result = fib1(n);
        System.out.println("运行时间：" + (System.currentTimeMillis() - start) + ", " + result);

        start = System.currentTimeMillis();
        result = fib2(n);
        System.out.println("运行时间：" + (System.currentTimeMillis() - start) + ", " + result);
    }

    /**
     * 方法一：递归
     * n = 50, 运行时间：43340 ms
     * @param n
     * @return
     */
    public static int fib1(int n) {
        if (n <= 1) {
            return n;
        }
        return fib1(n-1) + fib1(n-2);
    }

    /**
     * 方法二
     * 0 1 2 3 4 5 6 7  8  9  10 11
     * 0t 1 2 3 4                      计算的次数
     * 0 1 1 2 3 5 8 13 21 34 55 89
     * 
     */
    public static int fib2(int n) {
        if (n <= 1) {
            return n;
        }
        int first = 0;
        int second = 1;
        for (int i = 0; i < n-1; i++) {
            int sum = first + second;
            first = second;
            second = sum;
        }
        return second;
    }

}