package datastoragelayer;

import java.sql.ResultSet;
import java.sql.SQLException;

import entitylayer.Medewerker;

public class InlogDAO {

  public boolean LogIn(String id, String password) throws SQLException {
     DatabaseConnection connection = new DatabaseConnection();
     Boolean bool = false;


     //gaat kijken of er een connectie bestaat.
     if (connection.openConnection()) {
        //sql voor informatie uit de database te halen.
        //query that contains all the ID, tableID and statusses from all available

        ResultSet result_2;
        String query = "SELECT login_name, password FROM `staff` WHERE group_ID IN(4,5) AND login_name ='" + id +
              "' AND password = '" + password + "';";
        // 3 = geplaatst
        //uitkomst van de query wordt hier opgehaald.
        result_2 = connection.executeSQLSelectStatement(query);
        //geeft alle uitkomsten terug uit de database.
        try {
           while (result_2.next()) {
              new Medewerker(result_2.getString("login_name"), result_2.getString("password"));
              bool = true;
           }
        } catch (SQLException e) {
           throw e;

        }
     }

     return bool;

  }


}
