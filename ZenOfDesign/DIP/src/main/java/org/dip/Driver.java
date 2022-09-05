package org.dip;

/**
 * @author zhangming
 */
public class Driver implements IDriver {

    private ICar car;

    public void setCar(ICar car) {
        this.car = car;
    }

    public void drive() {
        car.run();
    }
}
