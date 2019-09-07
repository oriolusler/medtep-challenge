package domain;

import java.util.*;

public class Inventory<E> {
    private Map<E, Integer> items;

    public Inventory() {
        this.items = new HashMap<E, Integer>();
    }

//    public List<E> getInventoryElements() {
//        return new ArrayList<E>(this.items.keySet());
//    }

    public int getItemQuantity(E item) {
        return this.items.get(item);
    }

    public void addSupply(E item, int newQuantity) {
        if (itemAlreadyExists(item)) {
            this.items.computeIfPresent(item, (e, quantitySaved) -> quantitySaved + newQuantity);
        } else {
            this.items.putIfAbsent(item, newQuantity);
        }
    }

    private boolean itemAlreadyExists(E itemToCheck) {
        return this.items.containsKey(itemToCheck);
    }

    public void addSupplies(List<E> newProducts) {
        newProducts.forEach(product -> addSupply(product, 1));
    }

    public void removeSupply(E item, int quantity) {
        this.items.computeIfPresent(item, (e, quantitySaved) -> quantitySaved - quantity);
    }

    public void clearInventory() {
        this.items.keySet().forEach(item -> this.items.replace(item, 0));
    }

    public Map<E, Integer> getItems() {
        return this.items;
    }
}
