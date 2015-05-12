package EntityLayer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Thomas on 9-5-2015.
 */
public class Receipt {
    private HashMap<Item, Integer> items;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date date;
    String dateRepresentation;
    private int serverId;

    public Receipt(){
        items = new HashMap<Item, Integer>();
        date = new Date();
        dateRepresentation = dateFormat.format(date);
    }

    public HashMap returnItems(){
        return items;
    }

    public String getDateRepresentation() {
        return dateRepresentation;
    }

    public int getServerId() {
        return serverId;
    }

    public void addOrder(Order order) {
        HashMap<Item, Integer> testmap = order.getItems();
        for(Map.Entry<Item, Integer> entry : testmap.entrySet()) {
            if (items.containsKey(entry.getKey())) {
                items.put(entry.getKey(), entry.getValue() + 1);
            }

            else if(!items.containsKey(entry.getKey())) {
                items.put(entry.getKey(), 1);
            }
        }
    }

    public double getTotalPrice() {
        int totalPrice = 0;

        for(Map.Entry<Item, Integer> entry : items.entrySet()) {
            totalPrice += (entry.getKey().getPrice() * entry.getValue());
        }

        return totalPrice;
    }
}
