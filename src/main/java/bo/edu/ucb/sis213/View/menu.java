package bo.edu.ucb.sis213.View;

import bo.edu.ucb.sis213.BL.ChangePin;
import bo.edu.ucb.sis213.BL.Operations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class menu extends JFrame{
    private JButton saldoButton;
    private JButton depositoButton;
    private JButton retiroButton;
    private JButton passwordButton;
    private JButton salirButton;
    private JPanel menuPanel;

    public menu(int idUser){
        setTitle("ATM");
        setSize(550, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        saldoButton = new JButton("Consultar saldo");
        depositoButton = new JButton("Realizar deposito");
        retiroButton = new JButton("Realizar retiro");
        passwordButton = new JButton("Cambiar password");
        salirButton = new JButton("Salir");

        menuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 1;
        gbc.gridy = 1;
        menuPanel.add(new JLabel("Menu de opciones"), gbc);

        gbc.gridy = 2;
        menuPanel.add(saldoButton, gbc);

        gbc.gridy = 3;
        menuPanel.add(depositoButton, gbc);

        gbc.gridy = 4;
        menuPanel.add(retiroButton, gbc);

        gbc.gridy = 5;
        menuPanel.add(passwordButton, gbc);

        gbc.gridy = 6;
        menuPanel.add(salirButton, gbc);

        setContentPane(menuPanel);

        saldoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Operations transactions = new Operations();
                BigDecimal saldo = transactions.consultarSaldo(idUser);
                JOptionPane.showMessageDialog(null, "Su saldo es de Bs. " + saldo);
            }
        });

        depositoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Operations transactions = new Operations();
                BigDecimal deposito = BigDecimal.valueOf(Long.parseLong(JOptionPane.showInputDialog("Ingrese el monto a depositar")));
                if(transactions.realizarDeposito(idUser, deposito)){
                    BigDecimal saldo = transactions.consultarSaldo(idUser);
                    JOptionPane.showMessageDialog(null, "Deposito realizado con exito, su saldo es de Bs. " + saldo);
                } else{
                    JOptionPane.showMessageDialog(null, "Monto invalido, favor de ingresar nuevamente");
                }
            }
        });

        retiroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Operations transactions = new Operations();
                BigDecimal retiro = BigDecimal.valueOf(Long.parseLong(JOptionPane.showInputDialog("Ingrese el monto a retirar")));

                if (transactions.realizarRetiro(idUser, retiro)) {
                    BigDecimal saldo = transactions.consultarSaldo(idUser);
                    JOptionPane.showMessageDialog(null, "Retiro realizado con exito, su saldo es de Bs. " + saldo);
                } else {
                    JOptionPane.showMessageDialog(null, "Monto invalido o saldo insuficiente, favor de ingresar nuevamente");
                }
            }
        });

        passwordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ChangePin changePin = new ChangePin();
                int pass = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su password"));

                if (changePin.verifyPin(idUser, pass)) {
                    int newPass = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su nuevo password"));
                    int confirmPass = Integer.parseInt(JOptionPane.showInputDialog("Confirme su password"));
                    if (changePin.changePin(idUser, newPass, confirmPass)) {
                        JOptionPane.showMessageDialog(null, "Password cambiado correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "Passwords no coinciden");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Password incorrecto");
                }
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }
}
