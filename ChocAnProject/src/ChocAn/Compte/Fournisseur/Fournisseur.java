/*
 * TP-3 IFT 2255 GÉNIE LOGICIEL UNIVERSITÉ DE MONTRÉAL ÉTÉ-2017
 * Auteurs  : 
 * Jonathan Larose 			20066082
 * Augusto dos Santos Latgé	20083794
 * Aliona Cupcea 			20057887
 * Dennis Orozco Martinez	20031060
 * 
 */
package ChocAn.Compte.Fournisseur;
import ChocAn.Compte.*;

public class Fournisseur extends Compte {
	
private static int compteur = 0;	//Compteur statique du nombre de Fournisseur créé au total. Utilisé pour générer les numéros de Fournisseur.
	
	/**
	 * Constructeur spécifique au Fournisseur.
	 * Initialise le statut et génère un nouveau numéro de Fournisseur.
	 * @param nom Nom du Fournisseur.
	 * @param adresse Adresse du Fournisseur.
	 * @param ville Ville du Fournisseur.
	 * @param province Province du Fournisseur.
	 * @param codePostal Code Postal du Fournisseur.
	 * @param email Adresse email du Fournisseur.
	 */
	public Fournisseur (String nom, String adresse, String ville, String province, String codePostal, String email) {
		
		super (nom, adresse, ville, province, codePostal, email);
		
		Fournisseur.compteur++;
		
		this.numero = genererNumero();
	}
	
	/**
	 * Méthode pour générer le nouveau numéro de Fournisseur.
	 * Devrait générer 99 999 999 numéro avant d'en refaire un identique.
	 * Pas 100% sécuritaire parce qu'on ne vérifie pas si le numéro existe déjà...
	 * @return Un nouveau numéro de Fournisseur.
	 */
	protected int genererNumero(){
		
		int numero = Fournisseur.compteur * 15485863 % 99999999;
		
		if(numero<0)
			numero += 99999999;
		
		return numero + 100000000;
	}	
	public String toString(){
		String fournisseurS = "Numéro de fournisseur :\r\n"+this.numero+"\r\n"+super.toString();
		return fournisseurS;
	}	
}