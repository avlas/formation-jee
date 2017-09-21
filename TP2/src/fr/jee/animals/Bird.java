package fr.jee.animals;

import fr.jee.Animal;
import fr.jee.Flying;

public class Bird extends Animal implements Flying {

	private String nom;
	private boolean isNocturne;
	
	/**
	 * @param nom
	 * @param isNocturne
	 */
	public Bird(int taille, int poids, String paysOrigine, String nom, boolean isNocturne) {
		super(taille, poids, paysOrigine);
		this.nom = nom;
		this.isNocturne = isNocturne;
	}

	@Override
	public void fly() {
		System.out.println("BIRD flies");
	}

	@Override
	public void manger() {
		System.out.println("BIRD mange");
	}

	@Override
	public void dormir() {
		System.out.println("BIRD dors");
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean isNocturne() {
		return isNocturne;
	}

	public void setNocturne(boolean isNocturne) {
		this.isNocturne = isNocturne;
	}

	@Override
	public String toString() {
		return "Bird [nom=" + nom + ", isNocturne=" + isNocturne + "], " + super.toString() + " ]";
	}
	
}
