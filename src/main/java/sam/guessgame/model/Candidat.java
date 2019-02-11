package sam.guessgame.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.guessgame.exception.AlgorithmException;

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

    private int sequenceSize;
    private String[] possibleValues;

    protected Candidat(int sequenceSize, String[] possibleValues) {
        this.sequenceSize = sequenceSize;
        this.possibleValues = possibleValues;
        init();
        LOGGER.debug("Candidat instance created");
    }

    public Candidat(Candidat fromCandidat) {
        candidatSequence = new ArrayList<List<String>>();
        for (List<String> symbols : fromCandidat.candidatSequence) {
            candidatSequence.add(new ArrayList<String>(symbols));
        }
    }

    public void init(){
        candidatSequence = new ArrayList<List<String>>();
        for (int i = 0; i < sequenceSize; i++) {
            candidatSequence.add(new ArrayList<String>(Arrays.asList(possibleValues)));
        }
    }

    private Candidat duplicate() {
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
    /*public void validSymbols(Sequence validSequence) {
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
    */

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

    /**
     * Réduit la liste des symbols d'une colonne donnée en enlevant un des symbols qu'elle contient
     *
     * @param position numéro de la colonne (commence à 0)
     * @param symbol   le symbol à enlever de la liste des possibilités
     */
    public void invalidSymbolAt(int position, Symbol symbol) {
        candidatSequence.get(position).remove(symbol.getSymbol());
        if (candidatSequence.get(position).size() == 1) {
            foundSymbol(new Symbol(position, candidatSequence.get(position).get(0)));
        }
    }

    /**
     * Génère une séquence aléatoire basée sur la liste des possibilités définies dans le Candidat.
     *
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

            if (possiblesValues.size() == 0)
                throw new AlgorithmException("Impossible de trouver une nouvelle sequence. L'algorithme est déficient, désolé... ou vous avez triché...");

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
