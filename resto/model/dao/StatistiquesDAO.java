package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatistiquesDAO {

    // Calcule le CA pour une date précise
    public double obtenirChiffreAffaireJour(String date) {
        String sql = "SELECT SUM(total) FROM Commande WHERE DATE(date_cmd) = ? AND etat = 'VALIDÉE'";
        try (Connection conn = Database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Liste les produits en rupture ou sous le seuil
    public List<Object[]> obtenirAlertesStock() {
        List<Object[]> alertes = new ArrayList<>();
        String sql = "SELECT nom, stock_actuel, seuil_alerte FROM Produit WHERE stock_actuel <= seuil_alerte";
        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                alertes.add(new Object[] { rs.getString("nom"), rs.getInt("stock_actuel"), rs.getInt("seuil_alerte") });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alertes;
    }

    // Récupère les top 5 produits vendus
    public List<Object[]> obtenirTopProduitsVendus() {
        List<Object[]> top = new ArrayList<>();
        String sql = "SELECT p.nom, SUM(lc.quantite) as total_vendu " +
                "FROM LigneCommande lc " +
                "JOIN Produit p ON lc.id_prod = p.id_prod " +
                "GROUP BY p.id_prod, p.nom " +
                "ORDER BY total_vendu DESC LIMIT 5";
        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                top.add(new Object[] { rs.getString("nom"), rs.getInt("total_vendu") });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return top;
    }
}