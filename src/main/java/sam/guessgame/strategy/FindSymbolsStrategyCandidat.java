package sam.guessgame.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.Symbol;

import java.util.ArrayList;
import java.util.List;

/**
 * Version de Candidat enrichie de fonctionnalités utilisées par la stratégie {@link FindSymbolsStrategy}.
 */
public class FindSymbolsStrategyCandidat extends Candidat {

    private final static Logger LOGGER = LoggerFactory.getLogger(FindSymbolsStrategyCandidat.class.getName());

    private List<String> potentialIncorrectPairs;

    public FindSymbolsStrategyCandidat(Candidat candidat){
        super(candidat);
    }

    /**
     * Indique si une Sequence est conforme aux possibilités définies par le Candidat. Cette méthode est utilisée après
     * réduction des possibilités à l'issue des différentes stratégies pour éviter d'appliquer les transformations sur des Sequences
     * dont certains symbols sont manifestement mal placés. Si c'est le cas, on préfère procéder à un nouveau tirage aléatoire, quitte
     * à perdre le bénéfices de l'historique des transformations effectuées.
     *
     * @param sequence dont on veut vérifier la conformité avec le candidat
     * @return true si conforme
     */
    public boolean isCompliantWith(Sequence sequence) {
        int position = 0;
        for (String symbol : sequence.getSymbols()) {
            List<String> possibleSymbols = candidatSequence.get(position);
            boolean isInList = false;
            for (String possibleSymbol : candidatSequence.get(position)) {
                if (possibleSymbol.equals(symbol))
                    isInList = true;
            }
            if (!isInList)
                return false;
            position++;
        }
        return true;
    }

    /**
     * Teste si un symbol est présent plus d'une fois dans une séquence.
     * @param sequence séquence à tester
     * @return true si un symbol apparait plusieurs fois, false si chaque symbol est unique
     */
    public boolean hasDuplicatedSymbols(Sequence sequence) {
        boolean hasDuplicated = false;
        int position = 0;
        for (String symbol : sequence.getSymbols()) {
            // Si le symbol à cette position a été trouvé (1 seul symbol dans candidatsequence), on l'ignore
            if (candidatSequence.get(position).size() != 1) {
                // Ce symbol est-il présent deux foix dans la sequence?
                if (sequence.toString().indexOf(symbol) != sequence.toString().lastIndexOf(symbol)) {
                    LOGGER.debug("Le symbol " + symbol + " est 2 fois dans la sequence. On va le remplacer");
                    hasDuplicated = true;
                }
            }
        }
        return hasDuplicated;
    }

    /**
     * Permet de modifier une séquence en y positionnant un symbol dont la position est déterminée avec certitude.
     * En retour est donné le nombre d'affectations effectuées. En effet, cela revient à modifier la sequence EN PLUS
     * des modifications de la sequence attendues par la stratégie de changement de symbol, ce qui modifierait l'interprétation
     * de l'évolution des résultats entre deux séquences.
     *
     * @param sequence séquence à modifier
     * @return nombre de symbols dont la position a été modifiée dans la séquence afin de se conformer aux positions identifiées dans le Candidat
     */
    public int setCorrectSymbolsInSequence(Sequence sequence) {
        LOGGER.debug("Si un symbol est identifié, on force sa position dans la séquence. Sequence initiale: " + sequence.toString());
        LOGGER.debug("Possibilités: " + this.toString());
        int nbAdjustement = 0;
        int positionNb = 0;
        for (List<String> symbols : candidatSequence) {
            if (symbols.size() == 1) {
                if (!sequence.getSymbolAt(positionNb).equals(symbols.get(0))) {
                    sequence.setSymbolAt(positionNb, symbols.get(0));
                    nbAdjustement++;
                }
            }
            positionNb++;
        }
        return nbAdjustement;
    }

    /**
     * Mémorise le couple de symbols passés en paramètres comme étant SOIT présents dans la combinaison secrète, SOIT absents de cette
     * combinaison. La liste ainsi constituée sera utilisée lorsqu'il est établi avec certitude qu'un symbol n'est pas dans la séquence: on
     * pourra alors effacer son pair, ce qui optimise la recherche.
     *
     * @param symbol1 l'un des symbols
     * @param symbol2 l'autre symbol
     */
    public void storePotentialIncorrectPairs(Symbol symbol1, Symbol symbol2) {
        if (potentialIncorrectPairs == null)
            potentialIncorrectPairs = new ArrayList<>();
        potentialIncorrectPairs.add(symbol1.getSymbol() + symbol2.getSymbol());
        LOGGER.debug(">>>*** Peut etre tous les deux incorrects :" + symbol1.getSymbol() + " et " + symbol2.getSymbol());
    }

    public void invalidSymbol(Symbol symbol, boolean recursive) {
        for (List<String> symbols : candidatSequence) {
            symbols.remove(symbol.getSymbol());
        }
        LOGGER.debug(">>> Elimination de  " + symbol.getSymbol());
        if (recursive) {
            if (potentialIncorrectPairs != null) {
                for (String pair : potentialIncorrectPairs) {
                    LOGGER.debug("Liste de paires de symbols peut-etre incorrects: " + pair);
                    if (pair.contains(symbol.getSymbol())) {
                        invalidSymbol(new Symbol(0, pair.replace(symbol.getSymbol(), "")), false);
                    }
                }
            }
        }
    }

    public void invalidSymbol(Symbol symbol) {
        invalidSymbol(symbol, true);
    }

    /**
     * Returns true if the candidat is the solution, i.e. not uncertainty remains.
     *
     * @return true or false
     */
    public boolean isSolution() {
        removeFalseOptions();
        for (List<String> symbols : candidatSequence) {
            if (symbols.size() != 1)
                return false;
        }
        return true;
    }

    /**
     * Cette méthode permet d'enlever les symbols 'fantômes' qui peuvent être créés lorsqu'on réduit
     */
    private void removeFalseOptions() {
        int position = 0;
        for (List<String> symbols : candidatSequence) {
            if (symbols.size() == 1) {
                foundSymbol(new Symbol(position, symbols.get(0)));
            }
            position++;
        }
    }

    /**
     * Elimine les symbols qui ne sont pas dans une séquence.
     *
     * @param sequence la sequence à modifier
     */
    public void removeNotInSequence(Sequence sequence) {
        LOGGER.debug("Réduction du candidat car tous les symbols sont dans la séquence " + sequence.toString());
        LOGGER.debug("Avant réduction: " + this.toString());
        String validSymbols = sequence.toString();
        for (List<String> symbols : candidatSequence) {
            for (int position = 0; position < symbols.size(); position++) {
                String symbol = symbols.get(position);
                if (!validSymbols.contains(symbol)) {
                    symbols.remove(symbol);
                    position--;
                }
            }
        }
        LOGGER.debug("Après réduction:" + this.toString());
    }

}
