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
    private int status; // Geplaatst = 1, geaccepteerd = 2, gereed = 3, uitgeserveerd = 4
    private Map<Item, Integer> items;

    public Order(int id, int tableID, int status) {
        this.id = id;
        this.tableID = tableID;
        items = null;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getTableID() {
        return tableID;
    }

    public Map getItems() {
        return items;
    }

    public void addItem(Map<Item, Integer> items) {
        this.items = items;
    }

    public int getStatus() {
        return status;
    }

    public void setServed() {
        status = 4;
    }
}