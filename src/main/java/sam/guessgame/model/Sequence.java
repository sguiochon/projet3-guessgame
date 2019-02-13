package sam.guessgame.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Représentation d'une combinaison conforme aux paramètres d'un jeu (nombre de symbols et valeurs possibles).
 * Dans une partie, cette classe sera utilisée pour représenter à la fois la combinaison secrète à trouver et également
 * les différentes combinaisons proposées par le joueur devant la deviner.
 */
public class Sequence {

    private List<String> symbols;

    public Sequence() {
        symbols = new ArrayList();
    }

    public Sequence(List<String> symbols){
        this.symbols = new ArrayList();
        for (String symbol : symbols){
            this.symbols.add(symbol);
        }
    }

    public Sequence(int length) {
        symbols = new ArrayList<String>(Arrays.asList(new String[length]));
    }

    public Sequence(String... s) {
        symbols = new ArrayList<String>(Arrays.asList(s));
    }

    public List<String> getSymbols() {
        return symbols;
    }

    /**
     * Ajoute un symbol à la suite des autres dans la séquence.
     * @param symbol le symbol à ajouter
     */
    public void addSymbol(String symbol) {
        symbols.add(symbol);
    }

    public Sequence duplicate() {
        return new Sequence(this.toString().replace(" ", "").split(""));
    }

    /**
     * Affecte la valeur d'un symbo à la position indiquée (sans prendre en compte la position du {@link Symbol})
     * @param position
     * @param symbol
     */
    public void setSymbolAt(int position, String symbol) {
        symbols.set(position, symbol);
    }

    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        boolean isFirstSymbol = true;
        for (String s : symbols) {
            if (!isFirstSymbol) {
                stb.append(" ");
            } else {
                isFirstSymbol = false;
            }
            stb.append(s);
        }
        return stb.toString();
    }

    /**
     * Renvoie le symbol situé à la position indiquée
     * @param position la position
     * @return la valeur du symbol présent à la position
     */
    public String getSymbolAt(int position) {
        return symbols.get(position);
    }

    /**
     * Enlève un symbol à une position donnée, raccourcissant ainsi la longueur de la séquence.
     * @param position position à laquelle on élimine le symbol
     */
    public void removeSymbolAt(int position){
        symbols.remove(position);
    }
}
