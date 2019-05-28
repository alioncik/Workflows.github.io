package ChocAn.Service;
/**
 * 
 * @author aliona
 */
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ListeDeServiceTest {
	private ServiceRendu testListeDeService;
	
	@Before 
	public void creerListeDeService(){
		/**
		 *création d'un compte de liste de service
		 *dateService : date à laquelle le service a été fourni
		 *commemtaire : facultatif sur le service fourni
		 *membre : membre qui a reçu le service
		 * fournisseur : fournisseur qui a offert le service
		 * service : service qui a été offert
		 */
		testListeDeService = new  ServiceRendu(null, null, null, null, null);
		
		}
	/**
	 * Tester la méthode ajouterServiceRendu
	 * 
	 */
	@Test
	public void ajouterServiceRendu() {
	
		testListeDeService.getCommentaire(); //des comantaires dans la liste de service
		String desired = String.format("28-07-2017","ok",900000000, 100000000, 100000);
		assertEquals( desired);
		
		
	}
	
	
	
	

	private void assertEquals(String desired) {
		// TODO Auto-generated method stub
		
	}

}
