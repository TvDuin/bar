package LogicLayer;

import DataStorageLayer.ServingDAO;
import EntityLayer.Order;
import EntityLayer.Receipt;

import java.util.ArrayList;

/**
 * Created by Thomas on 29-4-2015.
 */
public class OrderManager {
    private ServingDAO serving;

    //Serving is an instance off servingDAO
    public OrderManager() {
        serving = new ServingDAO();
    }

    //geeft alle servirngs terug uit de de servingDao
    public ArrayList<Order> getAllLiquidOrders() {
        return serving.retrieveBeverages();
    }

    public ArrayList<Order> getAllSolidOrders() {
        return serving.retrieveSolids();
    }

    public void serveOrder(Order order, int serverId) {
        serving.addServer(order, serverId);
    }

    public Receipt returnReceipt(int tabbleId) {
        Receipt receipt = new Receipt();
        for(Order o : serving.retrieveAllFromTable(tabbleId)) {
            receipt.addOrder(o);
        }

        receipt.setTotalPrice();

        return receipt;
    }

    public boolean setOrderPayed(int tableId, int EmployeeId){
        return serving.setOrderPayedDAO(tableId, EmployeeId);
    }
}
