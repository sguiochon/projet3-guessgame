package sam.guessgame;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum GameMode {
    Challenger (1, "Vous devez trouver la combinaison de l'ordinateur"),
    Defenseur (2, "L'ordinateur doit trouver votre combinaison"),
    Spectateur(3, "Ordinateur contre ordinateur"),
    Duel(4, "Duel homme/ordinateur");

    private static List<GameMode> gameModes = new ArrayList<GameMode>(EnumSet.of(Challenger, Defenseur, Spectateur, Duel));

    private final int internalValue;
    private final String description;

    GameMode(int value, String description){
        this.internalValue = value;
        this.description = description;
    }

    static String getDescriptions(){
        String description = "";
        for (GameMode gameMode : gameModes){
            description = description + gameMode.toString();
        }
        return description;
    }

    public String toString(){
        return internalValue + " (" + description + ")\n";
    }

    static GameMode getByInternalValue(int value){
        return gameModes.get(value-1);
    }

    static int[] getInternalValues(){
        int[] internalValues = new int[gameModes.size()];
        int i = 0;
        for (GameMode gameMode : gameModes){
            internalValues[i] = gameMode.internalValue;
            i++;
        }
        return internalValues;
    }
}
