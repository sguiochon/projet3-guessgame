package sam.guessgame.model;

public class Symbol {

    int column;
    String symbol;

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
