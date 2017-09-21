package fr.jee;

public abstract class Animal {

	private int taille;	
	private int poids;	
	private String paysOrigine;
	
	
	/**
	 * @param taille
	 * @param poids
	 * @param paysOrigine
	 */
	public Animal(int taille, int poids, String paysOrigine) {
		super();
		this.taille = taille;
		this.poids = poids;
		this.paysOrigine = paysOrigine;
	}
	
	public abstract void manger();
	
	public abstract void dormir();
	
	public int getTaille() {
		return taille;
	}
	public void setTaille(int taille) {
		this.taille = taille;
	}
	public int getPoids() {
		return poids;
	}
	public void setPoids(int poids) {
		this.poids = poids;
	}
	public String getPaysOrigine() {
		return paysOrigine;
	}
	public void setPaysOrigine(String paysOrigine) {
		this.paysOrigine = paysOrigine;
	}

	@Override
	public String toString() {
		return "Animal [taille=" + taille + ", poids=" + poids + ", paysOrigine=" + paysOrigine + "]";
	}
		
}
