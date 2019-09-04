package domain;

public enum Coin {
    FIVE_CNT(0.05),
    TEN_CNT(0.10),
    TWENTY_CNT(0.20),
    FIFTY_CNT(0.50),
    EURO(1),
    TWO_EURO(2);


    public final double value;

    Coin(double value) {
        this.value = value;
    }
}
