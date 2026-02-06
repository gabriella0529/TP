package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private JLabel lblCA = new JLabel("Chiffre d'affaires du jour : 0 FCFA");
    private JTable tableAlertes;
    private DefaultTableModel modelAlertes;
    private JTable tableTopVentes;
    private DefaultTableModel modelTopVentes;

    public DashboardPanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // --- EN-TÊTE : CA ---
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlHeader.setOpaque(false);
        lblCA.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblCA.setForeground(new Color(34, 139, 34)); // Forest Green
        pnlHeader.add(lblCA);
        add(pnlHeader, BorderLayout.NORTH);

        // --- CENTRE : DEUX COLONNES ---
        JPanel pnlMain = new JPanel(new GridLayout(1, 2, 20, 20));
        pnlMain.setOpaque(false);

        // 1. Alertes Stock
        JPanel pnlAlertes = createSectionPanel("⚠️ RÉAPPROVISIONNEMENT NÉCESSAIRE", Color.RED);
        String[] colAlertes = { "Produit", "Stock", "Seuil" };
        modelAlertes = new DefaultTableModel(colAlertes, 0);
        tableAlertes = new JTable(modelAlertes);
        tableAlertes.setRowHeight(25);
        pnlAlertes.add(new JScrollPane(tableAlertes), BorderLayout.CENTER);

        // 2. Top Ventes
        JPanel pnlTopVentes = createSectionPanel("⭐ TOP 5 PRODUITS VENDUS", new Color(0, 102, 204));
        String[] colTop = { "Produit", "Qte Totale" };
        modelTopVentes = new DefaultTableModel(colTop, 0);
        tableTopVentes = new JTable(modelTopVentes);
        tableTopVentes.setRowHeight(25);
        pnlTopVentes.add(new JScrollPane(tableTopVentes), BorderLayout.CENTER);

        pnlMain.add(pnlAlertes);
        pnlMain.add(pnlTopVentes);
        add(pnlMain, BorderLayout.CENTER);
    }

    private JPanel createSectionPanel(String title, Color titleColor) {
        JPanel pnl = new JPanel(new BorderLayout(10, 10));
        pnl.setOpaque(false);
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(titleColor);
        pnl.add(lblTitle, BorderLayout.NORTH);
        pnl.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 2));
        return pnl;
    }

    public void setChiffreAffaire(double ca) {
        lblCA.setText(String.format("Chiffre d'affaires du jour : %.0f FCFA", ca));
    }

    public void ajouterAlerte(Object[] ligne) {
        modelAlertes.addRow(ligne);
    }

    public void viderAlertes() {
        modelAlertes.setRowCount(0);
    }

    public void ajouterTopVente(Object[] ligne) {
        modelTopVentes.addRow(ligne);
    }

    public void viderTopVentes() {
        modelTopVentes.setRowCount(0);
    }
}