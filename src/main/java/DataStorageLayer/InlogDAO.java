package DataStorageLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import EntityLayer.Medewerker;

public class InlogDAO {
	
	  public List<Medewerker> LogIn() throws SQLException{
	        DatabaseConnection connection = new DatabaseConnection();
	        List<Medewerker> Login = new ArrayList<Medewerker>();
	        
	        //gaat kijken of er een connectie bestaat.
	        if(connection.openConnection()) {
	        //sql voor informatie uit de database te halen.
	             //query that contains all the ID, tableID and statusses from all available
	           
	            ResultSet result_2;
	            String query = "SELECT `ID`,`wachtwoord' FROM `gebruiker gb,groep g` WHERE `gb.groepnaam = g.groepnaam AND g.bediening  = 1;";
	            // 3 = geplaatst
	            //uitkomst van de query wordt hier opgehaald.
	            result_2 = connection.executeSQLSelectStatement(query);
	            //geeft alle uitkomsten terug uit de database.
	            try {
	                while (result_2.next()) {
	                    Login.add(new Medewerker(result_2.getInt("ID"),result_2.getString("WachtWoord"))) ;
	                }
	            }

	            catch (SQLException e) {
	                throw e;
	            }
	        }

	        return Login;
	    }

	    
	        }


