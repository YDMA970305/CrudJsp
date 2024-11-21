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

        try (Connection conn = ConnectionDbMySql.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

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

    //Metodo para actualizar usuario
    public void updateUser(User user) throws UserNotFoundException, SQLException, ClassNotFoundException {
        String query = "UPDATE user SET password = ?, name = ?, last_name = ?, role = ?, email = ?, phone = ?, status = ?, created_at = ? WHERE id = ?";
        try (Connection conn = ConnectionDbMySql.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getLast_name());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getPhone());
            stmt.setString(7, user.getStatus());
            stmt.setString(8, user.getCreated_at());
            stmt.setString(9, user.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new UserNotFoundException("El usuario con el id " + user.getId() + " no existe");
            }
        } catch (SQLException e) {
            throw e;// propaga  el error

        }

    }

    //metodo para eliminar un usuario
    public void deleteUser(String id) throws UserNotFoundException, SQLException, ClassNotFoundException {
        String query = "DELETE FROM user WHERE id = ?";
        try (Connection conn = ConnectionDbMySql.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new UserNotFoundException("El usuario con el id " + id + " no existe");
            }
        } catch (SQLException e) {
            throw e;// propaga  el error
        }

    }

    //metodo para obtener un usuario por su id
    public User getUserById(String id) throws UserNotFoundException, SQLException, ClassNotFoundException {
        String query = "SELECT * FROM user WHERE id = ?";
        User user = null;
        try (Connection conn = ConnectionDbMySql.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(
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
            } else {
                throw new UserNotFoundException("El usuario con el id " + id + " no existe");
            }
        } catch (SQLException e) {
            throw e;// propaga  el error
        }
        return user;

    }

    //metodo para autenticar un usuario por email y password
    public User getUserByEmailAndPassword(String email, String password) throws UserNotFoundException, SQLException, ClassNotFoundException {
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
        User user = null;
        try {
            Connection conn = ConnectionDbMySql.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(
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
            } else {
                var message = "El usuario con el email " + email + " no existe";
                throw new UserNotFoundException(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    //metodo para obtener un usuario por email

    public User getUserByEmail(String email) throws UserNotFoundException, SQLException, ClassNotFoundException {
        String query = "SELECT * FROM user WHERE email = ?";
        User user = null;
        try {
            Connection conn = ConnectionDbMySql.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(
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
            } else {
                throw new UserNotFoundException("El usuario con el email " + email + " no existe");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    //metodo para buscar usuarios por nombre o email
    public List<User> searchUsers(String nameOrEmail) throws UserNotFoundException, SQLException, ClassNotFoundException {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user WHERE name = ? OR email = ?";
        try {
            Connection conn = ConnectionDbMySql.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + nameOrEmail + "%");
            stmt.setString(2, "%" + nameOrEmail + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                userList.add(new User(
                        rs.getString("id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("status"),
                        rs.getString("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
