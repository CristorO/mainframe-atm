package bo.edu.ucb.sis213.UI;

import bo.edu.ucb.sis213.VerifyUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class logIn extends JFrame {
    private JTextField tFUser;
    private JTextField tFPass;
    private JButton ingresarButton;
    private JButton exitButton;
    private JPanel inPanel;

    public logIn() {
        setTitle("ATM");
        setSize(550, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        tFUser = new JTextField(15);
        tFPass = new JPasswordField(15);
        ingresarButton = new JButton("Ingresar");
        exitButton = new JButton("Salir");

        inPanel = new JPanel();
        inPanel.setLayout(null); // Usamos un layout nulo para ubicar manualmente los componentes

        JLabel inicioLabel = new JLabel("Inicio de Sesion");
        inicioLabel.setBounds(180, 20, 150, 20);
        inPanel.add(inicioLabel);

        JLabel usuarioLabel = new JLabel("Usuario");
        usuarioLabel.setBounds(80, 100, 100, 20);
        inPanel.add(usuarioLabel);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(80, 150, 100, 20);
        inPanel.add(passwordLabel);

        tFUser.setBounds(180, 100, 150, 20);
        inPanel.add(tFUser);

        tFPass.setBounds(180, 150, 150, 20);
        inPanel.add(tFPass);

        ingresarButton.setBounds(150, 200, 100, 30);
        inPanel.add(ingresarButton);

        exitButton.setBounds(280, 200, 100, 30);
        inPanel.add(exitButton);

        setContentPane(inPanel);


        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                VerifyUser verifyUser = new VerifyUser();

                String userName = tFUser.getText();
                int pass = Integer.parseInt(tFPass.getText());

                int attemps = verifyUser.validarUsuario(userName, pass);

                if (attemps > 0){
                    JOptionPane.showMessageDialog(null, "Acceso concedido, Bienvenido");
                    menu menu = new menu(attemps);
                    menu.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o password incorrecto, le quedan " + (attemps * -1) + " intentos");
                    if (attemps == 0) {
                        dispose();
                    }
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }
}
