package ec.edu.ups.vista.Usuario;

import javax.swing.*;

public class RegistrarUsuario {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton registrarButton;
    private JButton cancelarButton;

    public RegistrarUsuario() {
        JFrame frame = new JFrame("Registrar Usuario");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public JTextField getTextField3() {
        return textField3;
    }

    public JTextField getTextField4() {
        return textField4;
    }

    public JButton getRegistrarButton() {
        return registrarButton;
    }

    public JButton getCancelarButton() {
        return cancelarButton;
    }

}
