package io.whaley.algorithms;

public class Asserts {
    public static void test(boolean value) {
        try {
            if (!value) throw new Exception("测试不通过");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
