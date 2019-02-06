package sam.guessgame.model;

/**
 * Représentation d'un symbol dans une séquence.
 */
public class Symbol {

    private int column;
    private String symbol;

    public Symbol(int column, String symbol){
        this.column = column;
        this.symbol = symbol;
    }

    public int getColumn(){
        return column;
    }

    public String getSymbol(){
        return symbol;
    }
}
