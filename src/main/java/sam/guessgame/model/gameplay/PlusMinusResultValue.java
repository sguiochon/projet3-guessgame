package sam.guessgame.model.gameplay;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Représentation du résultat de la comparaison entre un chiffre d'une combinaison (typiquement, la combinaison secrète)
 * et une autre combinaison (typiquement, un essai)
 */
public enum PlusMinusResultValue {
    Moins("-"),Egal("="),Plus("+"), Inconnu("?");

    private static List<PlusMinusResultValue> resultValues = new ArrayList<PlusMinusResultValue>(EnumSet.of(Moins, Egal, Plus));

    public static String[] getSymbols(){
        String[] symbols = new String[resultValues.size()];
        int i = 0;
        for (PlusMinusResultValue resultValue : resultValues){
            symbols[i] = resultValue.symbol;
            i++;
        }
        return symbols;
    }

    public static PlusMinusResultValue getBySymbol(String symbol){
        for (PlusMinusResultValue value : resultValues){
            if (value.symbol.equals(symbol))
                return value;
        }
        return null;
    }

    private String symbol;

    PlusMinusResultValue(String symbol){
        this.symbol = symbol;
    }

    public String toString(){
        return symbol;
    }
}
