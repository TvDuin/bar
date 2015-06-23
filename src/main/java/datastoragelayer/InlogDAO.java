package datastoragelayer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author D2
 * @version v1.0
 * Class that handles all the traffic between the LoginGui and the Databaseconnection.
 * This checks the input the user has given when entering his/her login details..
 */

public class InlogDAO {

  public boolean logIn(String id, String password) throws SQLException {
     DatabaseConnection connection = new DatabaseConnection();
     Boolean bool = false;


     // Checks if a valid connection has been established.
     if (connection.openConnection()) {
        ResultSet result2;
        String query = "SELECT login_name, password FROM `staff` WHERE group_ID IN(1,4,5) AND login_name ='" + id +
              "' AND password = '" + password + "';";
        result2 = connection.executeSQLSelectStatement(query); // Query gets executed.
        try {
           while (result2.next()) {
              bool = true;
           }
        } catch (SQLException e) {
           throw e;

        }
     }

     return bool;

  }


}
