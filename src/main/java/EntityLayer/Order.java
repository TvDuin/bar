package EntityLayer;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Thomas on 29-4-2015.
 */
public class Order {
    private int id;
    private int tableId;
    private Date time;
    private String item;
    private int status;

    public Order(int id, int tableId, Date time, String item, int status) {
        this.id = id;
        this.tableId = tableId;
        this.time = time;
        this.item = item;
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

    public String getItem() {
        return item;
    }

    public int getStatus() {
        return status;
    }
}