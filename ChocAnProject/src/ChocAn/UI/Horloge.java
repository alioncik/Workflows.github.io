/*
 * TP-3 IFT 2255 GÉNIE LOGICIEL UNIVERSITÉ DE MONTRÉAL ÉTÉ-2017
 * Auteurs  : 
 * Jonathan Larose 			20066082
 * Augusto dos Santos Latgé	20083794
 * Aliona Cupcea 			20057887
 * Dennis Orozco Martinez	20031060
 * 
 */
package ChocAn.UI;
import java.util.Calendar;
import java.util.Date;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import ChocAn.Service.ServiceRendu;
import ChocAn.Compte.*;
import ChocAn.Compte.Fournisseur.*;
import ChocAn.Compte.Membre.*;

public class Horloge{
	
	// Format des valeurs
	private static NumberFormat formatter = NumberFormat.getCurrencyInstance(java.util.Locale.CANADA);
	
	/**
	 * Retourner la date actuel (impression automatique du rapport) moins 7 jours
	 * @return : la date actuel moins 7 jours
	 */
	private static String calculeDebutPeriode() {
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = sdf.format(cal.getTime());
        
        return strDate;
	}
	/**
	 * Retourner la date actuel (impression automatique du rapport)
	 * @return : la date actuel
	 */
	private static String calculeFinPeriode() {
		
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = sdf.format(cal.getTime());
        
        return strDate;
	}
	
	/**
	 * Méthode pour comparer deux dates (égal, plus grande ou plus petite)
	 * Dates dans le format JJ-MM-AAAA
	 * @param debutPeriode : date de début de la période
	 * @param finPeriode : date de fin de la période
	 * @return : -1 si date1 plus petite, 0 si égales, 1 si date1 plus grande
	 */
	private static int comparerDates(String date1, String date2) {
		
		String date1Jours = date1.substring(0,2);
		String date2Jours = date2.substring(0,2);
		String date1Mois = date1.substring(3,5);
		String date2Mois = date2.substring(3,5);
		String date1Annee =date1.substring(6,10);
		String date2Annee = date2.substring(6,10);		
		
		if(date1.compareTo(date2) == 0) {
			return 0;
		} else {
			if (date1Annee.compareTo(date2Annee) > 0) {
				return 1;
			} else {			
				if (date1Annee.compareTo(date2Annee) == 0 && 
						date1Mois.compareTo(date2Mois) > 0) {
					return 1;
				} else {
					if (date1Annee.compareTo(date2Annee) == 0 && 
							date1Mois.compareTo(date2Mois) == 0 &&
								date1Jours.compareTo(date2Jours) > 0) {
						return 1;
					} else {
						return -1;
					}
				}
			}
		}				
	}
		
	/**
	 * Méthode pour vérifier si une date appartient à la période indiquée
	 * Dates dans le format JJ-MM-AAAA
	 * @param debutPeriode : date de début de la période
	 * @param finPeriode : date de fin de la période
	 * @param dateAChequer : date à vérifier (si appartient à la période)
	 * @return : vrai si appartient, faux sinon
	 */
	private static boolean appartientPeriode(String debutPeriode, String finPeriode, String dateAChequer) {
	
		if (comparerDates(debutPeriode, dateAChequer) < 0 && comparerDates(finPeriode, dateAChequer) >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Méthode qu'imprime les rapports de tous les membres à l'initialisation du programme	 * 
	 * Elle itére sur toute la liste de services reçu par le membre
	 * Elle crée met le rapport créé dans un dossier Rapport_Membre créé à partir de l'adresse du projet	 * 
	 */
	public static void genererRapportsMembres(ChocAnUI ui) {
		
		Compte[] membreTemp = ui.listeMembre.getListe();
		
		// Itérer sur tous les membres de la liste de membres
		for (Compte membre : membreTemp) {
			
			if (membre instanceof Membre2) {
				
				// créer le path et le dossier pour l'enregistrement des rapports
				String pathS = new File("").getAbsolutePath();
				Path path = Paths.get(pathS);
				if (Files.exists(path)) {
					new File(pathS+"\\Rapports_Membres").mkdir();
				}
				
				// définir la période du rapport
				String dateNomRap = calculeDebutPeriode()+"_a_"+calculeFinPeriode();
				
				// path et nom du rapport
				String nomDuFichier = pathS+"\\Rapports_Membres\\rapport_membre_no_"+membre.getNumero()+dateNomRap+".txt";
						
				// Construction du rapport
				Date date = new Date();
				String dateS = date.toString();
				String dateRap = dateS.substring(0, 3)+" "+dateS.substring(8, 10)+" "+dateS.substring(4, 7)+" "+dateS.substring(24, 28);
						
				String rapport = "Rapport de Membre\r\n\r\n";				
				rapport += "Date du rapport : "+dateRap+":\r\n\r\n";
				rapport += "Période du rapport : "+calculeDebutPeriode()+" à "+calculeFinPeriode()+"\r\n\r\n";
				rapport += membre.toString();				
				rapport += "Services reçus :\r\n\r\n";
				
				int qteServRecusTotal = 0;			// garder la qte total des services reçus par le membre
				double valeurServRecusTotal = 0;	// garder la valeur total des services reçus par le membre
				
				// récuperer la liste des services rendus au Membre
				ServiceRendu[] servRend = membre.getListeService();
				
				// itérer sur la liste des services reçus par le membre
				for (ServiceRendu service : servRend) {
					
					if (service instanceof ServiceRendu) {
						
						// si le service appartient à la période du rapport
						if (appartientPeriode(calculeDebutPeriode(), calculeFinPeriode(), (service.getDateHeureActuelle()).substring(0,10))) {
							qteServRecusTotal++;
							valeurServRecusTotal += service.getService().getFrais();
							rapport += service.toString()+"\r\n";
						}
					}
				}
				
				rapport += "Quantité Total Services Reçus : "+qteServRecusTotal+"\r\n";
				rapport += "Valeur Total Services Reçus : "+formatter.format(valeurServRecusTotal)+"\r\n\r\n";
				
				// Appel à la méthode de la classe ChocAn
				UI.imprimerRapport(rapport, nomDuFichier);
			}			
		}
		System.out.println("Procedure automantique de génération des rapports de membres conclue avec success. Regardez dossier Rapports_Membres.\n");
	}
	
	/**
	 * Méthode qu'imprime les rapports de tous les fournisseurs à l'initialisation du programme
	 * Elle itére sur toute la liste de services fournis du fournisseur
	 * Elle crée met le rapport créé dans un dossier Rapport_Fournisseur créé à partir de l'adresse du projet
	 */
	public static void genererRapportsFournisseurs(ChocAnUI ui) {
		
		Compte[] fournisseurTemp = ui.listeFournisseur.getListe();
		
		// Itérer sur tous les fournisseurs de la liste de fournisseurs
		for (Compte fournisseur : fournisseurTemp) {
			
			if (fournisseur instanceof Fournisseur2) {

				// créer le path et le dossier pour l'enregistrement des rapports
				String pathS = new File("").getAbsolutePath();
				Path path = Paths.get(pathS);
				if (Files.exists(path)) {
					new File(pathS+"\\Rapports_Fournisseurs").mkdir();
				}
				
				// définir la date d'impression du rapport et la période analysée
				String dateNomRap = calculeDebutPeriode()+"_a_"+calculeFinPeriode();
				ServiceRendu[] servFournis = fournisseur.getListeService();
				
				// path et nom du rapport
				String nomDuFichier = pathS+"\\Rapports_Fournisseurs\\rapport_fournisseur_no_"+fournisseur.getNumero()+dateNomRap+".txt";
						
				// Construction du rapport
				Date date = new Date();
				String dateS = date.toString();
				String dateRap = dateS.substring(0, 3)+" "+dateS.substring(8, 10)+" "+dateS.substring(4, 7)+" "+dateS.substring(24, 28);

				String rapport = "Rapport de Fournisseur\r\n\r\n";				
				rapport += "Date du rapport : "+dateRap+":\r\n\r\n";
				rapport += "Période du rapport : "+calculeDebutPeriode()+" à "+calculeFinPeriode();
				rapport += fournisseur.toString();				
				rapport += "Services fournis :\r\n\r\n";
				
				int qteServFourTotal = 0;			// garder la qte total des services fournis par le fournisseur
				double valeurServFourTotal = 0;		// garder la valeur total des services fournis par le fournisseur
						
				// Itérer sur la liste des services fournis par le fournisseur
				for (ServiceRendu service : servFournis) {

					if (service instanceof ServiceRendu) {
						
						// si le service appartient à la période du rapport
						if (appartientPeriode(calculeDebutPeriode(), calculeFinPeriode(), (service.getDateHeureActuelle()).substring(0,10))) {
							qteServFourTotal++;
							valeurServFourTotal += service.getService().getFrais();
							rapport += service.toString()+"\r\n";
						}
					}				
				}
				
				rapport += "Quantité Total Services Fournis : "+qteServFourTotal+"\r\n";
				rapport += "Valeur Total Services Fournis : "+formatter.format(valeurServFourTotal)+"\r\n\r\n";
				
				// Appel à la méthode de la classe ChocAn
				UI.imprimerRapport(rapport, nomDuFichier);
			}			
		}
		System.out.println("Procedure automantique de génération des rapports de fournisseur conclue avec success. Regardez dossier Rapports_Fournisseurs.\n");
	}
	
	/**
	 * Méthode qu'imprime les rapports pour le transfert électronique de fond (TEF) à l'initialisation du programme
	 * Elle itére sur toutes les listes de services fournis (factures) de tous les fournisseur.
	 * Si un fournisseur a facturé un service dans la période du rapport il va être calculé sinon, non.
	 * Elle crée met le rapport créé dans un dossier Rapport_TEF créé à partir de l'adresse du projet
	 * @param ui : objet du type UI passé en paramètre pour donner accès aux listes (Membres, Fournisseurs, Services)
	 */
	public static void genererTEF(ChocAnUI ui) {
		
		// créer le path et le dossier pour l'enregistrement des rapports
		String pathS = new File("").getAbsolutePath();
		Path path = Paths.get(pathS);
		if (Files.exists(path)) {
			new File(pathS+"\\Rapports_TEF").mkdir();
		}
		
		// définir la date d'impression du rapport et la période analysée
		Date date = new Date();
		String dateS = date.toString();
		String dateRap = dateS.substring(0, 3)+" "+dateS.substring(8, 10)+" "+dateS.substring(4, 7)+" "+dateS.substring(24, 28);
		String dateNomRap = calculeDebutPeriode()+"_a_"+calculeFinPeriode();
		
		// path et nom du rapport
		String nomDuFichier = pathS+"\\Rapports_TEF\\rapport_TEF_"+dateNomRap+".txt";
				
		// Construction du rapport
		String rapport = "Rapport pour le transfert électronique de fond (TEF) \r\n\r\n";		
		rapport += "Date du rapport : "+dateRap+":\r\n\r\n";
		rapport += "Période du rapport : "+calculeDebutPeriode()+" à "+calculeFinPeriode()+"\r\n\r\n";		
		rapport += "Services fournis au membres :\r\n\r\n";			
		rapport += "Payement aux fournisseurs :\r\n\r\n";
		
		double valeurServFourTotal = 0;		// garder la valeur total des services fournis pour tous les fournisseurs
		
		// Itérer sur chaque fournisseur de la liste de fournisseurs
		Compte[] fourTemp = ui.listeFournisseur.getListe();
		for (Compte fournisseur : fourTemp) {			

			int qteServFourTemp = 0;			//garder la qte de services fournis pour chaque fournisseur
			double valeurServFourTemp = 0;		//garder la valeur des services fournis pour chaque fournisseur
			
			if (fournisseur instanceof Fournisseur2) {				
				
				// récuperer la liste des services fournis par le Fournisseur
				ServiceRendu[] servTemp2 = fournisseur.getListeService();
				
				// si le service appartient à la période du rapport
				for (ServiceRendu service: servTemp2) {
					
					if (service instanceof ServiceRendu) {
						
						// Si le service est compris dans la période du rapport
						if (appartientPeriode(calculeDebutPeriode(), calculeFinPeriode(), (service.getDateHeureActuelle()).substring(0,10))) {
							qteServFourTemp++;
							valeurServFourTemp += service.getService().getFrais();
						}
					}				
				}
			}			
			
			// Si au moins un service fourni
			if (qteServFourTemp != 0) {
				
				valeurServFourTotal += valeurServFourTemp;
				rapport += fournisseur.toString();
				rapport += "Valeur à payer au fournisseur no. "+fournisseur.getNumero()+" : "+formatter.format(valeurServFourTemp)+"\r\n\r\n";
			}
		}
		
		rapport += "Valeur total à payer aux fournisseurs : "+formatter.format(valeurServFourTotal)+"\r\n\r\n";
		
		// Appel à la méthode de la classe ChocAn
		UI.imprimerRapport(rapport, nomDuFichier);
		System.out.println("Procedure automatique de génération du rapport pour le transfert électronique de fond (TEF) conclue avec succès. Regardez dossier Rapports_Synthese.\n");
	}
	
}
