package DataStorageLayer;

import EntityLayer.Order;

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

    public ArrayList<Order> retrieveLiquids() {
        DatabaseConnection connection = new DatabaseConnection();
        ArrayList<Order> availableOrders = new ArrayList<Order>();

        if(connection.openConnection()) {
            String query = "SELECT * FROM `liquidOrder`";
            ResultSet result;

            result = connection.executeSQLSelectStatement(query);

            try {
                while (result.next()) {
                    availableOrders.add(new Order(result.getInt("id"), result.getInt("tableId"), result.getTime("time"), result.getString("item")));
                }
            }

            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return availableOrders;
    }
}
