package model.dao;

import java.sql.*;

public class UtilisateurDAO {
    public boolean authentifier(String login, String password) {
        String sql = "SELECT * FROM Utilisateur WHERE login = ? AND password = ?";
        try (Connection conn = Database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // Retourne vrai si un utilisateur correspond
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}