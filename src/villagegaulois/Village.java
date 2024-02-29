package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		if (this.chef == null) {
			throw new VillageSansChefException();
		}
		
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder builder = new StringBuilder(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int indiceEtal = this.marche.trouverEtalLibre();
		
		if (indiceEtal == -1) {
			builder.append("Aucun étal n'est disponible.");
		} else {
			this.marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
			builder.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + indiceEtal + ".");
		}
		
		return builder.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] etals = this.marche.trouverEtals(produit);
		
		if (etals.length == 0) {
			return "Il n'y a pas de vendeur qui propose des " + produit + " au marché.";
		} else if (etals.length == 1) {
			return "Seul le vendeur " + etals[0].getVendeur().getNom() + " propose des " + produit + " au marché.";
		} else {
			StringBuilder builder = new StringBuilder("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (Etal etal : etals) {
				builder.append("- " + etal.getVendeur().getNom() + "\n");
			}
			return builder.toString();
		}
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return this.marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etal = this.rechercherEtal(vendeur);
		if (etal == null) {
			return "Le vendeur " + vendeur + " n'occupe aucun étal.";
		} else {
			return etal.libererEtal();
		}
	}
	
	public String afficherMarche() {
		return this.marche.afficherMarche();
	}
	
	private static class Marche {
		private Etal[] etals;
		
		private Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				this.etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduits) {
			Etal etal = this.etals[indiceEtal];
			if (etal.isEtalOccupe()) {
				System.out.println("L'étal est déjà occupé");
			} else {
				etal.occuperEtal(vendeur, produit, nbProduits);
			}
		}
		
		private int trouverEtalLibre() {
			int i = this.etals.length-1;
			while (i >= 0 && this.etals[i].isEtalOccupe()) {
				i--;
			}
			return i;
		}
		
		private Etal[] trouverEtals(String produit) {
			int[] indicesEtals = new int[this.etals.length];
			int nbEtals = 0;
			for (int i = 0; i < this.etals.length; i++) {
				Etal etal = this.etals[i];
				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					indicesEtals[nbEtals++] = i;
				}
			}
			
			Etal[] etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = this.etals[indicesEtals[i]];
			}
			
			return etals;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (Etal etal : this.etals) {
				if (etal.isEtalOccupe() && etal.getVendeur().equals(gaulois)) {
					return etal;
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			StringBuilder builder = new StringBuilder();
			int nbOccupes = 0;
			
			for (int i = 0; i < this.etals.length; i++) {
				Etal etal = this.etals[i];
				if (etal.isEtalOccupe()) {
					builder.append(etal.afficherEtal());
					nbOccupes++;
				}
			}
			
			int nbVides = this.etals.length-nbOccupes;
			if (nbVides > 0) {				
				builder.append("Il reste " + nbVides + " étals non utilisés dans le marché.");
			}
			
			return builder.toString();
		}
	}
}