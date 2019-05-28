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
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class ServiceRendu {

	private String dateHeureActuelle;
	private String dateService;
	private String commentaire;
	private Membre2 membre;
	private Fournisseur2 fournisseur;
	private Service2 service;
	
	/**
	 * Constructeur d'un service fourni
	 * @param dateService : date à laquelle le service a été fourni
	 * @param commemtaire : facultatif sur le service fourni
	 * @param membre : membre qui a reçu le service
	 * @param fournisseur : fournisseur qui a offert le service
	 * @param service : service qui a été offert
	 */
	public ServiceRendu(String dateService, String commemtaire, Membre2 membre, Fournisseur2 fournisseur,Service2 service) {
		
		this.dateService = dateService;
		this.commentaire = commemtaire;
		this.membre = membre;
		this.fournisseur = fournisseur;
		this.service = service;
		this.dateHeureActuelle = calculeDate();
	}
	 
	/**
	 * Méthode pour calculer la date sous la forme : JJ-MM-AAA HH:MM:SS
	 * @return String contenant le format désiré
	 */
	public String calculeDate(){
		
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SS");
        String strDate = sdf.format(cal.getTime());
        return strDate;
	}
	
	public String getDateHeureActuelle(){
		return this.dateHeureActuelle;
	}
	
	public String getDateService(){
		return this.dateService;
	}
	
	public String getCommentaire(){
		return this.commentaire;
	}
	
	public Membre2 getMembre(){
		return this.membre;
	}
	
	public Fournisseur2 getFourni(){
		return this.fournisseur;
	}
	
	public Service2 getService(){
		return this.service;
	}
	
	/**
	 * Méthode pour afficher les informations d'un service fourni
	 */
	public String toString(){
		
		String result = "Code : "+ this.getService().getCode() +"\r\n" + 
						"Nom : "+this.getService().getNom()+"\r\n" +
						"Frais : "+this.getService().getFrais()+"\r\n"+
						"Date de service : "+(this.dateHeureActuelle).substring(0,10)+"\r\n";
		return result;
	}
}