package org.lsp;

public class Client {

    public static void main(String[] args) {
        Solider sanMao = new Solider();
        sanMao.setGun(new Rifle());
        sanMao.killEnemy();
    }

}
