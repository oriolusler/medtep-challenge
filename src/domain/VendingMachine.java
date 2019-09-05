package domain;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    private Inventory<Coin> coinInventory;
    private Inventory<Product> productInventory;
//    private List<Order> orderHistory;
    private double currentMoney;

    public VendingMachine() {
        this.coinInventory = new Inventory<Coin>();
        this.productInventory = new Inventory<Product>();
//        this.orderHistory = new ArrayList<Order>();
        resetCurrentMoney();
    }

//    public double getCurrentMoney() {
//        return this.currentMoney;
//    }

    public int productItemRemaining(Product item) {
        return this.productInventory.getItemQuantity(item);
    }

    public int coinItemRemaining(Coin item) {
        return this.coinInventory.getItemQuantity(item);
    }

//    public Inventory<Coin> getCoinInventory() {
//        return coinInventory;
//    }
//
//    public Inventory<Product> getProductInventory() {
//        return productInventory;
//    }

    public void resetCurrentMoney() {
        this.currentMoney = 0;
    }

    public void addProducts(List<Product> newProducts) {
        this.productInventory.addSupplies(newProducts);
    }

    public void addCoins(List<Coin> newCoins) {
        this.coinInventory.addSupplies(newCoins);
    }
}
