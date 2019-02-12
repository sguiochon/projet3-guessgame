package sam.guessgame.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sam.guessgame.exception.AlgorithmException;
import sam.guessgame.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Stratégie mise en oeuvre par un décodeur joué par l'ordinateur afin de trouver la combinaison secrète
 * au jeu du mastermind.
 * La stratégie consiste en deux étapes réalisées successivement (à moins que la solution ne soit trouvée avant...) :
 * 1) la première étape {@link #evalResultThenChangeOneSymbol(Session)} consiste à changer 1 symbol à la fois lors de chaque essai et d'analyser la façon dont le résultat evolue. Cela permet de réduire le
 * nombre de possibilités en éliminant des symbols ou au contraire, en trouvant la position de certains avec certitude;
 * 2) lorsque tous les symbols sont trouvés (mais certains pas bien placés), la deuxième étape {@link #evalResultThenPermuteTwoSymbols(Session)} consiste à réaliser des permutations de deux symbols à chaque essai. Là
 * encore, c'est l'analyse de la façon dont les résultats évoluent qui permet de trouver la bonne position des symbols.
 */
@Component
public class FindSymbolsStrategy implements SessionVisitor<MastermindResult> {

    private final static Logger LOGGER = LoggerFactory.getLogger(FindSymbolsStrategy.class.getName());

    private final static Random random = new Random(System.currentTimeMillis());
    private FindSymbolsStrategyCandidat candidat;

    // données utilisées lors de la stratégie de changement d'un symbol à la fois:
    private Symbol oldSymbol;
    private Symbol newSymbol;
    int nbSuccessiveChanges;
    private int adjustement;

    // données utilisées lors de la stratégie de permutation de deux symbols entre deux essais:
    private Symbol symbolA;
    private Symbol symbolB;
    int nbSuccessivePermutations;



    public void init(Candidat candidat){
        this.candidat = new FindSymbolsStrategyCandidat(candidat);
        nbSuccessivePermutations = 0;
        nbSuccessiveChanges = 0;
        adjustement = 0;
    }

    @Override
    public Sequence visit(Session<MastermindResult> session){

        boolean hasFoundAllSymbols = false;

        Round<MastermindResult> lastRound = session.getRounds().get(session.getRounds().size()-1);
        if (lastRound.getResult().getNbCorrectPosition() + lastRound.getResult().getNbCorrectSymbol()==candidat.candidatSequence.size()) {
            LOGGER.debug("Tous les symbols sont identifiés...");
            //Réduction des possibilités en éliminant  les symbols manifestement incorrects...
            candidat.removeNotInSequence(lastRound.getAttempt());
            LOGGER.debug("Combinaisons possibles : " + candidat.toString());
            hasFoundAllSymbols = true;
        }

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
            Round<MastermindResult> lastRound = session.getRounds().get(session.getRounds().size()-1);
            Round<MastermindResult> previousRound = session.getRounds().get(session.getRounds().size()-2);

            if (lastRound.getResult().getNbCorrectPosition()==0){
                //TODO: implémenter une stratégie basée sur la recherche d'autres sequences proposées ayant obtenu un résultat nbCorrectPosition >1
                // Si un symbol est présent à la même position pour les deux sequences, c'est une mauvaise place nécessairement.
            }

            int deltaNbCorrectPosition = lastRound.getResult().getNbCorrectPosition() - previousRound.getResult().getNbCorrectPosition();
            LOGGER.debug("Variation du nombre de Positions Trouvées: " + deltaNbCorrectPosition);

            if (deltaNbCorrectPosition==2){
                // les deux symbols sont maintenant à la bonne position
                Symbol symbolAAfterPermut = new Symbol(symbolB.getColumn(), symbolA.getSymbol());
                Symbol symbolBAfterPermu = new Symbol(symbolA.getColumn(), symbolB.getSymbol());
                candidat.foundSymbol(symbolAAfterPermut);
                candidat.foundSymbol(symbolBAfterPermu);
                LOGGER.debug("La place APRES permutation (donc celle du tour précédent) est la bonne -> Bon emplacement trouvé pour " + symbolAAfterPermut.getSymbol() + " (col: " + symbolAAfterPermut.getColumn()+ ") et " + symbolBAfterPermu.getSymbol() +" col:" + symbolBAfterPermu.getColumn() +")");
            }
            else if(deltaNbCorrectPosition==-2){
                // les deux symbols étaient à la bonne place avant la permutation:
                candidat.foundSymbol(symbolA);
                candidat.foundSymbol(symbolB);
                LOGGER.debug("La place AVANT permutation (donc celle de l'avant dernier tour) est la bonne -> Bon emplacement trouvé pour " + symbolA.getSymbol() + " (col: " + symbolA.getColumn()+ ") et " + symbolB.getSymbol() +" col:" + symbolB.getColumn() +")");
            }
            else if(deltaNbCorrectPosition==0){
                // Les deux symbols étaient à la mauvaise place avant et apres la permutation:
                candidat.invalidSymbolAt(symbolA.getColumn(), symbolA);
                candidat.invalidSymbolAt(symbolA.getColumn(), symbolB);
                candidat.invalidSymbolAt(symbolB.getColumn(), symbolA);
                candidat.invalidSymbolAt(symbolB.getColumn(), symbolB);
                LOGGER.debug(">>>>> Les deux symbols permutés sont à la mauvaise place au deux derniers tours: " + symbolA.getSymbol() + " (col:" + symbolA.getColumn() + "," + symbolB.getColumn() + ") et " + symbolB.getSymbol());
            }

        }
        // Suite à l'affinage de Candidat, si la solution est trouvée (bon symbol identifié à chaque position), on la propose:
        if (candidat.isSolution()){
            return candidat.generateRandomSequence(true);
        }
        Sequence latestAttempt = session.getRounds().get(session.getRounds().size()-1).getAttempt();
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
                LOGGER.debug("On génère une nouvelle combinaison car les permutations passées conduisent à proposer des symbols mal placés");
                nbSuccessivePermutations = 0;
                return candidat.generateRandomSequence(true, session);
            }

            // S'il y a plus de 2 emplacements, on choisit aléatoirement
            int nbA=0;
            int nbB=1;
            if (positions.size()>2){
                LOGGER.debug("je dois choisir aléatoirement entre " + positions.size() + " emplacements à permuter");
                nbA = random.nextInt(positions.size());
                do{
                    nbB = random.nextInt(positions.size());
                }while (nbA==nbB);
            }
            positionA = positions.get(nbA);
            positionB = positions.get(nbB);

            if (positionA==-1 || positionB==-1)
                throw new AlgorithmException("Impossible de trouver une nouvelle sequence. L'algorithme est déficient, désolé... ou vous avez triché...");

            LOGGER.debug("Permutation des positions " + positionA + " et " + positionB);
            symbolA = new Symbol(positionB, newSequence.getSymbolAt(positionB));
            symbolB = new Symbol(positionA, newSequence.getSymbolAt(positionA));

            newSequence.setSymbolAt(positionA, symbolA.getSymbol());
            newSequence.setSymbolAt(positionB, symbolB.getSymbol());

            // Chercher si cette sequence a deja ete utilisée durant la session.
            for (Round<MastermindResult> round : session.getRounds()) {
                if (round.getAttempt().toString().contains(newSequence.toString())) {
                    LOGGER.debug("Cette sequence a deja ete soumise: " + newSequence.toString());
                    isAlreadySubmitted = true;
                }
            }
            LOGGER.debug("Résultat de la transformation: " + candidat.toString() + " >>>>>>>>> " + newSequence.toString());
        }
        while(isAlreadySubmitted);
        return newSequence;
    }




    public Sequence evalResultThenChangeOneSymbol(Session<MastermindResult> session){

        nbSuccessiveChanges++;

        // Analyse de l'écart entre les résultats des deux derniers essais:
        if (nbSuccessiveChanges >=2){
            // On compare le résultat des deux derniers essais pour en déduire la pertinence du dernier changement de symbol:

            Round<MastermindResult> lastRound = session.getRounds().get(session.getRounds().size()-1);
            Round<MastermindResult> previousRound = session.getRounds().get(session.getRounds().size()-2);

            int deltaNbCorrectPosition = lastRound.getResult().getNbCorrectPosition() - previousRound.getResult().getNbCorrectPosition();
            int deltaNbCorrectSymbol = lastRound.getResult().getNbCorrectSymbol() - previousRound.getResult().getNbCorrectSymbol();

            LOGGER.debug("Analyse des résultats des 2 derniers tours: adjustement: " + adjustement + ", deltaBPosition: " + deltaNbCorrectPosition + ", deltaBCouleur: " + deltaNbCorrectSymbol);

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
                        // On ne peut pas être certain...aucune décision possible...
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
                    LOGGER.debug("Ancien symbol ("+oldSymbol.getSymbol()+") est correct, Nouveau symbol ("+newSymbol.getSymbol()+") est incorrect");
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
        return changeOneSymbol(session.getRounds().get(session.getRounds().size()-1).getAttempt(), session);
    }

    public Sequence changeOneSymbol(final Sequence sequence, final Session<MastermindResult> session){

        boolean hasFoundAllSymbols = false;

        int currentColumn = 0;
        boolean isPermuted = false;

        String stringSequence = sequence.toString();
        Sequence temporarySequence = sequence.duplicate();

        adjustement = candidat.setCorrectSymbolsInSequence(temporarySequence);

        // Si la transformation a forcé un symbol, on s'assure qu'il n'est pas présent aux autres positions. Si c'est le cas, on fait une permutation:
        isPermuted = candidat.hasDuplicatedSymbols(temporarySequence);

        LOGGER.debug("Sequence initiale: "+stringSequence + ". est " + (isPermuted?"transformée en ":"préservée en ") + temporarySequence.toString());

        for (List<String> symbols : candidat.candidatSequence){
            if (symbols.size()>1){
                if (!isPermuted) {
                    LOGGER.debug("En position " + currentColumn + ", les symbols candidats sont: " + symbols);
                    for (String symbol : symbols) {
                        if (isPermuted)
                            break;

                        LOGGER.debug("Essai du symbol " + symbol + " en comparant avec " + temporarySequence.toString().replace(" ",""));

                        //if (!temporarySequence.toString().replace(" ","").substring(0, currentColumn).contains(symbol)) {
                        if (!temporarySequence.toString().contains(symbol) || hasFoundAllSymbols) {

                            LOGGER.debug("Le symbol " + symbol + " n'est pas présent dans " + temporarySequence.toString());

                            oldSymbol = new Symbol(currentColumn, sequence.getSymbols().get(currentColumn));
                            newSymbol = new Symbol(currentColumn, symbol);

                            Sequence backup = temporarySequence.duplicate();
                            temporarySequence.getSymbols().set(currentColumn, symbol);

                            if (session != null) { // Si un historique des sequences est fourni à l'appel de la méthode, on s'assure que la nouvelle sequence n'a pas déjà été proposée
                                boolean isAlreadySubmitted = false;
                                for (Round<MastermindResult> round : session.getRounds()) {
                                    if (round.getAttempt().toString().contains(temporarySequence.toString())) {
                                        LOGGER.debug("Cette sequence a deja été proposée: " + temporarySequence.toString());
                                        isAlreadySubmitted = true;
                                    }
                                }
                                if (!isAlreadySubmitted) {
                                    LOGGER.debug("Ancien symbol: " + oldSymbol.getSymbol() + " est remplacé par " + newSymbol.getSymbol());
                                    isPermuted=true;
                                } else {
                                    // On restaure l'état de la sequence avant l'essai infructueux
                                    temporarySequence = backup.duplicate();
                                }
                            } else {
                                LOGGER.debug("Pas besoin de vérifier la presence de la nouvelle séquence dans l'historique des coups précédents...");
                                isPermuted=true;
                            }
                        } else {
                            LOGGER.debug("Le symbol proposé (" + symbol + ") est déjà dans la sequence, on l'ignore...");
                        }
                    }
                }else{
                    LOGGER.debug("Pas de changement de symbol nécessaire pour la colonne " + currentColumn);
                }
            }
            currentColumn++;
        }
        LOGGER.debug("Changement d'un symbol réussi? "+ isPermuted);
        if (!isPermuted){
            throw new AlgorithmException("Impossible de trouver une nouvelle sequence. L'algorithme est déficient, désolé... ou vous avez triché...");
        }
        return temporarySequence;
    }

}
