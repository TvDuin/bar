package entitylayer;

import logiclayer.OrderManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Thomas on 9-5-2015.
 */
public class Receipt {
    private Map<Item, Integer> items;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date date;
    String dateRepresentation;
    private int serverId;
    private String totalPrice;
    private int tempTotal;

    public Receipt(){
        items = new HashMap<Item, Integer>();
        date = new Date();
        dateRepresentation = dateFormat.format(date);
    }

    public Map returnItems(){
        return items;
    }

    public String getDateRepresentation() {
        return dateRepresentation;
    }

    public int getServerId() {
        return serverId;
    }

    public void addOrder(Order order) {
        Map<Item, Integer> oldMap = order.getItems();

        for(Map.Entry<Item, Integer> oldEntry : oldMap.entrySet()) {
            if(mapContainsKeyWithName(items, oldEntry.getKey().getName())) {
                items.put(oldEntry.getKey(), items.get(retrieveKeyByName(items, oldEntry.getKey().getName())) + oldEntry.getValue());
            }

            else {
                items.put(oldEntry.getKey(), oldEntry.getValue());
            }
        }
    }

    public Item retrieveKeyByName(Map<Item, Integer> map, String name) {
        Item item = null;

        for(Map.Entry<Item, Integer> entry : map.entrySet()) {
            if(entry.getKey().getName().equals(name)) {
                item = entry.getKey();
            }
        }

        return item;
    }

    public boolean mapContainsKeyWithName(Map<Item, Integer> map ,String name) {
        boolean result = false;
        for (Map.Entry<Item, Integer> entry : map.entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                result = true;
            }
        }
        return result;
    }

    public int setTempPrice() {
        tempTotal = 0;

        for(Map.Entry<Item, Integer> entry : items.entrySet()) {
            tempTotal += (entry.getKey().getPrice() * entry.getValue());
        }

        return tempTotal;
    }

    public void setTotalPrice(String totalPriceInEuros) {
        totalPrice = totalPriceInEuros;
    }

    public String print() {

        String newLine = System.getProperty("line.separator");
        OrderManager manager = new OrderManager();
        ArrayList<String> itemList = new ArrayList<String>();

        String date = (dateRepresentation + newLine);
        String empty = ("-----------------" + newLine);

        for(Map.Entry<Item, Integer> entry : items.entrySet()) {
            itemList.add((entry.getKey().getName() + "     " + entry.getValue() + "x  " + manager.centsToEuros(entry.getKey().getPrice()) + newLine));
        }

        String totalprice = String.valueOf(totalPrice);
        return date + empty + itemList + newLine + "-----------" + newLine + "Totaalbedrag: " + totalprice;
    }

}
