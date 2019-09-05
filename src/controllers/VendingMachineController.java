package controllers;

import domain.Coin;
import domain.Order;
import domain.Product;
import domain.VendingMachine;
import exceptions.NoEnoughMoneyException;
import exceptions.NoEnoughProductsException;

import java.util.ArrayList;
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

    public void buyProduct(Product product) throws NoEnoughMoneyException, NoEnoughProductsException {
        Order newOrder = new Order(product, this.vendingMachine.getCurrentMoney());

        validateOrder(newOrder);
        removeProduct(product);
        transferCurrentMoneyToInventory();
    }

    private void removeProduct(Product product) {
        this.vendingMachine.removeProduct(product);
    }

    private void transferCurrentMoneyToInventory() {
        this.vendingMachine.transferCurrentMoneyToInventory();
    }

    private boolean validateOrder(Order order) throws NoEnoughMoneyException, NoEnoughProductsException {
        double money = order.getCoins().stream().mapToDouble(coin -> coin.value).sum();

        if (money < order.getProduct().price)
            throw new NoEnoughMoneyException("No enough money. Introduce more");

        if (productRemaining(order.getProduct()) < 1) {
            throw new NoEnoughProductsException("No product remaining");
        }

        return true;
    }

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

    public void clearInventory() {
        this.vendingMachine.clearProducts();
    }

    public void clearCurrentMoney() {
        this.vendingMachine.resetCurrentMoney();
    }

    public List<Coin> cancelOrder() {
        List<Coin> coinsToReturn = new ArrayList<>(this.vendingMachine.getCurrentMoney());
        this.vendingMachine.resetCurrentMoney();
        return coinsToReturn;
    }
}
