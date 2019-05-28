/*
 * TP-3 IFT 2255 GÉNIE LOGICIEL UNIVERSITÉ DE MONTRÉAL ÉTÉ-2017
 * Auteurs  : 
 * Jonathan Larose 			20066082
 * Augusto dos Santos Latgé	20083794
 * Aliona Cupcea 			20057887
 * Dennis Orozco Martinez	20031060
 * 
 */
package ChocAn.Service;

public class RepertoireDesFournisseurs {
	
	Service[] listeServices = new Service[16];
	private static int elemListe = 0; 		// Compteur statique du nombre de services créé au total. Utilisé pour générer les code de Membre.
	
	public static int getElemListe() {
		return elemListe;
	}
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public int trouverServicePosition(int code){		
		
		for(int i=0; i<elemListe; i++){
			
			if(listeServices[i].getCode() == code){
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Méthode pour retrouver un service dans la liste de services
	 * @param num : code du service recherché
	 * @return le service voulu ou excéption s'il n'existe pas ou si la liste de services est vide
	 * @throws Excèption  : si il n'y a pas de services disponibles
	 */
	public Service trouverService(int codeService) throws Exception {
		
		int position = trouverServicePosition(codeService);

		if(position<0)
			throw new Exception("Le service recherché ne se trouve pas dans la base de données");

		return listeServices[position];
	}

	/**
	 * Méthode pour retrouver le nom d'un service
	 * @param code : code du service dont on veut savoir le nom
	 * @return le nom du service correspondant au code ou excéption si la liste de services
	 * 		   est vide.
	 * @throws Exception : pas de services disponibles
	 */
	public String getNom(int code) throws Exception {
		
		if (elemListe == 0) 
			throw new Exception("Il n'y a pas de services disponibles, veuillez en rajouter");
			
		for (int i=0; i<listeServices.length; i++)
			
			if (listeServices[i].getCode() == code)
				return listeServices[i].getNom();
		
		return "Le service voulu n'exite pas";
	}

	/**
	 * Méthode pour savoir les frais d'un service
	 * @param code : code du service dont on veut savoir les frais
	 * @return les frais associés à ce service ou exception si la liste est vide ou le service n'existe pas
	 * @throws Exception : il n'y a pas de services disponibles
	 */
	public double getFrais(int codeService) throws Exception {
		
		if (elemListe == 0) 
			throw new Exception("Il n'y a pas de services disponibles, veuillez en rajouter");
			
		
		for (int i=0; i<listeServices.length; i++)
			
			if (listeServices[i].getCode() == codeService)
				return listeServices[i].getFrais();

		throw new Exception("Il n'y a pas de service associe a ce code");
	}

	/**
	 * Méthode pour ajouter un nouveau service à la liste de services
	 * @param nom : nom du service qu'on veut ajouter
	 * @param code : code du service qu'on veut ajouter
	 * @param frais : frais du service qu'on veut ajouter
	 */
	public void ajouterService(Service service){

		if (listeServices.length == elemListe-1)
			doubleListe();		
		
		listeServices[elemListe] = service;
		elemListe++;
		
		return;
	}
	
	/**
	 * Méthode pour trier la liste de services, fait appel à la methode quickSort
	 * @return listeServices alphabétiquement ordonnée
	 * @throws Exception : s'il n'y a pas de liste de services
	 */
	public Service[] trierListeAlpha() throws Exception {
		
		if (elemListe == 0) 
			throw new Exception("Il n'existe pas de liste de services");
		
		if(elemListe == 1)
			return listeServices;
		else
			quickSort(0, elemListe-1);
		
		return listeServices;
	}
	
	/**
	 * Methode pour trier alfabétiquement la listeServices. 
	 * @param petit : indice initial pour éfféctuer le tri
	 * @param grand : indice maximal pour éfféctuer le tri
	 */
	public void quickSort(int petit, int grand) {
		
		int i = petit;
		int j = grand;
		String pivot = listeServices[i + (j - i) / 2].getNom();
		
		while (i <= j) {
			while (listeServices[i].getNom().compareToIgnoreCase(pivot) < 0) {
				i++;
			}
			while (listeServices[j].getNom().compareToIgnoreCase(pivot) > 0) {
				j--;
			}
			if (i <= j) {
				change(i,j);
				i++;
				j--;
			}
		}
		if (petit < j) {
            quickSort(petit, j);
        }
        if (i < grand) {
            quickSort(i, grand);
        }
	}
	
	/**
	 * méthode auxiliare pour éfféctuer le tri de quickSort
	 * @param i : indice de listeServices à échanger
	 * @param j : indice de listeServices à échanger
	 */
	public void change(int i, int j) {
		
		Service temp = listeServices[i];
		listeServices[i] = listeServices[j];
		listeServices[j]=temp;
	}


	/**
	 * Méthode pour supprimer un service
	 * @param code : code du service qu'on veut supprimer
	 * @throws Exception  : si la liste est vide
	 */
	public void supprimerService(int codeService) throws Exception {
		
		int position = trouverServicePosition(codeService);
		
		if(position<0)
			throw new Exception("Le service que vous voulez supprimer n'existe pas");
			
		elemListe--;
		
		listeServices[position] = listeServices[elemListe];
		listeServices[elemListe] = null;

		if (listeServices.length*0.3 > elemListe && listeServices.length > 16)
			reduireTable();
		
		return;
	}
	
	/**
	 * Méthode pour doubler la taille de la listeServices car elle etait déjà remplie
	 */
	public void doubleListe(){
		
		Service[] listeTemp = new Service[this.listeServices.length*2];
		System.arraycopy(this.listeServices, 0, listeTemp, 0, listeServices.length);
		this.listeServices = listeTemp;
	}
	 
	/**
	 * Méthode pour réduire de moitie la taille de listeServices car elle etait remplie de moins de 30%
	 */
	public void reduireTable() {
		
		Service[] listeTemp = new Service[this.listeServices.length/2];
		System.arraycopy(this.listeServices, 0, listeTemp, 0, listeServices.length);
		this.listeServices = listeTemp;
	}
}