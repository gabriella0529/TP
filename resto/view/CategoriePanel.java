package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CategoriePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtLibelle = new JTextField(15);
    private JButton btnAjouter = new JButton("Ajouter");
    private JButton btnSupprimer = new JButton("Supprimer");

    public CategoriePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Haut : Formulaire d'ajout
        JPanel pnlSaisie = new JPanel();
        pnlSaisie.add(new JLabel("Libellé :"));
        pnlSaisie.add(txtLibelle);
        pnlSaisie.add(btnAjouter);
        add(pnlSaisie, BorderLayout.NORTH);

        // Centre : Table
        String[] colonnes = { "ID", "Libellé" };
        model = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bas : Boutons actions
        JPanel pnlActions = new JPanel();
        pnlActions.add(btnSupprimer);
        add(pnlActions, BorderLayout.SOUTH);
    }

    public String getLibelle() {
        return txtLibelle.getText().trim();
    }

    public void setLibelle(String s) {
        txtLibelle.setText(s);
    }

    public JButton getBtnAjouter() {
        return btnAjouter;
    }

    public JButton getBtnSupprimer() {
        return btnSupprimer;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public int getSelectedId() {
        int row = table.getSelectedRow();
        if (row != -1) {
            return (int) model.getValueAt(row, 0);
        }
        return -1;
    }

    public void ajouterLigne(Object[] data) {
        model.addRow(data);
    }

    public void viderTable() {
        model.setRowCount(0);
    }
}
