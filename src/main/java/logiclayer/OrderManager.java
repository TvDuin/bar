package logiclayer;

import datastoragelayer.ServingDAO;
import entitylayer.*;
import datastoragelayer.InlogDAO;

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

    }

    //geeft alle servirngs terug uit de de servingDao
    public List<Order> getAllLiquidOrders() throws SQLException{
        return serving.retrieveBeverages();
    }

    public List<Order> getAllSolidOrders() throws SQLException {
        return serving.retrieveSolids();
    }

    public void serveOrder(Order order, int serverId) throws SQLException{
        serving.serveOrder(order, serverId);
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

    public boolean checkLogin(String id, String password){

        boolean check = false ;

        try {
            if (login.LogIn(id, password) == true){
                check = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return check;
    }

    public String getDagResultaten(String soort){
        if(soort == "Dranken"){
            return "Dranken Datum";
        }
        else if(soort == "Gerechten"){
            return "Gerechten Datum";
        }
        else if(soort == "Totaal"){
            return "Totaal Datum";
        }
        else{
            return "Niks ingevuld.";
        }
    }


    }

