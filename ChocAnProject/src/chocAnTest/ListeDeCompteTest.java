package chocAnTest;
import static org.junit.Assert.*;
import org.junit.Test;
import ChocAn.Compte.ListeDeCompte2;
import ChocAn.Compte.Fournisseur.*;
import ChocAn.Compte.Membre.*;
/**
 * Test des méthodes ajouter membre de la liste de membres
 * Test éfféctué par Dennis Orozco Martinez 20031060
 */
public class ListeDeCompteTest {

	@Test
	public void testAjoutCompteMembre() {
		
		ListeDeCompte2 l = new ListeDeCompte2();	//Création d"une liste de comptes
		assertTrue(l.getnombreCompte() == 0); 	// aucun élément dans la liste 
		
		// Création d"un nouveau compte de membre
		Membre2 a = new Membre2(null, null, null, null, null, null);
		
		l.ajouterCompte(a);						//Ajout du compte du membre dans la liste de comptes
		assertEquals(1,l.getnombreCompte());	//un élément dans laliste de comptes
		
	}
	
	@Test
	public void testAjoutCompteFournisseur(){
		
		ListeDeCompte2 l = new ListeDeCompte2();
		//création d'un compte de fournisseur
		Fournisseur2 b = new Fournisseur2(null, null, null, null, null, null);
		
		l.ajouterCompte(b);							//Ajout d'un compte de fournisseur dans la liste de comptes
		assertEquals(1,l.getnombreCompte());		//un élément dans la liste de comptes
	}
	
	@Test
	public void testDoublageListeDeCompte2(){
		
		ListeDeCompte2 l = new ListeDeCompte2();
		for (int i = 0; i < 17; i++) {			//Création-ajout de plusieurs membres pour tester le doublage du tableau 
			Membre2 i1 = new Membre2(null,null,null,null,null,null);
			l.ajouterCompte(i1);
		}
		assertEquals(17,l.getnombreCompte());	//état de lal iste après l'ajout des comptes de membre
												//vérifier si la taille à été doublée

	}
}
