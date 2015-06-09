package datastoragelayer;

import entitylayer.Item;
import entitylayer.Order;
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

    //maak een arraykist aan van orders
    public List<Order> retrieveBeverages() throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();
        List<Order> availableOrders = new ArrayList<Order>();
        
        //gaat kijken of er een connectie bestaat.
        if(connection.openConnection()) {
            ResultSet result_1; //query that contains all the ID, tableID and statusses from all available
            String query = "SELECT `ID`,`table_ID`,`status` FROM `beverage_order` WHERE `status` = 1;"; // 3 = geplaatst
            result_1 = connection.executeSQLSelectStatement(query);

            try {
                while (result_1.next()) {
                    ResultSet result_2;
                    String query2 = "SELECT `beverage_item_ID`,`amount` FROM `beverage_order_item` WHERE `order_ID` = " + result_1.getInt("ID") + ";"; // Retrieves all the different items + correct amounts that are linked to the given ID.
                    result_2 = connection.executeSQLSelectStatement(query2);
                    Map<Item, Integer> items_tmp = new HashMap<Item, Integer>(); //Map to store the individual items of an order in

                    while (result_2.next()) {
                        ResultSet result_3;
                        String query3 = "SELECT `name`,`price` FROM `beverage_menu_item` WHERE `ID` = " + result_2.getInt("beverage_item_ID") + ";"; // Retrieves all the names that are linked to the given beverage_item_ID.
                        result_3 = connection.executeSQLSelectStatement(query3);
                        //voeg hier de  items toe aan de MAP
                        items_tmp.put(new Item(result_2.getInt("beverage_item_ID"), result_3.getString("name"), result_3.getDouble("price")), result_2.getInt("amount"));
                    }

                    //maak hier order aan
                    availableOrders.add(new Order(result_1.getInt("ID"), result_1.getInt("table_ID"), items_tmp, result_1.getInt("status")));
                }
            }

            catch (SQLException e) {
                throw e;
            }
        }

        return availableOrders;
    }

    public List<Order> retrieveSolids() throws SQLException{ //Awaiting comments from the kitchen staff
        DatabaseConnection connection = new DatabaseConnection();
        List<Order> availableSolidOrders = new ArrayList<Order>();

        //gaat kijken of er een connectie bestaat.
        if(connection.openConnection()) {
            ResultSet result_1; //query that contains all the ID, tableID and statusses from all available
            String query = "SELECT `ID`,`table_ID`,`status` FROM `dish_order` WHERE `status` = 2 OR `status` = 3;"; // 2 = geaccepteerd, 3 = gereed
            result_1 = connection.executeSQLSelectStatement(query);

            try {
                while (result_1.next()) {
                    ResultSet result_2;
                    String query2 = "SELECT `dish_item_ID`,`amount` FROM `dish_order_item` WHERE `order_ID` = " + result_1.getInt("ID") + ";"; // Retrieves all the different items + correct amounts that are linked to the given ID.
                    result_2 = connection.executeSQLSelectStatement(query2);
                    Map<Item, Integer> items_tmp = new HashMap<Item, Integer>(); //Map to store the individual items of an order in

                    while (result_2.next()) {
                        ResultSet result_3;
                        String query3 = "SELECT `name`,`price` FROM `dish_menu_item` WHERE `ID` = " + result_2.getInt("dish_item_ID") + ";"; // Retrieves all the names that are linked to the given beverage_item_ID.
                        result_3 = connection.executeSQLSelectStatement(query3);
                        //voeg hier de  items toe aan de MAP
                        items_tmp.put(new Item(result_2.getInt("dish_item_ID"), result_3.getString("name"), result_3.getDouble("price")), result_2.getInt("amount"));
                    }

                    //maak hier order aan
                    availableSolidOrders.add(new Order(result_1.getInt("ID"), result_1.getInt("table_ID"), items_tmp, result_1.getInt("status")));
                }
            }

            catch (SQLException e) {
                throw e;
            }
        }

        return availableSolidOrders;
    }

    public void serveOrder(Order order, int serverId) throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();

        order.setServed(); //Sets the status of the Object (in the application) as served.

        //Check for a valid connection
        if(connection.openConnection()) {
            //Insert SQL code here
            String query_1 =  "UPDATE `dish_order` SET `status` = 4 WHERE `ID` = " + order.getId();
            String query_2 =  "UPDATE `dish_order` SET `staff_ID` = " + serverId + " WHERE `ID` = " + order.getId();
            String query_3 =  "UPDATE `beverage_order` SET `status` = 4 WHERE `ID` = " + order.getId();
            String query_4 =  "UPDATE `beverage_order` SET `staff_ID` = " + serverId + " WHERE `ID` = " + order.getId();
            try {
                connection.executeSQLInsertStatement(query_1);
                connection.executeSQLInsertStatement(query_2);
                connection.executeSQLInsertStatement(query_3);
                connection.executeSQLInsertStatement(query_4);
            }

            catch(SQLException e) {
                throw e;
            }
        }
    }

    public List<Order> retrieveAllFromTable(int tableId) throws SQLException{ //Do we need to also include server ID in order history?
        DatabaseConnection connection = new DatabaseConnection();
        List<Order> ordersFromTable = new ArrayList<Order>();

        //Check for valid connection
        if(connection.openConnection()) {
            ResultSet result_1;
            String query_1 = "SELECT `ID` FROM `bill` WHERE `table_id` = " + tableId + " AND `is_paid` = 0"; // This results in only the current table being selected as nobody has paid their bill yet
            result_1 = connection.executeSQLSelectStatement(query_1);

            try {
                while (result_1.next()) {
                    ResultSet result_2; //query that contains all the ID, tableID and statusses from all available
                    String query_2 = "SELECT `ID`,`table_ID`,`status` FROM `beverage_order` WHERE `bill_ID` = " + result_1; //
                    result_2 = connection.executeSQLSelectStatement(query_2);

                    while (result_2.next()) {
                        ResultSet result_3;
                        String query_3 = "SELECT `beverage_item_ID`,`amount` FROM `beverage_order_item` WHERE `order_ID` = " + result_2.getInt("ID") + ";"; // Retrieves all the different items + correct amounts that are linked to the given ID.
                        result_3 = connection.executeSQLSelectStatement(query_3);
                        Map<Item, Integer> items_tmp = new HashMap<Item, Integer>(); //Map to store the individual items of an order in

                        while (result_3.next()) {
                            ResultSet result_4;
                            String query_4 = "SELECT `name`,`price` FROM `beverage_menu_item` WHERE `ID` = " + result_3.getInt("beverage_item_ID") + ";"; // Retrieves all the names that are linked to the given beverage_item_ID.
                            result_4 = connection.executeSQLSelectStatement(query_4);
                            //voeg hier de  items toe aan de MAP
                            items_tmp.put(new Item(result_3.getInt("beverage_item_ID"), result_4.getString("name"), result_4.getDouble("price")), result_3.getInt("amount"));
                        }

                        //maak hier order aan
                        ordersFromTable.add(new Order(result_2.getInt("ID"), result_2.getInt("table_ID"), items_tmp, result_2.getInt("status")));
                    }

                    ResultSet result_5; //query that contains all the ID, tableID and statusses from all available
                    String query_5 = "SELECT `ID`,`table_ID`,`status` FROM `dish_order` WHERE `status` = 2 OR `status` = 3;"; // 2 = geaccepteerd, 3 = gereed
                    result_5 = connection.executeSQLSelectStatement(query_5);

                    while (result_5.next()) {
                        ResultSet result_6;
                        String query_6 = "SELECT `dish_item_ID`,`amount` FROM `dish_order_item` WHERE `order_ID` = " + result_1.getInt("ID") + ";"; // Retrieves all the different items + correct amounts that are linked to the given ID.
                        result_6 = connection.executeSQLSelectStatement(query_6);
                        Map<Item, Integer> items_tmp = new HashMap<Item, Integer>(); //Map to store the individual items of an order in

                        while (result_6.next()) {
                            ResultSet result_7;
                            String query_7 = "SELECT `name`,`price` FROM `dish_menu_item` WHERE `ID` = " + result_2.getInt("dish_item_ID") + ";"; // Retrieves all the names that are linked to the given beverage_item_ID.
                            result_7 = connection.executeSQLSelectStatement(query_7);
                            //voeg hier de  items toe aan de MAP
                            items_tmp.put(new Item(result_6.getInt("dish_item_ID"), result_7.getString("name"), result_7.getDouble("price")), result_6.getInt("amount"));
                        }

                        //maak hier order aan
                        ordersFromTable.add(new Order(result_5.getInt("ID"), result_5.getInt("table_ID"), items_tmp, result_5.getInt("status")));
                    }

                }
            }

            catch(SQLException e) {
                throw e;
            }
        }

        return ordersFromTable;
    }
    public boolean setOrderPayedDAO(int tableId, int EmployeeId) throws SQLException
    {
        DatabaseConnection connection = new DatabaseConnection();
        boolean bool = false;

        //gaat kijken of er een connectie bestaat.
        if(connection.openConnection()) {
            //sql voor informatie uit de database te halen.
            String query = "SELECT * INTO TABLE 2 FROM TABLE 1 WHERE tableId = " + tableId + "";
            String query2 = "INSERT INTO TABLE 2 (EmployeeId) VALUES ('" + EmployeeId + "')";
            String query3 = "DELETE * FROM TABLE WHERE tableid = " + tableId;
            ResultSet result;
            //uitkomst van de query wordt hier opgehaald.
            result = connection.executeSQLSelectStatement(query);
            connection.executeSQLInsertStatement(query2);
            connection.executeSQLDeleteStatement(query3);
            //geeft alle uitkomsten terug uit de database.
            try {
                if(result.next()) {
                    bool = true;
                }

                else {
                    bool = false;
                }
            }
            catch (SQLException e) {
                throw e;
            }
        }
        return bool;
    }
}


