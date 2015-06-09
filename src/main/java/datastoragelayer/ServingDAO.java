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
            String query = "SELECT `ID`,`table_ID`,`status` FROM `beverage_order` WHERE `status` = 3;"; // 3 = geplaatst
            result_1 = connection.executeSQLSelectStatement(query);

            try {
                while (result_1.next()) {
                    ResultSet result_2; //query that contains
                    String query2 = "SELECT `beverage_item_ID`,`amount` FROM `beverage_order_item` WHERE `order_ID` = " + result_1.getInt("ID") + ";"; // Retrieves all the different items + correct amounts that are linked to the given ID.
                    result_2 = connection.executeSQLSelectStatement(query2);
                    Map<Item, Integer> items_tmp = new HashMap<Item, Integer>(); //Map to store the individual items of an order in

                    while (result_2.next()) {
                        ResultSet result_3; //query that contains
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

    public List<Order> retrieveSolids() throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();
        List<Order> availableSolidOrders = new ArrayList<Order>();

        //Check for valid connection
        if(connection.openConnection()) {
            //sql voor informatie uit de database te halen.
            String query = "SELECT * FROM `SolidOrder`";
            ResultSet result;
            //uitkomst van de query wordt hier opgehaald.
            result = connection.executeSQLSelectStatement(query);
            //geeft alle uitkomsten terug uit de database.
            try {
                while (result.next()) {
//                    availableSolidOrders.add(new Order(result.getInt("id"), result.getInt("tableId"), result.getTime("time"), result.getString("item"), result.getInt("status")));
                }
            }

            catch (SQLException e) {
                throw e;
            }
        }

        return availableSolidOrders;
    }

    public void addServer(Order order, int serverId) throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();

        //Check for a valid connection
        if(connection.openConnection()) {
            //Insert SQL code here
            String query = "INSERT INTO `served` (`orderId`, `serverId`) VALUES (" + order.getId() + ", " + serverId + ")"; //Mysql has to auto fill the index of the entry (PK)
            try {
                connection.executeSQLInsertStatement(query);
            }

            catch(SQLException e) {
                throw e;
            }
        }
    }

    public List<Order> retrieveAllFromTable(int tableId) throws SQLException{ //Do we need to also include server ID in order history?
        DatabaseConnection connection = new DatabaseConnection();
        List<Order> ordersFromTable = new ArrayList<Order>();
        String query;

        //Check for valid connection
        if(connection.openConnection()) {
            //First select all the orders that belong to one table.
            query = "SELECT * FROM `liquidOrder` WHERE `tableId` = " + tableId;
            ResultSet liquidResult = connection.executeSQLSelectStatement(query);
            query = "SELECT * FROM `solidOrder` WHERE `tableId` = " + tableId;
            ResultSet solidResult = connection.executeSQLSelectStatement(query);

            //Then delete the existing orders in the current table {Make way for the new}
            query = "DELETE * FROM `liquidOrder` WHERE `tableId` = " + tableId;
            connection.executeSQLDeleteStatement(query);
            query = "DELETE * FROM `solidOrder` WHERE `tableId` = " + tableId;
            connection.executeSQLDeleteStatement(query);
//Fixing needed, awaiting results from DB council
//            try {
//                while (liquidResult.next()) {
//                    ordersFromTable.add(new Order(liquidResult.getInt("id"), liquidResult.getInt("tableId"), liquidResult.getTime("time"), liquidResult.getString("item"), liquidResult.getInt("status")));
//                }
//
//                while(solidResult.next()) {
//                    ordersFromTable.add(new Order(solidResult.getInt("id"), solidResult.getInt("tableId"), solidResult.getTime("time"), solidResult.getString("item"), solidResult.getInt("status")));
//                }
//
//                for(Order o : ordersFromTable) {
//                    query = "INSERT INTO `orderHistory` (`id`, `tableId`, `time`, `item`) VALUES ('" + o.getId() + "', '" + o.getTableId() + "','" + o.getTime() + "','" + o.getItem() + "')"; //Creates the order history
//                    connection.executeSQLInsertStatement(query);
//                }
//            }
//
//            catch (SQLException e) {
//                throw e;
//            }
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


