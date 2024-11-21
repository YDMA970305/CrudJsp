/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Infrastructure.Persistence;

/**
 *
 * @author Yesid Martinez
 */
import Business.Exceptions.DuplicateUserException;
import Business.Exceptions.UserNotFoundException;
import Domain.Model.User;
import Infrastructure.Database.ConnectionDbMySql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserCRUD {

    //metodo para obtener todos los usuarios de la base de datos
    public static List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";
        try {
            Connection conn = ConnectionDbMySql.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                new User(
                        rs.getString("id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("status"),
                        rs.getString("created_at")
                );

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    //metodo para agregar un usuario a la base de datos
    public void addUser(User user) throws DuplicateUserException, SQLException, ClassNotFoundException {
        String query = "INSERT INTO user (id, password, name, last_name, role, email, phone, status, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection conn = ConnectionDbMySql.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, user.getId());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getLast_name());
            stmt.setString(5, user.getRole());
            stmt.setString(6, user.getEmail());
            stmt.setString(7, user.getPhone());
            stmt.setString(8, user.getStatus());
            stmt.setString(9, user.getCreated_at());
            stmt.executeUpdate();
        } catch (SQLException e) {
            //aqui manejamos los errores
            if (e.getErrorCode() == 1062) {//codigo de la clave duplicada
                throw new DuplicateUserException("El usuario ya existe");
            } else {
                throw e;

            }
        }
    }

}
