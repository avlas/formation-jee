package fr.jee.animals;

import fr.jee.Swimming;

public class Duck extends Bird implements Swimming {
	
	public Duck(int taille, int poids, String paysOrigine) {
		super(taille, poids, paysOrigine, "DUCK", false);
	}

	@Override
	public void swim() {
		System.out.println("DUCK swims");	
	}
	
	@Override
	public String toString() {
		return "Duck [" + super.toString() ;
	}
}
