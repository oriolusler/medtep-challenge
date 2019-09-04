package domain;

public enum Product {
    COKE(1.50),
    SPRITE(1.45),
    WATER(0.90);

    public final double price;

    Product(double price) {
        this.price = price;
    }
}
