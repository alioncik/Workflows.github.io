package chocAnTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import ChocAn.Compte.ListeDeCompte2;
import ChocAn.Service.ListeDeService2;
import ChocAn.Service.RepertoireDesFournisseurs2;
import ChocAn.UI.ChocAnUI;


/**
 * Test des méthodes pour entrer des données en UI
 * Test éfféctué par Augusto dos Santos Latgé 20083794
 */
public class UITest {
	
	private ChocAnUI ui;
	
	@Before 
	public void creerChocAnUI() {
		ListeDeCompte2 lm = new ListeDeCompte2();
		ListeDeCompte2 lf = new ListeDeCompte2();
		ListeDeService2 ls = new ListeDeService2();
		RepertoireDesFournisseurs2 rf = new RepertoireDesFournisseurs2();
		ui = new ChocAnUI(lm, lf, ls, rf);
	}
	
	
	@Test 
	/**
	 * Tester la méthode promptOuiNon
	 */
	public void testPromptOuiNon() {
		ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
		System.setIn(in);
		
		assertTrue(ui.promptOuiNon("Entrez oui ou non :"));	
		
		System.setIn(System.in);
		
		in = new ByteArrayInputStream("2".getBytes());		
		System.setIn(in);
		
		assertFalse(ui.promptOuiNon("Entrez oui ou non :"));
		
		System.setIn(System.in);	
		
		// On n'arrive pas à tester le cas où l'entrée n'est pas 1 ou 2...
	}
}
