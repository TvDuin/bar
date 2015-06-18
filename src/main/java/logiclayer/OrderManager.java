package logiclayer;

import datastoragelayer.ServingDAO;
import entitylayer.*;
import datastoragelayer.InlogDAO;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 29-4-2015.
 */
public class OrderManager {
    private ServingDAO serving;
    private InlogDAO login;
    private List<Order> availableBeverageOrders;
    private List<Order> availableDishOrders;

    //Serving is an instance off servingDAO
    public OrderManager() {
        serving = new ServingDAO();
        login = new InlogDAO();
        availableBeverageOrders = new ArrayList<Order>();
        availableDishOrders = new ArrayList<Order>();
    }

    //geeft alle servings terug uit de de servingDao
    public List<Order> getAllLiquidOrders() throws SQLException{
        availableBeverageOrders = serving.retrieveOrders(1, "beverage");
        for(Order o : availableBeverageOrders) {
            o.addItem(serving.getBeverageItems(o.getId()));
        }
        return availableBeverageOrders;
    }

    public List<Order> getAllSolidOrders() throws SQLException {
        availableDishOrders = serving.retrieveOrders(1, "dish");
        for(Order o : availableDishOrders) {
            o.addItem(serving.getDishItems(o.getId()));
        }
        return availableDishOrders;
    }

    public void serveOrder(Order order, int serverId) throws SQLException{
        serving.serveOrder(order, serverId);
    }

    public Receipt returnReceipt(int tableId) throws SQLException{
        Receipt receipt = new Receipt();
        for(Order o : serving.retrieveAllFromTable(tableId)) {
            receipt.addOrder(o);
        }

        receipt.setTotalPrice();

        return receipt;
    }

    public boolean setOrderPaid(int tableId, int staffID) throws SQLException{
        return serving.setOrderPaid(tableId, staffID);
    }

    public boolean checkLogin(String id, String password){

        boolean check = false ;

        try {
            if (login.logIn(id, password)){
                check = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return check;
    }

    public String getDagResultaten(String soort, String datum) throws SQLException{
        List<Integer> tempList = new ArrayList<Integer>();

        try {
            tempList = serving.getDayResults(datum);

            if(soort == "Dranken"){
                return "€" + tempList.get(1);
            }
            else if(soort == "Gerechten"){
                return "€" + tempList.get(2);
            }
            else if(soort == "Totaal"){
                return "€" + tempList.get(3);
            }
            else{
                return "Geen Datum ingevuld!";
            }
        }

        catch (SQLException e) {
            throw e;
        }

    }
}

