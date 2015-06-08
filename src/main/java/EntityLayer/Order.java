package EntityLayer;

import EntityLayer.Item;

import java.util.Date;
import java.util.Map;

/**
 * Created by Thomas on 29-4-2015.
 */
public class Order {
    private int id;
    private int table_ID;
    private Map<Item, Integer> items;
    private int status; //1 = geplaatst, 2 = geaccepteerd, 3 = gereed, 4 = uitgeserveerd & 5 = betaald

    public Order(int id, int table_ID, Map<Item, Integer> items, int status) {
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