package de.fim.wad.eventplanning.db;

import java.sql.*;

public class JavaH2Server {
    Connection connection;

    public JavaH2Server(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ResultSet getAllEvents(){

        String query = "SELECT * FROM events";
        ResultSet result = null;
        try{
            Statement statement = connection.createStatement();
            result = statement.executeQuery(query);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet getEventsByName(String name){
        String query = "SELECT * FROM events WHERE name LIKE '%" + name + "'%";
        ResultSet result = null;
        try{
            Statement statement = connection.createStatement();
            result = statement.executeQuery(query);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet getEventsByLoaction(String location){
        String query = "SELECT * FROM events WHERE location LIKE '%" + location + "'%";
        ResultSet result = null;
        try{
            Statement statement = connection.createStatement();
            result = statement.executeQuery(query);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
