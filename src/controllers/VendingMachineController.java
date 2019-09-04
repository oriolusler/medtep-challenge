package controllers;

import domain.Coin;
import domain.Order;
import domain.Product;
import domain.VendingMachine;

import java.util.List;
import java.util.Set;

public class VendingMachineController implements IVendingMachine {
    private VendingMachine vendingMachine;

    public VendingMachineController() {
        this.vendingMachine = new VendingMachine();
    }

    @Override
    public Set<Product> showProducts() {
        return this.vendingMachine.getProductInventory().getInventoryElements();
    }

    @Override
    public double consultPrice(Product product) {
        return this.vendingMachine.getProductInventory().getItemQuantity(product);
    }

    @Override
    public void buyProduct(Product product, List<Coin> coins) throws Exception {
        Order newOrder = new Order(product, coins);

        if (validOrder(newOrder)) {
            this.vendingMachine.getProductInventory().removeSupply(product, 1);
            coins.forEach(coin -> this.vendingMachine.getCoinInventory().addSupply(coin, 1));
        }

    }

    private boolean validOrder(Order order) throws Exception {
        double money = order.getCoins().stream().mapToDouble(coin -> coin.value).sum();

        if (money < order.getProduct().price)
            throw new Exception("No enough money");

        return true;
    }

    @Override
    public List<Coin> refund() {
        return null;
    }
}
