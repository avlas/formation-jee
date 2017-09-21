package fr.jee.animals;

import fr.jee.Animal;
import fr.jee.Swimming;

public class Fish extends Animal implements Swimming {
	
	private String nom;
	private boolean isEauSale;
		
	/**
	 * @param taille
	 * @param poids
	 * @param paysOrigine
	 * @param nom
	 * @param isEauSale
	 */
	public Fish(int taille, int poids, String paysOrigine, String nom, boolean isEauSale) {
		super(taille, poids, paysOrigine);
		this.nom = nom;
		this.isEauSale = isEauSale;
	}

	@Override
	public void swim() {
		System.out.println("FISH swims");	
	}
	
	@Override
	public void manger() {
		System.out.println("FISH mange");		
	}
	
	@Override
	public void dormir() {
		System.out.println("FISH dors");		
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean isEauSale() {
		return isEauSale;
	}

	public void setEauSale(boolean isEauSale) {
		this.isEauSale = isEauSale;
	}

	@Override
	public String toString() {
		return "Fish [ nom=" + nom + ", isEauSale=" + isEauSale + ", " + super.toString() + " ]" ;
	}
	
}
