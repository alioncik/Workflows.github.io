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

public class ChocAnUI extends UI {
	
	public ChocAnUI (ListeDeCompte2 lm, ListeDeCompte2 lf, ListeDeService2 ls, RepertoireDesFournisseurs2 rf){
		
		super(lm,lf,ls,rf);
	}
	
	public void menu(){
		
		boolean sortir = false;
		UI ui = null;
		
		String itemsMenu = "Menu Connection :\n"
                + "Composez le numéro correspondant sur le clavier.\n"
                + "\t1 - Acme.\n"
                + "\t2 - Fournisseur.\n"
                + "\t3 - Gérant ChocAn.\n"
                + "\t4 - Opérateur ChocAn.\n"
                + "\t5 - Sortir.";
		
		while(!sortir){
			
			int selection = promptOption(itemsMenu, 5);
			
			switch (selection) {
				case 1:
					ui = new UIAcme(listeMembre);
					break;
				case 2:
					ui = new UIFournisseur(listeMembre, listeFournisseur, listeService, repFournisseur);
					break;
				case 3:
					ui = new UIGerant(listeMembre, listeFournisseur, listeService, repFournisseur);
					break;
				case 4:
					ui = new UIOperateurChocAn(listeMembre, listeFournisseur, listeService, repFournisseur);
					break;
				case 5:
					//sortir = true;
					//break;
					System.exit(0);
			}
			
			ui.menu();
		}
		
		return;	
	}
}
