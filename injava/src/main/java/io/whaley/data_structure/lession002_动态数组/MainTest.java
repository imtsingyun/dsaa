package io.whaley.data_structure.lession002_动态数组;

public class MainTest {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("abc");

        ArrayList<Integer> intList = new ArrayList<>();
        intList.add(1222);

        ArrayList<Student> studentArrayList = new ArrayList<>();
        studentArrayList.add(new Student("Tom", 19));

        System.out.println(studentArrayList);

    }

    static class Student {
        private String name;
        private Integer age;

        public Student(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
