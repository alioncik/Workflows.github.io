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
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import ChocAn.Service.*;
import ChocAn.Compte.Fournisseur.*;
import ChocAn.Compte.Membre.*;

public class UIFournisseur extends UI {
	
	//Attributs
	private Fournisseur2 fournisseur;
	
	public UIFournisseur (ListeDeCompte2 lm, ListeDeCompte2 lf, ListeDeService2 ls, RepertoireDesFournisseurs2 rf){
		
		super(lm,lf,ls,rf);
	}

	/**
	 * Méthode pour le flow du Fournisseur. Permet à au fournisseur de faire des actions via le terminal.
	 * Le fournisseur doit d'abord activer le terminal avec un numéro de Fournisseur valide avant d'avoir accès à plus d'options.
	 */
	public void menu(){
		
		//Menu Activation Terminal
		String itemsMenu = "Menu Connection :\n"
                + "Selectionnez l'option souhaitée dans la liste ci-dessous en appuyant le numéro correspondant sur le clavier.\n"
                + "\t1 - Activer Terminal. \n"
                + "\t2 - Sortir.";
		
		boolean terminalActif = false;
		
		while(!terminalActif){
			
			int selection = promptOption(itemsMenu, 2);
			
			switch (selection) {
				case 1:
					terminalActif = activerTerminal();
					break;
				case 2:
					return;
			}
		}
		
		//Menu Terminal Activé
		itemsMenu = "Terminal du Fournisseur :\n"
                + "Selectionnez l'option souhaitée dans la liste ci-dessous en appuyant le numéro correspondant sur le clavier.\n"
                + "\t1 - Glisser une carte de membre dans le terminal. \n"
                + "\t2 - Consulter le répertoire des fournisseurs.\n"
                + "\t3 - Sortir.";
		
		while(true){
			
			int selection = promptOption(itemsMenu, 3);
			
			switch (selection) {
				case 1:
					enregistrerService();
					break;
				case 2:
					consulterRepFournisseur();
					break;
				case 3:
					return;
			}
		}
	}
	
	/**
	 * Méthode qui active le terminal du Fournisseur.
	 * Si l'activation fonctionne, initialise l'attribut <i>fournisseur</i> du UIFournisseur courant avec le compte du Fournisseur qui l'utilise.
	 * @return Retourne true si le terminal a été activé avec succès
	 */
	public boolean activerTerminal() {
		
		int numero = promptNumFournisseur();
		
		Fournisseur2 fournisseur = null;
		
		try {
			fournisseur = (Fournisseur2) listeFournisseur.trouverCompte(numero);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println("Fournisseur introuvable.");
			return false;
		}		
		
		/*if(fournisseur == null){
			
			System.out.println("Numéro invalide. Fournisseur introuvable.");
			return false;
		}*/
		
		this.fournisseur = fournisseur;
		
		return true;
	}

	
	/**
	 * Fonction pour enregistrer un service rendu à un membre par un fournisseur.
	 * Crée le service rendu et l'ajoute à la liste de service.
	 * @param numero Numéro du membre à qui le service à été fourni.
	 */
	public void enregistrerService() {
		
		/*
		 * Validation du membre
		 */
		//Trouver si le membre existe dans la base de donnée ChocAn		
		Membre2 client = null;
		
		int numero = promptNumMembre();
				
		try {
			client = (Membre2) listeMembre.trouverCompte(numero);
			
		//Si on ne trouve pas le membre dans la liste
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return;
		}
		
		//Si on ne trouve pas le membre dans la liste
		/*if(client == null){
			System.out.println("Numero invalide. Membre introuvable.");
			return;
		}*/
		
		//Si le membre trouvé est suspendu
		if(client.getStatut() == StatutMembre.Suspendu){
			System.out.println("Membre suspendu.\n");
			return;
		}
		//Sinon le numéro est valide
		System.out.println("Numero valide.\n");
		
		
		/*
		 * Enregistrement du service
		 */
		boolean validCode = false;
		int code;
		Service2 service = null;
		
		//Demande le code du service offert par le fournisseur jusqu'à ce qu'il entre un code valide.
		do{
			code = promptCodeService();
			
			//Trouve le code dans le répertoire des fournisseurs
			try{
				service = repFournisseur.trouverService(code);
			}
			catch(Exception e){
				System.out.println(e.getLocalizedMessage());
				continue;
			}
			//Si un service est trouvé dans le répertoire, on valide que c'est bien le bon service.
			String confirme = "Il s'agit bien du bon service : " + service.getNom() + "?";
			if(promptOuiNon(confirme))
				validCode = true;
			
		}while(!validCode);
		
		
		String date = promptDate("Entrez la date à laquelle le service à été fourni :");
		
		//On laisse le fournisseur entrer un commentaire s'il le décide
		String commentaire = "";
		
		if(promptOuiNon("Désirez-vous entrer un commentaire?")){
			commentaire = prompt("Entrez un commentaire (maximum 100 caractères) :", 0, 100);
		}
				
		listeService.ajouterServiceRendu(date, commentaire, client, this.fournisseur, service);
		
		return;
	}

	/**
	 * 
	 * @param num
	 */
	public void consulterRepFournisseur() {
		
		/*
		 * Construction du rapport
		 */
		Service2 [] servListe;
		String repertoire = "Répertoire Des Fournisseurs\r\n\r\n";
		
		try{
			servListe = this.repFournisseur.trierListeAlpha();
		}
		catch(Exception e){
			System.out.println(e.getLocalizedMessage());
			return;
		}
		
		for(int i=0; i<servListe.length; i++){
			
			if(servListe[i] == null)
				break;
			
			Service2 s = servListe[i];
			
			repertoire += (i+1)+". "+s.getNom()+" :\r\n\tCode : " + s.getCode()+"\r\n\tFrais : "+s.getFrais()+"$\r\n\r\n";
		}
		
		
		/*
		 * Création du fichier dans le dossier4
		 */
		String pathS = new File("").getAbsolutePath();
		Path path = Paths.get(pathS);
		if (Files.exists(path)) {
			new File(pathS+"\\Repertoire_Fournisseur").mkdir();
		}
		
		String nomDuFichier = pathS+"\\Repertoire_Fournisseur\\repertoireDufournisseur.txt";
			
		UI.imprimerRapport(repertoire, nomDuFichier);
		
		System.out.println("Répertoire des Fournisseurs généré avec succès!");
		
		envoyerEmail(this.fournisseur.getEmail(), "Répertoire des fournisseurs", pathS + "Repertoire_Fournisseur\\repertoireDufournisseur.txt\n");

		return;
	}
	
	public void envoyerEmail(String email, String message, String joinPath){
		
		System.out.println("\n(Simulation 'envoyer un email'. Pas implémenté pour vrai)");
		System.out.println("Email envoyé :\n\tÀ : "+ email + "\n\tFichier joint : "+ joinPath);
		return;
	}
	
}