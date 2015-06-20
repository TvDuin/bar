package logiclayer;

import datastoragelayer.ServingDAO;
import entitylayer.*;
import datastoragelayer.InlogDAO;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        availableDishOrders.clear();
        availableDishOrders.addAll(serving.retrieveOrders(3, "dish"));
        availableDishOrders.addAll(serving.retrieveOrders(2, "dish"));
        for(Order o : availableDishOrders) {
            o.addItem(serving.getDishItems(o.getId()));
        }
        return availableDishOrders;
    }

    public void serveOrder(Order order, int serverId) throws SQLException{
        serving.serveOrder(order, serverId);
    }

    public List<Order> retrieveAllFromTable(int tableID) throws SQLException {
        int billID = serving.getBillID(tableID);
        List<Order> tmpList = new ArrayList<Order>();
        List<Order> allOrders = new ArrayList<Order>();

        tmpList = serving.retrieveOrdersByID(billID, "beverage");

        for(Order o : tmpList) {
            o.addItem(serving.getBeverageItems(o.getId()));
        }

        allOrders.addAll(tmpList);

        tmpList = serving.retrieveOrdersByID(billID, "dish");

        for(Order o : tmpList) {
            o.addItem(serving.getDishItems(o.getId()));
        }

        return allOrders;
    }

    public Receipt returnReceipt(int tableId) throws SQLException{
        Receipt receipt = new Receipt();
        for(Order o : retrieveAllFromTable(tableId)) {
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

    public String getDayResults(String soort, String date) throws SQLException{
        List<Order> tmpList;
        List<Integer> billIDs = serving.getAllPaidBills(date);
        int beverageTotal = 0;
        int dishTotal = 0;
        int total = 0;

        for(Integer ID : billIDs) {
            tmpList = serving.retrieveOrdersByID(ID, "beverage");

            for(Order o : tmpList) {
                for(Map.Entry<Item, Integer> entry : serving.getBeverageItems(o.getId()).entrySet()) {
                    beverageTotal += (entry.getKey().getPrice() * entry.getValue());
                }
            }
        }

        for(Integer ID : billIDs) {
            tmpList = serving.retrieveOrdersByID(ID, "dish");

            for(Order o : tmpList) {
                for(Map.Entry<Item, Integer> entry : serving.getDishItems(o.getId()).entrySet()) {
                    dishTotal += (entry.getKey().getPrice() * entry.getValue());
                }
            }
        }

        total = beverageTotal + dishTotal;

        if(soort == "Dranken"){
            return centsToEuros(beverageTotal) + " Euro";
        }
        else if(soort == "Gerechten"){
            return "" + centsToEuros(dishTotal) + " Euro";
        }
        else if(soort == "Totaal"){
            return "" + centsToEuros(total) + " Euro";
        }
        else{
            return "Geen Datum ingevuld!";
        }
    }

    public String centsToEuros(int cents) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.FRANCE);
        return nf.format(cents/100.0);
    }
}

