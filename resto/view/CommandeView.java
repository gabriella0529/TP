package view;

import java.util.Scanner;

public class CommandeView {
    private Scanner scanner = new Scanner(System.in);

    public int afficherMenuEtSaisirId() {
        System.out.println("\n--- NOUVELLE COMMANDE ---");
        System.out.print("Entrez l'ID du produit (ou 0 pour terminer) : ");
        return scanner.nextInt();
    }

    public int saisirQuantite() {
        System.out.print("Quantité : ");
        return scanner.nextInt();
    }

    public void afficherMessage(String message) {
        System.out.println("[INFO] " + message);
    }
}