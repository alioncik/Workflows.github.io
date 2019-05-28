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

public class Service {
	
	private String nom;
	private int codeService;
	private double frais;
	private static int code = 1;

	/**
	 * Constructeur de la classe service
	 * @param codeService : code du service à creer
	 * @param nomService : nom du service à creer
	 * @param fraisService : frais associés au service à creer
	 */
	public Service (/*int codeService, */String nomService, double fraisService) {
		
		this.codeService = genererCode()/*codeService*/;
		this.nom = nomService;
		this.frais = fraisService;
	}
	
	/**
	 * Méthode pour générer le nouveau code de Service.
	 * Devrait générer 999 999 numéro avant d'en refaire un identique.
	 * Pas 100% sécuritaire parce qu'on ne vérifie pas si le numéro existe déjà...
	 * @return Un nouveau code de Service.
	 */
	protected int genererCode(){
		
		int newCode = code*135977 % 999999;
		
		if(newCode<100000)
			newCode += 899999;
		

		code = newCode;
		
		return newCode;
	}
	 /**
	  * 
	  * @param codeService : code su service qu'on veut valider
	  * @return true si le service existe, false sinon
	  */
	public boolean valideRempli(int codeService) {
		
		if (this.codeService != codeService) {
			return false;
		} else {
			return true;
		}
	}

	public int getCode(){
		return this.codeService;
	}

	public String getNom(){
		return this.nom;
	}

	public double getFrais(){
		return this.frais;
	}
	
	public void setNom (String newNom){
		this.nom = newNom;;
	}

	public void setFrais(double fraisService){
		this.frais = fraisService;
	}
	
	/**
	 * Méthode pour afficher les informations d'un service
	 */
	public String toString() {
		
		String result = "Code : "+this.codeService+"\r\n"+
						"Nom : "+this.nom+"\r\n"+
						"frais : "+this.frais+"\r\n";
		return result;
	}
}