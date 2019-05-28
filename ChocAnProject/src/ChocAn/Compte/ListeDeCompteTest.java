package ChocAn.Compte;
import static org.junit.Assert.*;

import org.junit.Test;

import ChocAn.Compte.Membre.Membre;
/**
 * Test des méthodes inserer-supprimer membre de la liste de membres
 * Test éfféctué par Dennis Orozco Martinez 20031060
 */

public class ListeDeCompteTest {

	@Test
	public void ajouterCompte() {
		ListeDeCompte l = new ListeDeCompte();
		Membre a = new Membre(null, null, null, null, null, null);
		l.ajouterCompte(a);
		assertEquals(1,l.liste.length);
		//assertTrue(l.trouverCompte(a.numero));
	}
	
	
	/*
	 * public class CollectionTests{
	 * 
	 * @Test 
	 * public voidemptyCollection() {
	 * Collection collection= new ArrayList();
	 * assertEquals(0, collection.size());
	 * assertTrue(collection.isEmpty());
	 * 
	 * }
	 * @Test 
	 * public voidaddOneItem() {
	 * Collection collection= new ArrayList();
	 * collection.add("itemA");
	 * assertEquals(1, collection.size());
	 * assertTrue(collection.contains(“itemA”));}}
	 */

}
