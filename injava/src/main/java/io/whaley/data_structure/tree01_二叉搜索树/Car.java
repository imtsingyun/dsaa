package io.whaley.data_structure.tree01_二叉搜索树;

public class Car implements Comparable<Car> {

    private String brand;

    private int money;

    public Car(String brand, int money) {
        this.brand = brand;
        this.money = money;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public int compareTo(Car o) {
        return this.money - o.money;
    }

    @Override
    public String toString() {
        return brand + ':' + money;
    }
}
