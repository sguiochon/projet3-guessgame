package sam.guessgame.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.guessgame.exception.AlgorithmException;
import sam.guessgame.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class FindSymbolsAlgorithm implements VisitorAlgorithm<MastermindResult> {

    private final static Logger LOGGER = LoggerFactory.getLogger(FindSymbolsAlgorithm.class.getName());

    private final static Random random = new Random(System.currentTimeMillis());
    private final Candidat candidat;

    // données utilisées lors de la stratégie de changement d'un symbol à la fois:
    private Symbol oldSymbol;
    private Symbol newSymbol;
    int nbSuccessiveChanges = 0;
    private int adjustement = 0;

    // données utilisées lors de la stratégie de permutation de deux symbols entre deux essais:
    private Symbol symbolA;
    private Symbol symbolB;
    int nbSuccessivePermutations = 0;


    public FindSymbolsAlgorithm(Candidat candidat){
        this.candidat = candidat;
    }

    @Override
    public Sequence visit(Session<MastermindResult> session){

        boolean hasFoundAllSymbols = false;

        Round<MastermindResult> lastRound = session.rounds.get(session.rounds.size()-1);
        if (lastRound.getResult().getNbCorrectPosition() + lastRound.getResult().getNbCorrectSymbol()==candidat.candidatSequence.size())
            hasFoundAllSymbols = true;

        Sequence attempt = null;
        if (hasFoundAllSymbols){
            attempt = evalResultThenPermuteTwoSymbols(session);
        }
        else{
            attempt = evalResultThenChangeOneSymbol(session);
        }
        return attempt;

    }

    public Sequence evalResultThenPermuteTwoSymbols(Session<MastermindResult> session){

        nbSuccessivePermutations++;

        if (nbSuccessivePermutations>=2){
            // On compare le résultat des deux derniers essais pour en déduire la pertinence de la permutation:
            Round<MastermindResult> lastRound = session.rounds.get(session.rounds.size()-1);
            Round<MastermindResult> previousRound = session.rounds.get(session.rounds.size()-2);

            if (lastRound.getResult().getNbCorrectPosition()==0){
                //TODO: implémenter une stratégie basée sur la recherche d'autres sequences proposées ayant obtenu un résultat nbCorrectPosition >1
                // Si un symbol est présent à la même position pour les deux sequences, c'est une mauvaise place nécessairement.
            }

            int deltaNbCorrectPosition = lastRound.getResult().getNbCorrectPosition() - previousRound.getResult().getNbCorrectPosition();
            System.out.println("deltaBPosition: " + deltaNbCorrectPosition);

            if (deltaNbCorrectPosition==2){
                // les deux symbols sont maintenant à la bonne position
                Symbol symbolAAfterPermut = new Symbol(symbolB.getColumn(), symbolA.getSymbol());
                Symbol symbolBAfterPermu = new Symbol(symbolA.getColumn(), symbolB.getSymbol());
                candidat.foundSymbol(symbolAAfterPermut);
                candidat.foundSymbol(symbolBAfterPermu);

                System.out.println("@@@ @@@ @@@ >>>>> Apres permut is OK - Bon emplacement trouvé pour " + symbolAAfterPermut.getSymbol() + " (col: " + symbolAAfterPermut.getColumn()+ ") et " + symbolBAfterPermu.getSymbol() +" col:" + symbolBAfterPermu.getColumn() +")");
            }
            else if(deltaNbCorrectPosition==-2){
                // les deux symbols étaient à la bonne place avant la permutation:
                candidat.foundSymbol(symbolA);
                candidat.foundSymbol(symbolB);
                System.out.println("@@@ @@@ @@@ >>>>> Avant permut is OK - Bon emplacement trouvé pour " + symbolA.getSymbol() + " (col: " + symbolA.getColumn()+ ") et " + symbolB.getSymbol() +" col:" + symbolB.getColumn() +")");
            }
            else if(deltaNbCorrectPosition==0){
                System.out.println(">>>>> Les deux symbols sont à la mauvaise place: " + symbolA.getSymbol() + " (col:" + symbolA.getColumn() + "," + symbolB.getColumn() + ") et " + symbolB.getSymbol());
                // Les deux symbols étaient à la mauvaise place avant et apres la permutation:
                candidat.invalidSymbolAt(symbolA.getColumn(), symbolA);
                candidat.invalidSymbolAt(symbolA.getColumn(), symbolB);
                candidat.invalidSymbolAt(symbolB.getColumn(), symbolA);
                candidat.invalidSymbolAt(symbolB.getColumn(), symbolB);
                System.out.println("Nouveau candidat après réductions: " + candidat.toString());
            }

        }
        // Suite à l'affinage de Candidat, si la solution est trouvée (bon symbol identifié à chaque position), on la propose:
        if (candidat.isSolution()){
            return candidat.generateRandomSequence(true);
        }
        Sequence latestAttempt = session.rounds.get(session.rounds.size()-1).getAttempt();
        return permuteTwoSymbols(latestAttempt, session);
    }

    private Sequence permuteTwoSymbols(final Sequence sequence, final Session<MastermindResult> session){
        // On identifie toutes les positions dans Candidat où il y a plusieurs symbols possibles (donc, dont la position n'est pas trouvée)
        int positionA;
        int positionB;
        boolean isAlreadySubmitted;
        Sequence newSequence;

        int position = 0;
        List<Integer> positions = new ArrayList<Integer>();
        for (List<String> symbols : candidat.candidatSequence){
            if (symbols.size()>1)
                positions.add(position);
            position++;
        }

        do {
            positionA=-1;
            positionB=-1;
            isAlreadySubmitted = false;
            newSequence = sequence.duplicate();
            candidat.setCorrectSymbolsInSequence(newSequence);

            if (!candidat.isCompliantWith(newSequence)){
                System.out.println("@#@#@#@#@#@##@@##@@#####@@@#######@@# Je remets la sequence dans le droit chemin.....");
                nbSuccessivePermutations = 0;
                return candidat.generateRandomSequence(true, session);
            }

            // S'il y a plus de 2 emplacements, on choisit aléatoirement
            int nbA=0;
            int nbB=1;
            if (positions.size()>2){
                System.out.println("je dois choisir aléatoirement entre " + positions.size() + " emplacements à permuter");
                nbA = random.nextInt(positions.size());
                do{
                    nbB = random.nextInt(positions.size());
                }while (nbA==nbB);
            }
            positionA = positions.get(nbA);
            positionB = positions.get(nbB);

            if (positionA==-1 || positionB==-1)
                throw new AlgorithmException("Impossible de trouver une nouvelle sequence. L'algorithme est déficient, désolé... ou vous avez triché...");

            System.out.println("Permutation des positions " + positionA + " et " + positionB);
            symbolA = new Symbol(positionB, newSequence.getSymbolAt(positionB));
            symbolB = new Symbol(positionA, newSequence.getSymbolAt(positionA));

            newSequence.setSymbolAt(positionA, symbolA.getSymbol());
            newSequence.setSymbolAt(positionB, symbolB.getSymbol());

            // Chercher si cette sequence a deja ete utilisée durant la session.
            for (Round<MastermindResult> round : session.rounds) {
                if (round.getAttempt().toString().contains(newSequence.toString())) {
                    System.out.println("Cette sequence a deja ete soumise: " + newSequence.toString());
                    isAlreadySubmitted = true;
                }
            }
            System.out.println(candidat.toString() + " >>>>>>>>> " + newSequence.toString());

        }
        while(isAlreadySubmitted);

        return newSequence;

    }




    public Sequence evalResultThenChangeOneSymbol(Session<MastermindResult> session){

        nbSuccessiveChanges++;

        // Analyse de l'écart entre les résultats des deux derniers essais:
        if (nbSuccessiveChanges >=2){
            // On compare le résultat des deux derniers essais pour en déduire la pertinence du dernier changement de symbol:

            Round<MastermindResult> lastRound = session.rounds.get(session.rounds.size()-1);
            Round<MastermindResult> previousRound = session.rounds.get(session.rounds.size()-2);

            int deltaNbCorrectPosition = lastRound.getResult().getNbCorrectPosition() - previousRound.getResult().getNbCorrectPosition();
            int deltaNbCorrectSymbol = lastRound.getResult().getNbCorrectSymbol() - previousRound.getResult().getNbCorrectSymbol();

            System.out.println("adjustement: " + adjustement + ", deltaBPosition: " + deltaNbCorrectPosition + ", deltaBCouleur: " + deltaNbCorrectSymbol);

            deltaNbCorrectPosition=deltaNbCorrectPosition-adjustement;
            if (deltaNbCorrectPosition==1){
                if (deltaNbCorrectSymbol==-1){
                    // Ancien Symbol est correct Symbol
                    // Nouveau symbol est bien placé
                    candidat.foundSymbol(newSymbol);
                }
                else if (deltaNbCorrectSymbol==0){// Ajout de la condition! sinon, mise en défaut en cas d'ajustement = 1
                    // Ancien Symbol est incorrect
                    candidat.invalidSymbol(oldSymbol);
                    // Nouveau symbol est bien placé
                    candidat.foundSymbol(newSymbol);
                }

            }
            else if (deltaNbCorrectPosition==-1){
                if (deltaNbCorrectSymbol==1){
                    // Ancien Symbol est bien placé
                    candidat.foundSymbol(oldSymbol);
                    // Nouveau symbol est correct symbol
                }
                else{
                    // Ancien Symbol est bien placé
                    candidat.foundSymbol(oldSymbol);
                    // Nouveau symbol est incorrect
                    if (adjustement==0) {
                        candidat.invalidSymbol(newSymbol);
                    }
                    else{
                        // On ne peut pas être certain...
                    }
                }
            }
            else{
                if (deltaNbCorrectSymbol==1){
                    // Ancien Symbol est incorrect
                    candidat.invalidSymbol(oldSymbol);
                    // Nouveau symbol est correct symbol

                }
                else if (deltaNbCorrectSymbol==-1){
                    System.out.println("Ancien symbol ("+oldSymbol.getSymbol()+") est correct, Nouveau symbol ("+newSymbol.getSymbol()+") est incorrect");
                    // Ancien Symbol est correct symbol
                    // Nouveau symbol est incorrect
                    if (adjustement==0)
                        candidat.invalidSymbol(newSymbol);
                    else {
                        // On ne peut pas etre certain....
                    }
                }
                else{
                    // Soit les deux symbols sont corrects soit les deux sont incorrects. On garde l'information
                    // pour l'utiliser lorsqu'on a trouvé un symbol incorrect. Car alors, on eliminera l'autre, faisant d'une pierre deux coups...
                    if (adjustement==0)
                        candidat.storePotentialIncorrectPairs(oldSymbol, newSymbol);
                }
            }

        }
        // Suite à l'affinage de Candidat, si la solution est trouvée (bon symbol identifié à chaque position), on la propose:
        if (candidat.isSolution()){
            return candidat.generateRandomSequence(true);
        }
        // Sinon, on propose maintenant une nouvelle sequence en changeant 1 seul symbol :
        return changeOneSymbol(session.rounds.get(session.rounds.size()-1).getAttempt(), session);
    }

    public Sequence changeOneSymbol(final Sequence sequence, final Session<MastermindResult> session){

        boolean hasFoundAllSymbols = false;

        int currentColumn = 0;
        boolean isPermuted = false;

        String stringSequence = sequence.toString();
        System.out.println(stringSequence);
        Sequence temporarySequence = sequence.duplicate();
        adjustement = candidat.setCorrectSymbolsInSequence(temporarySequence);

        // Si la transformation a forcé un symbol, on s'assure qu'il n'est pas présent aux autres positions. Si c'est le cas, on fait une permutation:
        isPermuted = candidat.hasDuplicatedSymbols(temporarySequence);

        System.out.println("Sequence initiale: "+stringSequence + ". est transformée en " + temporarySequence.toString());

        for (List<String> symbols : candidat.candidatSequence){
            if (symbols.size()>1){
                if (!isPermuted) {
                    System.out.println("- Column " + currentColumn + " are several possible symbols: " + symbols);
                    for (String symbol : symbols) {
                        if (isPermuted)
                            break;

                        System.out.println("je tente " + symbol + " en comparant avec " + temporarySequence.toString().replace(" ",""));

                        //if (!temporarySequence.toString().replace(" ","").substring(0, currentColumn).contains(symbol)) {
                        if (!temporarySequence.toString().contains(symbol) || hasFoundAllSymbols) {

                            System.out.println("Ce symbol n'est pas présent dans " + temporarySequence.toString());

                            oldSymbol = new Symbol(currentColumn, sequence.getSymbols().get(currentColumn));
                            newSymbol = new Symbol(currentColumn, symbol);

                            Sequence backup = temporarySequence.duplicate();
                            temporarySequence.getSymbols().set(currentColumn, symbol);

                            if (session != null) { // Si un historique des sequences est fourni à l'appel de la méthode, on s'assure que la nouvelle sequence n'a pas déjà été proposée
                                boolean isAlreadySubmitted = false;
                                for (Round<MastermindResult> round : session.rounds) {
                                    if (round.getAttempt().toString().contains(temporarySequence.toString())) {
                                        System.out.println("Cette sequence a deja ete soumise: " + temporarySequence.toString());
                                        isAlreadySubmitted = true;
                                    }
                                }

                                if (!isAlreadySubmitted) {
                                    System.out.println("Ancien symbol: " + oldSymbol.getSymbol() + " est remplacé par " + newSymbol.getSymbol());
                                    isPermuted=true;
                                    //temporarySequence.getSymbols().set(currentColumn, symbol);
                                    //return Optional.of(temporarySequence);
                                } else {
                                    // On restaure l'état de la sequence avant l'essai infructueux
                                    temporarySequence = backup.duplicate();
                                }
                            } else {
                                System.out.println("Pas besoin de vérifier la presence dans l'historique des coups...");
                                isPermuted=true;
                                //temporarySequence.getSymbols().set(currentColumn, symbol);
                                //return Optional.of(temporarySequence);
                            }
                        } else {
                            System.out.println("Le symbol " + symbol + " est déjà dans la sequence, on l'ignore...");
                        }
                    }
                }else{
                    System.out.println("Pas de permutation necessaire pour la colonne " + currentColumn);

                }
            }
            currentColumn++;
        }
        System.out.println("@@@@@ Permutation réussie? "+ isPermuted);
        if (!isPermuted){
            //System.out.println("&&&&&&&&&&&&&&&&&&&&&&&& &&&&&&&&&&&&&&& &&&&&&&&& &&&&& && & Nouvelle sequence aléatoire") ;
            //temporarySequence = candidat.generateRandomSequence(true, session);
            //nbSuccessiveChanges = 0;
            throw new AlgorithmException("Impossible de trouver une nouvelle sequence. L'algorithme est déficient, désolé... ou vous avez triché...");
        }
        return temporarySequence;
    }

}
