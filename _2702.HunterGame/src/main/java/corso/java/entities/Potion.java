package corso.java.entities;

public class Potion extends Item {
   
	private final int buffLife = 5; // Buff di vita che la pozione fornisce
   
	public Potion() {
        super(0, 0, '$'); 
    }

	public int getBuffLife() {
		return buffLife;
	}
}