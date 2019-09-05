package test;

import static domain.Coin.*;
import static domain.Product.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import controllers.VendingMachineController;
import domain.Coin;
import domain.Product;
import domain.VendingMachine;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {

    VendingMachineController vendingMachineController = new VendingMachineController();

    private List<Product> initialProducts = new ArrayList<Product>(Arrays.asList(COKE, SPRITE, WATER, COKE));
    private List<Coin> initialCoins = new ArrayList<Coin>(Arrays.asList(
            FIVE_CNT, TEN_CNT, TWENTY_CNT, FIFTY_CNT, EURO, TWO_EURO,
            FIVE_CNT, TEN_CNT, TWENTY_CNT, FIFTY_CNT, EURO, TWO_EURO,
            FIVE_CNT, TEN_CNT, TWENTY_CNT, FIFTY_CNT, EURO, TWO_EURO,
            FIVE_CNT, TEN_CNT, TWENTY_CNT, FIFTY_CNT, EURO, TWO_EURO,
            FIVE_CNT, TEN_CNT, TWENTY_CNT, FIFTY_CNT, EURO, TWO_EURO));

    @Test
    public void coinsCorrectlyAdd() {

        vendingMachineController.refillCoins(initialCoins);

        assertEquals(vendingMachineController.coinRemaining(FIFTY_CNT), 5);
        assertEquals(vendingMachineController.coinRemaining(TEN_CNT), 5);
        assertEquals(vendingMachineController.coinRemaining(TWENTY_CNT), 5);
        assertEquals(vendingMachineController.coinRemaining(FIFTY_CNT), 5);
        assertEquals(vendingMachineController.coinRemaining(EURO), 5);
        assertEquals(vendingMachineController.coinRemaining(TWO_EURO), 5);
    }

    @Test
    public void productsCorrectlyAdd() {
        vendingMachineController.refillProducts(initialProducts);

        assertEquals(vendingMachineController.productRemaining(COKE), 2);
        assertEquals(vendingMachineController.productRemaining(SPRITE), 1);
        assertEquals(vendingMachineController.productRemaining(WATER), 1);
    }
}