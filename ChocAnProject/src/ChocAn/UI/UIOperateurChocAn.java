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
import ChocAn.Compte.*;
import ChocAn.Compte.Membre.*;
import ChocAn.Compte.Fournisseur.*;
import ChocAn.Service.*;


public class UIOperateurChocAn extends UI {
	
	public UIOperateurChocAn (ListeDeCompte2 lm, ListeDeCompte2 lf, ListeDeService2 ls, RepertoireDesFournisseurs2 rf) {
			
			super(lm,lf,ls,rf);
	
	}
	
	/**
	 * Affiche les options des opérations à effectuer par l'utilisateur (opérateur ChocAn)
	 * Utilise le méthode promptOption de UI
	 * @Override la méthode menu() de la super classe UI
	 */
	public void menu(){
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
					+ "\t10 - Revenir au menu principal.\n";
			int selection = promptOption(itemsMenu, 10);
			
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
				case 10: 	menuActif = false;
							break;	
				default: 	break;
			}	
		}
	}

	/**
	 * Méthode pour ajouter un Membre à ListeDeMembre
	 */
	public void ajouterMembre() {	
		String nom = prompt("Entrez le nom du membre (maximum 25 caractères) : ", 1, 25);
		String adresse = prompt("Entrez l'adresse (maximum 25 caractères) : ", 1, 25);
		String ville = prompt("Entrez la ville (maximum 25 caractères) : ", 1, 25);
		String province = prompt("Entrez la province (doit avoir 2 caractères) : ", 2, 2);
		String codePostal = prompt("Entrez le codePostal (doit avoir 5 caractères) : ", 5, 5);
		String email = prompt("Entrez le courriel (maximum 25 caractères) : ", 1, 25);	
		
		Membre2 newMembre = new Membre2(nom, adresse, ville, province, codePostal, email);
		listeMembre.ajouterCompte(newMembre);
		System.out.println("Membre numero "+newMembre.getNumero()+" ajouté avec success.\n");
	}

	/**
	 * Méthode pour ajouter un Fournisseur à ListeDeFournisseur
	 */
	public void ajouterFournisseur() {
		String nom = prompt("Entrez le nom du fournisseur (maximum 25 caractères) : ", 1, 25);
		String adresse = prompt("Entrez l'adresse (maximum 25 caractères) : ", 1, 25);
		String ville = prompt("Entrez la ville (maximum 25 caractères) : ", 1, 25);
		String province = prompt("Entrez la province (doit avoir 2 caractères) : ", 2, 2);
		String codePostal = prompt("Entrez le codePostal (doit avoir 5 caractères) : ", 2, 5);
		String email = prompt("Entrez le courriel (maximum 25 caractères) : ", 1, 25);
		
		Fournisseur2 newFournisseur = new Fournisseur2(nom, adresse, ville, province, codePostal, email);
		listeFournisseur.ajouterCompte(newFournisseur);
		System.out.println("Fournisseur numéro "+newFournisseur.getNumero()+" ajouté avec success.\n");
	}
	

	/**
	 * Méthode pour supprimer un Membre de ListeDeMembre
	 * @param num : le numéro de compte du Membre
	 */
	public void supprimerMembre() {
		
		int num = 0;
		boolean validNum = false;
		boolean premierEntreeBoucle = true;
		Membre2 membre = null;
		
		//Demande un numero de membre valide.
		do {
			if (premierEntreeBoucle) {
				num = promptNumMembre();
				premierEntreeBoucle = false;
			} else {
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
				membre = (Membre2) listeMembre.trouverCompte(num);				
				String message = "C'est bien le membre qui vous voulez supprimer ?\n"+membre.toString();
				boolean confirmation = promptOuiNon(message);
				
				if (confirmation) {
					
					String message2 = "Êtes-vous sûr de la suppression ?\n\n";
					boolean confirmationFinale = promptOuiNon(message2);
						listeMembre.supprimerCompte(num);
						System.out.println("Membre supprimé avec succès.\n");
					if (confirmationFinale) {
						
					}
				}				
				validNum = true;
			}
			catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				continue;
			}			
		} while (!validNum);
		
		//listeMembre.supprimerCompte(num);
	}

	/**
	 * Méthode pour supprimer un Fournisseur de ListeDeFournisseur
	 * @param num : le numéro de compte du Fournisseur
	 */
	public void supprimerFournisseur() {
		
		int num = 0;
		boolean validNum = false;
		boolean premierEntreeBoucle = true;
		Fournisseur2 fournisseur = null;
		
		//Demande un numero de fournisseur valide.
		do {
			if (premierEntreeBoucle) {
				num = promptNumFournisseur();
				premierEntreeBoucle = false;
			} else {
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
				
				fournisseur = (Fournisseur2) listeFournisseur.trouverCompte(num);		
				String message = "C'est bien le fournisseur qui vous voulez supprimer ?\n"+fournisseur.toString();
				boolean confirmation = promptOuiNon(message);
				
				if (confirmation) {
					
					String message2 = "Êtes-vous sûr de la suppression ?\n\n";
					boolean confirmationFinale = promptOuiNon(message2);

					if (confirmationFinale) {
						listeFournisseur.supprimerCompte(num);
						System.out.println("Fournisseur supprimé avec succès.\n");
					}
				}				
				validNum = true;
			}
			catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				continue;
			}			
		} while (!validNum);	
		
		//listeFournisseur.supprimerCompte(num);
	}

	/**
	 * Méthode pour modifier les champs (nom, age, adresse, etc.) d'un compte de Membre
	 * @param num : le numéro de compte du Membre
	 */
	public void modifierMembre() {
		
		int num = 0;
		boolean validNum = false;
		boolean premierEntreeBoucle = true;
		Membre2 membre = null;
		
		//Demande un numero de membre valide.
		do {
			if (premierEntreeBoucle) {
				num = promptNumMembre();
				premierEntreeBoucle = false;
			} else {
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
				membre = (Membre2) listeMembre.trouverCompte(num);
				validNum = true;
			}
			catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				continue;
			}			
		} while (!validNum);	

		boolean menuActif = true;
		while (menuActif) {			
			String itemsMenu = "Mise à jour compte de membre \n"
					+ "État actuel du compte :\n"+membre.toString()				
					+ "Selectionnez l'option souhaitée dans la liste ci-dessous en appuiant le numéro correspondant sur le clavier.\n"
					+ "\t1 - Mettre à jour le nom.\n"
					+ "\t2 - Mettre à jour l'adresse.\n"
					+ "\t3 - Mettre à jour la ville.\n"
					+ "\t4 - Mettre à jour la province.\n"
					+ "\t5 - Mettre à jour le codePostal.\n"
					+ "\t6 - Mettre à jour le courriel.\n"
					+ "\t7 - Revenir au menu précédent.\n";
			int selection = promptOption(itemsMenu, 7);
			
			switch (selection) {
				case 1: 	String nom = prompt("Entrez le nom du membre (maximum 25 caractères) : ", 1, 25);
							membre.setNom(nom);				
							break;
				case 2: 	String adresse = prompt("Entrez l'adresse (maximum 25 caractères) : ", 1, 25);
							membre.setAdresse(adresse);
							break;
				case 3: 	String ville = prompt("Entrez la ville (maximum 25 caractères) : ", 1, 25);
							membre.setVille(ville);
							break;
				case 4: 	String province = prompt("Entrez la province (doit avoir 2 caractères) : ", 2, 2);
							membre.setProvince(province);
							break;
				case 5: 	String codePostal = prompt("Entrez le codePostal (doit avoir 5 caractères) : ", 5, 5);
							membre.setCodePostal(codePostal);
							break;
				case 6: 	String email = prompt("Entrez le courriel (maximum 25 caractères) : ", 1, 25);
							membre.setEmail(email);	
							break;				
				case 7: 	menuActif = false;
							break;	
				default: 	break;
			}	
		}
		System.out.println("Membre mis à jour.\n");
	}

		/**
		 * Méthode pour modifier les champs (nom, age, adresse, etc.) d'un compte de Fournisseur
		 * @param num : le numéro de compte du Fournisseur
		 */
		public void modifierFournisseur() {
			
			int num = 0;
			boolean validNum = false;
			boolean premierEntreeBoucle = true;
			Fournisseur2 fournisseur = null;
			
			//Demande un numero de fournisseur valide.
			do {
				if (premierEntreeBoucle) {
					num = promptNumFournisseur();
					premierEntreeBoucle = false;
				} else {
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
					fournisseur = (Fournisseur2) listeFournisseur.trouverCompte(num);
					if (fournisseur instanceof Fournisseur2) {
						System.out.println("Est fournisseur!");
					}
					System.out.println("TestFournisseur1");
					validNum = true;
				}
				catch (Exception e) {
					System.out.println(e.getLocalizedMessage());
					continue;
				}			
			} while (!validNum);	

			boolean menuActif = true;
			while (menuActif) {
				System.out.println("TestFournisseur2");
				String itemsMenu = "Mise à jour compte de membre \n"
						+ "État actuel du compte :\n"+fournisseur.toString()		
						+ "Selectionnez l'option souhaitée dans la liste ci-dessous en appuiant le numéro correspondant sur le clavier.\n"
						+ "\t1 - Mettre à jour le nom.\n"
						+ "\t2 - Mettre à jour l'adresse.\n"
						+ "\t3 - Mettre à jour la ville.\n"
						+ "\t4 - Mettre à jour la province.\n"
						+ "\t5 - Mettre à jour le codePostal.\n"
						+ "\t6 - Mettre à jour le courriel.\n"
						+ "\t7 - Revenir au menu précédent.\n";
				int selection = promptOption(itemsMenu, 7);
				
				switch (selection) {
					case 1: 	String nom = prompt("Entrez le nom du membre (maximum 25 caractères) : ", 1, 25);
								fournisseur.setNom(nom);				
								break;
					case 2: 	String adresse = prompt("Entrez l'adresse (maximum 25 caractères) : ", 1, 25);
								fournisseur.setAdresse(adresse);
								break;
					case 3: 	String ville = prompt("Entrez la ville (maximum 25 caractères) : ", 1, 25);
								fournisseur.setVille(ville);
								break;
					case 4: 	String province = prompt("Entrez la province (doit avoir 2 caractères) : ", 2, 2);
								fournisseur.setProvince(province);
								break;
					case 5: 	String codePostal = prompt("Entrez le codePostal (doit avoir 5 caractères) : ", 5, 5);
								fournisseur.setCodePostal(codePostal);
								break;
					case 6: 	String email = prompt("Entrez le courriel (maximum 25 caractères) : ", 1, 25);
								fournisseur.setEmail(email);	
								break;				
					case 7: 	menuActif = false;
								break;	
					default: 	break;
				}	
			}
			System.out.println("Fournisseur mis à jour.\n");
		}
		
	/**
	 * Méthode pour ajouter un Service au Répertoire des Fournisseurs
	 * @param code : le code du Service
	 */
	public void ajouterService() {
		String nom = prompt("Entrez le nom du service (maximum 25 caractères) : ", 1, 25);
		double frais = promptFrais("Entrez les frais (ex. 1507.74) : ");	
		
		Service2 newService = new Service2(nom, frais);
		repFournisseur.ajouterService(newService);
		System.out.println("Service code "+newService.getCode()+" ajouté avec success.\n");

	}
	
	/**
	 * Méthode pour modifier les champs (nom et frais) d'un Service du Répertoire des Fournisseurs
	 * @param code : le code du Service
	 */
	public void modifierService() {
		
		int code = 0;
		boolean validNum = false;
		boolean premierEntreeBoucle = true;
		Service2 service = null;
		String serviceS = "";
		
		//Demande un code de Service valide.
		do {
			if (premierEntreeBoucle) {
				code = promptCodeService();
				premierEntreeBoucle = false;
			} else {
				String codeCorrect = "Confirmez le code du Service.\n"
						+ "Selectionnez l'option souhaitée dans la liste ci-dessous en appuiant le numéro correspondant sur le clavier.\n"
						+ "\t1 - Entrez le code du Service.\n"
						+ "\t2 - Revenir au menu précédent.\n";
				int selection = promptOption(codeCorrect, 2);
				
				switch (selection) {
					case 1: code = promptCodeService();
							break;
					case 2: return;			
				}
			}
			
			//Trouve le service dans le Répertoire de Service
			try {
				System.out.println("test0");
				service = repFournisseur.trouverService(code);
				System.out.println("test");
				serviceS = service.toString();
				System.out.println("test2");
				validNum = true;
			}
			catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				continue;
			}			
		} while (!validNum);	

			
		boolean menuActif = true;
		while (menuActif) {
			String itemsMenu = "Mise à jour d'un service\n"
					+ "État actuel du service :" + serviceS+"\n"
					+ "Selectionnez l'option souhaitée dans la liste ci-dessous en appuiant le numéro correspondant sur le clavier.\n"
					+ "\t1 - Mettre à jour le nom.\n"
					+ "\t2 - Mettre à jour les frais.\n"
					+ "\t3 - Revenir au menu précédent.\n";
			int selection = promptOption(itemsMenu, 3);
			
			switch (selection) {
				case 1: 	String nom = prompt("Entrez le nouveau nom du service (maximum 25 caractères) : ", 1, 25);
							service.setNom(nom);
							break;
				case 2: 	String frais = prompt("Entrez les nouveaux frais du service (sans le $) : ", 1, 25);
							double DFrais = Double.parseDouble(frais);
							service.setFrais(DFrais);	
							break;
				case 3: 	menuActif = false;
							break;	
				default: 	break;
			}	
		}
		System.out.println("Service mis à jour.\n");
	}
	
	/**
	 * Méthode pour supprimer un Service du Répertoire des Fournisseurs
	 * @param code : le code du Service
	 */
	public void supprimerService() {
		
		int code = 0;
		boolean validNum = false;
		boolean premierEntreeBoucle = true;
		Service2 service = null;
				
		//Demande un code de Service valide.
		do {
			if (premierEntreeBoucle) {
				code = promptCodeService();
				premierEntreeBoucle = false;
			} else {
				String codeCorrect = "Confirmez le code du Service.\n"
						+ "Selectionnez l'option souhaitée dans la liste ci-dessous en appuiant le numéro correspondant sur le clavier.\n"
						+ "\t1 - Entrez le code du Service.\n"
						+ "\t2 - Revenir au menu précédent.\n";
				int selection = promptOption(codeCorrect, 2);
				
				switch (selection) {
					case 1: code = promptCodeService();
							break;
					case 2: return;			
				}
			}
			
			//Trouve le service dans le Répertoire de Service
			try {
				
				service = repFournisseur.trouverService(code);		
				String message = "C'est bien le service qui vous voulez supprimer ?\n"+service.toString();
				boolean confirmation = promptOuiNon(message);
				
				if (confirmation) {
					
					String message2 = "Êtes-vous sûr de la suppression ?\n\n";
					boolean confirmationFinale = promptOuiNon(message2);

					if (confirmationFinale) {
						repFournisseur.supprimerService(code);
						System.out.println("Service supprimé avec succès.\n");
					}
				}							
				validNum = true;
			}
			catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				continue;
			}			
		} while (!validNum);
	}
}