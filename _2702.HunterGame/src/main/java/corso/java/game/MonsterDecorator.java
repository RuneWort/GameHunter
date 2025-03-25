package corso.java.game;

import corso.java.entities.Monster;
import lombok.Setter;

public class MonsterDecorator extends Monster implements Pawn {
    
	private Monster monster;
	
	@Setter
	private int row;
	@Setter
	private int column;
	
	public MonsterDecorator(Monster handler) {
		super(handler.getLifeLevel(), handler.getAttack(), handler.getSymbol());
		this.monster = handler;
	}
	
	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getColumn() {
		return column;
	}
}