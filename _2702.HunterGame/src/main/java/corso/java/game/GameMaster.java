package corso.java.game;

import corso.java.entities.GameGrid;

public interface GameMaster {
	
	void evaluateStatus();
     
	void hunterMove(char direction);
	
	void fairyMove();
    
	void monstersMove();
	
	boolean hunterWon();
	
	boolean hunterLose();
	
	GameGrid grid();

	GameStatus getStatus();

}