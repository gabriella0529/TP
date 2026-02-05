package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProduitPanel extends JPanel {
    private JTable tableProduits;
    private DefaultTableModel tableModel;
    private JButton btnAjouter = new JButton("Ajouter");
    private JButton btnModifier = new JButton("Modifier");
    private JButton btnSupprimer = new JButton("Supprimer");

    public ProduitPanel() {
        setLayout(new BorderLayout());

        // Configuration du tableau
        String[] colonnes = { "ID", "Nom", "Catégorie", "Prix", "Stock", "Alerte" };
        tableModel = new DefaultTableModel(colonnes, 0);
        tableProduits = new JTable(tableModel);

        add(new JScrollPane(tableProduits), BorderLayout.CENTER);

        // Barre d'outils pour les actions
        JPanel pnlActions = new JPanel();
        pnlActions.add(btnAjouter);
        pnlActions.add(btnModifier);
        pnlActions.add(btnSupprimer);
        add(pnlActions, BorderLayout.SOUTH);
    }

    // Méthodes pour mettre à jour le tableau
    public void viderTableau() {
        tableModel.setRowCount(0);
    }

    public void ajouterLigne(Object[] ligne) {
        tableModel.addRow(ligne);
    }

    // Getters pour le contrôleur
    public JButton getBtnAjouter() {
        return btnAjouter;
    }

    public JButton getBtnModifier() {
        return btnModifier;
    }

    public JButton getBtnSupprimer() {
        return btnSupprimer;
    }

    public JTable getTableProduits() {
        return tableProduits;
    }
}