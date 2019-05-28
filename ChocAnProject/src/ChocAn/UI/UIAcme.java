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
import ChocAn.Compte.Membre.*;
import ChocAn.Compte.*;

public class UIAcme extends UI {
	
	
	public UIAcme(ListeDeCompte2 lm){
		super(lm,null,null,null);
	}
	
	public void menu(){
		
		//Menu Activation Terminal
		String itemsMenu = "Menu Connection :\n"
                + "Selectionnez l'option souhaitée dans la liste ci-dessous en appuiant le numéro correspondant sur le clavier.\n"
                + "\t1 - Changer Statut d'un membre. \n"
                + "\t2 - Sortir.";
		
		boolean sortir = false;
		
		while(!sortir){
			
			int selection = promptOption(itemsMenu, 2);
			
			switch (selection) {
				case 1:
					changerStatut(promptNumMembre());
					break;
				case 2:
					return;
			}
		}
	}
	
	/**
	 * 
	 * @param num
	 */
	public void changerStatut(int num) {
		Membre2 m = null;
		try {
			m = (Membre2) listeMembre.trouverCompte(num);
		} catch (Exception e) {
			System.out.println(e);
			return;
		}
		
		m.changeStatut();
		
		System.out.println("Nouveau Statut : "+m.getStatut());
		
		return;
	}

}