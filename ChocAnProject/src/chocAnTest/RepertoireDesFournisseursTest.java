/**
 * @author Jonathan Larose
 */

package chocAnTest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import ChocAn.Service.*;

public class RepertoireDesFournisseursTest {
	
	private RepertoireDesFournisseurs2 rf = new RepertoireDesFournisseurs2();
	

	@Before
	public void clearListe(){
		rf = new RepertoireDesFournisseurs2();
	}

	@Test(expected = Exception.class)
	public void testTrierListeAlphaListeVide() throws Exception {
		
		//TrierAlphaListe : Liste Vide
		Service2[] ls = rf.trierListeAlpha();
	}
	
	@Test
	public void testTrierAlphaUnElement() throws Exception{
		
		try{
			Service2 s1 = new Service2("Aquaforme", 1);
			
			rf.ajouterService(s1);
			
			Service2[] ls = rf.trierListeAlpha();
			
			Service2[] lt = new Service2[ls.length];
			
			lt[0] = s1;
			
			assertArrayEquals(lt, ls);
		}
		catch(Exception e){
			
		}
	}
	
	@Test
	public void testTrierListeAlphaNormal() throws Exception {
		
		try{
			Service2 s1 = new Service2("Aquaforme", 1);
			Service2 s2 = new Service2("Baignade", 1);
			Service2 s3 = new Service2("Cardio", 1);
			Service2 s4 = new Service2("Course", 1);
			Service2 s5 = new Service2("Escalade", 1);
			Service2 s6 = new Service2("Zumba", 1);
			
			//Liste non trié
			rf.ajouterService(s3);
			rf.ajouterService(s2);
			rf.ajouterService(s4);
			rf.ajouterService(s6);
			rf.ajouterService(s5);
			rf.ajouterService(s1);
			
			Service2[] ls = rf.trierListeAlpha();
			
			Service2[] lt = new Service2[ls.length];
			
			lt[0] = s1;
			lt[1] = s2;
			lt[2] = s3;
			lt[3] = s4;
			lt[4] = s5;
			lt[5] = s6;
			
			assertArrayEquals(lt, ls);
		}
		catch(Exception e){		
		}
	}
	
	
	@Test
	public void testTrierListeAlphaElementsPareils() throws Exception {
		
		try{
			Service2 s1a = new Service2("Aquaforme", 1);
			Service2 s2a = new Service2("Aquaforme", 1);
			Service2 s3a = new Service2("Aquaforme", 1);
			Service2 s4a = new Service2("Course", 1);
			Service2 s5a = new Service2("Escalade", 1);
			Service2 s6a = new Service2("Zumba", 1);
			
			//Liste non trié
			rf.ajouterService(s3a);
			rf.ajouterService(s2a);
			rf.ajouterService(s4a);
			rf.ajouterService(s6a);
			rf.ajouterService(s5a);
			rf.ajouterService(s1a);
			
			Service2[] lss = rf.trierListeAlpha();
			
			Service2[] ltt = new Service2[lss.length];
			
			ltt[0] = s1a;
			ltt[1] = s2a;
			ltt[2] = s3a;
			ltt[3] = s4a;
			ltt[4] = s5a;
			ltt[5] = s6a;
			
			assertArrayEquals(ltt, lss);
		}
		catch(Exception e){
			
		}
	}
	
	@Test
	public void testTrierListeAlphaDejaTrier() throws Exception {
		
		try{
			Service2 s1 = new Service2("Aquaforme", 1);
			Service2 s2 = new Service2("Baignade", 1);
			Service2 s3 = new Service2("Cardio", 1);
			Service2 s4 = new Service2("Course", 1);
			Service2 s5 = new Service2("Escalade", 1);
			Service2 s6 = new Service2("Zumba", 1);
			
			//Liste non trié
			rf.ajouterService(s1);
			rf.ajouterService(s2);
			rf.ajouterService(s3);
			rf.ajouterService(s4);
			rf.ajouterService(s5);
			rf.ajouterService(s6);
			
			Service2[] ls = rf.trierListeAlpha();
			
			Service2[] lt = new Service2[ls.length];
			
			lt[0] = s1;
			lt[1] = s2;
			lt[2] = s3;
			lt[3] = s4;
			lt[4] = s5;
			lt[5] = s6;
			
			assertArrayEquals(lt, ls);
		}
		catch(Exception e){		
		}
	}
	
	@Test
	public void testTrierListeAlphaLongueListe() throws Exception {
		
		try{
			int length = 350000000;
			
			Service2[] ls = new Service2[length];
			
			for(int i=length-1; i>=0; i--){
				
				Service2 s = new Service2("service"+i, 100.);
				
				ls[i] = s;
				rf.ajouterService(s);
			}
			
			Service2 [] lt = rf.trierListeAlpha();
			
			assertArrayEquals(lt, ls);
		}
		catch(Exception e){		
		}
	}
}
