package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StockPanel extends JPanel {
    private JComboBox<String> comboProduits = new JComboBox<>();
    private JTable tableMouvements;
    private DefaultTableModel tableModel;
    private JButton btnEntree = new JButton("Enregistrer une Entrée");
    private JButton btnSortie = new JButton("Enregistrer une Sortie");
    private JLabel lblStockActuel = new JLabel("Stock Actuel : -");

    public StockPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Zone Haut : Sélection du produit
        JPanel pnlHaut = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlHaut.add(new JLabel("Produit :"));
        pnlHaut.add(comboProduits);
        pnlHaut.add(lblStockActuel);
        add(pnlHaut, BorderLayout.NORTH);

        // Zone Centre : Historique
        String[] colonnes = { "Date", "Type", "Quantité", "Motif" };
        tableModel = new DefaultTableModel(colonnes, 0);
        tableMouvements = new JTable(tableModel);
        add(new JScrollPane(tableMouvements), BorderLayout.CENTER);

        // Zone Bas : Actions
        JPanel pnlBas = new JPanel();
        pnlBas.add(btnEntree);
        pnlBas.add(btnSortie);
        add(pnlBas, BorderLayout.SOUTH);
    }

    // Getters
    public JComboBox<String> getComboProduits() {
        return comboProduits;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getBtnEntree() {
        return btnEntree;
    }

    public JButton getBtnSortie() {
        return btnSortie;
    }

    public void setStockActuel(int qte) {
        lblStockActuel.setText("Stock Actuel : " + qte);
    }
}
