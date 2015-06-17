package entitylayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thomas on 29-4-2015.
 */

//test

public class Order {
    private int id;
    private int tableID;
    private Map<Item, Integer> items;
    private Item item;
    private int quantity;
    private int status; // Geplaatst = 1, geaccepteerd = 2, gereed = 3, uitgeserveerd = 4

    public Order(int id, int tableID, Item item, int quantity ,int status) {
        this.id = id;
        this.tableID = tableID;
        this.item = item;
        this.quantity = quantity;
        //this.items = items;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getTableID() {
        return tableID;
    }

    public Item getItem(){ return item; }

    public int getQuantity(){ return quantity; }

    public Map getItems() {
        return items;
    }

    public int getStatus() {
        return status;
    }

    public void setServed() {
        status = 4;
    }
}