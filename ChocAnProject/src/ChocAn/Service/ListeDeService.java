/*
 * TP-3 IFT 2255 GÉNIE LOGICIEL UNIVERSITÉ DE MONTRÉAL ÉTÉ-2017
 * Auteurs  : 
 * Jonathan Larose 			20066082
 * Augusto dos Santos Latgé	20083794
 * Aliona Cupcea 			20057887
 * Dennis Orozco Martinez	20031060
 * 
 */
package ChocAn.Service;
import ChocAn.Compte.Fournisseur.*;
import ChocAn.Compte.Membre.*;

public class ListeDeService { 							// il s'agit de la liste de services rendus
	ServiceRendu[] servTous = new ServiceRendu[16];		// taille minimale du tableau de services rendus
	int elemTous = 0;									// nombre d'elements dans la liste
	
	/**
	 * Méthode pour trouver la liste de services qu'un membre a reçu
	 * @param numMembre : numéro du compte du membre
	 * @return liste de services reçus par le membre
	 * @throws Excéption : s'il n'y a pas de services reçus par ce membre
	 */
	public ServiceRendu[] getServMem(int numMembre) throws Exception {
		
		if (elemTous == 0 || servTous == null) 
			throw new Exception("Il n'y a pas de services reçus pour aucun membre");
		
		ServiceRendu[] listeMembre = new ServiceRendu[servTous.length];
		int compteur = 0;
		
		for (int i = 0; i < elemTous; i++) {
			if (servTous[i].getMembre().getNumero() == numMembre) {
				listeMembre[compteur] = servTous[i];
				compteur++;
			}
		}
		return listeMembre;
	}

	/**
	 * Méthode qui tri la liste de services rendus pour obtenir les services offerts par le fournisseur
	 * @param numFourni : numéro du compte du fournisseur
	 * @return liste de services offerts par le fournisseur
	 * @throws Excéption : si aucun fournisseur n'a pas offert de services
	 */
	public ServiceRendu[] getServFourn(int numFourni) throws Exception {
		
		if (elemTous == 0 || servTous == null) 
			throw new Exception("Il n'y a pas de services fournis pour aucun fournisseur");
		
		ServiceRendu[] listeFourni = new ServiceRendu[servTous.length];
		int compteur = 0;
		
		for (int i = 0; i < elemTous; i++) {
			if (servTous[i].getFourni().getNumero() == numFourni) {
				listeFourni[compteur] = servTous[i];
				compteur++;
			}
		}
		return listeFourni;
	}

	/**
	 * Méthode qui retourne la liste complète de services rendus
	 * @return liste de services rendus
	 * @throws Excéption : Si aucun service rendu a date
	 */
	public ServiceRendu[] getAll() throws Exception {
		
		if (elemTous == 0 || servTous == null) 
			throw new Exception("Il n'y a pas de services Rendus a date");
		
		return servTous;
	}

	/**
	 * Méthode pour ajouter un service rendu à la liste de services rendus
	 * @param dateHeureActuelle : String contenant la date et l'heure de générer le rapport
	 * @param dateService : date où le service a été rendu
	 * @param commentaire : commentaire facultatif dans chaque service rendu
	 * @param membre : Membre qui a reçu le service
	 * @param fournisseur : Fournisseur qui a donné le service
	 */
	public void ajouterServiceRendu(String dateService, String commentaire,
										Membre2 membre, Fournisseur2 fournisseur, Service2 service) {
		
		if (elemTous == servTous.length) 
			doubleTable();
		
		servTous[elemTous+1] = new ServiceRendu(dateService, commentaire, membre ,fournisseur, service);
		
		membre.ajouterServiceRendu(servTous[elemTous+1]);
		
		fournisseur.ajouterServiceRendu(servTous[elemTous+1]);
						
		elemTous++;
		return;
	}
	
	/**
	 * Méthode pour doubler la taille du tableu quand elle est toute remplie
	 */
	public void doubleTable(){
		
		ServiceRendu[] temp = new ServiceRendu[servTous.length*2];
		System.arraycopy(this.servTous, 0, temp, 0, servTous.length);
		this.servTous = temp;
	}

}