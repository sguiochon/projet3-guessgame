package sam.guessgame.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Cette classe représente les différentes combinaisos possibles à un instant donné:
 * A chaque position de la combinaison, correspond une liste de symbols possibles (car non éliminés).
 * Si cette liste contient un seul symbol, c'est qu'il est établi avec certitude que ce symbol est à la colonne
 * correspondante dans la combinaison à deviner. La combinaison est connue avec certitude si cette classe définit un seul
 * symbol à chaque colonne.
 */
public class Candidat {

    private final static Logger LOGGER = LoggerFactory.getLogger(Candidat.class.getName());

    private final static Random random = new Random(System.currentTimeMillis());

    public List<List<String>> candidatSequence;
    List<String> potentialIncorrectPairs;

    public Candidat(int sequenceSize, String[] possibleValues) {
        candidatSequence = new ArrayList<List<String>>();
        for (int i = 0; i < sequenceSize; i++) {
            candidatSequence.add(new ArrayList<String>(Arrays.asList(possibleValues)));
        }
        LOGGER.debug("Candidat instance created");
    }

    public Candidat duplicate() {
        Candidat mirror = new Candidat(0, null);
        for (List<String> items : this.candidatSequence) {
            mirror.candidatSequence.add(new ArrayList(items));
        }
        return mirror;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        boolean isStartPosition1 = true;
        for (List<String> symbols : candidatSequence) {
            if (!isStartPosition1)
                str.append("-");
            else
                isStartPosition1 = false;
            str.append("(");
            boolean isStartPosition2 = true;
            for (String symbol : symbols) {
                if (!isStartPosition2)
                    str.append(",");
                else
                    isStartPosition2 = false;
                str.append(symbol);
            }
            str.append(")");
        }
        return str.toString();
    }

    /**
     * Reduces the number of candidat symbols based on the exhaustive list of valid symbol.
     *
     * @param validSequence: sequence made of correct symbols (whether correctly positionned ot not) that must persist in the Candidate instance. Other symbols must be removed as being not valid.
     */
    public void validSymbols(Sequence validSequence) {
        String validSymbols = validSequence.toString();
        for (List<String> symbols : candidatSequence) {
            String[] tempoSymbols = symbols.toArray(new String[0]);
            for (int i = 0; i < tempoSymbols.length; i++) {
                String symbol = tempoSymbols[i];
                if (!validSymbols.contains(symbol)) {
                    symbols.remove(symbol);
                }
            }
        }
    }

    /**
     * Reduces the number of candidat symbols based on the known symbol at one specified position.
     *
     * @param symbol
     */
    public void foundSymbol(Symbol symbol) {
        int currentColumn = 0;
        for (List<String> symbols : candidatSequence) {
            if (currentColumn == symbol.getColumn()) {
                // On ne garde que le symbol dans la liste des possibilités
                candidatSequence.set(currentColumn, new ArrayList<>(Arrays.asList(symbol.getSymbol())));
            } else {
                // On enlève le symbol de la liste car il n'est pas dans cette colonne:
                symbols.remove(symbol.getSymbol());
            }
            currentColumn++;
        }
        LOGGER.debug("<<< Certitude pour " + symbol.getSymbol() + " ce qui réduit le possibilités à: " + toString());
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

    public void invalidSymbolAt(int position, Symbol symbol) {
        candidatSequence.get(position).remove(symbol.getSymbol());
        if (candidatSequence.get(position).size() == 1) {
            foundSymbol(new Symbol(position, candidatSequence.get(position).get(0)));
        }
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
     * Génère une séquence aléatoire basée sur la liste des possibilités définies dans le Candidat.
     * @param onlyUniqueSymbol true si la séquence ne doit pas contenir de doublons
     * @return la séquence générée
     */
    public Sequence generateRandomSequence(boolean onlyUniqueSymbol) {
        LOGGER.debug("Démarrage d'un tirage aléatoire sur la base des possibilités suivantes: " + this.toString());
        Sequence sequence = new Sequence(candidatSequence.size());
        Candidat workCandidat = this.duplicate();

        // On va traiter les colonnes par nombre de possibilités croissant:
        List<NumberOfSymbols> list = new ArrayList<>();
        int nbCol = 0;
        for (List<String> symbols : candidatSequence) {
            list.add(new NumberOfSymbols(symbols.size(), nbCol));
            nbCol++;
        }
        list.sort(Comparator.comparingInt(NumberOfSymbols::getNbOfSymbols));

        for (NumberOfSymbols n : list) {
            LOGGER.debug("Tirage aléatoire à la position " + n.columnNb + " qui contient " + n.nbOfSymbols + " symbols possibles");
            List<String> possiblesValues = workCandidat.candidatSequence.get(n.columnNb);
            int r = random.nextInt(possiblesValues.size());
            String selectedSymbol = possiblesValues.get(r);
            sequence.setSymbolAt(n.columnNb, selectedSymbol);
            if (onlyUniqueSymbol) {
                for (int j = 0; j < workCandidat.candidatSequence.size(); j++) {
                    workCandidat.candidatSequence.get(j).remove(selectedSymbol);
                }
            }
        }
        return sequence;
    }

    /**
     * Mémorise le couple de symbols passés en paramètres comme étant SOIT présents dans la combinaison secrète, SOIT absents de cette
     * combinaison. La liste ainsi constituée sera utilisée lorsqu'il est établi avec certitude qu'un symbol n'est pas dans la séquence: on
     * pourra alors effacer son pair, ce qui optimise la recherche.
     *
     * @param symbol1
     * @param symbol2
     */
    public void storePotentialIncorrectPairs(Symbol symbol1, Symbol symbol2) {
        if (potentialIncorrectPairs == null)
            potentialIncorrectPairs = new ArrayList<>();
        potentialIncorrectPairs.add(symbol1.getSymbol() + symbol2.getSymbol());
        LOGGER.debug(">>>*** Peut etre tous les deux incorrects :" + symbol1.getSymbol() + " et " + symbol2.getSymbol());
    }

    /**
     * Permet de modifier une séquence en y positionnant un symbol dont la position est déterminée avec certitude.
     * En retour est donné le nombre d'affectations effectuées. En effet, cela revient à modifier la sequence EN PLUS
     * des modifications de la sequence attendues par la stratégie de changement de symbol, ce qui modifierait l'interprétation
     * de l'évolution des résultats entre deux séquences.
     *
     * @param sequence
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
     * Elimine les symbols qui ne sont pas dans une séquence.
     *
     * @param sequence
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

    /**
     * Génère une séquence aléatoirement qui n'a pas déjà été proposée dans la session
     *
     * @param onlyUniqueSymbols
     * @param session
     * @return
     */
    public Sequence generateRandomSequence(boolean onlyUniqueSymbols, Session<MastermindResult> session) {
        boolean isNewSequence = true;
        int nbTrials = 0;
        Sequence sequence;
        do {
            sequence = generateRandomSequence(onlyUniqueSymbols);
            nbTrials++;
            for (Round round : session.getRounds()) {
                if (round.getAttempt().toString().equals(sequence.toString())) {
                    isNewSequence = false;
                    LOGGER.debug("la sequence " + sequence.toString() + " est déjà présente dans la session.");
                    break;
                }
            }
        }
        while (!isNewSequence && nbTrials < 20);
        return sequence;
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
     * Classe interne représentant le nombre de symbols possibles à un numéro de colonne donné.
     * Cette classe est utilisée lors de la génération aléatoire d'une séquence.
     */
    private class NumberOfSymbols {
        int nbOfSymbols;
        int columnNb;

        NumberOfSymbols(int nbOfSymbols, int columnNb) {
            this.nbOfSymbols = nbOfSymbols;
            this.columnNb = columnNb;
        }

        Integer getNbOfSymbols() {
            return nbOfSymbols;
        }
    }
}
