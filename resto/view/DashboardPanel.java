package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private JLabel lblCA = new JLabel("Chiffre d'affaires du jour : 0.00 €");
    private JTable tableAlertes;
    private DefaultTableModel modelAlertes;

    public DashboardPanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // En-tête : Chiffre d'Affaires
        lblCA.setFont(new Font("Arial", Font.BOLD, 22));
        lblCA.setForeground(new Color(0, 102, 0)); // Vert sombre
        add(lblCA, BorderLayout.NORTH);

        // Centre : Tableau des alertes
        JPanel pnlCentre = new JPanel(new BorderLayout());
        pnlCentre.add(new JLabel("⚠️ PRODUITS À RÉAPPROVISIONNER"), BorderLayout.NORTH);

        String[] colonnes = { "Produit", "Stock Actuel", "Seuil d'Alerte" };
        modelAlertes = new DefaultTableModel(colonnes, 0);
        tableAlertes = new JTable(modelAlertes);

        // Colorer le tableau pour attirer l'attention
        tableAlertes.setGridColor(Color.RED);

        pnlCentre.add(new JScrollPane(tableAlertes), BorderLayout.CENTER);
        add(pnlCentre, BorderLayout.CENTER);
    }

    public void setChiffreAffaire(double ca) {
        lblCA.setText(String.format("Chiffre d'affaires du jour : %.2f €", ca));
    }

    public void ajouterAlerte(Object[] ligne) {
        modelAlertes.addRow(ligne);
    }

    public void viderAlertes() {
        modelAlertes.setRowCount(0);
    }
}