package bo.edu.ucb.sis213.BL;

import bo.edu.ucb.sis213.dao.Conection;
import bo.edu.ucb.sis213.dao.HistoricoDB;
import bo.edu.ucb.sis213.dao.UsuariosDB;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Operations {
    static HistoricoDB historicoDB = new HistoricoDB();
    static UsuariosDB usuariosDB = new UsuariosDB();
    public static BigDecimal consultarSaldo(int idUser) {
        return historicoDB.getSaldoById(idUser);
    }

    public static boolean realizarDeposito(int idUser, BigDecimal cantidad) {
        BigDecimal saldo = consultarSaldo(idUser);

        try {
            if (cantidad.intValue() > 0) {
                saldo = saldo.add(cantidad);
                historicoDB.createHistorico(idUser, "deposito", cantidad);
                usuariosDB.updateSaldo(idUser, saldo);

                return true;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean realizarRetiro(int idUser, BigDecimal cantidad) {
        BigDecimal saldo = consultarSaldo(idUser);

        try {
            if (cantidad.intValue() > 0 && cantidad.intValue() < saldo.intValue()) {
                saldo = saldo.subtract(cantidad);
                historicoDB.createHistorico(idUser, "retiro", cantidad);
                usuariosDB.updateSaldo(idUser, saldo);

                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
