package org.lsp;

public class Solider {

    private AbstractGun gun;

    public void setGun(AbstractGun gun) {
        this.gun = gun;
    }

    public void killEnemy() {
        System.out.println("士兵开始杀敌...");
        gun.shoot();
    }

}
