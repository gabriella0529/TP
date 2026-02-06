package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CommandePanel extends JPanel {
    // Composants pour le haut (Sélection produit)
    private JComboBox<String> comboProduits = new JComboBox<>();
    private JTextField txtQuantite = new JTextField(5);
    private JButton btnAjouterLigne = new JButton("Ajouter au panier");

    // Composants pour le centre (Le panier)
    private JTable tablePanier;
    private DefaultTableModel tableModel;
    private JLabel lblTotal = new JLabel("Total : 0 FCFA");

    // Composants pour le bas (Actions de commande)
    private JButton btnValider = new JButton("Valider la Commande");
    private JButton btnAnnuler = new JButton("Annuler");
    private JButton btnSupprimerLigne = new JButton("Retirer du panier");

    public CommandePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- ZONE HAUT : SELECTION ---
        JPanel pnlSaisie = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSaisie.add(new JLabel("Produit :"));
        pnlSaisie.add(comboProduits);
        pnlSaisie.add(new JLabel("Quantité :"));
        pnlSaisie.add(txtQuantite);
        pnlSaisie.add(btnAjouterLigne);
        add(pnlSaisie, BorderLayout.NORTH);

        // --- ZONE CENTRE : TABLEAU ---
        String[] colonnes = { "ID Produit", "Désignation", "Prix Unitaire", "Quantité", "Montant Ligne" };
        tableModel = new DefaultTableModel(colonnes, 0);
        tablePanier = new JTable(tableModel);
        add(new JScrollPane(tablePanier), BorderLayout.CENTER);

        // --- ZONE BAS : TOTAL ET ACTIONS ---
        JPanel pnlBas = new JPanel(new BorderLayout());

        lblTotal.setFont(new Font("Arial", Font.BOLD, 20));
        lblTotal.setForeground(Color.BLUE);
        pnlBas.add(lblTotal, BorderLayout.NORTH);

        JPanel pnlBoutons = new JPanel();
        pnlBoutons.add(btnSupprimerLigne);
        pnlBoutons.add(btnValider);
        pnlBoutons.add(btnAnnuler);
        pnlBas.add(pnlBoutons, BorderLayout.SOUTH);

        add(pnlBas, BorderLayout.SOUTH);
    }

    // Getters pour le contrôleur
    public JComboBox<String> getComboProduits() {
        return comboProduits;
    }

    public JTextField getTxtQuantite() {
        return txtQuantite;
    }

    public JButton getBtnAjouterLigne() {
        return btnAjouterLigne;
    }

    public JButton getBtnValider() {
        return btnValider;
    }

    public JButton getBtnAnnuler() {
        return btnAnnuler;
    }

    public JButton getBtnSupprimerLigne() {
        return btnSupprimerLigne;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getTablePanier() {
        return tablePanier;
    }

    public void setTotal(double total) {
        lblTotal.setText(String.format("Total : %.0f FCFA", total));
    }
}