package datastoragelayer;

import java.sql.*;

/**
 * @author D2
 * @version v1.0
 * Class that handles the connection and the exec   ution of queries to and on the selected database.
 */

public class DatabaseConnection {

    private Connection connection;

    // The Statement object has been defined as a field because some methods
    // may return a ResultSet object. If so, the statement object may not
    // be closed as you would do when it was a local variable in the query
    // execution method.
    private Statement statement;

    /**
     * Lazyloads the Connection and Statement objects
     */
    public DatabaseConnection()
    {
        connection = null;
        statement = null;
    }

    public boolean openConnection() throws SQLException
    {
        boolean result = false;

        if(connection == null)
        {
            try
            {
                // Try to create a connection with the library database; No user and password have been set.
                connection = DriverManager.getConnection("jdbc:mysql://localhost/hartigehapivp4d" , "root", "");
                        //"jdbc:mysql://145.48.6.148/hartigehapivp4d" , "root", "10ec4u"); // Switch comments to switch database
                if(connection != null)
                {
                    statement = connection.createStatement();
                }

                result = true;
            }
            catch(SQLException e)
            {
                throw e;
            }
        }
        else
        {
            // A connection was already initalized.
            result = true;
        }

        return result;
    }

    public boolean connectionIsOpen() throws SQLException
    {
        boolean open = false;

        if(connection != null && statement != null)
        {
            try
            {
                open = !connection.isClosed() && !statement.isClosed();
            }
            catch(SQLException e)
            {
                throw e;
            }
        }
        // Else, at least one the connection or statement fields is null, so
        // no valid connection.

        return open;
    }

    public void closeConnection() throws SQLException
    {
        try
        {
            statement.close();

            // Close the connection
            connection.close();
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public ResultSet executeSQLSelectStatement(String query) throws SQLException
    {
        ResultSet resultset = null;

        // First, check whether a some query was passed and the connection with
        // the database.
        if(query != null && connectionIsOpen())
        {
            // Then, if succeeded, execute the query.
            try
            {
                resultset = statement.executeQuery(query);
            }
            catch(SQLException e)
            {
                throw e;
            }
        }

        return resultset;
    }

    public boolean executeSQLDeleteStatement(String query) throws SQLException
    {
        boolean result = false;

        // First, check whether a some query was passed and the connection with
        // the database.
        if(query != null && connectionIsOpen())
        {
            // Then, if succeeded, execute the query.
            try
            {
                statement.executeUpdate(query);
                result = true;
            }
            catch(SQLException e)
            {
                throw e;
            }
        }

        return result;
    }

    public void executeSQLInsertStatement(String query) throws SQLException//Executes insert query
    {
        // First, check whether a some query was passed and the connection with
        // the database.
        if(query != null && connectionIsOpen())
        {
            // Then, if succeeded, execute the query.
            try
            {
                statement.executeUpdate(query);
            }
            catch(SQLException e)
            {
                throw e;
            }
        }
    }
}

