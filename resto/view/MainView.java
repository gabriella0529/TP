package view;

import javax.swing.*;

import java.awt.*;

public class MainView extends JFrame {
    // Composants de l'interface
    private JButton btnProduits = new JButton("Produits");
    private JButton btnStocks = new JButton("Stocks");
    private JButton btnCommandes = new JButton("NouvelleCommande");
    private JPanel container = new JPanel(new CardLayout()); // Pour changer de page

    public MainView() {
        setTitle("Luxury Kitchen - Accueil");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centre la fenêtre

        // Barre d'outils (Navigation)
        JToolBar toolBar = new JToolBar();
        toolBar.add(btnProduits);
        toolBar.add(btnStocks);
        toolBar.add(btnCommandes);

        add(toolBar, BorderLayout.NORTH);
        add(container, BorderLayout.CENTER);
    }

    // Getters pour que le contrôleur puisse ajouter des écouteurs (Listeners)
    public JButton getBtnProduits() {
        return btnProduits;
    }

    public JButton getBtnStocks() {
        return btnStocks;
    }

    public JButton getBtnCommandes() {
        return btnCommandes;
    }

    public JPanel getMainContainer() {
        return container;
    }
}