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
    public void coinsCorrectlyAdd() {

        vendingMachineController.refillCoins(initialCoins);

        assertEquals(vendingMachineController.coinRemaining(FIVE_CNT), 6);
        assertEquals(vendingMachineController.coinRemaining(TEN_CNT), 5);
        assertEquals(vendingMachineController.coinRemaining(TWENTY_CNT), 4);
        assertEquals(vendingMachineController.coinRemaining(FIFTY_CNT), 3);
        assertEquals(vendingMachineController.coinRemaining(EURO), 2);
        assertEquals(vendingMachineController.coinRemaining(TWO_EURO), 1);
    }

    @Test
    public void productsCorrectlyAdd() {
        vendingMachineController.refillProducts(initialProducts);

        assertEquals(vendingMachineController.productRemaining(COKE), 3);
        assertEquals(vendingMachineController.productRemaining(SPRITE), 2);
        assertEquals(vendingMachineController.productRemaining(WATER), 1);
    }
}