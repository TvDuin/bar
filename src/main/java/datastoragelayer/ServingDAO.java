package datastoragelayer;

import entitylayer.Item;
import entitylayer.Order;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thomas on 29-4-2015.
 */
public class ServingDAO {

    public ServingDAO(){
        //Nothing to see here
    }

    public int getBillID(int table_id) throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();
        int billID = 0;

        if(connection.openConnection()) {
            try {
                ResultSet result1;
                String query = "SELECT `ID` FROM `bill` WHERE `table_id` = " + table_id + " AND `is_paid` = 0;";
                result1 = connection.executeSQLSelectStatement(query);

                while(result1.next()) {
                    billID = result1.getInt("ID");
                }
            }
            catch(SQLException e) {
                throw e;
            }
        }
        return billID;
    }

    public List<Order> retrieveOrders(int status, String type) throws SQLException { //retrieves orders by status and, either 1 or 3, and either beverage or dish
        DatabaseConnection connection = new DatabaseConnection();
        List<Order> availableOrders = new ArrayList<Order>();

        if(connection.openConnection()) {
            try {
                ResultSet result1; //query that contains all the ID, tableID and statusses from all available
                String query = "SELECT `ID`,`table_ID`,`status` FROM `" + type + "_order` WHERE `status` = " + status + ";"; // 1 = geplaatst
                result1 = connection.executeSQLSelectStatement(query);

                while(result1.next()) {
                    availableOrders.add(new Order(result1.getInt("ID"), result1.getInt("table_ID"), result1.getInt("status")));
                }
            }
            catch(SQLException e) {
                throw e;
            }
        }
        return availableOrders;
    }

    public List<Order> retrieveOrdersByID(int id, String type) throws SQLException { //retrieves orders by id
        DatabaseConnection connection = new DatabaseConnection();
        List<Order> availableOrders = new ArrayList<Order>();

        if(connection.openConnection()) {
            try {
                ResultSet result1; //query that contains all the ID, tableID and statusses from all available
                String query = "SELECT `ID`,`table_ID`,`status` FROM `" + type + "_order` WHERE `ID` = " + id + ";";
                result1 = connection.executeSQLSelectStatement(query);

                while(result1.next()) {
                    availableOrders.add(new Order(result1.getInt("ID"), result1.getInt("table_ID"), result1.getInt("status")));
                }
            }
            catch(SQLException e) {
                throw e;
            }
        }
        return availableOrders;
    }

    public Map<Item, Integer> getBeverageItems(int id) throws SQLException { //Retrieves the items that belong to one order
        DatabaseConnection connection = new DatabaseConnection();
        Map<Item, Integer> items = new HashMap<Item, Integer>(); //Map to store the individual items of an order in

        if(connection.openConnection()) {
            try {
                ResultSet result;
                //Combining two queries here.
                String query = "SELECT beverage_order_item.beverage_item_ID, beverage_order_item.amount, beverage_menu_item.name, beverage_menu_item.price " +
                        "FROM beverage_order_item INNER JOIN beverage_menu_item ON beverage_order_item.beverage_item_ID = beverage_menu_item.ID " +
                        "WHERE beverage_order_item.order_ID = " + id;
                result = connection.executeSQLSelectStatement(query);

                while(result.next()) {
                    //fill hashmap here using a the result of the join statement
                    items.put(new Item(result.getInt("beverage_item_ID"), result.getString("name"), result.getInt("price")), result.getInt("amount"));
                }
            }
            catch(SQLException e) {
                throw e;
            }
        }
        return items;
    }

    public Map<Item, Integer> getDishItems(int id) throws SQLException { //Retrieves the items that belong to one order
        DatabaseConnection connection = new DatabaseConnection();
        Map<Item, Integer> items = new HashMap<Item, Integer>(); //Map to store the individual items of an order in

        if(connection.openConnection()) {
            try {
                ResultSet result;
                //Combining two queries here.
                String query = "SELECT dish_order_item.dish_item_ID, dish_order_item.amount, dish_menu_item.name, dish_menu_item.price " +
                        "FROM dish_order_item INNER JOIN dish_menu_item ON dish_order_item.dish_item_ID = dish_menu_item.ID " +
                        "WHERE dish_order_item.order_ID = " + id;
                result = connection.executeSQLSelectStatement(query);

                while(result.next()) {
                    //fill hashmap here using a the result of the join statement
                    items.put(new Item(result.getInt("dish_item_ID"), result.getString("name"), result.getInt("price")), result.getInt("amount"));
                }
            }
            catch(SQLException e) {
                throw e;
            }
        }
        return items;
    }

    public void serveOrder(Order order, int serverId) throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();

        order.setServed(); //Sets the status of the Object (in the application) as served.

        //Check for a valid connection
        if(connection.openConnection()) {
            //Insert SQL code here
            String query1 =  "UPDATE `dish_order` SET `status` = 4 WHERE `ID` = " + order.getId();
            String query2 =  "UPDATE `dish_order` SET `staff_ID` = " + serverId + " WHERE `ID` = " + order.getId();
            String query3 =  "UPDATE `beverage_order` SET `status` = 4 WHERE `ID` = " + order.getId();
            String query4 =  "UPDATE `beverage_order` SET `staff_ID` = " + serverId + " WHERE `ID` = " + order.getId();
            try {
                connection.executeSQLInsertStatement(query1);
                connection.executeSQLInsertStatement(query2);
                connection.executeSQLInsertStatement(query3);
                connection.executeSQLInsertStatement(query4);
            }

            catch(SQLException e) {
                throw e;
            }
        }
    }
    

    public boolean setOrderPaid(int tableId, int staff_ID) throws SQLException
    {
        DatabaseConnection connection = new DatabaseConnection();
        boolean bool = false;

        //gaat kijken of er een connectie bestaat.
        if(connection.openConnection()) {
            //sql voor informatie uit de database te halen.
            String query1 = "UPDATE `bill` SET `staff_ID` = " + staff_ID + " WHERE `table_id` = " + tableId + " AND is_paid = 0";
            String query2 = "UPDATE `bill` SET `is_paid` = 1 WHERE `table_id` = " + tableId + " AND is_paid = 0";

            try {
                connection.executeSQLInsertStatement(query1);
                connection.executeSQLInsertStatement(query2);
                bool = true;
            }
            catch (SQLException e) {
                throw e;
            }
        }
        return bool;
    }

    public List<Integer> getDayResults(String date) throws SQLException { //String format = YYYY-mm-dd
        DatabaseConnection connection = new DatabaseConnection();
        List<Integer> values = new ArrayList<Integer>();

        //Check for valid connection
        if(connection.openConnection()) {
            ResultSet result1;
            String query1 = "SELECT `ID` FROM `bill` WHERE `date` = '" + date + "' AND `is_paid` = 1 "; // Selects all bill_ID's that are paid for and are on the given date
            result1 = connection.executeSQLSelectStatement(query1);

            try {
                while (result1.next()) {
                    ResultSet result2; //query that contains all the ID, tableID and statusses from all available
                    String query2 = "SELECT `ID`,`table_ID`,`status` FROM `beverage_order` WHERE `bill_ID` = " + result1; //
                    result2 = connection.executeSQLSelectStatement(query2);

                    while (result2.next()) {
                        ResultSet result3;
                        String query3 = "SELECT `beverage_item_ID`,`amount` FROM `beverage_order_item` WHERE `order_ID` = " + result2.getInt("ID") + ";"; // Retrieves all the different items + correct amounts that are linked to the given ID.
                        result3 = connection.executeSQLSelectStatement(query3);
                         //Map to store the individual items of an order in

                        while (result3.next()) {
                            ResultSet result4;
                            String query4 = "SELECT `price` FROM `beverage_menu_item` WHERE `ID` = " + result3.getInt("beverage_item_ID") + ";"; // Retrieves all the prices that are linked to the given beverage_item_ID.
                            result4 = connection.executeSQLSelectStatement(query4);
                            //voeg hier de  prijzen toe aan de ArrayList
                            values.set(1, values.get(1) + result4.getInt("price"));
                        }
                    }

                    ResultSet result5; //query that contains all the ID, tableID and statusses from all available
                    String query5 = "SELECT `ID`,`table_ID`,`status` FROM `dish_order` WHERE `status` = 2 OR `status` = 3;"; // 2 = geaccepteerd, 3 = gereed
                    result5 = connection.executeSQLSelectStatement(query5);

                    while (result5.next()) {
                        ResultSet result6;
                        String query6 = "SELECT `dish_item_ID`,`amount` FROM dish_order_item` WHERE `order_ID` = " + result1.getInt("ID") + ";"; // Retrieves all the different items + correct amounts that are linked to the given ID.
                        result6 = connection.executeSQLSelectStatement(query6);
                        Map<Item, Integer> itemsTmp = new HashMap<Item, Integer>(); //Map to store the individual items of an order in

                        while (result6.next()) {
                            ResultSet result7;
                            String query7 = "SELECT `price` FROM `dish_menu_item` WHERE `ID` = " + result2.getInt("dish_item_ID") + ";"; // Retrieves all the names that are linked to the given beverage_item_ID.
                            result7 = connection.executeSQLSelectStatement(query7);
                            //voeg hier de  items toe aan de MAP
                            values.set(2, values.get(2) + result7.getInt("price"));
                        }
                    }
                }
                values.set(3, values.get(1) + values.get(2));
            }

            catch(SQLException e) {
                throw e;
            }
        }

        return values;

    }

}


