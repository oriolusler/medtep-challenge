package domain;

import java.util.List;

public class Order {
    private Product product;
    private List<Coin> coins;

    public Order(Product product, List<Coin> coins) {
        this.product = product;
        this.coins = coins;
    }

    public Product getProduct() {
        return product;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public double calculateMoneyToReturn() {
        return totalMoneyIntroduced() - product.price;
    }

    private double totalMoneyIntroduced() {
        return coins.stream().mapToDouble(coin -> coin.value).sum();
    }
}
