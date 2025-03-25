package corso.java.game;

import corso.java.entities.Item;
import lombok.Setter;

public class ItemDecorator extends Item implements Pawn {

	@Setter
	private int row;
	@Setter
	private int column;
	private final Item originalItem;

	public ItemDecorator(Item handler) {
		super(handler.getLifeLevel(), handler.getAttack(), handler.getSymbol());
		this.originalItem = handler;
	}
	
	public Item getOriginalItem() {
        return originalItem;
    }

	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getColumn() {
		return column;
	}

	@Override
	public char getSymbol() {
		return super.getSymbol();
	}
}