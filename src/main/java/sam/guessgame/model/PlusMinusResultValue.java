package sam.guessgame.model;

import sam.guessgame.GameMode;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

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
