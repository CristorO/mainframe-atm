package bo.edu.ucb.sis213;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VerifyUser {
    public static int attemps = -3;
    public static int validarUsuario(String user, int pass) {
        Conection conection = new Conection();
        int usuarioId = 0;
        String query = "SELECT id FROM usuarios WHERE alias = ? AND pass = ?";
        try {
            PreparedStatement preparedStatement = conection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setInt(2, pass);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                usuarioId = resultSet.getInt("id");
                return usuarioId;
            }
            attemps += 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attemps;
    }
}
