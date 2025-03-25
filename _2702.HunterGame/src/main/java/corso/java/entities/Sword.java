package corso.java.entities;

import lombok.Getter;

public class Sword extends Item {
    
	@Getter
	private final int buffAttack = 5; // Buff di attacco che la sword fornisce
	
	public Sword() {
		super(0, 0, '£');
	}
}