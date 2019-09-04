package domain;

public class VendingMachine {
    private Inventory<Coin> coinInventory;
    private Inventory<Product> productInventory;
    private double currentMoney;

    public VendingMachine() {
        this.coinInventory = new Inventory<Coin>();
        this.productInventory = new Inventory<Product>();
        resetCurrentMoney();
    }

    public double getCurrentMoney() {
        return this.currentMoney;
    }

    public Inventory<Coin> getCoinInventory() {
        return coinInventory;
    }

    public Inventory<Product> getProductInventory() {
        return productInventory;
    }

    public void resetCurrentMoney() {
        this.currentMoney = 0;
    }
}
