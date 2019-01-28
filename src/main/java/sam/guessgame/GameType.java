package sam.guessgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public enum GameType {
    PlusMoins(1, "jeu dans lequel il faut trouver les chiffres de la combinaison"),
    MasterMind(2, "jeu de mastermind traditionnel");

    private static List<GameType> gameTypes = new ArrayList<GameType>(EnumSet.of(PlusMoins, MasterMind));

    private final int internalValue;
    private final String description;

    GameType(int value, String description){
        this.internalValue = value;
        this.description = description;
    }

    public int getInternalValue(){
        return internalValue;
    }

    public String toString(){
        return internalValue + " (" + description + ")\n";
    }

    static GameType getByInternalValue(int value){
        return gameTypes.get(value-1);
    }

    static String getDescription(){
        String description = "";
        for (GameType gameType : gameTypes){
            description = description + gameType.toString();
        }
        return description;
    }

    static int[] getInternalValues(){
        int[] internalValues = new int[gameTypes.size()];
        int i = 0;
        for (GameType gameType : gameTypes){
            internalValues[i] = gameType.internalValue;
            i++;
        }
        return internalValues;
    }

}
