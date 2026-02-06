package view;

import javax.swing.*;

import java.awt.*;

public class MainView extends JFrame {
    // Composants de l'interface
    private JButton btnProduits = new JButton("Produits");
    private JButton btnCategories = new JButton("Catégories");
    private JButton btnStocks = new JButton("Stocks");
    private JButton btnCommandes = new JButton("Nouvelle Commande");
    private JButton btnDashboard = new JButton("Tableau de Bord");
    private JPanel container = new JPanel(new CardLayout()); // Pour changer de page

    public MainView() {
        setTitle("Luxury Kitchen - Accueil");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centre la fenêtre

        // Barre d'outils (Navigation)
        JToolBar toolBar = new JToolBar();
        toolBar.add(btnDashboard);
        toolBar.add(btnCategories);
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

    public JButton getBtnCategories() {
        return btnCategories;
    }

    public JButton getBtnDashboard() {
        return btnDashboard;
    }

    public JPanel getMainContainer() {
        return container;
    }
}