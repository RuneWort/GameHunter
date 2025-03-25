package corso.java.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import corso.java.entities.DarkKnight;
import corso.java.entities.DirtySlime;
import corso.java.entities.GameActor;
import corso.java.entities.GameGrid;
import corso.java.entities.Item;
import corso.java.entities.Potion;
import corso.java.entities.Sword;

public class GameMasterV1 implements GameMaster {

	private boolean lose; // Flag per indicare se il giocatore ha perso
	private boolean won; // Flag per indicare se il giocatore ha vinto
	private boolean isFairyActive = true; // Flag per indicare se la fata è attiva
	private final Random rnd = new Random(); // Generatore di numeri casuali
	private final GameGrid gameGrid; // Griglia di gioco
	private final HunterPawn hunter = new HunterPawn(20, 2); // Istanza del cacciatore
	private final FairyPawn fairy = new FairyPawn(30, 0); // Istanza della fata
	private final List<MonsterDecorator> monsters = new ArrayList<MonsterDecorator>(); // Lista dei mostri
	private final List<ItemDecorator> potions = new ArrayList<ItemDecorator>(); // Lista delle pozioni
	private final List<ItemDecorator> swords = new ArrayList<ItemDecorator>(); // Lista delle swords

	public GameMasterV1() {
		gameGrid = new GameGrid(20, 15);

		// Aggiunge 5 DirtySlime alla lista dei mostri
		for (int i = 0; i < 5; i++) {
			monsters.add(new MonsterDecorator(new DirtySlime()));
		}
		spawnMonsters(); // Posiziona i mostri sulla griglia

		// Aggiunge 3 Potion alla lista delle pozioni
		for (int i = 0; i < 3; i++) {
			potions.add(new ItemDecorator(new Potion()));
		}
		spawnPotion(); // Posiziona le pozioni sulla griglia

		// Aggiunge 2 Sword alla lista delle swords
		for (int i = 0; i < 2; i++) {
			swords.add(new ItemDecorator(new Sword()));
		}
		spawnSword(); // Posiziona i mostri sulla griglia

		spawnFairy(); // Posiziona la fata sulla griglia

		// Aggiunge un DarkKnight alla lista dei mostri e lo posiziona
		monsters.add(new MonsterDecorator(new DarkKnight(20, 5)));
		spawnMonsters();

		// Posiziona il cacciatore al centro della griglia
		int row = gameGrid.getHeight() / 2;
		int column = gameGrid.getWidth() / 2;
		placeHunter(row, column);
	}

	// Metodi per gestire il posizionamento delle entities sulla griglia

	// Metodo per posizionare la pozion sulla griglia
	private void spawnPotion() {
		for (var potion : potions) {
			var x = rnd.nextInt(gameGrid.getWidth());
			var y = rnd.nextInt(gameGrid.getHeight());
			placePotion(potion, y, x);
		}
	}

	// Metodo per posizionare la sword sulla griglia
	private void spawnSword() {
		for (var sword : swords) {
			var x = rnd.nextInt(gameGrid.getWidth());
			var y = rnd.nextInt(gameGrid.getHeight());
			placeSword(sword, y, x);
		}
	}

	// Metodo per posizionare la fata sulla griglia
	private void spawnFairy() {
		var x = rnd.nextInt(gameGrid.getWidth());
		var y = rnd.nextInt(gameGrid.getHeight());
		placeFairy(y, x);
	}

	// Metodo per posizionare i mostri sulla griglia
	private void spawnMonsters() {
		for (var monster : monsters) {
			var x = rnd.nextInt(gameGrid.getWidth());
			var y = rnd.nextInt(gameGrid.getHeight());
			placeMonster(monster, y, x);
		}
	}

	// Metodo per posizionare la Potion in una specifica cella della griglia
	private void placePotion(ItemDecorator potion, int row, int col) {
		if (row >= 0 && row < gameGrid.getHeight() && col >= 0 && col < gameGrid.getWidth()) {
			gameGrid.getCells()[potion.getRow()][potion.getColumn()] = null;
			potion.setRow(row);
			potion.setColumn(col);
			gameGrid.getCells()[row][col] = potion;
		}
	}

	// Metodo per posizionare la Sword in una specifica cella della griglia
	private void placeSword(ItemDecorator sword, int row, int col) {
		if (row >= 0 && row < gameGrid.getHeight() && col >= 0 && col < gameGrid.getWidth()) {
			gameGrid.getCells()[sword.getRow()][sword.getColumn()] = null;
			sword.setRow(row);
			sword.setColumn(col);
			gameGrid.getCells()[row][col] = sword;
		}
	}

	// Metodo per posizionare la fata in una specifica cella della griglia
	private void placeFairy(int row, int col) {
		if (isFairyActive) {
			if (fairy.getRow() >= 0 && fairy.getColumn() >= 0) {
				gameGrid.getCells()[fairy.getRow()][fairy.getColumn()] = null;
			}
			fairy.setRow(row);
			fairy.setColumn(col);
			if (row >= 0 && col >= 0) {
				gameGrid.getCells()[row][col] = fairy;
			}
		}
	}

	// Metodo per posizionare un mostro in una specifica cella della griglia
	private void placeMonster(MonsterDecorator monster, int row, int col) {
		if (row >= 0 && row < gameGrid.getHeight() && col >= 0 && col < gameGrid.getWidth()) {
			gameGrid.getCells()[monster.getRow()][monster.getColumn()] = null;
			monster.setRow(row);
			monster.setColumn(col);
			gameGrid.getCells()[row][col] = monster;
		}
	}

	// Metodo per posizionare il cacciatore in una specifica cella della griglia
	private void placeHunter(int row, int col) {
		if (row >= 0 && row < gameGrid.getHeight() && col >= 0 && col < gameGrid.getWidth()) {
			if (hunter.getRow() >= 0 && hunter.getColumn() >= 0) {
				gameGrid.getCells()[hunter.getRow()][hunter.getColumn()] = null;
			}
			hunter.setRow(row);
			hunter.setColumn(col);
			gameGrid.getCells()[row][col] = hunter;
		}
	}

	// Metodo per muovere la fata in modo casuale
	@Override
	public void fairyMove() {
		var dx = rnd.nextInt(3) - 1;
		var dy = rnd.nextInt(3) - 1;

		var px = fairy.getColumn();
		var py = fairy.getRow();

		var x = px + dx;
		var y = py + dy;

		// Limita il movimento della fata ai bordi della griglia
		x = Math.max(0, Math.min(x, gameGrid.getWidth() - 1));
		y = Math.max(0, Math.min(y, gameGrid.getHeight() - 1));

		// Posiziona la fata nella nuova posizione
		placeFairy(y, x);
	}

	// Metodo per muovere i mostri in modo casuale
	@Override
	public void monstersMove() {
		for (var monster : monsters) {
			var dx = rnd.nextInt(3) - 1;
			var dy = rnd.nextInt(3) - 1;

			var px = monster.getColumn();
			var py = monster.getRow();

			var x = px + dx;
			var y = py + dy;

			// Limita il movimento ai bordi della griglia
			x = Math.max(0, Math.min(x, gameGrid.getWidth() - 1));
			y = Math.max(0, Math.min(y, gameGrid.getHeight() - 1));

			// Posiziona il mostro nella nuova posizione
			placeMonster(monster, y, x);
		}
	}

	// Metodo per muovere il cacciatore in una direzione specifica
	@Override
	public void hunterMove(char direction) {
		var dx = 0;
		var dy = 0;
		if (direction == 'a')
			dx = -1;
		if (direction == 'd')
			dx = 1;
		if (direction == 'w')
			dy = -1;
		if (direction == 's')
			dy = 1;

		var px = hunter.getColumn();
		var py = hunter.getRow();

		var x = px + dx;
		var y = py + dy;

		// Limita il movimento ai bordi della griglia
		x = Math.max(0, Math.min(x, gameGrid.getWidth() - 1));
		y = Math.max(0, Math.min(y, gameGrid.getHeight() - 1));

		placeHunter(y, x); // Posiziona il cacciatore nella nuova posizione
	}

	// Metodo per verificare se il cacciatore ha vinto
	@Override
	public boolean hunterWon() {
		return won;
	}

	// Metodo per verificare se il cacciatore ha perso
	@Override
	public boolean hunterLose() {
		return lose;
	}

	// Metodo per ottenere la griglia di gioco
	@Override
	public GameGrid grid() {
		return this.gameGrid;
	}

	@Override
	public void evaluateStatus() {
		// Posizioni e stati iniziali
		int hunterX = hunter.getColumn();
		int hunterY = hunter.getRow();
		int hunterLife = hunter.getLifeLevel();
		int hunterAttack = hunter.getAttack();

		// Ottieni la posizione della fata
		int fairyX = fairy.getColumn();
		int fairyY = fairy.getRow();
		int fairyLife = fairy.getLifeLevel();

		 // 1. Gestione collisione cacciatore-oggetti
	    GameActor cellContent = gameGrid.getCells()[hunterY][hunterX];
	    if (cellContent instanceof ItemDecorator) {
	        ItemDecorator itemDecorator = (ItemDecorator) cellContent;
	        Item originalItem = itemDecorator.getOriginalItem();

	        if (originalItem instanceof Potion) {
	            Potion potion = (Potion) originalItem;
	            hunter.setLifeLevel(hunter.getLifeLevel() + potion.getBuffLife());
	            System.out.println("Potion raccolta! +" + potion.getBuffLife() + " vita");
	            gameGrid.getCells()[hunterY][hunterX] = null;
	            potions.removeIf(p -> p.getRow() == hunterY && p.getColumn() == hunterX);
	        } 
	        else if (originalItem instanceof Sword) {
	            Sword sword = (Sword) originalItem;
	            hunter.setAttack(hunter.getAttack() + sword.getBuffAttack());
	            System.out.println("Spada raccolta! +" + sword.getBuffAttack() + " attacco");
	            gameGrid.getCells()[hunterY][hunterX] = null;
	            swords.removeIf(s -> s.getRow() == hunterY && s.getColumn() == hunterX);
	        }
	    }

	    // 2. Gestione mostri che distruggono potion
	    for (MonsterDecorator monster : monsters) {
	        GameActor monsterCell = gameGrid.getCells()[monster.getRow()][monster.getColumn()];
	        if (monsterCell instanceof ItemDecorator) {
	            ItemDecorator itemDecorator = (ItemDecorator) monsterCell;
	            if (itemDecorator.getOriginalItem() instanceof Potion) {
	                System.out.println("Un mostro ha distrutto una pozione!");
	                gameGrid.getCells()[monster.getRow()][monster.getColumn()] = null;
	                potions.removeIf(p -> p.getRow() == monster.getRow() && p.getColumn() == monster.getColumn());
	            }
	        }
	    }

		// 3. Gestione collisione tra cacciatore e fata
		if (hunterX == fairyX && hunterY == fairyY && isFairyActive) {
			// Il cacciatore guadagna il buff di vita dalla fata
			hunter.setLifeLevel(hunterLife + fairy.getBuffLife());
			System.out.println("Il cacciatore ha guadagnato " + fairy.getBuffLife() + " punti vita dalla fata!");

			// Incrementa il contatore del buff
			fairy.incrementBuffCount();

			// Se il buff è stato applicato 3 volte, rimuovi la fata dal gioco
			if (fairy.shouldBeRemoved()) {
				gameGrid.getCells()[fairyY][fairyX] = null;
				isFairyActive = false;
				System.out.println("La fata ha esaurito i suoi buff ed è scomparsa!");
			}
		}

		// 4. Gestione collisione tra fata e mostri
		if (isFairyActive) {
			for (var monster : monsters) {
				int monsterX = monster.getColumn();
				int monsterY = monster.getRow();

				// Se un mostro è sulla stessa cella della fata
				if (monsterX == fairyX && monsterY == fairyY) {
					// La fata perde vita in base all'attacco del mostro
					fairyLife -= monster.getAttack();
					fairy.setLifeLevel(fairyLife);

					// Se la fata muore, rimuovila dal gioco
					if (fairyLife <= 0) {
						gameGrid.getCells()[fairyY][fairyX] = null;
						isFairyActive = false;
						System.out.println("La fata è stata sconfitta da un mostro!");
					}
				}
			}
		}

		// 5. Gestione combattimento cacciatore-mostri
		List<MonsterDecorator> deadMonsters = new ArrayList<>();
		for (MonsterDecorator monster : monsters) {
			if (monster.getRow() == hunterY && monster.getColumn() == hunterX) {
				// Il cacciatore perde vita in base all'attacco del mostro
				hunterLife -= monster.getAttack();
				hunter.setLifeLevel(hunterLife);

				// Il mostro perde vita in base all'attacco del cacciatore
				int monsterLife = monster.getLifeLevel();
				monsterLife -= hunterAttack;
				monster.setLifeLevel(monsterLife);

				// Se il mostro muore, aggiungilo alla lista dei mostri da rimuovere
				if (monsterLife <= 0) {
					deadMonsters.add(monster);
					// Il cacciatore guadagna la vita del mostro sconfitto
					hunter.setLifeLevel(hunter.getLifeLevel() + monster.getLifeLevel());
				}
			}
		}

		// Controllo vittoria/sconfitta
		if (hunter.getLifeLevel() <= 0)
			lose = true;
		if (monsters.isEmpty())
			won = true;

		// Aggiorna la posizione del cacciatore
		placeHunter(hunter.getRow(), hunter.getColumn());
	}

	// Metodo per ottenere lo stato del gioco
	@Override
	public GameStatus getStatus() {
		return new GameStatus(fairy.getLifeLevel(), hunter.getLifeLevel(), monsters.size());
	}
}