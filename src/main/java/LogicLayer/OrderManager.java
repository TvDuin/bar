package LogicLayer;

import DataStorageLayer.ServingDAO;
import EntityLayer.Order;

import java.util.ArrayList;

/**
 * Created by Thomas on 29-4-2015.
 */
public class OrderManager {
    ServingDAO serving;

 //serving is een servingDAO
    public OrderManager() {
        serving = new ServingDAO();
    }
//geeft alle servirngs terug uit de de servingDao
    public ArrayList<Order> getAllLiquidOrders() {
        return serving.retrieveLiquids();
    }
}
