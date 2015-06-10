package datastoragelayer;

import java.sql.ResultSet;
import java.sql.SQLException;

import entitylayer.Medewerker;

public class InlogDAO {

  public boolean logIn(String id, String password) throws SQLException {
     DatabaseConnection connection = new DatabaseConnection();
     Boolean bool = false;


     //gaat kijken of er een connectie bestaat.
     if (connection.openConnection()) {
        //sql voor informatie uit de database te halen.
        //query that contains all the ID, tableID and statusses from all available

        ResultSet result2;
        String query = "SELECT login_name, password FROM `staff` WHERE group_ID IN(4,5) AND login_name ='" + id +
              "' AND password = '" + password + "';";
        // 3 = geplaatst
        //uitkomst van de query wordt hier opgehaald.
        result2 = connection.executeSQLSelectStatement(query);
        //geeft alle uitkomsten terug uit de database.
        try {
           while (result2.next()) {
              new Medewerker(result2.getString("login_name"), result2.getString("password"));
              bool = true;
           }
        } catch (SQLException e) {
           throw e;

        }
     }

     return bool;

  }


}
