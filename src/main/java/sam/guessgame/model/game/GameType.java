package sam.guessgame.model.game;

import java.util.EnumSet;

/**
 * Liste les types de jeu disponibles
 */
public enum GameType {
    PlusMoins(1, "Plus/Moins"),
    MasterMind(2, "Mastermind");

    private static EnumSet<GameType> gameTypes = EnumSet.of(PlusMoins, MasterMind);

    private final int internalValue;
    private final String description;

    GameType(int value, String description) {
        this.internalValue = value;
        this.description = description;
    }

    public static GameType getByInternalValue(int value) {
        for (GameType gameType : gameTypes){
            if (gameType.internalValue==value)
                return gameType;
        }
        return null;
    }

    public static String getDescription() {
        String description = "";
        for (GameType gameType : gameTypes) {
            description = description + gameType.toString();
        }
        return description;
    }

    public static int[] getInternalValues() {
        int[] internalValues = new int[gameTypes.size()];
        int i = 0;
        for (GameType gameType : gameTypes) {
            internalValues[i] = gameType.internalValue;
            i++;
        }
        return internalValues;
    }

    @Override
    public String toString() {
        return internalValue + " : " + description + "\n";
    }

}
