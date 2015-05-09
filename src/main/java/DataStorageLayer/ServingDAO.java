package DataStorageLayer;

import EntityLayer.Order;
import sun.plugin.javascript.navig.Array;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Thomas on 29-4-2015.
 */
public class ServingDAO {
    public ServingDAO(){
        //Nothing to see here
    }

    //maak een arraykist aan van orders
    public ArrayList<Order> retrieveLiquids() {
        DatabaseConnection connection = new DatabaseConnection();
        ArrayList<Order> availableOrders = new ArrayList<Order>();
        
        //gaat kijken of er een connectie bestaat.
        if(connection.openConnection()) {
        //sql voor informatie uit de database te halen.
            String query = "SELECT * FROM `liquidOrder`";
            ResultSet result;
            //uitkomst van de query wordt hier opgehaald.
            result = connection.executeSQLSelectStatement(query);
            //geeft alle uitkomsten terug uit de database.
            try {
                while (result.next()) {
                    availableOrders.add(new Order(result.getInt("id"), result.getInt("tableId"), result.getTime("time"), result.getString("item"), result.getInt("status")));
                }
            }

            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return availableOrders;
    }

    public ArrayList<Order> retrieveSolids() {
        DatabaseConnection connection = new DatabaseConnection();
        ArrayList<Order> availableSolidOrders = new ArrayList<Order>();

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
                    availableSolidOrders.add(new Order(result.getInt("id"), result.getInt("tableId"), result.getTime("time"), result.getString("item"), result.getInt("status")));
                }
            }

            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return availableSolidOrders;
    }

    public void addServer(Order order, int serverId) {
        DatabaseConnection connection = new DatabaseConnection();

        //Check for a valid connection
        if(connection.openConnection()) {
            //Insert SQL code here
            String query = "INSERT INTO `served` (`orderId`, `serverId`) VALUES (" + order.getId() + ", " + serverId + ")"; //Mysql has to auto fill the index of the entry (PK)
            try {
                connection.executeSQLInsertStatement(query);
            }

            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Order> retrieveAllFromTable(int tableId) { //Do we need to also include server ID in order history?
        DatabaseConnection connection = new DatabaseConnection();
        ArrayList<Order> ordersFromTable = new ArrayList<Order>();
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

            try {
                while (liquidResult.next()) {
                    ordersFromTable.add(new Order(liquidResult.getInt("id"), liquidResult.getInt("tableId"), liquidResult.getTime("time"), liquidResult.getString("item"), liquidResult.getInt("status")));
                }

                while(solidResult.next()) {
                    ordersFromTable.add(new Order(solidResult.getInt("id"), solidResult.getInt("tableId"), solidResult.getTime("time"), solidResult.getString("item"), solidResult.getInt("status")));
                }

                for(Order o : ordersFromTable) {
                    query = "INSERT INTO `orderHistory` (`id`, `tableId`, `time`, `item`) VALUES ('" + o.getId() + "', '" + o.getTableId() + "','" + o.getTime() + "','" + o.getItem() + "')"; //Creates the order history
                    connection.executeSQLInsertStatement(query);
                }
            }

            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ordersFromTable;
    }
}
