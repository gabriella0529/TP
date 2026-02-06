package model.dao;

import model.MouvementStock;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MouvementStockDAO {

    /**
     * Enregistre un mouvement et met à jour le stock du produit dans une
     * transaction.
     */
    public void enregistrer(MouvementStock mvt) throws SQLException {
        Connection conn = null;
        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            // 1. Enregistrer le mouvement
            String sqlMvt = "INSERT INTO MouvementStock (type_mvt, id_prod, quantite, motif) VALUES (?, ?, ?, ?)";
            PreparedStatement psMvt = conn.prepareStatement(sqlMvt);
            psMvt.setString(1, mvt.getType().toString().equals("ENTREE") ? "ENTRÉE" : "SORTIE");
            psMvt.setInt(2, mvt.getIdProduit());
            psMvt.setInt(3, mvt.getQuantite());
            psMvt.setString(4, mvt.getMotif());
            psMvt.executeUpdate();

            // 2. Mettre à jour le stock du produit
            String sqlStock = "UPDATE Produit SET stock_actuel = stock_actuel " +
                    (mvt.getType() == MouvementStock.Type.ENTREE ? "+ ?" : "- ?") +
                    " WHERE id_prod = ?";
            PreparedStatement psStock = conn.prepareStatement(sqlStock);
            psStock.setInt(1, mvt.getQuantite());
            psStock.setInt(2, mvt.getIdProduit());

            int affected = psStock.executeUpdate();
            if (affected == 0)
                throw new SQLException("Produit introuvable lors de la mise à jour du stock.");

            conn.commit();
        } catch (SQLException e) {
            if (conn != null)
                conn.rollback();
            throw e;
        } finally {
            if (conn != null)
                conn.setAutoCommit(true);
        }
    }

    public List<MouvementStock> listerParProduit(int idProd) {
        List<MouvementStock> liste = new ArrayList<>();
        String sql = "SELECT * FROM MouvementStock WHERE id_prod = ? ORDER BY date_mvt DESC";
        try (Connection conn = Database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProd);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MouvementStock.Type type = rs.getString("type_mvt").equals("ENTRÉE") ? MouvementStock.Type.ENTREE
                        : MouvementStock.Type.SORTIE;
                liste.add(new MouvementStock(
                        rs.getInt("id_mvt"),
                        type,
                        rs.getInt("id_prod"),
                        rs.getInt("quantite"),
                        rs.getTimestamp("date_mvt").toLocalDateTime(),
                        rs.getString("motif")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
}
