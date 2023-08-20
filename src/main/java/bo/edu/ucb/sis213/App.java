package bo.edu.ucb.sis213;

import bo.edu.ucb.sis213.UI.logIn;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class App {
    private static int usuarioId;
    private static double saldo;
    private static int pinActual;

    public static void main(String[] args) {
        Conection conection = new Conection();
        VerifyUser verify = new VerifyUser();

        logIn logIn = new logIn();
        logIn.setVisible(true);
    }
}
