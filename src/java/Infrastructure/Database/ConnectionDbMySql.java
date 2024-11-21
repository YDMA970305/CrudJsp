/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Infrastructure.Database;

/**
 *
 * @author Yesid Martinez
 */

 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.SQLException;
 import java.sql.*;

public class ConnectionDbMySql {
 private static final String DB_URL = "jdbc:mysql://localhost:3306/ejemplocrudjsp";
 private static final String USER = "root";
 private static final String PASSWORD = "123456";
 private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

//metodo que devuelve una conexion a la base de datos
 public static Connection getConnection() throws SQLException, ClassNotFoundException {
 Connection conn = null;
    try {
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
        throw new SQLException("No se ha podido cargar el driver de MySQL");
    } catch (SQLException e) {
        e.printStackTrace();
        var message = "No se ha podido conectar a la base de datos";
        throw new SQLException(message);
    }
    return conn;
 }
  
}
