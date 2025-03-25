package corso.java;

import java.io.IOException;

import corso.java.entities.GameActor;
import corso.java.entities.GameGrid;
import corso.java.game.GameMasterV1;

public class Program {

	static void printGrid(GameGrid grid) {
		var cells = grid.getCells();
		System.out.print(' ');
		for (var col = 0; col < grid.getWidth(); ++col) {
			System.out.print(col % 10); // Stampa l'indice della colonna
		}
		System.out.println();
		for (var row = 0; row < grid.getHeight(); ++row) {
			System.out.print(row % 10); // Stampa l'indice della riga
			for (var col = 0; col < grid.getWidth(); ++col) {
				GameActor entity = cells[row][col];
				if (entity == null) {
					System.out.print('.'); // Cella vuota
				} else {
					System.out.print(entity.getSymbol()); // Simbolo dell'entità
				}
			}
			System.out.println();
		}
	}

	static void gameLoop() {
		var master = new GameMasterV1();
		while (!master.hunterLose() && !master.hunterWon()) {
			System.out.println(master.getStatus()); // Stampa lo stato del gioco
			printGrid(master.grid()); // Stampa la griglia

			char d = inputHunter(); // Input del giocatore
			master.hunterMove(d); // Muovi il cacciatore
			master.evaluateStatus(); // Valuta collisioni e stato del gioco

			master.fairyMove(); // Muovi la fata
			master.evaluateStatus(); // Valuta collisioni e stato del gioco

			master.monstersMove(); // Muovi i mostri
			master.evaluateStatus(); // Valuta collisioni e stato del gioco
			
		}

		// Stampa il risultato del gioco
		printGameOverMessage(master);
	}

	private static void printGameOverMessage(GameMasterV1 master) {
		if (master.hunterLose()) {
			System.out.println("Hai perso! Il cacciatore ha perso tutta la sua vita.");
		} else if (master.hunterWon()) {
			System.out.println("Hai vinto! Tutti i mostri sono stati sconfitti.");
		} else {
			System.out.println("Il gioco continua...");
		}
	}

	private static char inputHunter() {
		System.out.println("Move (wasd): ");
		try {
			// Leggi il primo carattere
			var d = System.in.read();

			// Pulisci il buffer di input (leggi tutti i caratteri rimanenti fino a '\n')
			while (System.in.available() > 0) {
				System.in.read();
			}

			// Converti il carattere in minuscolo e verifica se è valido
			char direction = Character.toLowerCase((char) d);
			if (direction == 'w' || direction == 'a' || direction == 's' || direction == 'd') {
				return direction;
			} else {
				System.out.println("Input non valido. Usa 'w', 'a', 's' o 'd'.");
				return inputHunter(); // Richiedi nuovamente l'input
			}
		} catch (IOException e) {
			System.out.println("Errore di input. Riprova.");
			return inputHunter(); // Richiedi nuovamente l'input
		}
	}

	public static void main(String[] args) {
		var master = new GameMasterV1();
		printGrid(master.grid());

		System.out.println("Giocchiamo!!!");
		gameLoop();
	}
}