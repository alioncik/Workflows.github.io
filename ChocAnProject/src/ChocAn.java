/*
 * TP-3 IFT 2255 GÉNIE LOGICIEL UNIVERSITÉ DE MONTRÉAL ÉTÉ-2017
 * Auteurs  : 
 * Jonathan Larose 			20066082
 * Augusto dos Santos Latgé	20083794
 * Aliona Cupcea 			20057887
 * Dennis Orozco Martinez	20031060
 * 
 */
import ChocAn.UI.ChocAnUI;
import ChocAn.UI.Horloge;
import ChocAn.Compte.Fournisseur.*;
import ChocAn.Compte.Membre.*;
import ChocAn.Compte.*;
import ChocAn.Service.*;

public class ChocAn {
	
	/**
	 * Méthode qui demarre le programme
	 * @param args : non nécéssaires
	 */
	public static void main(String args[]){
		
		ListeDeCompte2 lm = new ListeDeCompte2();
		ListeDeCompte2 lf = new ListeDeCompte2();
		ListeDeService2 ls = new ListeDeService2();
		RepertoireDesFournisseurs2 rf = new RepertoireDesFournisseurs2();
		
		genererMFSS(lm,lf,ls,rf);
		
		ChocAnUI ui = new ChocAnUI(lm,lf,ls,rf);
		
		Horloge.genererRapportsMembres(ui);
		Horloge.genererRapportsFournisseurs(ui);
		Horloge.genererTEF(ui);
		
		ui.menu();
		
		return;
	}
	
	/**
	 * Méthode qui initialise le programme avec des listes de Membres, de fournisseurs et de services.
	 * Méethode utilisée pour simuler une petite base de données, on imprime les informations pour 
	 * pouvoir accès aux codes des Membres, services et fournisseurs.
	 * @param lm : Liste de compte
	 * @param lf : liste de compte
	 * @param ls : liste de services
	 * @param rf : repertoire de fournisseurs
	 */
	public static void genererMFSS(ListeDeCompte2 lm, ListeDeCompte2 lf, ListeDeService2 ls, RepertoireDesFournisseurs2 rf){
		
		Membre2 m1 = new Membre2("Jonathan Larose", "1234 rue St-Denis", "Montreal", "QC", "12345", "jonlar@umontreal.ca");
		Membre2 m2 = new Membre2("Bruce Campbell", "555 rue St-Denis", "Laval", "ON", "66666", "bc@gmail.com");
		Membre2 m3 = new Membre2("Remi Lavigne", "69 rue Labelle", "Montreal", "QC", "14553", "remi@umontreal.ca");
		Membre2 m4 = new Membre2("John John", "34 rue Toto", "Montreal", "QC", "12353", "toto@umontreal.ca");
		
		System.out.println("Numéro m1 : "+m1.getNumero());
		System.out.println("Numéro m2 : "+m2.getNumero());
		System.out.println("Numéro m3 : "+m3.getNumero());
		System.out.println("Numéro m4 : "+m4.getNumero());
		
		lm.ajouterCompte(m1);
		lm.ajouterCompte(m2);
		lm.ajouterCompte(m3);
		lm.ajouterCompte(m4);
		
		Fournisseur2 f1 = new Fournisseur2("Massage Gagnier", "555 St-Denis", "Montreal", "QC", "p4p5p", "massagegagier@hotmail.ca");
		Fournisseur2 f2 = new Fournisseur2("Yoga Yo", "700 rue St-Denis", "Laval", "QC", "6556", "yogayo@gmail.com");
		Fournisseur2 f3 = new Fournisseur2("Physio fix", "1004 rue Labelle", "Montreal", "QC", "12ggg", "pf@gmail.ca");
		Fournisseur2 f4 = new Fournisseur2("Fit4life", "34 st-Laurent", "Montreal", "QC", "1p3p4", "f4l@gmail.ca");
		
		System.out.println("Numéro f1 : "+f1.getNumero());
		System.out.println("Numéro f2 : "+f2.getNumero());
		System.out.println("Numéro f3 : "+f3.getNumero());
		System.out.println("Numéro f4 : "+f4.getNumero());
		
		lf.ajouterCompte(f1);
		lf.ajouterCompte(f2);
		lf.ajouterCompte(f3);
		lf.ajouterCompte(f4);
		
		Service2 s1 = new Service2("Massage", 75.75);
		Service2 s2 = new Service2("Physio", 123.75);
		Service2 s3 = new Service2("Yoga", 72.50);
		Service2 s4 = new Service2("Course", 15.33);
		
		System.out.println("Numéro s1 : "+s1.getCode());
		System.out.println("Numéro s2 : "+s2.getCode());
		System.out.println("Numéro s3 : "+s3.getCode());
		System.out.println("Numéro s4 : "+s4.getCode());
		
		rf.ajouterService(s1);
		rf.ajouterService(s2);
		rf.ajouterService(s3);
		rf.ajouterService(s4);
		
		ls.ajouterServiceRendu("10-12-2017", "", m1, f1, s1);
		ls.ajouterServiceRendu("11-12-2017", "", m1, f3, s3);
		ls.ajouterServiceRendu("12-01-2018", "", m1, f2, s2);
		ls.ajouterServiceRendu("10-05-2017", "", m2, f2, s4);
		ls.ajouterServiceRendu("24-07-2017", "", m2, f2, s2);
		ls.ajouterServiceRendu("03-06-2017", "", m4, f1, s1);
		ls.ajouterServiceRendu("01-11-2002", "", m3, f4, s2);
	}
}
