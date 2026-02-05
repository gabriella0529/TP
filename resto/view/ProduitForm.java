package view;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ProduitForm extends JDialog {
    private JTextField txtNom = new JTextField(20);
    private JComboBox<String> comboCategories = new JComboBox<>();
    private JTextField txtPrix = new JTextField(10);
    private JTextField txtStock = new JTextField(10);
    private JTextField txtSeuil = new JTextField(10);
    private JButton btnEnregistrer = new JButton("Enregistrer");
    private JButton btnAnnuler = new JButton("Annuler");
    private boolean isValide = false;

    public ProduitForm(Frame parent, Map<Integer, String> categories) {
        super(parent, "Ajouter un Produit", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        // Remplir le combo avec les libellés de catégories
        for (String libelle : categories.values()) {
            comboCategories.addItem(libelle);
        }

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nom du produit :"));
        panel.add(txtNom);
        panel.add(new JLabel("Catégorie :"));
        panel.add(comboCategories);
        panel.add(new JLabel("Prix de vente (€) :"));
        panel.add(txtPrix);
        panel.add(new JLabel("Stock initial :"));
        panel.add(txtStock);
        panel.add(new JLabel("Seuil d'alerte :"));
        panel.add(txtSeuil);

        panel.add(btnEnregistrer);
        panel.add(btnAnnuler);

        add(panel);

        btnAnnuler.addActionListener(e -> dispose());
        btnEnregistrer.addActionListener(e -> {
            isValide = true;
            dispose();
        });
    }

    // Getters pour récupérer les saisies
    public String getNom() {
        return txtNom.getText();
    }

    public String getCategorieSelectionnee() {
        return (String) comboCategories.getSelectedItem();
    }

    public String getPrix() {
        return txtPrix.getText();
    }

    public String getStock() {
        return txtStock.getText();
    }

    public String getSeuil() {
        return txtSeuil.getText();
    }

    public boolean isConfirmed() {
        return isValide;
    }
}