package corso.java.entities;

import lombok.Getter;

public class Fairy extends GameActor {

    @Getter
    private final int buffLife = 5; // Buff di vita che la fata fornisce
    private int buffCount = 0; // Contatore per il numero di volte che il buff è stato applicato

    public Fairy(int lifeLevel, int attack) {
        super(lifeLevel, attack, '%');
    }

    // Metodo per incrementare il contatore del buff
    public void incrementBuffCount() {
        buffCount++;
    }

    // Metodo per verificare se la fata deve essere rimossa
    public boolean shouldBeRemoved() {
        return buffCount >= 3;
    }
}