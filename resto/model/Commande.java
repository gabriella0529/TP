package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente une commande passée par un client.
 */
public class Commande {
    public enum Etat {
        EN_COURS, VALIDEE, ANNULEE
    }

    private int id;
    private LocalDateTime date;
    private Etat etat;
    private double total;
    private List<LigneCommande> lignes;

    public Commande(int id, LocalDateTime date, Etat etat, double total) {
        this.id = id;
        this.date = date;
        this.etat = etat;
        this.total = total;
        this.lignes = new ArrayList<>();
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Etat getEtat() {
        return etat;
    }

    public double getTotal() {
        return total;
    }

    public List<LigneCommande> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommande> lignes) {
        this.lignes = lignes;
    }

    public void ajouterLigne(LigneCommande ligne) {
        this.lignes.add(ligne);
        this.total += ligne.getMontantLigne();
    }
}
