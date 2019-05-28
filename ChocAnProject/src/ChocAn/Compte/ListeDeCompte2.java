/*
 * TP-3 IFT 2255 GÉNIE LOGICIEL UNIVERSITÉ DE MONTRÉAL ÉTÉ-2017
 * Auteurs  : 
 * Jonathan Larose 			20066082
 * Augusto dos Santos Latgé	20083794
 * Aliona Cupcea 			20057887
 * Dennis Orozco Martinez	20031060
 * 
 */
package ChocAn.Compte;

public class ListeDeCompte2 {
	
	protected static int initialLength = 16;
	protected Compte [] liste;
	protected int nombreCompte;
	
	public ListeDeCompte2(){
		
		liste = new Compte[initialLength];
		nombreCompte = 0;
	}
	
	public Compte[] getListe(){
		return this.liste;
	}
	
	public int getnombreCompte(){
		return this.nombreCompte;
	}
	
	/**
	 * Méthode qui double la taille de la liste.
	 * Appelé au besoin si le tableau est plein.
	 */
	private void doublerListe(){
		
		Compte [] newListe = new Compte[this.liste.length*2];
		
		System.arraycopy(this.liste, 0, newListe, 0, this.liste.length);
		
		this.liste = newListe;
		
		return;
	}
	
	/**
	 * Méthode qui réduit la taille de la liste de moitié.
	 * Appelé au besoin si le tableau est peu rempli.
	 */
	private void reduireListe(){
		
		Compte [] newListe = new Compte[this.liste.length/2];
		
		System.arraycopy(this.liste, 0, newListe, 0, this.liste.length);
		
		this.liste = newListe;
		
		return;
	}
	
	/**
	 * Méthode qui trouve et retourne la position dans la lsite du Compte associé au numéro passé en paramètre.
	 * Retourne -1 si aucun Compte correspondant n'est trouvé.
	 * Méthode utilisé à l'interne par d'autre méthode de la classe.
	 * @param num Le numéro du Compte à trouver.
	 * @return La position du Compte dans la liste.
	 */
	private int trouverComptePosition(int num){
		
		for(int i=0; i<nombreCompte; i++){
			
			if (liste[i].getNumero() == num)
				return i;
		}
		
		return -1;
	}
	
	/**
	 * Méthode qui trouve et retourne le compte associé au numéro passé en paramètre.
	 * Retourne null sinon.
	 * @param num Le numéro du Compte à trouver.
	 * @return Le Compte trouvé.
	 * @throws Exception : s'il n'existe pas de membre associé au numéro rentré
	 */
	public Compte trouverCompte(int num) throws Exception {
			
		int position = trouverComptePosition(num);
		
		if (position < 0)
			throw new Exception("Il n'existe pas de compte associé à ce numéro.\n");
		
		return liste[position];
	}
	
	/**
	 * Méthode qui ajoute un Compte à la liste.
	 * S'assure également que le tableau n'est pas trop plein et l'aggrandi au besoin.
	 * @param Compte Le Compte à ajouter à la liste.
	 */
	public void ajouterCompte(Compte c) {
		
		if(liste.length == nombreCompte)
			doublerListe();

		liste[nombreCompte] = c;
		
		nombreCompte++;
		
		return;
	}
		

	/**
	 * Méthode qui supprime un Compte à la liste.
	 * @param num Le numero de Compt à supprimer.
	 * @throws Exception : s'il n'existe pas de compte à supprimer associé au numéro rentré
	 */
	
	public void supprimerCompte(int num) throws Exception{
		
		int position = trouverComptePosition(num);
		
		if (position < 0){
			throw new Exception("Compte introuvable.\n");
		}
			
		this.nombreCompte--;
		
		liste[position] = liste[nombreCompte];
		liste[nombreCompte] = null;
		
		if (nombreCompte < liste.length*0.3 && liste.length > 16)
			reduireListe();
		
		return;
	}
}