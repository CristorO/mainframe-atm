package bo.edu.ucb.sis213;

import java.util.Scanner;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class App {
    private static int usuarioId;
    private static double saldo;
    private static int pinActual;

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 3306;
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    private static final String DATABASE = "atm";

    public static Connection getConnection() throws SQLException {
        String jdbcUrl = String.format("jdbc:mysql://%s:%d/%s", HOST, PORT, DATABASE);
        try {
            // Asegúrate de tener el driver de MySQL agregado en tu proyecto
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL Driver not found.", e);
        }

        return DriverManager.getConnection(jdbcUrl, USER, PASSWORD);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int intentos = 3;

        System.out.println("Bienvenido al Cajero Automático.");

        Connection connection = null;
        try {
            connection = getConnection(); // Reemplaza esto con tu conexión real
        } catch (SQLException ex) {
            System.err.println("No se puede conectar a Base de Datos");
            ex.printStackTrace();
            System.exit(1);
        }
        

        while (intentos > 0) {
            System.out.print("Ingrese su PIN de 4 dígitos: ");
            int pinIngresado = scanner.nextInt();
            if (validarPIN(connection, pinIngresado)) {
                pinActual = pinIngresado;
                mostrarMenu(connection);
                break;
            } else {
                intentos--;
                if (intentos > 0) {
                    System.out.println("PIN incorrecto. Le quedan " + intentos + " intentos.");
                } else {
                    System.out.println("PIN incorrecto. Ha excedido el número de intentos.");
                    System.exit(0);
                }
            }
        }
    }

    public static boolean validarPIN(Connection connection, int pin) {
        String query = "SELECT id, saldo FROM usuarios WHERE pin = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, pin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                usuarioId = resultSet.getInt("id");
                saldo = resultSet.getDouble("saldo");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void mostrarMenu(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenú Principal:");
            System.out.println("1. Consultar saldo.");
            System.out.println("2. Realizar un depósito.");
            System.out.println("3. Realizar un retiro.");
            System.out.println("4. Cambiar PIN.");
            System.out.println("5. Salir.");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    consultarSaldo(connection);
                    break;
                case 2:
                    realizarDeposito(connection);
                    break;
                case 3:
                    realizarRetiro(connection);
                    break;
                case 4:
                    cambiarPIN(connection);
                    break;
                case 5:
                    System.out.println("Gracias por usar el cajero. ¡Hasta luego!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    public static void consultarSaldo(Connection connection) {
        String query = "SELECT saldo FROM usuarios WHERE id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, usuarioId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                System.out.println("Su saldo actual es: $" + resultSet.getDouble("saldo"));
            }         
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }

    public static void realizarDeposito(Connection connection) {
        String queryUsuarios = "UPDATE usuarios SET saldo=? WHERE id=?";
        String queryHistorico = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (?, 'deposito', ?)";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad a depositar: $");
        double cantidad = scanner.nextDouble();

        try {
            if (cantidad <= 0) {
                System.out.println("Cantidad no válida.");
            } else {
                PreparedStatement PSUsuarios = connection.prepareStatement(queryUsuarios);
                PreparedStatement PSHistorico = connection.prepareStatement(queryHistorico);
                saldo += cantidad;

                PSUsuarios.setDouble(1, saldo);
                PSUsuarios.setInt(2, usuarioId);
                PSUsuarios.executeUpdate();

                PSHistorico.setInt(1, usuarioId);
                PSHistorico.setDouble(2, cantidad);
                PSHistorico.executeUpdate();
                System.out.println("Depósito realizado con éxito. Su nuevo saldo es: $" + saldo);
                PSUsuarios.close();
                PSHistorico.close();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void realizarRetiro(Connection connection) {
        String queryUsuarios = "UPDATE usuarios SET saldo=? WHERE id=?";
        String queryHistorico = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (?, 'retiro', ?)";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad a retirar: $");
        double cantidad = scanner.nextDouble();

        try {
            if (cantidad <= 0) {
                System.out.println("Cantidad no válida.");
            } else if (cantidad > saldo) {
                System.out.println("Saldo insuficiente.");
            } else {
                PreparedStatement PSUsuarios = connection.prepareStatement(queryUsuarios);
                PreparedStatement PSHistorico = connection.prepareStatement(queryHistorico);
                saldo -= cantidad;

                PSUsuarios.setDouble(1, saldo);
                PSUsuarios.setInt(2, usuarioId);
                PSUsuarios.executeUpdate();

                PSHistorico.setInt(1, usuarioId);
                PSHistorico.setDouble(2, cantidad);
                PSHistorico.executeUpdate();
                System.out.println("Retiro realizado con éxito. Su nuevo saldo es: $" + saldo);
                PSUsuarios.close();
                PSHistorico.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void cambiarPIN(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese su PIN actual: ");
        int pinIngresado = scanner.nextInt();

        try {
            if (autenticarPIN(connection, pinIngresado)) {
                System.out.print("Ingrese su nuevo PIN: ");
                int nuevoPin = scanner.nextInt();
                System.out.print("Confirme su nuevo PIN: ");
                int confirmacionPin = scanner.nextInt();

                if (nuevoPin == confirmacionPin) {
                    String query = "UPDATE usuarios SET pin=? WHERE id=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, nuevoPin);
                    preparedStatement.setInt(2, usuarioId);
                    preparedStatement.executeUpdate();

                    System.out.println("PIN actualizado con éxito.");
                    preparedStatement.close();
                } else {
                    System.out.println("Los PINs no coinciden.");
                }
            } else {
                System.out.println("PIN incorrecto.");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        
    }

    public static boolean autenticarPIN(Connection connection, int pin){
        String query = "SELECT pin FROM usuarios WHERE pin = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, pin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
