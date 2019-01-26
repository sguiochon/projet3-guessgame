package sam.guessgame.model;

import java.util.*;

public class Candidat {

    private final static Random random = new Random(System.currentTimeMillis());;

    public List<List<String>> candidatSequence;
    List<String> potentialIncorrectPairs;

    public Candidat(int sequenceSize, String[] possibleValues){
        candidatSequence = new ArrayList<List<String>>();
        for (int i=0; i<sequenceSize; i++){
            candidatSequence.add(new ArrayList<String>(Arrays.asList(possibleValues)));
        }
    }

    public Candidat(List<String>... lists){
        candidatSequence = new ArrayList<List<String>>();
        for (List<String> list : lists){
           candidatSequence.add(list);
        }
    }

    public Candidat(String[]... arrays){
        candidatSequence = new ArrayList<List<String>>();
        for (String[] array : arrays){
            candidatSequence.add(new ArrayList(Arrays.asList(array)));
        }
    }

    public Candidat duplicate(){
        Candidat mirror = new Candidat(0, null);
        for (List<String> items : this.candidatSequence){
            mirror.candidatSequence.add(new ArrayList(items));
        }
        return mirror;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        boolean isStartPosition1 = true;
        for (List<String> symbols : candidatSequence){
            if (!isStartPosition1)
                str.append("-");
            else
                isStartPosition1 = false;
            str.append("(");
            boolean isStartPosition2 = true;
            for (String symbol : symbols){
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
     * @param validSequence: sequence made of correct symbols (whether correctly positionned ot not) that must persist in the Candidate instance. Other symbols must be removed as being not valid.
     */
    public void validSymbols(Sequence validSequence){
        String validSymbols = validSequence.toString();
        for (List<String> symbols : candidatSequence){
            String[] tempoSymbols = symbols.toArray(new String[0]);
            for (int i=0; i<tempoSymbols.length; i++){
                String symbol = tempoSymbols[i];
                if (!validSymbols.contains(symbol)){
                    symbols.remove(symbol);
                }
            }
        }
    }

    /**
     * Reduces the number of candidat symbols based on the known symbol at one specified position.
     * @param symbol
     */
    public void foundSymbol(Symbol symbol) {
        int currentColumn = 0;
        for (List<String> symbols : candidatSequence){
            if (currentColumn==symbol.column){
                // On ne garde que le symbol dans la liste des possibilités
                candidatSequence.set(currentColumn, new ArrayList<>(Arrays.asList(symbol.symbol)));
            }
            else{
                // On enlève le symbol de la liste car il n'est pas dans cette colonne:
                symbols.remove(symbol.symbol);
            }
            currentColumn++;
        }
        System.out.println("<<< Certitude pour " + symbol.symbol);
    }

    public void invalidSymbol(Symbol symbol, boolean recursive) {
        for (List<String> symbols : candidatSequence){
            symbols.remove(symbol.symbol);
        }
        System.out.println(">>> Elimination de  " + symbol.symbol);
        if (recursive) {
            if (potentialIncorrectPairs != null) {
                for (String pair : potentialIncorrectPairs) {
                    if (pair.contains(symbol.symbol)) {
                        //potentialIncorrectPairs.remove(pair);
                        invalidSymbol(new Symbol(0, pair.replace(symbol.symbol, "")), false);
                    }
                }
            }
        }
    }

    public void invalidSymbol(Symbol symbol){
        invalidSymbol(symbol, true);
    }

    /**
     * Returns true if the candidat is the solution, i.e. not uncertainty remains.
     * @return true or false
     */
    public boolean isSolution(){
        for (List<String> symbols: candidatSequence){
            if (symbols.size()!=1)
                return false;
        }
        return true;
    }

    public Sequence generateRandomSequence(){
        Sequence sequence = new Sequence();
        Candidat workCandidat = this.duplicate();
        int currentColumn = 0;
        for (List<String> possiblesValues : workCandidat.candidatSequence){
            int r = random.nextInt(possiblesValues.size());
            String selectedSymbol = possiblesValues.get(r);
            sequence.addSymbol(selectedSymbol);
            for (int j=currentColumn+1; j<workCandidat.candidatSequence.size(); j++){
                workCandidat.candidatSequence.get(j).remove(selectedSymbol);
            }
        }
        return sequence;
    }


    public void storePotentialIncorrectPairs(Symbol oldSymbol, Symbol newSymbol) {
        if (potentialIncorrectPairs==null)
            potentialIncorrectPairs = new ArrayList<>();
        potentialIncorrectPairs.add(oldSymbol.symbol+newSymbol.symbol);
        System.out.println(">>>*** Peut etre tous les deux incorrects :" + oldSymbol.symbol + " et " + newSymbol.symbol);
    }

    public int setCorrectSymbolsInSequence(Sequence temporarySequence) {
        System.out.println(">>>> " + temporarySequence.toString());
        int nbAdjustement = 0;
        int positionNb = 0;
        for(List<String> symbols : candidatSequence){
            if (symbols.size()==1) {
                if (!temporarySequence.getSymbolAt(positionNb).equals(symbols.get(0))){
                    temporarySequence.setSymbolAt(positionNb, symbols.get(0));
                    nbAdjustement++;
                }
            }
            //else{
            //    temporarySequence.setSymbolAt(positionNb, "");
            //}
            positionNb++;
        }
        return nbAdjustement;
    }
}
