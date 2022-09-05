package org.lod.wizard.bad;

import java.util.Random;

public class Wizard {

    private Random random = new Random(System.currentTimeMillis());

    public int first() {
        System.out.println("执行第一个方法..");
        return random.nextInt(100);
    }

    public int second() {
        System.out.println("执行第二个方法..");
        return random.nextInt(100);
    }

    public int third() {
        System.out.println("执行第三个方法..");
        return random.nextInt(100);
    }


}
