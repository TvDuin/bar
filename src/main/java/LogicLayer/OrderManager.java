package LogicLayer;

import DataStorageLayer.ServingDAO;
import EntityLayer.*;

import java.sql.SQLException;
import java.util.List;

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
    public List<Order> getAllLiquidOrders() throws SQLException{
        return serving.retrieveBeverages();
    }

    public List<Order> getAllSolidOrders() throws SQLException {
        return serving.retrieveSolids();
    }

    public void serveOrder(Order order, int serverId) throws SQLException{
        serving.addServer(order, serverId);
    }

    public Receipt returnReceipt(int tabbleId) throws SQLException{
        Receipt receipt = new Receipt();
        for(Order o : serving.retrieveAllFromTable(tabbleId)) {
            receipt.addOrder(o);
        }

        receipt.setTotalPrice();

        return receipt;
    }

    public boolean setOrderPayed(int tableId, int EmployeeId) throws SQLException{
        return serving.setOrderPayedDAO(tableId, EmployeeId);
    }
     Medewerker Medewerker1= new Medewerker(209331, "hond");
}
