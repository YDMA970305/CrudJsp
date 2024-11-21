/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Services;

/**
 *
 * @author Yesid Martinez
 */
import Business.Exceptions.DuplicateUserException;
import Business.Exceptions.UserNotFoundException;
import Infrastructure.Persistence.UserCRUD;
import Domain.Model.User;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

public class UserService {

    //constructor
    private UserCRUD userCRUD;

    public UserService() {
        userCRUD = new UserCRUD();
    }

    //metodo para obtener todos los usuarios de la base de datos
    public List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        return userCRUD.getAllUsers();
    }

    //metodo para agregar un usuario a la base de datos
    public void createUser(String id, String password, String name, String last_name, String role, String email, String phone, String status) throws DuplicateUserException, SQLException, ClassNotFoundException {
        LocalTime horaActual = LocalTime.now();
        User user = new User(id, password, name, last_name, role, email, phone, status, horaActual.toString());
        userCRUD.addUser(user);
    }

    //Metodo para actualizar usuario
    public void updateUser(String id, String password, String name, String last_name, String role, String email, String phone, String status)
            throws UserNotFoundException, SQLException, ClassNotFoundException {
        User user = new User(id, password, name, last_name, role, email, phone, status, null);
        userCRUD.updateUser(user);
    }

    //metodo para eliminar un usuario
    public void deleteUser(String id)
            throws UserNotFoundException, SQLException, ClassNotFoundException {
        userCRUD.deleteUser(id);
    }

    //metodo para obtener un usuario por su id
    public User getUserById(String id)
            throws UserNotFoundException, SQLException, ClassNotFoundException {
        return userCRUD.getUserById(id);
    }

    //metodo para autenticar un usuario por login
    public User loginUser(String email, String password)
            throws UserNotFoundException, SQLException, ClassNotFoundException {
        User user = userCRUD.getUserByEmailAndPassword(email, password);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            throw new UserNotFoundException("Credenciales incorrectas no existe");
        }
    }

    //metodo para buscar usuarios por nombre o email
    public List<User> searchUsers(String nameOrEmail) throws UserNotFoundException, SQLException, ClassNotFoundException {
        return userCRUD.searchUsers(nameOrEmail);
    }

}
