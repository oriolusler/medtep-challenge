package controllers;

import domain.Coin;
import domain.Product;

import java.util.List;
import java.util.Set;

interface IVendingMachine {

    Set<Product> showProducts();

    double consultPrice(Product product);

    void buyProduct(Product product, List<Coin> coins) throws Exception;

    List<Coin> refund();


}
