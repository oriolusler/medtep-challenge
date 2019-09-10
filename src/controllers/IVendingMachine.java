package controllers;

import domain.Coin;
import domain.Order;
import domain.Product;
import exceptions.NoEnoughExchange;
import exceptions.NoEnoughMoneyException;
import exceptions.NoEnoughProductsException;

import java.util.List;

interface IVendingMachine {

    void refillProducts(List<Product> newProducts);

    void refillCoins(List<Coin> newCoins);

    List<Coin> cancelOrder();

    Order buyProduct(Product product) throws NoEnoughExchange, NoEnoughMoneyException, NoEnoughProductsException;

    void insertCoin(Coin coin);

    void clearProductsInventory();

    void clearCoinsInventory();

    void clearCurrentMoney();

    void resetVendingMachine();
}
