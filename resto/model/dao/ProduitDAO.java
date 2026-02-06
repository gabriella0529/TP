package model.dao;

import model.Produit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO {

    public List<Produit> listerTous() {
        List<Produit> liste = new ArrayList<>();
        String sql = "SELECT * FROM Produit";

        try (Connection conn = Database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(new Produit(
                        rs.getInt("id_prod"),
                        rs.getString("nom"),
                        rs.getDouble("prix_vente"),
                        rs.getInt("stock_actuel"),
                        rs.getInt("id_cat"),
                        rs.getInt("seuil_alerte")));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
        return liste;
    }

    public Produit trouverParId(int id) {
        String sql = "SELECT * FROM Produit WHERE id_prod = ?";
        try (Connection conn = Database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Produit(
                            rs.getInt("id_prod"),
                            rs.getString("nom"),
                            rs.getDouble("prix_vente"),
                            rs.getInt("stock_actuel"),
                            rs.getInt("id_cat"),
                            rs.getInt("seuil_alerte"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
        return null;
    }

    public void ajouter(Produit produit) {
        String sql = "INSERT INTO Produit (nom, prix_vente, stock_actuel, id_cat, seuil_alerte) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produit.getNom());
            ps.setDouble(2, produit.getPrix());
            ps.setInt(3, produit.getStock());
            ps.setInt(4, produit.getIdCategorie());
            ps.setInt(5, produit.getSeuilAlerte());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de l'ajout : " + e.getMessage());
            throw new RuntimeException("Impossible d'ajouter le produit", e);
        }
    }

    public void modifier(Produit produit) {
        String sql = "UPDATE Produit SET nom = ?, prix_vente = ?, stock_actuel = ?, id_cat = ?, seuil_alerte = ? WHERE id_prod = ?";
        try (Connection conn = Database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produit.getNom());
            ps.setDouble(2, produit.getPrix());
            ps.setInt(3, produit.getStock());
            ps.setInt(4, produit.getIdCategorie());
            ps.setInt(5, produit.getSeuilAlerte());
            ps.setInt(6, produit.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la modification : " + e.getMessage());
            throw new RuntimeException("Impossible de modifier le produit", e);
        }
    }

    public void supprimer(int idProd) throws SQLException {
        String sql = "DELETE FROM Produit WHERE id_prod = ?";

        try (Connection conn = Database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProd);
            ps.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            // Règle de gestion : gérer les dépendances avec les commandes
            throw new SQLException("Impossible de supprimer ce produit car il figure dans des commandes existantes.");
        }
    }
}
