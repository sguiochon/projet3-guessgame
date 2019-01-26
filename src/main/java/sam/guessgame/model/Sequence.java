package sam.guessgame.model;

import java.util.*;

/**
 * A sequence is a list of symbols. One symbol can only be used once, multiple occurrences is not
 * allowed...
 * A sequence may represent the sequence to guess or a attempt to guess.
 */
public class Sequence {

    private List<String> symbols;

    public Sequence(){
        symbols = new ArrayList();
    }

    public Sequence(int length){
        symbols = new ArrayList<String>(Arrays.asList(new String[length]));
    }

    public Sequence(String... s) {
        symbols = new ArrayList<String>(Arrays.asList(s));
    }



    public List<String> getSymbols(){
        return symbols;
    }

    public void addSymbol(String symbol){
        symbols.add(symbol);
    }

    public Sequence duplicate(){
        return new Sequence(this.toString().replace(" ","").split(""));
    }

    public void setSymbolAt(int position, String symbol){
        symbols.set(position, symbol);
    }

    @Override
    public String toString(){
        StringBuilder stb = new StringBuilder();
        boolean isFirstSymbol = true;
        for (String s : symbols){
            if (!isFirstSymbol){
                stb.append(" ");
            }
            else{
                isFirstSymbol = false;
            }
            stb.append(s);
        }
        return stb.toString();
    }

    public String getSymbolAt(int position) {
        return symbols.get(position);
    }
}
