package bo.edu.ucb.sis213.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuariosDB {
    static Conection conection = new Conection();
    public static int getUserByIdPin(String user, int pin) {
        String query = "SELECT id FROM usuarios WHERE alias=? AND pin=?";
        try {
            PreparedStatement preparedStatement = conection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setInt(2, pin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean getPinByIdPin(int idUser, int pin) {
        String query = "SELECT pin FROM usuarios WHERE id=? AND pin=?";

        try {
            PreparedStatement preparedStatement = conection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idUser);
            preparedStatement.setInt(2, pin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void updatePin(int idUser, int newPin) {
        String query = "UPDATE usuarios SET pin=? WHERE id=?";

        try {
            PreparedStatement preparedStatement = conection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, newPin);
            preparedStatement.setInt(2, idUser);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateSaldo(int idUser, BigDecimal saldo) {
        String query = "UPDATE usuarios SET saldo=? WHERE id=?";
        try {
            PreparedStatement preparedStatement = conection.getConnection().prepareStatement(query);
            preparedStatement.setBigDecimal(1, saldo);
            preparedStatement.setInt(2, idUser);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
