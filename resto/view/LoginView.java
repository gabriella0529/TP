package view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField txtLogin = new JTextField(15);
    private JPasswordField txtPassword = new JPasswordField(15);
    private JButton btnConnexion = new JButton("Se connecter");

    public LoginView() {
        setTitle("Authentification - Luxury Kitchen");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Mise en page avec GridBagLayout pour un rendu centré et propre
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Utilisateur :"), gbc);
        gbc.gridx = 1;
        panel.add(txtLogin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnConnexion, gbc);

        add(panel);
    }

    // Getters pour le contrôleur
    public String getLogin() {
        return txtLogin.getText();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public JButton getBtnConnexion() {
        return btnConnexion;
    }
}