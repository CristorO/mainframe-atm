package bo.edu.ucb.sis213;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ChangePassword {
    public static boolean verifyPass(int idUser, int pass) {
        Conection conection = new Conection();
        String query = "SELECT pass FROM usuarios WHERE id=? AND pass=?";

        try {
            PreparedStatement preparedStatement = conection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idUser);
            preparedStatement.setInt(2, pass);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public static boolean changePass(int idUser, int newPass, int confirmPass) {
        try {
            if (newPass == confirmPass) {
                Conection conection = new Conection();
                String query = "UPDATE usuarios SET pass=? WHERE id=?";
                PreparedStatement preparedStatement = conection.getConnection().prepareStatement(query);
                preparedStatement.setInt(1, newPass);
                preparedStatement.setInt(2, idUser);
                preparedStatement.executeUpdate();

                preparedStatement.close();

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
