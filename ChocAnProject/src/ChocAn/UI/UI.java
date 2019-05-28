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
import ChocAn.Service.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.*;
import java.util.Scanner;


public abstract class UI {
	
	protected ListeDeCompte2 listeMembre;
	protected ListeDeCompte2 listeFournisseur;
	protected ListeDeService2 listeService;
	protected RepertoireDesFournisseurs2 repFournisseur;
	
	/**
	 * Constructeur par défaut pour les UI.
	 * @param lm Liste des membres ChocAn.
	 * @param lf Liste des fournisseurs ChocAn.
	 * @param ls Liste des services rendus par ChocAn pour la semaine courante.
	 * @param rf Liste des service offert par ChocAn.
	 */
	public UI (ListeDeCompte2 lm, ListeDeCompte2 lf, ListeDeService2 ls, RepertoireDesFournisseurs2 rf){
		
		this.listeMembre = lm;
		this.listeFournisseur = lf;
		this.listeService = ls;
		this.repFournisseur = rf;
	};
	
	/**
	 * Méthode à implémenter dans les sous-classes. Chaque UI à son propre menu.
	 * C'est cette méthode qui est appelé de l'extérieur pour débuter le flow du UI.
	 * Affiche les options disponibles à l'utilisateur.
	 * Exécute les actions choisies par l'utilisateur.
	 */
	public abstract void menu();
	
	/* Méthodes Prompt* :
	 * Disponible dans tous les instances d'UI.
	 * Pemettent de recevoir les inputs de l'utilisateur dans le format voulu.
	 */
	
	/**
	 * Méthode pour demander des inputs à l'utilisateur.
	 * C'est la méthode qui demande les inputs de la forme la plus général : un String de longeur indéfini.
	 * D'autres méthodes appelent <b><i>prompt</i></b> pour demander des inputs plus précis.
	 * @param message Message à afficher à l'utilisateur pour lui faire savoir ce qui est attendu.
	 * @return Un String entré par l'utilisateur.
	 */
	public String prompt(String message) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println(message);
		String input = scanner.nextLine();
		
		return input;
	}
	
	
	/**
	 * Méthode pour demander des inputs à l'utilisateur.
	 * Demande un String à l'utilisateur, avec des bornes sur la longueur attendue.
	 * @param message Message à afficher à l'utilisateur pour lui faire savoir ce qui est attendu.
	 * @param min Longueur minimal du input de l'utilisateur.
	 * @param max Longueur maximal du input de l'utilisateur.
	 * @return Un String de la longueur attendu, entré par l'utilisateur.
	 */
	public String prompt(String message, int min, int max){
		
		String input;
		
		while(true){
			
			input = prompt(message);
			
			if(input.length() >= min && input.length() <= max)
				break;
			else if(min == max)
				System.out.println("Vous devez entrer " + max + " caractères\n");
			else
				System.out.println("Vous devez entrer entre " + min + " et " + max + " caractères\n");
		}
		
		return input;
	}

	/**
	 * Méthode qui permet à l'utilisateur de choisir une option parmis celles offertes.
	 * La méthode affichent les options disponible et le numéro de chaque option.
	 * L'utilisateur entre le numéro de l'option.
	 * @param message Message à afficher à l'utilisateur pour lui faire savoir ce qui est attendu.
	 * @param nombreOption Nombre d'option disponible.
	 * @return int Numero de l'option choisie.
	 */
	public int promptOption(String message, int nombreOption) {
		
		int option;	//Contiendra l'option choisi par l'utilisateur
		String input;	//Contriendra l'input de l'utilisateur avant d'être convertie en int
		
		//Demande à l'utilisateur d'entrer une option tant qu'il n'entre pas une option valide.
		while(true){
			
			input = prompt(message);
			
			try{
				option = Integer.parseInt(input);
				
				if(option > 0 && option <= nombreOption)
					break;
				else
					throw new NumberFormatException();
			}
			catch(NumberFormatException e){
				System.out.println("Option Invalide.\nEntrez une option entre 1 et "+ Integer.toString(nombreOption));
			}
		}
		
		return option;
	}
	
	/**
	 * Méthode qui demande à l'utilisateur de choisir parmis l'option 1 : oui ou l'option 2 : non.
	 * L'utilisateur entre le numéro de l'option.
	 * @param message Question qui peut être répondu par "oui" ou par "non" qui sera affiché à l'utilisateur.
	 * @return boolean true = "Oui", false = "Non".
	 */
	public boolean promptOuiNon(String message) {
		
		message += "\n1. Oui.\n2. Non.";
		
		int option = promptOption(message, 2);
		
		if(option == 1)
			return true;
		else
			return false;
	}
	
	/**
	 * Méthode pour demander un numéro de compte à l'utilisateur.
	 *  C'est une méthode de forme la plus général. Elle retourne un numéro de Membre ou de Fournisseur.
	 *  Les méthodes <b><i>promptNumMembre</i></b> et <b><i>promptNumFournisseur</i></b> appellent cette méthode pour demander des inputs plus précis.
	 * @param message Message à afficher à l'utilisateur pour lui faire savoir ce qui est attendu.
	 * @return Un numéro de compte à 9 chiffre.
	 */
	public int promptNumCompte(String message){
		
		int numero;	//Contiendra le numero entrée par l'utilisateur
		String input;	//Contriendra l'input de l'utilisateur avant d'être convertie en int
		
		//Demande à l'utilisateur d'entrer un numéro tant qu'il n'entre pas un numéro valide.
		while(true){
			
			input = prompt(message);
			
			try{
				numero = Integer.parseInt(input);
				
				if(input.length() == 9 && (input.charAt(0) == '1' || input.charAt(0) == '9'))
					break;
				else
					throw new NumberFormatException();
			}
			catch(NumberFormatException e){
				System.out.println("Numéro Invalide.\nLe numéro de compte doit avoir 9 chiffres.\n"
						+ "Numéro de Membre : 9xxxxxxxx\n"
						+ "Numéro de Fournisseur : 1xxxxxxxx\n");
			}
		}
		
		return numero;
	}
	
	/**
	 * Méthode qui appelle <b>promptNumMembre</b> avec un message par défaut.
	 * @return Un numéro de Membre entré par l'utilisateur.
	 */
	public int promptNumMembre(){
		return promptNumMembre("Entrez un numéro de membre :");
	}
	
	/**
	 * Méthode qui demande un numéro de Membre à l'utilisateur et le retourne.
	 * @param message Message à afficher à l'utilisateur pour lui faire savoir ce qui est attendu.
	 * @return Un numéro de Membre entré par l'utilisateur.
	 */
	public int promptNumMembre(String message){
		
		int numero;
		
		while(true){
			
			numero = promptNumCompte(message);
			
			if(numero >= 900000000)
				break;
			else
				System.out.println("Numéro Invalide.\nUn numéro de Membre doit avoir 9 chiffre et commencer par '9'.\n");
		}		
		
		return numero;
	}
	
	/**
	 * Méthode qui appelle <b>promptNumFournisseur</b> avec un message par défaut.
	 * @return Un numéro de Fournisseur entré par l'utilisateur.
	 */
	public int promptNumFournisseur(){
		return  promptNumFournisseur("Entrez un numéro de fournisseur :");
	}
	
	/**
	 * Méthode qui demande un numéro de Fournisseur à l'utilisateur et le retourne.
	 * @param message Message à afficher à l'utilisateur pour lui faire savoir ce qui est attendu.
	 * @return Un numéro de Fournisseur entré par l'utilisateur.
	 */
	public int promptNumFournisseur(String message){
		
		int numero;
		
		while(true){
			
			numero = promptNumCompte(message);
			
			if(numero < 200000000)
				break;
			else
				System.out.println("Numéro Invalide.\nUn numéro de Fournisseur doit avoir 9 chiffre et commencer par '1'.\n");
		}		
		
		return numero;
	}
	
	/**
	 * Méthode qui appelle <b>promptCodeService</b> avec un message par défaut.
	 * @return Un code de Service entré par l'utilisateur.
	 */
	public int promptCodeService(){
		return promptCodeService("Entrez un code de service :");
	}
	
	/**
	 * Méthode qui demande un code de Service à l'utilisateur et le retourne.
	 * @param message Message à afficher à l'utilisateur pour lui faire savoir ce qui est attendu.
	 * @return Un code de Service entré par l'utilisateur.
	 */
	public int promptCodeService(String message){
		
		int code;	//Contiendra le code entrée par l'utilisateur
		String input;	//Contriendra l'input de l'utilisateur avant d'être convertie en int
		
		//Demande à l'utilisateur d'entrer un numéro tant qu'il n'entre pas un numéro valide.
		while(true){
			
			input = prompt(message);
			
			try{
				code = Integer.parseInt(input);
				
				if(input.length() == 6)
					break;
				else
					throw new NumberFormatException();
			}
			catch(NumberFormatException e){
				System.out.println("Code Invalide.\nLe code de service doit avoir 6 chiffres.\n");
			}
		}
		
		return code;
	}
	
	/**
	 * Méthode qui demande un date à l'utilisateur et la retourne.
	 * @param message Message à afficher à l'utilisateur pour lui faire savoir ce qui est attendu.
	 * @return Une date entrée par l'utilisateur.
	 */
	public String promptDate(String message){
		
		String date;
		
		message += "\n(Dans le format : JJ-MM-AAAA)";
		
		while(true){
			
			date = prompt(message);
			try{
				
				if(date.length() != 10)
					throw new Exception();
			
				int jour = Integer.parseInt(date.substring(0,2));
				int mois = Integer.parseInt(date.substring(3,5));
				int annee = Integer.parseInt(date.substring(6));
				String s = "" + date.charAt(2) + date.charAt(5);
				
				if(s.equals("--")
					&& (jour > 0 && jour <= 31)
					&& (mois > 0 && mois <= 12)
					&& (annee >= 2000 && annee < 3000))
					break;
				else
					throw new Exception();
			}
			catch(NumberFormatException e){
				System.out.println("Date Invalide.\nLe format de la date doit être : JJ-MM-AAAA\n");
			}
			catch(Exception e){
				System.out.println("Date Invalide.\nLe format de la date doit être : JJ-MM-AAAA\n");
			}
		}
		
		return date;
	}
	
	/**
	 * 
	 * @param message
	 * @return
	 */
	public double promptFrais(String message){
		
		String strDouble;
		double d = 0.;
		boolean valide = false;
		
		do{
			strDouble = prompt(message);
			
			try{			
				d = Double.parseDouble(strDouble);
				
			}
			catch(NumberFormatException e){
				System.out.println("Frais invalide. Mauvais format.\n");
				continue;
			}
			
			valide = true;
			
		} while(!valide);
		
		return d;
	}
	
	/**
	 * M�thode utilis�e par les classes UIGerant et Horloge pour l'impression de rapports en format .txt
	 * @param rapport : String contenant le rapport � �tre imprim�
	 * @param nomDuFichier : String contenant le nom du fichier et l'emplacement m�moire o� il sera cr��
	 */
	public static void imprimerRapport(String rapport, String nomDuFichier) {
		
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			fw = new FileWriter(nomDuFichier);
			bw = new BufferedWriter(fw);
			bw.write(rapport);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}