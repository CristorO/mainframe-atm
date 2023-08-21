package bo.edu.ucb.sis213;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Operations {
    public static BigDecimal consultarSaldo(int idUser) {
        Conection conection = new Conection();
        BigDecimal saldo = BigDecimal.valueOf(0.00);
        String query = "SELECT saldo FROM usuarios WHERE id = ?";
        try{
            PreparedStatement preparedStatement = conection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                saldo = resultSet.getBigDecimal("saldo");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return saldo;
    }

    public static boolean realizarDeposito(int idUser, BigDecimal cantidad) {
        Conection conection = new Conection();
        BigDecimal saldo = consultarSaldo(idUser);
        String queryUsuarios = "UPDATE usuarios SET saldo=? WHERE id=?";
        String queryHistorico = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (?, 'deposito', ?)";

        try {
            if (cantidad.intValue() > 0) {
                PreparedStatement PSUsuarios = conection.getConnection().prepareStatement(queryUsuarios);
                PreparedStatement PSHistorico = conection.getConnection().prepareStatement(queryHistorico);
                saldo = saldo.add(cantidad);

                PSUsuarios.setBigDecimal(1, saldo);
                PSUsuarios.setInt(2, idUser);
                PSUsuarios.executeUpdate();

                PSHistorico.setInt(1, idUser);
                PSHistorico.setBigDecimal(2, cantidad);
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

    public static boolean realizarRetiro(int idUser, BigDecimal cantidad) {
        Conection conection = new Conection();
        String queryUsuarios = "UPDATE usuarios SET saldo=? WHERE id=?";
        String queryHistorico = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (?, 'retiro', ?)";
        BigDecimal saldo = consultarSaldo(idUser);

        try {
            if (cantidad.intValue() > 0 && cantidad.intValue() < saldo.intValue()) {
                PreparedStatement PSUsuarios = conection.getConnection().prepareStatement(queryUsuarios);
                PreparedStatement PSHistorico = conection.getConnection().prepareStatement(queryHistorico);
                saldo = saldo.subtract(cantidad);

                PSUsuarios.setBigDecimal(1, saldo);
                PSUsuarios.setInt(2, idUser);
                PSUsuarios.executeUpdate();

                PSHistorico.setInt(1, idUser);
                PSHistorico.setBigDecimal(2, cantidad);
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
