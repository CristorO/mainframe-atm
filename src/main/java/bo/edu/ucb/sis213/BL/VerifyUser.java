package bo.edu.ucb.sis213.BL;

import bo.edu.ucb.sis213.dao.UsuariosDB;

public class VerifyUser {
    public static int attemps = -3;
    public static int validarUsuario(String user, int pass) {
        UsuariosDB usuariosDB = new UsuariosDB();
        int usuario = usuariosDB.getUserByIdPin(user, pass);

        if (usuario > 0) {
            return usuario;
        }
        attemps += 1;
        return attemps;
    }
}
