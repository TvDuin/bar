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
}
