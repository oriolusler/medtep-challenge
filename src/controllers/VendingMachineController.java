package controllers;

import domain.Coin;
import domain.Product;
import domain.VendingMachine;

import java.util.List;

public class VendingMachineController implements IVendingMachine {
    private VendingMachine vendingMachine;

    public VendingMachineController() {
        this.vendingMachine = new VendingMachine();
    }

    public double getCurrentMoney() {
        return this.vendingMachine.getTotalCurrentMoney();
    }

    public int productRemaining(Product product) {
        return this.vendingMachine.productItemRemaining(product);
    }

    public int coinRemaining(Coin coin) {
        return this.vendingMachine.coinItemRemaining(coin);
    }

//    @Override
//    public void buyProduct(Product product, List<Coin> coins) throws Exception {
//        Order newOrder = new Order(product, coins);
//
//        if (validOrder(newOrder)) {
//            this.vendingMachine.getProductInventory().removeSupply(product, 1);
//            coins.forEach(coin -> this.vendingMachine.getCoinInventory().addSupply(coin, 1));
//        }
//
//    }

//    private boolean validOrder(Order order) throws Exception {
//        double money = order.getCoins().stream().mapToDouble(coin -> coin.value).sum();
//
//        if (money < order.getProduct().price)
//            throw new Exception("No enough money");
//
//        return true;
//    }

    @Override
    public void refillProducts(List<Product> newProducts) {
        this.vendingMachine.addProducts(newProducts);
    }

    @Override
    public void refillCoins(List<Coin> newCoins) {
        this.vendingMachine.addCoins(newCoins);
    }

    public void insertCoin(Coin coin) {
        this.vendingMachine.insertCoin(coin);
    }
}
