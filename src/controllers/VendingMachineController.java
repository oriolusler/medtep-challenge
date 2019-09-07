package controllers;

import domain.Coin;
import domain.Order;
import domain.Product;
import domain.VendingMachine;
import exceptions.NoEnoughMoneyException;
import exceptions.NoEnoughProductsException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static domain.Coin.*;

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

    public Product buyProduct(Product product) throws Exception {
        Order newOrder = new Order(product, new ArrayList(this.vendingMachine.getCurrentMoney()));

        validateOrder(newOrder);
        removeProduct(product);
        transferCurrentMoneyToInventory();

        double moneyToReturn = newOrder.calculateMoneyToReturn();
        double roundOff = Math.round(moneyToReturn * 100.0) / 100.0;
        List<Coin> coinsToReturn = getChange(roundOff);

        return product;
    }

    private void removeProduct(Product product) {
        this.vendingMachine.removeProduct(product);
    }

    private void transferCurrentMoneyToInventory() {
        this.vendingMachine.transferCurrentMoneyToInventory();
    }

    private void validateOrder(Order order) throws NoEnoughMoneyException, NoEnoughProductsException {
        double money = order.getCoins().stream().mapToDouble(coin -> coin.value).sum();

        if (money < order.getProduct().price)
            throw new NoEnoughMoneyException("No enough money. Introduce more");

        if (productRemaining(order.getProduct()) < 1) {
            throw new NoEnoughProductsException("No product remaining");
        }

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

    public List getChange(double change) throws Exception {

        Map<Coin, Integer> currentsInventoryCoins = vendingMachine.getCoins();

        if (change > 0) {
            List<Coin> changes = new ArrayList<>();
            double changeRemaining = change;
            while (changeRemaining > 0) {
                changeRemaining = Math.round(changeRemaining * 100.0) / 100.0;
                if (changeRemaining >= TWO_EURO.value && coinRemain(TWO_EURO)) {
                    changes.add(TWO_EURO);
                    changeRemaining = changeRemaining - TWO_EURO.value;
                } else if (changeRemaining >= EURO.value && coinRemain(EURO)) {
                    changes.add(EURO);
                    changeRemaining = changeRemaining - EURO.value;
                } else if (changeRemaining >= FIFTY_CNT.value && coinRemain(FIFTY_CNT)) {
                    changes.add(FIFTY_CNT);
                    changeRemaining = changeRemaining - FIFTY_CNT.value;
                } else if (changeRemaining >= TWENTY_CNT.value && coinRemain(TWENTY_CNT)) {
                    changes.add(TWENTY_CNT);
                    changeRemaining = changeRemaining - TWENTY_CNT.value;
                } else if (changeRemaining >= TEN_CNT.value && coinRemain(TEN_CNT)) {
                    changes.add(TEN_CNT);
                    changeRemaining = changeRemaining - TEN_CNT.value;
                } else if (changeRemaining >= FIVE_CNT.value && coinRemain(FIFTY_CNT)) {
                    changes.add(FIVE_CNT);
                    changeRemaining = changeRemaining - FIVE_CNT.value;
                } else {
                    throw new Exception("Not Sufficient Change, Please try another product");
                }
            }
            return changes;
        }
        return null;
    }

    private boolean coinRemain(Coin coin) {
        return this.vendingMachine.coinItemRemaining(coin) > 0;
    }
}
