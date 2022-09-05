package org.dip;

/**
 * @author zhangming
 */
public class Client {
    public static void main(String[] args) {
        IDriver zhangSan = new Driver();
        ICar benz = new Benz();
        ICar bmw = new BMW();
        zhangSan.setCar(benz);
        zhangSan.drive();
        zhangSan.setCar(bmw);
        zhangSan.drive();
    }
}
