package datastoragelayer;



import entitylayer.Item;
import entitylayer.Order;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author D2
 * @version v1.0
 * Class that handles all the traffic between the Ordermanager class and the Databaseconnection.
 * This class executes all queries used to retrieve data regarding orders and Bar related items.
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
                String query = "SELECT `table_id` FROM `bill` WHERE `table_id` = " + table_id + " AND `is_paid` = 0;";
                result1 = connection.executeSQLSelectStatement(query);

                while(result1.next()) {
                    billID = result1.getInt("table_id");
                }
            }
            catch(SQLException e) {
                throw e;
            }
        }

        if(connection.connectionIsOpen()) {
            connection.closeConnection();
        }

        return billID;
    }

    public List<Integer> getActiveTables() throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();
        List<Integer> activeTables = new ArrayList<Integer>();

        if(connection.openConnection()) {
            try {
                ResultSet result1;
                String query = "SELECT `table_id` FROM `bill` WHERE `is_paid` = 0;";
                result1 = connection.executeSQLSelectStatement(query);

                while(result1.next()) {
                    activeTables.add(result1.getInt("table_id"));
                }
            }
            catch(SQLException e) {
                throw e;
            }
        }

        if(connection.connectionIsOpen()) {
            connection.closeConnection();
        }

        return activeTables;
    }

    public int getWantsToPay(int table_id) throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();
        int wantsToPay = 0;

        if(connection.openConnection()) {
            try {
                ResultSet result1;
                String query = "SELECT `wantsToPay` FROM `bill` WHERE `table_id` = " + table_id;
                result1 = connection.executeSQLSelectStatement(query);

                while(result1.next()) {
                    wantsToPay = result1.getInt("wantsToPay");
                }
            }
            catch(SQLException e) {
                throw e;
            }
        }

        if(connection.connectionIsOpen()) {
            connection.closeConnection();
        }

        return wantsToPay;
    }

    public List<Order> retrieveOrders(int status, String type) throws SQLException { //retrieves orders by status, either 1 or 3, and either beverage or dish
        DatabaseConnection connection = new DatabaseConnection();
        List<Order> availableOrders = new ArrayList<Order>();

        if(connection.openConnection()) {
            try {
                ResultSet result1; //query that contains all the ID, tableID and statusses from all available
                String query = "SELECT `ID`,`table_ID`,`status` FROM `" + type + "_order` WHERE `status` = " + status + ";"; // 1 = placed
                result1 = connection.executeSQLSelectStatement(query);

                while(result1.next()) {
                    availableOrders.add(new Order(result1.getInt("ID"), result1.getInt("table_ID"), result1.getInt("status")));
                }
            }
            catch(SQLException e) {
                throw e;
            }
        }

        if(connection.connectionIsOpen()) {
            connection.closeConnection();
        }

        return availableOrders;
    }

    public List<Order> retrieveOrdersByID(int id, String type) throws SQLException { //Retrieves orders by id
        DatabaseConnection connection = new DatabaseConnection();
        List<Order> availableOrders = new ArrayList<Order>();

        if(connection.openConnection()) {
            try {
                ResultSet result1; //Query that contains all the ID, tableID and statuses from all available
                String query = "SELECT `ID`,`table_ID`,`status` FROM `" + type + "_order` WHERE `table_ID` = " + id + ";";
                result1 = connection.executeSQLSelectStatement(query);

                while(result1.next()) {
                    availableOrders.add(new Order(result1.getInt("ID"), result1.getInt("table_ID"), result1.getInt("status")));
                }
            }
            catch(SQLException e) {
                throw e;
            }
        }

        if(connection.connectionIsOpen()) {
            connection.closeConnection();
        }

        return availableOrders;
    }

    public Map<Item, Integer> getBeverageItems(int id) throws SQLException { //Retrieves the items that belong to one order
        DatabaseConnection connection = new DatabaseConnection();
        Map<Item, Integer> items = new LinkedHashMap<Item, Integer>(); //LinkedHashMap to store the individual items of an order in

        if(connection.openConnection()) {
            try {
                ResultSet result;
                //Combining two queries here.
                String query = "SELECT beverage_order_item.beverage_item_ID, beverage_order_item.amount, beverage_menu_item.name, beverage_menu_item.price " +
                        "FROM beverage_order_item INNER JOIN beverage_menu_item ON beverage_order_item.beverage_item_ID = beverage_menu_item.ID " +
                        "WHERE beverage_order_item.order_ID = " + id;
                result = connection.executeSQLSelectStatement(query);

                while(result.next()) {
                    //Fill hashmap here using a the result of the join statement
                    items.put(new Item(result.getInt("beverage_item_ID"), result.getString("name"), result.getInt("price")), result.getInt("amount"));
                }
            }
            catch(SQLException e) {
                throw e;
            }
        }

        if(connection.connectionIsOpen()) {
            connection.closeConnection();
        }

        return items;
    }

    public Map<Item, Integer> getDishItems(int id) throws SQLException { //Retrieves the items that belong to one order
        DatabaseConnection connection = new DatabaseConnection();
        Map<Item, Integer> items = new LinkedHashMap<Item, Integer>(); //Map to store the individual items of an order in

        if(connection.openConnection()) {
            try {
                ResultSet result;
                //Combining two queries here.
                String query = "SELECT dish_order_item.dish_item_ID, dish_order_item.amount, dish_menu_item.name, dish_menu_item.price " +
                        "FROM dish_order_item INNER JOIN dish_menu_item ON dish_order_item.dish_item_ID = dish_menu_item.ID " +
                        "WHERE dish_order_item.order_ID = " + id;
                result = connection.executeSQLSelectStatement(query);

                while(result.next()) {
                    //Fill hashmap here using the result of the join statement
                    items.put(new Item(result.getInt("dish_item_ID"), result.getString("name"), result.getInt("price")), result.getInt("amount"));
                }
            }
            catch(SQLException e) {
                throw e;
            }
        }

        if(connection.connectionIsOpen()) {
            connection.closeConnection();
        }
        return items;
    }

    public void serveBeverageOrder(int id, int serverId) throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();
        Map<Item, Integer> items;

        //Check for a valid connection
        if(connection.openConnection()) {
            //Insert SQL code here
            String query3 =  "UPDATE `beverage_order` SET `status` = 6 WHERE `ID` = " + id;
            String query4 =  "UPDATE `beverage_order` SET `staff_ID` = " + serverId + " WHERE `ID` = " + id;
            try {
                connection.executeSQLInsertStatement(query3);
                connection.executeSQLInsertStatement(query4);

                items = getBeverageItems(id);
                for(Map.Entry<Item, Integer> entry : items.entrySet()) {
                    String query5 = "UPDATE `food` SET `stock` = (stock - " + entry.getValue() + ")  WHERE `name` = '"+ entry.getKey().getName() + "';";
                    connection.executeSQLInsertStatement(query5);
                }
            }

            catch(SQLException e) {
                throw e;
            }
        }

        if(connection.connectionIsOpen()) {
            connection.closeConnection();
        }
    }

    public void serveDishOrder(int id, int serverId) throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();

        //Check for a valid connection
        if(connection.openConnection()) {
            //Insert SQL code here

            String query1 =  "UPDATE `dish_order` SET `status` = 6 WHERE `ID` = " + id;
            String query2 =  "UPDATE `dish_order` SET `staff_ID` = " + serverId + " WHERE `ID` = " + id;
            try {
                connection.executeSQLInsertStatement(query1);
                connection.executeSQLInsertStatement(query2);
            }

            catch(SQLException e) {
                throw e;
            }
        }

        if(connection.connectionIsOpen()) {
            connection.closeConnection();
        }
    }


    public boolean setOrderPaid(int tableId, int staff_ID) throws SQLException
    {
        DatabaseConnection connection = new DatabaseConnection();
        boolean bool = false;

        // Checks for a valid conncetion
        if(connection.openConnection()) {
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

        if(connection.connectionIsOpen()) {
            connection.closeConnection();
        }

        return bool;
    }

    public List<Integer> getAllPaidBills(String date) throws SQLException { //String format = YYYY-mm-dd
        DatabaseConnection connection = new DatabaseConnection();
        List<Integer> IDs = new ArrayList<Integer>();

        //Check for valid connection
        if(connection.openConnection()) {
            ResultSet result1;
            String query1 = "SELECT `ID` FROM `bill` WHERE `date` = '" + date + "' AND `is_paid` = 1 "; // Selects all bill_ID's that are paid for and are on the given date
            result1 = connection.executeSQLSelectStatement(query1);

            try {
                while (result1.next()) {
                    IDs.add(result1.getInt("ID"));
                }

                result1.close();
            }

            catch(SQLException e) {
                throw e;
            }
        }

        if(connection.connectionIsOpen()) {
            connection.closeConnection();
        }

        return IDs;
    }

    public List<String> getActiveBarMembers() throws SQLException{ // Retrieves all active staff-members that work in the bar.
        DatabaseConnection connection = new DatabaseConnection();
        List<String> staffNames = new ArrayList<String>();

        if(connection.openConnection()) {
            ResultSet result1;
            String query1 = "SELECT staff.ID, staff.login_name, present.active FROM staff INNER JOIN present ON staff.ID = present.staff_ID WHERE present.active = 1 AND staff.group_ID = 5;";
            result1 = connection.executeSQLSelectStatement(query1);
            try {
                while(result1.next()) {
                    staffNames.add(result1.getString("login_name"));
                }
            }

            catch(SQLException e) {
                throw e;
            }
        }
        return staffNames;
    }

    public int getStaffId(String loginName) throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();
        int staffID = 0;

        if(connection.openConnection()) {
            ResultSet result1;
            String query1 = "SELECT ID FROM staff WHERE login_name = '" + loginName + "'";
            result1 = connection.executeSQLSelectStatement(query1);
            try {
                while(result1.next()) {
                    staffID = result1.getInt("ID");
                }
            }

            catch(SQLException e) {
                throw e;
            }
        }
        return staffID;
    }


    public void setBillDate(String date, String time, int tableId, int serverID) throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();

        if(connection.openConnection()) {
            String query1 = "UPDATE `bill` SET `date` = '" + date + "' , `time` = '" + time + "' , staff_ID = '" + serverID + "' WHERE `table_id` = " + tableId + " AND is_paid = 0";

            try {
                connection.executeSQLInsertStatement(query1);
            }
            catch(SQLException e) {
                throw e;
            }
        }

    }
}


