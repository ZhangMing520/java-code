package org.ocp;

public class OffNovelBook extends NovelBook {

    public OffNovelBook(String name, int price, String author) {
        super(name, price, author);
    }

    @Override
    public int getPrice() {
        int selfPrice = super.getPrice();
        if (selfPrice > 4000) {
            // 原价大于40元，打9折
            return selfPrice * 90 / 100;
        } else {
            return selfPrice * 80 / 100;
        }
    }

}
