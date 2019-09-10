package test;

import static domain.Coin.*;
import static domain.Product.*;
import static org.junit.jupiter.api.Assertions.*;

import controllers.VendingMachineController;
import domain.Coin;
import domain.Order;
import domain.Product;
import exceptions.NoEnoughExchange;
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

        assertEquals(vendingMachineController.numberOfSpecificCoinRemaining(FIVE_CNT), 6);
        assertEquals(vendingMachineController.numberOfSpecificCoinRemaining(TEN_CNT), 5);
        assertEquals(vendingMachineController.numberOfSpecificCoinRemaining(TWENTY_CNT), 4);
        assertEquals(vendingMachineController.numberOfSpecificCoinRemaining(FIFTY_CNT), 3);
        assertEquals(vendingMachineController.numberOfSpecificCoinRemaining(EURO), 2);
        assertEquals(vendingMachineController.numberOfSpecificCoinRemaining(TWO_EURO), 1);
    }

    @Test
    public void addProducts() {
        vendingMachineController.refillProducts(initialProducts);

        assertEquals(vendingMachineController.numberOfSpecificProductRemaining(COKE), 3);
        assertEquals(vendingMachineController.numberOfSpecificProductRemaining(SPRITE), 2);
        assertEquals(vendingMachineController.numberOfSpecificProductRemaining(WATER), 1);
    }

    @Test
    public void insertCoins() {
        vendingMachineController.insertCoin(EURO);
        vendingMachineController.insertCoin(FIFTY_CNT);
        vendingMachineController.insertCoin(TWENTY_CNT);

        assertEquals(vendingMachineController.howMuchMoneyHasUserIntroduced(), 1.70);
    }

    @Test
    public void buyProduct() throws Exception {
        addCoins();
        addProducts();

        vendingMachineController.insertCoin(FIFTY_CNT);
        vendingMachineController.insertCoin(TWENTY_CNT);
        vendingMachineController.insertCoin(TWENTY_CNT);

        Order newOrder = vendingMachineController.buyProduct(WATER);

        Product productBought = newOrder.getProduct();
        List<Coin> exchange = newOrder.getExchange();

        assertEquals(productBought, WATER);
        assertNull(exchange);

        assertEquals(vendingMachineController.numberOfSpecificProductRemaining(COKE), 3);
        assertEquals(vendingMachineController.numberOfSpecificProductRemaining(SPRITE), 2);
        assertEquals(vendingMachineController.numberOfSpecificProductRemaining(WATER), 0);
        assertEquals(vendingMachineController.howMuchMoneyHasUserIntroduced(), 0);
    }

    @Test
    public void buyProductHighPrice() throws Exception {
        addCoins();
        addProducts();
        insertCoins();

        vendingMachineController.insertCoin(EURO);
        vendingMachineController.insertCoin(EURO);
        vendingMachineController.insertCoin(FIFTY_CNT);
        vendingMachineController.insertCoin(TWENTY_CNT);
        vendingMachineController.insertCoin(TWENTY_CNT);
        vendingMachineController.insertCoin(TEN_CNT);
        vendingMachineController.insertCoin(FIVE_CNT);

        Order newOrder = vendingMachineController.buyProduct(WATER);

        Product productBought = newOrder.getProduct();

        assertEquals(productBought, WATER);

        assertEquals(vendingMachineController.numberOfSpecificProductRemaining(COKE), 3);
        assertEquals(vendingMachineController.numberOfSpecificProductRemaining(SPRITE), 2);
        assertEquals(vendingMachineController.numberOfSpecificProductRemaining(WATER), 0);
        assertEquals(vendingMachineController.howMuchMoneyHasUserIntroduced(), 0);
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

        vendingMachineController.clearProductsInventory();

        Exception exception = assertThrows(NoEnoughProductsException.class, () -> vendingMachineController.buyProduct(COKE));
        assertEquals(exception.getMessage(), "No product remaining");
    }

    @Test
    public void getChange() throws NoEnoughExchange, NoEnoughProductsException, NoEnoughMoneyException {
        addCoins();
        addProducts();

        vendingMachineController.insertCoin(TWO_EURO);

        Order newOrder = vendingMachineController.buyProduct(COKE);

        List<Coin> exchange = newOrder.getExchange();

        double exchangeExpected = TWO_EURO.value - COKE.price;

        double exchangeReceived = exchange.stream().mapToDouble(coin -> coin.value).sum();
        assertEquals(exchangeExpected, exchangeReceived);
    }

    @Test
    public void buyProductWithoutEnoughChange() {
        addCoins();
        addProducts();
        insertCoins();

        vendingMachineController.clearCoinsInventory();

        Exception exception = assertThrows(NoEnoughExchange.class, () -> vendingMachineController.buyProduct(COKE));
        assertEquals(exception.getMessage(), "Not sufficient change");
    }

    @Test
    public void cancelOrder() {
        addCoins();
        addProducts();
        insertCoins();

        List<Coin> refund = vendingMachineController.cancelOrder();

        assertEquals(vendingMachineController.howMuchMoneyHasUserIntroduced(), 0.0);

        assertTrue(refund.contains(EURO));
        assertTrue(refund.contains(FIFTY_CNT));
        assertTrue(refund.contains(TWENTY_CNT));
    }

    @Test
    public void resetVendingMachine() {
        addCoins();
        addProducts();
        insertCoins();

        vendingMachineController.resetVendingMachine();

        assertEquals(vendingMachineController.numberOfSpecificCoinRemaining(FIVE_CNT), 0);
        assertEquals(vendingMachineController.numberOfSpecificCoinRemaining(TEN_CNT), 0);
        assertEquals(vendingMachineController.numberOfSpecificCoinRemaining(TWENTY_CNT), 0);
        assertEquals(vendingMachineController.numberOfSpecificCoinRemaining(FIFTY_CNT), 0);
        assertEquals(vendingMachineController.numberOfSpecificCoinRemaining(EURO), 0);
        assertEquals(vendingMachineController.numberOfSpecificCoinRemaining(TWO_EURO), 0);

        assertEquals(vendingMachineController.numberOfSpecificProductRemaining(COKE), 0);
        assertEquals(vendingMachineController.numberOfSpecificProductRemaining(SPRITE), 0);
        assertEquals(vendingMachineController.numberOfSpecificProductRemaining(WATER), 0);

        assertEquals(vendingMachineController.howMuchMoneyHasUserIntroduced(), 0);
    }


}