package EntityLayer;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Thomas on 9-5-2015.
 */
public class Receipt {
    private ArrayList<Order> items;
    private Date date;

    public Receipt(){
        items = new ArrayList<Order>();
        date = new Date();
        date.getDate();
        //Initiate the current Date and time. -> date
    }

    public ArrayList<Order> returnItems(){
        return items;
    }

    public void addOrderToReceipt(Order order) {
        items.add(order);
    }
}
