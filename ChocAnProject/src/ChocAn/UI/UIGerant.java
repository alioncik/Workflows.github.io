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
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Date;
import ChocAn.Service.ListeDeService2;
import ChocAn.Service.RepertoireDesFournisseurs2;
import ChocAn.Service.ServiceRendu;
import ChocAn.Compte.*;
import ChocAn.Compte.Fournisseur.*;
import ChocAn.Compte.Membre.*;

public class UIGerant extends UIOperateurChocAn {
	
	public UIGerant (ListeDeCompte2 lm, ListeDeCompte2 lf, ListeDeService2 ls, RepertoireDesFournisseurs2 rf) {
		
		super(lm,lf,ls,rf);
		
	}
	
	NumberFormat formatter = NumberFormat.getCurrencyInstance(java.util.Locale.CANADA);
	
	/**
	 * Affiche les options des opérations à effectuer par l'utilisateur (gérant ChocAn)
	 * Utilise le méthode promptOption de UI
	 * @Override la méthode menu() de la super classe UIOpérateurChocAn
	 */
	public void menu() {
		
		boolean menuActif = true;
		
		while (menuActif) {
			String itemsMenu = "Menu d'options\n"
					+ "Selectionnez l'option souhaitée dans la liste ci-dessous en composant le numéro correspondant sur le clavier.\n"
					+ "\t1 - Ajouter un membre.\n"
					+ "\t2 - Ajouter un fournisseur.\n"
					+ "\t3 - Ajouter un service.\n"
					+ "\t4 - Mettre à jour le dossier d\'un membre.\n"
					+ "\t5 - Mettre à jour le dossier d\'un fournisseur.\n"
					+ "\t6 - Mettre à jour un service.\n"
					+ "\t7 - Supprimer un membre.\n"
					+ "\t8 - Supprimer un fournisseur.\n"
					+ "\t9 - Supprimer un service.\n"
					+ "\t10 - Générer un rapport de membre.\n"
					+ "\t11 - Générer un rapport de fournisseur.\n"
					+ "\t12 - Générer un rapport de synthèse.\n"
					+ "\t13 - Revenir au menu principal.\n";
			int selection = promptOption(itemsMenu, 13);
			
			switch (selection) {
				case 1: 	ajouterMembre();
							break;
				case 2: 	ajouterFournisseur();
							break;
				case 3: 	ajouterService();
							break;
				case 4: 	modifierMembre();
							break;
				case 5: 	modifierFournisseur();
							break;
				case 6: 	modifierService();
							break;							
				case 7: 	supprimerMembre();
							break;
				case 8: 	supprimerFournisseur();
							break;
				case 9: 	supprimerService();
							break;
				case 10: 	genRapportMembre();
							break;
				case 11: 	genRapportFournisseur();
							break;
				case 12: 	genRapportSynthese();
							break;
				case 13: 	menuActif = false;
							break;	
				default: 	break;
			}	
		}		
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
	 * Méthode pour générer le rapport de compte d'un Membre
	 * Elle itére sur toute la liste de services reçu par le membre
	 * Elle crée met le rapport créé dans un dossier Rapport_Membre créé à partir de l'adresse du projet
	 * @param num : le numéro de compte du Membre
	 */
	public void genRapportMembre() {
		
		int num = 0;
		boolean validNum = false;
		boolean premierEntreeBoucle = true;
		Membre2 memRap = null;
		
		//Demande un numero de membre valide.
		do {
			// si numéro valide
			if (premierEntreeBoucle) {
				num = promptNumMembre();
				premierEntreeBoucle = false;
			} else {
				// si numéro invalide demander prochaine action de l'utilisateur
				String numCorrect = "Confirmez le numéro du membre.\n"
						+ "Selectionnez l'option souhaitée dans la liste ci-dessous en appuiant le numéro correspondant sur le clavier.\n"
						+ "\t1 - Entrez le numéro du membre.\n"
						+ "\t2 - Revenir au menu précédent.\n";
				int selection = promptOption(numCorrect, 2);
				
				switch (selection) {
					case 1: num = promptNumMembre();
							break;
					case 2: return;		
				}
			}
			
			//Trouve le membre dans la liste de membres
			try {
				memRap = (Membre2) listeMembre.trouverCompte(num);
				validNum = true;
			}
			catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				continue;
			}			
		} while (!validNum);		
		
		boolean periode = false;
		boolean premierEntreeBoucle2 = true;
		String debutPeriode = "";
		String finPeriode = "";
		
		//Demander un période valide pour le rapport (date du début >= date de la fin).
		do {
			if (premierEntreeBoucle2) {	
				
				debutPeriode = promptDate("Entrez la date du début de la période du rapport.");
				finPeriode = promptDate("Entrez la date de la fin de la période du rapport.");
				
				// Période valide
				if (comparerDates(debutPeriode, finPeriode) <= 0) {
					periode = true;
				} else {
					premierEntreeBoucle2 = false;
				}
				
			} else {
				// Si période invalide demander prochaine action de l'utilisateur
				String numCorrect = "La date de fin de la période doit être plus grande où égale à celle du début.\n"
						+ "Selectionnez l'option souhaitée dans la liste ci-dessous en appuiant composant le numéro correspondant sur le clavier.\n"
						+ "\t1 - Entrez la période.\n"
						+ "\t2 - Revenir au menu précédent.\n";
				int selection = promptOption(numCorrect, 2);
				
				switch (selection) {
					case 1: debutPeriode = promptDate("Entrez la date du début de la période du rapport.");
							finPeriode = promptDate("Entrez la date de la fin de la période du rapport.");
							if (comparerDates(debutPeriode, finPeriode) <= 0) {
								periode = true;
							} 
							break;
					case 2: return;		
				}
			}
		} while (!periode);
		
		// créer le path et le dossier pour l'enregistrement des rapports
		String pathS = new File("").getAbsolutePath();
		Path path = Paths.get(pathS);
		if (Files.exists(path)) {
			new File(pathS+"\\Rapports_Membres").mkdir();
		}
		
		// définir la période du rapport
		String dateNomRap = debutPeriode+"_a_"+finPeriode;
		
		// path et nom du rapport
		String nomDuFichier = pathS+"\\Rapports_Membres\\rapport_membre_no_"+num+dateNomRap+".txt";
				
		// Construction du rapport
		Date date = new Date();
		String dateS = date.toString();
		String dateRap = dateS.substring(0, 3)+" "+dateS.substring(8, 10)+" "+dateS.substring(4, 7)+" "+dateS.substring(24, 28);
				
		String rapport = "Rapport de Membre\r\n\r\n";		
		rapport += "Date du rapport : "+dateRap+":\r\n\r\n";
		rapport += "Période du rapport : "+debutPeriode+" à "+finPeriode+"\r\n\r\n";
		rapport += memRap.toString();		
		rapport += "Services reçus :\r\n\r\n";
		
		int qteServRecusTotal = 0;			// garder la qte total des services reçus par le membre
		double valeurServRecusTotal = 0;	// garder la valeur total des services reçus par le membre

		// récuperer la liste des services rendus au Membre
		ServiceRendu[] servRend = memRap.getListeService();
		
		// Itérer sur la liste des services reçus par le membre
		for (ServiceRendu service : servRend) {
			
			if (service instanceof ServiceRendu) {
				
				System.out.println(service.toString());
				
				// si le service appartient à la période du rapport
				if (appartientPeriode(debutPeriode, finPeriode, (service.getDateHeureActuelle()).substring(0,10))) {
					System.out.println(service.toString());
					qteServRecusTotal++;
					valeurServRecusTotal += service.getService().getFrais();
					rapport += service.toString()+"\r\n";
				}
			}
		}
		
		rapport += "Quantité Total Services Reçus : "+qteServRecusTotal+"\r\n";
		rapport += "Valeur Total Services Reçus : "+formatter.format(valeurServRecusTotal)+"\r\n\r\n";
		
		// Appel à la méthode de la classe ChocAn
		imprimerRapport(rapport, nomDuFichier);
		System.out.println("Rapport membre généré avec succès. Regardez dossier Rapports_Membres.\n");
	}

	/**
	 * Méthode pour générer le rapport de compte d'un Fournisseur
	 * Elle itére sur toute la liste de services fournis du fournisseur
	 * Elle crée met le rapport créé dans un dossier Rapport_Fournisseur créé à partir de l'adresse du projet
	 * @param num : le numéro de compte du Fournisseur
	 */
	public void genRapportFournisseur() {
		
		int num = 0;
		boolean validNum = false;
		boolean premierEntreeBoucle = true;
		Fournisseur2 fourRap = null;
		
		//Demande un numero de fournisseur valide.
		do {
			// si numéro valide
			if (premierEntreeBoucle) {
				num = promptNumFournisseur();
				premierEntreeBoucle = false;
			} else {
				// si numéro invalide demander prochaine action de l'utilisateur
				String numCorrect = "Confirmez le numéro du fournisseur.\n"
						+ "Selectionnez l'option souhaitée dans la liste ci-dessous en appuiant le numéro correspondant sur le clavier.\n"
						+ "\t1 - Entrez le numéro du fournisseur.\n"
						+ "\t2 - Revenir au menu précédent.\n";
				int selection = promptOption(numCorrect, 2);
				
				switch (selection) {
					case 1: num = promptNumFournisseur();
							break;
					case 2: return;		
				}
			}
			
			//Trouve le fournisseur dans la liste de fournisseurs
			try {
				fourRap = (Fournisseur2) listeFournisseur.trouverCompte(num);
				validNum = true;
			}
			catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				continue;
			}			
		} while (!validNum);
		
		boolean periode = false;
		boolean premierEntreeBoucle2 = true;
		String debutPeriode = "";
		String finPeriode = "";
		
		//Demander un période valide pour le rapport (date du début >= date de la fin).
		do {
			if (premierEntreeBoucle2) {	
				
				debutPeriode = promptDate("Entrez la date du début de la période du rapport.");
				finPeriode = promptDate("Entrez la date de la fin de la période du rapport.");
				
				// Période valide
				if (comparerDates(debutPeriode, finPeriode) <= 0) {
					periode = true;
				} else {
					premierEntreeBoucle2 = false;
				}
				
			} else {
				// Si période invalide demander prochaine action de l'utilisateur
				String numCorrect = "La date de fin de la période doit être plus grande où égale à celle du début.\n"
						+ "Selectionnez l'option souhaitée dans la liste ci-dessous en appuiant composant le numéro correspondant sur le clavier.\n"
						+ "\t1 - Entrez la période.\n"
						+ "\t2 - Revenir au menu précédent.\n";
				int selection = promptOption(numCorrect, 2);
				
				switch (selection) {
					case 1: debutPeriode = promptDate("Entrez la date du début de la période du rapport.");
							finPeriode = promptDate("Entrez la date de la fin de la période du rapport.");
							if (comparerDates(debutPeriode, finPeriode) <= 0) {
								periode = true;
							} 
							break;
					case 2: return;		
				}
			}
		} while (!periode);		
		
		// créer le path et le dossier pour l'enregistrement des rapports
		String pathS = new File("").getAbsolutePath();
		Path path = Paths.get(pathS);
		if (Files.exists(path)) {
			new File(pathS+"\\Rapports_Fournisseurs").mkdir();
		}
		
		// définir la date d'impression du rapport et la période analysée
		String dateNomRap = debutPeriode+"_a_"+finPeriode;
		ServiceRendu[] servFournis = fourRap.getListeService();
		
		// path et nom du rapport
		String nomDuFichier = pathS+"\\Rapports_Fournisseurs\\rapport_fournisseur_no_"+num+dateNomRap+".txt";
				
		// Construction du rapport
		Date date = new Date();
		String dateS = date.toString();
		String dateRap = dateS.substring(0, 3)+" "+dateS.substring(8, 10)+" "+dateS.substring(4, 7)+" "+dateS.substring(24, 28);

		String rapport = "Rapport de Fournisseur\r\n\r\n";
		rapport += "Date du rapport : "+dateRap+":\r\n\r\n";
		rapport += "Période du rapport : "+debutPeriode+" à "+finPeriode;
		rapport += fourRap.toString();		
		rapport += "Services fournis :\r\n\r\n";
		
		int qteServFourTotal = 0;			// garder la qte total des services fournis par le fournisseur
		double valeurServFourTotal = 0;		// garder la valeur total des services fournis par le fournisseur
				
		// Itérer sur la liste des services fournis par le fournisseur
		for (ServiceRendu service : servFournis) {

			if (service instanceof ServiceRendu) {
				
				// si le service appartient à la période du rapport
				if (appartientPeriode(debutPeriode, finPeriode, (service.getDateHeureActuelle()).substring(0,10))) {
					qteServFourTotal++;
					valeurServFourTotal += service.getService().getFrais();
					rapport += service.toString()+"\r\n";
				}
			}
			
		}
		
		rapport += "Quantité Total Services Fournis : "+qteServFourTotal+"\r\n";
		rapport += "Valeur Total Services Fournis : "+formatter.format(valeurServFourTotal)+"\r\n\r\n";
		
		// Appel à la méthode de la classe ChocAn
		imprimerRapport(rapport, nomDuFichier);	
		System.out.println("Rapport de fournisseur généré avec succès. Regardez dossier Rapports_Fournisseurs.\n");
	}

	/**
	 * Méthode pour générer le rapport de synthèse	 * 
	 * Elle itére sur toutes les listes de services reçu de tous les membres et
	 * sur la liste de services fournis de tous les fournisseurs
	 * Elle calcule la quantité des services fournis et reçus et la valeur correspondante
	 * Elle crée met le rapport créé dans un dossier Rapport_Synthèse créé à partir de l'adresse du projet
	 */
	public void genRapportSynthese() {
		
		boolean premierEntreeBoucle = true;
		boolean periode = false;
		String debutPeriode = "";
		String finPeriode = "";

		//Demander un période valide pour le rapport (date du début >= date de la fin).
		do {
			if (premierEntreeBoucle) {	
				
				debutPeriode = promptDate("Entrez la date du début de la période du rapport.");
				finPeriode = promptDate("Entrez la date de la fin de la période du rapport.");
				
				// Période valide
				if (comparerDates(debutPeriode, finPeriode) <= 0) {
					periode = true;
				} else {
					premierEntreeBoucle = false;
				}
				
			} else {
				// Si période invalide demander prochaine action de l'utilisateur
				String numCorrect = "La date de fin de la période doit être plus grande où égale à celle du début.\n"
						+ "Selectionnez l'option souhaitée dans la liste ci-dessous en appuiant composant le numéro correspondant sur le clavier.\n"
						+ "\t1 - Entrez la période.\n"
						+ "\t2 - Revenir au menu précédent.\n";
				int selection = promptOption(numCorrect, 2);
				
				switch (selection) {
					case 1: debutPeriode = promptDate("Entrez la date du début de la période du rapport.");
							finPeriode = promptDate("Entrez la date de la fin de la période du rapport.");
							if (comparerDates(debutPeriode, finPeriode) <= 0) {
								periode = true;
							} 
							break;
					case 2: return;		
				}
			}
		} while (!periode);
		
		// créer le path et le dossier pour l'enregistrement des rapports
		String pathS = new File("").getAbsolutePath();
		Path path = Paths.get(pathS);
		if (Files.exists(path)) {
			new File(pathS+"\\Rapports_Synthese").mkdir();
		}
		
		// définir la date d'impression du rapport et la période analysée
		Date date = new Date();
		String dateS = date.toString();
		String dateRap = dateS.substring(0, 3)+" "+dateS.substring(8, 10)+" "+dateS.substring(4, 7)+" "+dateS.substring(24, 28);
		String dateNomRap = debutPeriode+"_a_"+finPeriode;
		
		// path et nom du rapport
		String nomDuFichier = pathS+"\\Rapports_Synthese\\rapport_synthese_"+dateNomRap+".txt";
				
		// Construction du rapport
		String rapport = "Rapport de Synthèse\r\n\r\n";		
		rapport += "Date du rapport : "+dateRap+":\r\n\r\n";
		rapport += "Période du rapport : "+debutPeriode+" à "+finPeriode+"\r\n\r\n";		
		rapport += "Services fournis au membres :\r\n\r\n";				
		
		int qteServMemTotal = 0;			// garder la qte total des services reçus par le membre
		double valeurServMemTotal = 0;		// garder la valeur total des services reçus par le membre
		
		// Itérer sur chaque membre de la liste de membres
		Compte[] membreTemp = listeMembre.getListe();
		for (Compte membre : membreTemp) {
			
			int qteServMemTemp = 0;			// compteurs pour chaque service
			double valeurServMemTemp = 0;
			
			if (membre instanceof Membre2) {
				
				// récuperer la liste de services reçus par le Membre
				ServiceRendu[] servTemp = membre.getListeService();
				
				for (ServiceRendu service: servTemp) {
					
					if (service instanceof ServiceRendu) {
						
						// Si le service est compris dans la période du rapport
						if (appartientPeriode(debutPeriode, finPeriode, (service.getDateHeureActuelle()).substring(0,10))) {
							qteServMemTemp++;
							valeurServMemTemp += service.getService().getFrais();
						}
					}				
				}
			}			
			
			// Si au moins un service reçu
			if (qteServMemTemp != 0) {
				
				qteServMemTotal += qteServMemTemp;
				valeurServMemTotal += valeurServMemTemp;
				rapport += "No. Membre\tQté. Serv. Fournis\tValeur Services\r\n";
				rapport += membre.getNumero()+"\t\t"+qteServMemTemp+"\t\t"+formatter.format(valeurServMemTemp)+"\r\n";
			}
		}
		
		rapport += "Quantité Total Services Fournis : "+qteServMemTotal+"\r\n";
		rapport += "Valeur Total Services Fournis : "+formatter.format(valeurServMemTotal)+"\r\n\r\n";
		
		int qteServFourTotal = 0;
		double valeurServFourTotal = 0;
		
		rapport += "Services facturés à ChocAn :\r\n\r\n";
		
		// Itérer sur chaque fournisseur de la liste de fournisseurs
		Compte[] fourTemp = listeFournisseur.getListe();
		for (Compte fournisseur : fourTemp) {			

			int qteServFourTemp = 0;
			double valeurServFourTemp = 0;
			
			if (fournisseur instanceof Fournisseur2) {				
				
				// récuperer la liste de services fournis par le Fournisseur
				ServiceRendu[] servTemp2 = fournisseur.getListeService();
				
				for (ServiceRendu service: servTemp2) {
					
					if (service instanceof ServiceRendu) {
						
						// Si le service est compris dans la période du rapport
						if (appartientPeriode(debutPeriode, finPeriode, (service.getDateHeureActuelle()).substring(0,10))) {
							qteServFourTemp++;
							valeurServFourTemp += service.getService().getFrais();	
							
							rapport += "No. Fournisseur\tCode Service\tValeur Service\r\n";						
							rapport += fournisseur.getNumero()+"\t\t"+service.getService().getCode()+"\t\t"+formatter.format(service.getService().getFrais())+"\r\n";
						
						}
					}				
				}
			}			
			
			// Si au moins un service fourni
			if (qteServFourTemp != 0) {
				
				qteServFourTotal += qteServFourTemp;
				valeurServFourTotal += valeurServFourTemp;
				rapport += "Quantité Services Facturés Fournisseur no. "+fournisseur.getNumero()+" : "+qteServFourTemp+"\r\n";
				rapport += "Valeur Total Services Facturés Fournisseur no. "+fournisseur.getNumero()+" : "+formatter.format(valeurServFourTemp)+"\r\n\r\n";
			}
		}
		
		rapport += "Quantité Total Services Facturés : "+qteServFourTotal+"\r\n";
		rapport += "Valeur Total Services Facturés : "+formatter.format(valeurServFourTotal)+"\r\n\r\n";
		
		// Appel à la méthode de la classe ChocAn
		imprimerRapport(rapport, nomDuFichier);
		System.out.println("Rapport de synthèse généré avec succès. Regardez dossier Rapports_Synthese.");
	}
}