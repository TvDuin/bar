package entitylayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thomas on 29-4-2015.
 */

//test

public class Order {
    private int id;
    private int table_ID;
    private Map<Item, Integer> items;
    private int status; //3 = geplaatst

    public Order(int id, int table_ID, Map<Item, Integer> items, int status) {
        items = new HashMap<Item, Integer>();
        this.id = id;
        this.table_ID = table_ID;
        this.items = items;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getTable_ID() {
        return table_ID;
    }

    public Map getItems() {
        return items;
    }

    public int getStatus() {
        return status;
    }
}