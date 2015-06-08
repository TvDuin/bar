package LogicLayer;

import DataStorageLayer.ServingDAO;
import EntityLayer.*;
import DataStorageLayer.InlogDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Thomas on 29-4-2015.
 */
public class OrderManager {
    private ServingDAO serving;
    private InlogDAO login;

    //Serving is an instance off servingDAO
    public OrderManager() {
        serving = new ServingDAO();
        login = new InlogDAO();
        Medewerker Medewerker1= new Medewerker(209331, "hond");
    }

    //geeft alle servirngs terug uit de de servingDao
    /*public List<Order> getAllLiquidOrders() throws SQLException{
        return serving.retrieveBeverages();
    }*/

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

    public boolean checkLogin(int ID, String password){

        boolean check = false ;

        try {
            if (login.LogIn(ID, password) == true){
                check = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return true;
    }


    }

