package chocAnTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ChocAn.Service.*;
import ChocAn.Service.RepertoireDesFournisseurs2;


/**
 * Test des méthodes pour retrouver un Service dans le Répertoire des Fournisseurs
 * Test éfféctué par Augusto dos Santos Latgé 20083794
 */
public class RepertoireDesFournisseursTest2 {

	private RepertoireDesFournisseurs2 rf;
	private Service2 sv;
	
	@Before 
	public void creerRepertoire() {
		rf = new RepertoireDesFournisseurs2();
		sv = new Service2("massage", 100);
		rf.ajouterService(sv);
	}
	
	/**
	 * Tester si on retrouve le service sur le Répertoire des Fournisseurs
	 * Et s'il n'est pas null
	 * @throws Exception : pas d'exception attendue
	 */
	@Test
	public void testTrouverService() throws Exception {		
		
		// Service trouvé
		assertEquals(sv, rf.trouverService(sv.getCode()));
		
		// Service n'est pas null
		assertNotEquals(null, rf.trouverService(sv.getCode()));
	}
	
	/**
	 * Tester si la méthode lance une exception si on passe un code erroné
	 * @throws Exception : pas d'exception attendue
	 */
	@Test (expected = Exception.class)
	public void testTrouverServiceWithException() throws Exception {
		
		rf.trouverService(000000);		
		
	}

}
