package controllers;

import domain.Coin;
import domain.Product;

import java.util.List;

interface IVendingMachine {


//    void buyProduct(Product product, List<Coin> coins) throws Exception;
//
//    List<Coin> refund();

    void refillProducts(List<Product> newProducts);

    void refillCoins(List<Coin> newCoins);


}
