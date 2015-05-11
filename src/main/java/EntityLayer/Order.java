package EntityLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Thomas on 29-4-2015.
 */
public class Order {
    private int id;
    private int tableId;
    private Date time;
    private HashMap<Item, Integer> items;
    private int status;

    public Order(int id, int tableId, Date time, HashMap<Item, Integer> items, int status) {
        this.id = id;
        this.tableId = tableId;
        this.time = time;
        this.items = items;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getTableId() {
        return tableId;
    }

    public Date getTime() {
        return time;
    }

    public HashMap getItems() {
        return items;
    }

    public int getStatus() {
        return status;
    }
}