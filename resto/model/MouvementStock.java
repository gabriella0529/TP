package model;

import java.time.LocalDateTime;

/**
 * Représente un mouvement de stock (entrée ou sortie).
 */
public class MouvementStock {
    public enum Type {
        ENTREE, SORTIE
    }

    private int id;
    private Type type;
    private int idProduit;
    private int quantite;
    private LocalDateTime date;
    private String motif;

    public MouvementStock(int id, Type type, int idProduit, int quantite, LocalDateTime date, String motif) {
        this.id = id;
        this.type = type;
        this.idProduit = idProduit;
        this.quantite = quantite;
        this.date = date;
        this.motif = motif;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public int getQuantite() {
        return quantite;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getMotif() {
        return motif;
    }
}
