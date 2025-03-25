package corso.java.entities;

import lombok.Getter;

public class GameGrid {
	
	@Getter
	private int width;
	@Getter
	private int height;
	@Getter
	private final GameActor [][] cells;
	
	public GameGrid(int width, int height) {
		this.width = width;
		this.height = height;
		
		cells = new GameActor[height][];
		for(int row=0; row < height; ++row) {
			cells[row] = new GameActor[width];
		}
	}
}