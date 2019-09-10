package domain;

import java.util.*;

class Inventory<E> {
    private Map<E, Integer> items;

    Inventory() {
        this.items = new HashMap<>();
    }

    int getItemQuantity(E item) {
        return this.items.get(item);
    }

    private void addSupply(E item) {
        if (itemAlreadyExists(item)) {
            this.items.computeIfPresent(item, (e, quantitySaved) -> quantitySaved + 1);
        } else {
            this.items.putIfAbsent(item, 1);
        }
    }

    private boolean itemAlreadyExists(E itemToCheck) {
        return this.items.containsKey(itemToCheck);
    }

    void addSupplies(List<E> newProducts) {
        newProducts.forEach(this::addSupply);
    }

    void removeSupply(E item) {
        this.items.computeIfPresent(item, (e, quantitySaved) -> quantitySaved - 1);
    }

    void clearInventory() {
        this.items.keySet().forEach(item -> this.items.replace(item, 0));
    }

    Map<E, Integer> getItems() {
        return this.items;
    }

    void update(Map<E, Integer> copy) {
        this.items = copy;
    }
}
