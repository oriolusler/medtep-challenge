package domain;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Product product;
    private List<Coin> coins;
    private List<Coin> exchange;

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

    public void setExchange(List<Coin> newExchange) {
        if (newExchange != null)
            this.exchange = new ArrayList<>(newExchange);
    }

    public List<Coin> getExchange() {
        return this.exchange;
    }
}
