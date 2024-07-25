import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Magasin {
    private HashMap<String, Produit> produits;

    public Magasin() {
        produits = new HashMap<>();
    }

    // Méthodes pour ajouter, supprimer, modifier, rechercher, lister, etc.
    public void ajouterProduit(Produit produit) {
        produits.put(produit.getId(), produit);
    }
    public void supprimerProduit(String id) {
        produits.remove(id);
    }
    public void modifierProduit(String id, Produit produitModifie) {
        produits.put(id, produitModifie);
    }
    public Produit rechercherProduitParNom(String nom) {
        for (Produit produit : produits.values()) {
            if (produit.getNom().equalsIgnoreCase(nom)) {
                return produit;
            }
        }
        return null;
    }
    public List<Produit> listerProduitsParLettre(char lettre) {
        List<Produit> resultats = new ArrayList<>();
        for (Produit produit : produits.values()) {
            if (produit.getNom().charAt(0) == lettre) {
                resultats.add(produit);
            }
        }
        return resultats;
    }
    public int nombreDeProduitsEnStock() {
        return produits.size();
    }
    public List<Produit> afficherProduitsParCategorie(Class<?> categorie) {
        List<Produit> resultats = new ArrayList<>();
        for (Produit produit : produits.values()) {
            if (categorie.isInstance(produit)) {
                resultats.add(produit);
            }
        }
        return resultats;
    }
    public double valeurTotaleDuStock() {
        double total = 0;
        for (Produit produit : produits.values()) {
            total += produit.getPrix() * produit.getQuantite();
        }
        return total;
    }
    public Produit rechercherProduitParId(String id) {
        return produits.get(id);
    }
    public void sauvegarderProduits(String fichier) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
            for (Produit produit : produits.values()) {
                writer.write(produit.toString());
                writer.newLine();
            }
        }
    }

    // Méthode pour charger les produits depuis un fichier .txt
    public void chargerProduits(String fichier) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] details = ligne.split(",");
                if (details.length < 5) {
                    // Gérer l'erreur ou ignorer la ligne
                    continue;
                }
                String type = details[0];
                String id = details[1];
                String nom = details[2];
                double prix = Double.parseDouble(details[3]);
                int quantite = Integer.parseInt(details[4]);

                Produit produit = null;

                switch (type) {
                    case "Electronique":
                        if (details.length >= 7) {
                            String marque = details[5];
                            int garantie = Integer.parseInt(details[6]);
                            produit = new Electronique(id, nom, prix, quantite, marque, garantie);
                        }
                        break;
                    case "Alimentaire":
                        if (details.length >= 6) {
                            String dateExpiration = details[5];
                            produit = new Alimentaire(id, nom, prix, quantite, dateExpiration);
                        }
                        break;
                    case "Vestimentaire":
                        if (details.length >= 7) {
                            String taille = details[5];
                            String couleur = details[6];
                            produit = new Vestimentaire(id, nom, prix, quantite, taille, couleur);
                        }
                        break;
                }

                if (produit != null) {
                    produits.put(produit.getId(), produit);
                }
            }
        }
    }

}



