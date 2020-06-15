package io.whaley.lession006_二叉树.tree01_二叉搜索树;

public class Car implements Comparable<Car> {

    private String brand;

    private Double money;

    public Car(String brand, Double money) {
        this.brand = brand;
        this.money = money;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public int compareTo(Car o) {
        return 0;
    }
}
