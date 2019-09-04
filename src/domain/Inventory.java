package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Inventory<E> {
    private Map<E, Integer> items;

    public Inventory() {
        this.items = new HashMap<E, Integer>();
    }

    public Set<E> getInventoryElements() {
        return this.items.keySet();
    }

    public int getItemQuantity(E item) {
        return this.items.get(item);
    }

    public void addSupply(E item, int newQuantity) {
        this.items.computeIfPresent(item, (e, quantitySaved) -> quantitySaved + newQuantity);
    }

    public void removeSupply(E item, int quantity) {
        this.items.computeIfPresent(item, (e, quantitySaved) -> quantitySaved - quantity);
    }
}
