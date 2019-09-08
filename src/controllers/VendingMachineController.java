package controllers;

import domain.Coin;
import domain.Order;
import domain.Product;
import domain.VendingMachine;
import exceptions.NoEnoughExchange;
import exceptions.NoEnoughMoneyException;
import exceptions.NoEnoughProductsException;

import java.util.*;

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

    public Order buyProduct(Product product) throws NoEnoughExchange, NoEnoughMoneyException, NoEnoughProductsException {
        Order newOrder = new Order(product, new ArrayList(this.vendingMachine.getCurrentMoney()));

        validateOrder(newOrder);

        removeProduct(product);
        transferCurrentMoneyToInventory();
        newOrder.setExchange(getChange(newOrder));


        return newOrder;
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

    public void clearProductsInventory() {
        this.vendingMachine.clearProducts();
    }

    public void clearCoinsInventory() {
        this.vendingMachine.clearCoins();
    }

    public void clearCurrentMoney() {
        this.vendingMachine.resetCurrentMoney();
    }

    public List<Coin> cancelOrder() {
        List<Coin> coinsToReturn = new ArrayList<>(this.vendingMachine.getCurrentMoney());
        this.vendingMachine.resetCurrentMoney();
        return coinsToReturn;
    }

    public List<Coin> getChange(Order order) throws NoEnoughExchange {

        double moneyToReturn = order.calculateMoneyToReturn();
        double change = Math.round(moneyToReturn * 100.0) / 100.0;

        Map<Coin, Integer> copy = new HashMap<>(vendingMachine.getCoins());

        if (change > 0) {
            List<Coin> changes = new ArrayList<>();

            double changeRemaining = change;

            while (changeRemaining > 0) {
                changeRemaining = Math.round(changeRemaining * 100.0) / 100.0;

                if (changeRemaining >= TWO_EURO.value && coinRemain(copy, TWO_EURO)) {
                    changes.add(TWO_EURO);
                    changeRemaining = changeRemaining - TWO_EURO.value;
                    copy.computeIfPresent(TWO_EURO, (coin, integer) -> integer - 1);

                } else if (changeRemaining >= EURO.value && coinRemain(copy, EURO)) {
                    changes.add(EURO);
                    changeRemaining = changeRemaining - EURO.value;
                    copy.computeIfPresent(EURO, (coin, integer) -> integer - 1);

                } else if (changeRemaining >= FIFTY_CNT.value && coinRemain(copy, FIFTY_CNT)) {
                    changes.add(FIFTY_CNT);
                    changeRemaining = changeRemaining - FIFTY_CNT.value;
                    copy.computeIfPresent(FIFTY_CNT, (coin, integer) -> integer - 1);

                } else if (changeRemaining >= TWENTY_CNT.value && coinRemain(copy, TWENTY_CNT)) {
                    changes.add(TWENTY_CNT);
                    changeRemaining = changeRemaining - TWENTY_CNT.value;
                    copy.computeIfPresent(TWENTY_CNT, (coin, integer) -> integer - 1);

                } else if (changeRemaining >= TEN_CNT.value && coinRemain(copy, TEN_CNT)) {
                    changes.add(TEN_CNT);
                    changeRemaining = changeRemaining - TEN_CNT.value;
                    copy.computeIfPresent(TEN_CNT, (coin, integer) -> integer - 1);

                } else if (changeRemaining >= FIVE_CNT.value && coinRemain(copy, FIFTY_CNT)) {
                    changes.add(FIVE_CNT);
                    changeRemaining = changeRemaining - FIVE_CNT.value;
                    copy.computeIfPresent(FIVE_CNT, (coin, integer) -> integer - 1);
                } else {
                    throw new NoEnoughExchange("Not Sufficient Change, Please try another product");
                }
            }

            this.vendingMachine.updateCoinsInventory(copy);

            return changes;
        }
        return null;
    }

    private boolean coinRemain(Map<Coin, Integer> map, Coin coin) {
        return map.get(coin) > 0;
    }
}
