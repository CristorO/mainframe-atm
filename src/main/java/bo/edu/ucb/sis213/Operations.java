package bo.edu.ucb.sis213;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Operations {
    public static double consultarSaldo(int idUser) {
        Conection conection = new Conection();
        double saldo = 0.0;
        String query = "SELECT saldo FROM usuarios WHERE id = ?";
        try{
            PreparedStatement preparedStatement = conection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                saldo = resultSet.getDouble("saldo");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return saldo;
    }

    public static boolean realizarDeposito(int idUser, double cantidad) {
        Conection conection = new Conection();
        double saldo = consultarSaldo(idUser);
        String queryUsuarios = "UPDATE usuarios SET saldo=? WHERE id=?";
        String queryHistorico = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (?, 'deposito', ?)";

        try {
            if (cantidad > 0) {
                PreparedStatement PSUsuarios = conection.getConnection().prepareStatement(queryUsuarios);
                PreparedStatement PSHistorico = conection.getConnection().prepareStatement(queryHistorico);
                saldo += cantidad;

                PSUsuarios.setDouble(1, saldo);
                PSUsuarios.setInt(2, idUser);
                PSUsuarios.executeUpdate();

                PSHistorico.setInt(1, idUser);
                PSHistorico.setDouble(2, cantidad);
                PSHistorico.executeUpdate();

                PSUsuarios.close();
                PSHistorico.close();
                return true;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean realizarRetiro(int idUser, double cantidad) {
        Conection conection = new Conection();
        String queryUsuarios = "UPDATE usuarios SET saldo=? WHERE id=?";
        String queryHistorico = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (?, 'retiro', ?)";
        double saldo = consultarSaldo(idUser);

        try {
            if (cantidad > 0 && cantidad < saldo) {
                PreparedStatement PSUsuarios = conection.getConnection().prepareStatement(queryUsuarios);
                PreparedStatement PSHistorico = conection.getConnection().prepareStatement(queryHistorico);
                saldo -= cantidad;

                PSUsuarios.setDouble(1, saldo);
                PSUsuarios.setInt(2, idUser);
                PSUsuarios.executeUpdate();

                PSHistorico.setInt(1, idUser);
                PSHistorico.setDouble(2, cantidad);
                PSHistorico.executeUpdate();

                PSUsuarios.close();
                PSHistorico.close();

                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
