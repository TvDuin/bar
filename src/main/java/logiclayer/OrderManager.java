package logiclayer;

import datastoragelayer.ServingDAO;
import entitylayer.*;
import datastoragelayer.InlogDAO;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thomas on 29-4-2015.
 */
public class OrderManager {
    private ServingDAO serving;
    private InlogDAO login;
    private ArrayList<Order> liquidOrders;
    private ArrayList<Order> solidOrders;

    //Serving is an instance off servingDAO
    public OrderManager() {
        serving = new ServingDAO();
        login = new InlogDAO();
        testOrders();

    }

    public void testOrders(){
        liquidOrders = new ArrayList<Order>();
        solidOrders = new ArrayList<Order>();

        Item koffie = new Item(01, "Koffie", 1.50);
        Item bier = new Item(02, "Bier", 1.96);

        Item pizza = new Item(03, "Pizza", 9.95);
        Item ijsje = new Item(04, "Ijscoupe", 1.00);

        liquidOrders.add(new Order(1, 2, koffie, 2 , 4));
        liquidOrders.add(new Order(2, 3, koffie, 1, 3));
        liquidOrders.add(new Order(3, 1, bier, 10, 1));

        solidOrders.add(new Order(1, 2, pizza, 2, 3));
        solidOrders.add(new Order(3, 1, ijsje, 5, 2));

    }

    //geeft alle servirngs terug uit de de servingDao
    public ArrayList<Order> getAllLiquidOrders() throws SQLException{
        return liquidOrders;
        //return serving.retrieveBeverages();
    }

    public List<Order> getAllSolidOrders() throws SQLException {
        return solidOrders;
        //return serving.retrieveSolids();
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

