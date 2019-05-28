/*
 * TP-3 IFT 2255 GÉNIE LOGICIEL UNIVERSITÉ DE MONTRÉAL ÉTÉ-2017
 * Auteurs  : 
 * Jonathan Larose 			20066082
 * Augusto dos Santos Latgé	20083794
 * Aliona Cupcea 			20057887
 * Dennis Orozco Martinez	20031060
 * 
 */
package ChocAn.Compte.Membre;
import ChocAn.Compte.Compte;

public class Membre2 extends Compte {
	
	private StatutMembre statut;	//Statut de l'abonnement du membre : Actif ou Suspendu.
	private static int compteur = 0;	//Compteur statique du nombre de membre créé au total. Utilisé pour générer les numéros de Membre.
	
	/**
	 * Constructeur spécifique au Membre.
	 * Initialise le statut et génère un nouveau numéro de Membre.
	 * @param nom Nom du Membre.
	 * @param adresse Adresse du Membre.
	 * @param ville Ville du Membre.
	 * @param province Province du Membre.
	 * @param codePostal Code Postal du Membre.
	 * @param email Adresse email du Membre.
	 */
	public Membre2 (String nom, String adresse, String ville, String province, String codePostal, String email) {
		
		super (nom, adresse, ville, province, codePostal, email);
		
		Membre2.compteur++;
		
		this.numero = genererNumero();
		this.statut = StatutMembre.Actif;
	}
	
	public StatutMembre getStatut(){
		return this.statut;
	}
	
	public void changeStatut(){
		
		if(this.statut == StatutMembre.Actif)
			this.statut = StatutMembre.Suspendu;
		else
			this.statut = StatutMembre.Actif;
	}
	
	/**
	 * Méthode pour générer le nouveau numéro de Membre.
	 * Devrait générer 99 999 999 numéro avant d'en refaire un identique.
	 * Pas 100% sécuritaire parce qu'on ne vérifie pas si le numéro existe déjà...
	 * @return Un nouveau numéro de Membre.
	 */
	protected int genererNumero(){
		
		int numero = Membre2.compteur * 179424673 % 99999999;
		
		if(numero<0)
			numero += 99999999;
		
		return numero + 900000000;
	}
	
	/**
	 * Méthode toString() pour Membre
	 * @return : le toString() de Membre
	 */
	public String toString() {
		String membreS = "Numéro de membre : "+this.numero+"\r\n"+super.toString()+"Statut : "+statut+"\r\n";
		return membreS;
	}
}