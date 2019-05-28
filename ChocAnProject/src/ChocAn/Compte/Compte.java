/*
 * TP-3 IFT 2255 GÉNIE LOGICIEL UNIVERSITÉ DE MONTRÉAL ÉTÉ-2017
 * Auteurs  : 
 * Jonathan Larose 			20066082
 * Augusto dos Santos Latgé	20083794
 * Aliona Cupcea 			20057887
 * Dennis Orozco Martinez	20031060
 * 
 */
package ChocAn.Compte;
import ChocAn.Service.*;

public class Compte {

	//Attributs
	protected int numero;
	protected String nom;
	protected String adresse;
	protected String ville;
	protected String province;
	protected String codePostal;
	protected String email;
	protected ServiceRendu [] listeService;
	private int nombreServiceRendu;
	
	public Compte(String nom, String adresse, String ville, String province, String codePostal, String email ){
	
		this.nom = nom;
		this.adresse = adresse;
		this.ville = ville;
		this.province = province;
		this.codePostal = codePostal;
		this.email = email;
		this.listeService = new ServiceRendu[16];
		this.nombreServiceRendu = 0;
	}
	
	
	public int getNumero(){
		return this.numero;
	}

	public String getAdresse(){
		return this.adresse;
	}

	public void setAdresse(String newAdresse){
		this.adresse = newAdresse;
	}

	public String getNom(){
		return this.nom;
	}

 
	public void setNom(String newNom){
		this.nom =newNom;
	}
	public String getProvince(){
		return this.province;
	}
	
	public void setProvince(String newProvince){
		this.province = newProvince;
	}
	
	public String getVille(){
		return this.ville;
	}
	
	public void setVille(String newVille){
		this.ville = newVille;
	
	}
	
	public String getCodePostal(){
		return this.codePostal;
	}
	
	public void setCodePostal(String newCodePostal){
		this.codePostal = newCodePostal;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setEmail(String newEmail){
		this.email = newEmail;
	}
	
	public ServiceRendu[] getListeService(){
		return this.listeService;
	}
	
	
	/**
	 * Méthode qui double la taille de la liste.
	 * Appelé au besoin si le tableau est plein.
	 */
	private void doublerListe(){
		
		ServiceRendu [] newListe = new ServiceRendu[this.listeService.length*2];
		
		System.arraycopy(this.listeService, 0, newListe, 0, this.listeService.length);
		
		this.listeService = newListe;
		
		return;
	}
	
	public void ajouterServiceRendu(ServiceRendu sr){
		
		if(nombreServiceRendu == this.listeService.length)
			doublerListe();
		
		this.listeService[nombreServiceRendu] = sr;
		
		nombreServiceRendu++;
		
		return;
	}
	
	
	/**
	*
	*/
	public String toString(){
		String compteS = 	"Nom : "+nom+"\r\n"
							+"Adresse : "+adresse+"\r\n"
							+"Vile : "+ville+"\r\n"
							+"Province : "+province+"\r\n"
							+"Code Postal : "+codePostal+"\r\n"
							+"Courriel : "+email+"\r\n";
		return compteS;	    
	}
}