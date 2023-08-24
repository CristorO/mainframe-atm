package bo.edu.ucb.sis213.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HistoricoDB {
    static Conection conection = new Conection();

    public static BigDecimal getSaldoById(int idUser) {
        String query = "SELECT saldo FROM usuarios WHERE id=?";
        BigDecimal saldo = BigDecimal.valueOf(0.00);
        try {
            PreparedStatement preparedStatement = conection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                saldo = resultSet.getBigDecimal("saldo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saldo;
    }

    public static void createHistorico(int idUser, String operacion, BigDecimal cantidad) {
        String query = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = conection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idUser);
            preparedStatement.setString(2, operacion);
            preparedStatement.setBigDecimal(3, cantidad);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
