package com.example.jvm;

/**
 * @author zhangming
 * @date 2019/3/10 23:30
 * <p>
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 * <p>
 * 进入老年代年龄参数设置
 */
public class TenuringThreshold {

    private static final int _1MB = 1024 * 1024;

    public static void testTenuringThreshold() {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];

        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }


    public static void main(String[] args) {
        testTenuringThreshold();
    }
}
