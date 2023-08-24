package bo.edu.ucb.sis213.BL;

import bo.edu.ucb.sis213.dao.UsuariosDB;

public class ChangePin {
    static UsuariosDB usuariosDB = new UsuariosDB();
    public static boolean verifyPin(int idUser, int pin) {
        if (usuariosDB.getPinByIdPin(idUser, pin)) {
            return true;
        }

        return false;
    }

    public static boolean changePin(int idUser, int newPin, int confirmPin) {
        if (newPin == confirmPin) {
            usuariosDB.updatePin(idUser, newPin);
            return true;
        }
        return false;
    }
}
