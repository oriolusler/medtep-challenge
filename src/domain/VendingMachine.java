package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VendingMachine {
    private Inventory<Coin> coinInventory;
    private Inventory<Product> productInventory;
    //    private List<Order> orderHistory;
    private List<Coin> currentMoney;

    public VendingMachine() {
        this.coinInventory = new Inventory<Coin>();
        this.productInventory = new Inventory<Product>();
        this.currentMoney = new ArrayList<>();
    }

    public List<Coin> getCurrentMoney() {
        return this.currentMoney;
    }

    public int productItemRemaining(Product item) {
        return this.productInventory.getItemQuantity(item);
    }

    public int coinItemRemaining(Coin item) {
        return this.coinInventory.getItemQuantity(item);
    }

    public Map<Coin, Integer> getCoins() {
        return this.coinInventory.getItems();
    }

//    public Inventory<Coin> getCoinInventory() {
//        return coinInventory;
//    }
//
//    public Inventory<Product> getProductInventory() {
//        return productInventory;
//    }


    public void addProducts(List<Product> newProducts) {
        this.productInventory.addSupplies(newProducts);
    }

    public void addCoins(List<Coin> newCoins) {
        this.coinInventory.addSupplies(newCoins);
    }

    public void insertCoin(Coin coin) {
        this.currentMoney.add(coin);
    }

    public double getTotalCurrentMoney() {
        return this.currentMoney.stream().mapToDouble(coin -> coin.value).sum();
    }

    public void removeProduct(Product product) {
        this.productInventory.removeSupply(product, 1);
    }

    public void transferCurrentMoneyToInventory() {
        this.coinInventory.addSupplies(this.currentMoney);
        resetCurrentMoney();
    }

    public void resetCurrentMoney() {
        this.currentMoney.clear();
    }


    public void updateCoinsInventory(Map<Coin, Integer> copy) {
        this.coinInventory.update(copy);
    }

    public void clearProducts() {
        this.productInventory.clearInventory();
    }

    public void clearCoins() {
        this.coinInventory.clearInventory();
    }
}
