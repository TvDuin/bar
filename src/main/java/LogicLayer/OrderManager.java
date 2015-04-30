package LogicLayer;

import DataStorageLayer.ServingDAO;
import EntityLayer.Order;

import java.util.ArrayList;

/**
 * Created by Thomas on 29-4-2015.
 */
public class OrderManager {
    ServingDAO serving;

    public OrderManager() {
        serving = new ServingDAO();
    }

    public ArrayList<Order> getAllLiquidOrders() {
        return serving.retrieveLiquids();
    }
}
