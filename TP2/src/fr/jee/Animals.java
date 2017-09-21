package fr.jee;

import fr.jee.animals.Bird;
import fr.jee.animals.Duck;
import fr.jee.animals.Fish;

public class Animals {

	public static void main(String[] args) {
	
		Bird bird1 = new Bird(5, 10, "FR", "BIRD_1", true);
		System.out.println(bird1.toString());
		bird1.manger();
		bird1.dormir();
		bird1.fly();
		
		Bird bird2 = new Bird(10, 5, "RO", "BIRD_2", false);
		System.out.println(bird2.toString());
		bird2.manger();
		bird2.dormir();
		bird2.fly();
		
		Duck duck = new Duck(15, 1, "CAN");
		System.out.println(duck.toString());
		duck.manger();
		duck.dormir();
		duck.fly();
		duck.swim();
		
		Fish fish1 = new Fish(5, 10, "FR", "FISH_1", true);
		System.out.println(fish1.toString());
		fish1.manger();
		fish1.dormir();
		fish1.swim();
		
		Fish fish2 = new Fish(15, 10, "RO", "FISH_2", false);
		System.out.println(fish2.toString());
		fish2.manger();
		fish2.dormir();
		fish2.swim();
	}

}
