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

    public Order(int id, int tableId, Date time, String item) {
        this.id = id;
        this.tableId = tableId;
        this.time = time;
        this.item = item;
    }
}