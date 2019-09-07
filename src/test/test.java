package test;

import static domain.Coin.*;
import static domain.Product.*;
import static org.junit.jupiter.api.Assertions.*;

import controllers.VendingMachineController;
import domain.Coin;
import domain.Product;
import exceptions.NoEnoughMoneyException;
import exceptions.NoEnoughProductsException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {

    private VendingMachineController vendingMachineController = new VendingMachineController();

    private List<Product> initialProducts = new ArrayList<Product>(Arrays.asList(
            COKE, SPRITE, WATER,
            COKE, SPRITE,
            COKE));

    private List<Coin> initialCoins = new ArrayList<Coin>(Arrays.asList(
            FIVE_CNT, TEN_CNT, TWENTY_CNT, FIFTY_CNT, EURO, TWO_EURO,
            FIVE_CNT, TEN_CNT, TWENTY_CNT, FIFTY_CNT, EURO,
            FIVE_CNT, TEN_CNT, TWENTY_CNT, FIFTY_CNT,
            FIVE_CNT, TEN_CNT, TWENTY_CNT,
            FIVE_CNT, TEN_CNT,
            FIVE_CNT));

    @Test
    public void addCoins() {

        vendingMachineController.refillCoins(initialCoins);

        assertEquals(vendingMachineController.coinRemaining(FIVE_CNT), 6);
        assertEquals(vendingMachineController.coinRemaining(TEN_CNT), 5);
        assertEquals(vendingMachineController.coinRemaining(TWENTY_CNT), 4);
        assertEquals(vendingMachineController.coinRemaining(FIFTY_CNT), 3);
        assertEquals(vendingMachineController.coinRemaining(EURO), 2);
        assertEquals(vendingMachineController.coinRemaining(TWO_EURO), 1);
    }

    @Test
    public void addProducts() {
        vendingMachineController.refillProducts(initialProducts);

        assertEquals(vendingMachineController.productRemaining(COKE), 3);
        assertEquals(vendingMachineController.productRemaining(SPRITE), 2);
        assertEquals(vendingMachineController.productRemaining(WATER), 1);
    }

    @Test
    public void insertCoins() {
        vendingMachineController.insertCoin(EURO);
        vendingMachineController.insertCoin(FIFTY_CNT);
        vendingMachineController.insertCoin(TWENTY_CNT);

        assertEquals(vendingMachineController.getCurrentMoney(), 1.70);
    }

    @Test
    public void buyProduct() throws Exception {
        addCoins();
        addProducts();
        insertCoins();

        Product productBought = vendingMachineController.buyProduct(WATER);

        assertEquals(productBought, COKE);

        assertEquals(vendingMachineController.productRemaining(COKE), 2);
        assertEquals(vendingMachineController.getCurrentMoney(), 0);
    }

    @Test
    public void buyProductWithoutEnoughMoney() {
        addCoins();
        addProducts();
        insertCoins();

        vendingMachineController.clearCurrentMoney();

        Exception exception = assertThrows(NoEnoughMoneyException.class, () -> vendingMachineController.buyProduct(COKE));
        assertEquals(exception.getMessage(), "No enough money. Introduce more");
    }

    @Test
    public void buyProductWithoutEnoughProducts() {
        addCoins();
        addProducts();
        insertCoins();

        vendingMachineController.clearInventory();

        Exception exception = assertThrows(NoEnoughProductsException.class, () -> vendingMachineController.buyProduct(COKE));
        assertEquals(exception.getMessage(), "No product remaining");
    }

    @Test
    public void returnMoney() {
        addCoins();
        addProducts();
        insertCoins();

        List<Coin> refund = vendingMachineController.cancelOrder();

        assertEquals(vendingMachineController.getCurrentMoney(), 0.0);

        assertTrue(refund.contains(EURO));
        assertTrue(refund.contains(FIFTY_CNT));
        assertTrue(refund.contains(TWENTY_CNT));
    }
}