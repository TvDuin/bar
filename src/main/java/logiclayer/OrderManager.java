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

    //Returns all beverage orders from the ServingDAO
    public List<Order> getAllLiquidOrders() throws SQLException{
        availableBeverageOrders = serving.retrieveOrders(1, "beverage");
        for(Order o : availableBeverageOrders) {
            o.addItem(serving.getBeverageItems(o.getId()));
        }
        return availableBeverageOrders;
    }

    //Returns all dish orders from the ServingDAO
    public List<Order> getAllSolidOrders() throws SQLException {
        availableDishOrders.clear();
        availableDishOrders.addAll(serving.retrieveOrders(4, "dish"));
        availableDishOrders.addAll(serving.retrieveOrders(2, "dish"));
        for(Order o : availableDishOrders) {
            o.addItem(serving.getDishItems(o.getId()));
        }
        return availableDishOrders;
    }

    public void serveBeverageOrder(int id, int serverId) throws SQLException{
        serving.serveBeverageOrder(id, serverId);
    }

    public void serveDishOrder(int id, int serverId) throws SQLException{
        serving.serveDishOrder(id, serverId);
    }

    public List<Integer> getActivetables() throws SQLException{
        return serving.getActiveTables();
    }

    public List<Order> retrieveAllFromTable(int tableID) throws SQLException { // Retrieves all orders from a given table
        int billID = serving.getBillID(tableID);
        List<Order> tmpList;
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

        allOrders.addAll(tmpList);

        return allOrders;
    }

    public Receipt returnReceipt(int tableId, String servername) throws SQLException{ // Creates and returns a receipt.
        Receipt receipt = new Receipt();
        receipt.setServerName(servername);
        for(Order o : retrieveAllFromTable(tableId)) {
            receipt.addOrder(o);
        }

        receipt.setTotalPrice(centsToEuros(receipt.setTempPrice()));

        return receipt;
    }

    public int getWantsToPay(int tableID){ // Retrieves tables that want to pay
        int wantsToPay = 0;

        try {
            wantsToPay = serving.getWantsToPay(tableID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wantsToPay;
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

    public String getDayResults(String soort, String date) throws SQLException{ //Retrieves the results on a given day
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
            return centsToEuros(dishTotal) + " Euro";
        }
        else if(soort == "Totaal"){
            return centsToEuros(total) + " Euro";
        }
        else{
            return "Geen Datum ingevuld!";
        }
    }

    public List<String> getActiveBarMembers() {
        List<String> tmpList = new ArrayList<String>();

        try {
            tmpList = serving.getActiveBarMembers();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tmpList;
    }

    public int getStaffId(String loginName) { // Retrieves the staff_ID that belongs to the given loginName
        int id = 0;

        try {
            id = serving.getStaffId(loginName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public void setBillDate(String date, String time, int tableID, String servername) { //Sets the date of a bill
        try {
            serving.setBillDate(date, time, tableID, getStaffId(servername));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String centsToEuros(int cents) { //Converts cents to euros
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.FRANCE);
        return nf.format(cents/100.0);
    }
}

