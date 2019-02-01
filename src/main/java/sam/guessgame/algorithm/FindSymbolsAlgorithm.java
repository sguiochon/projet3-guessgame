package sam.guessgame.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.guessgame.exception.AlgorithmException;
import sam.guessgame.model.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class FindSymbolsAlgorithm implements VisitorAlgorithm<MastermindResult> {

    private final static Logger LOGGER = LoggerFactory.getLogger(FindSymbolsAlgorithm.class.getName());

    private final static Random random = new Random(System.currentTimeMillis());
    private final Candidat candidat;

    private Symbol oldSymbol;
    private Symbol newSymbol;
    private int adjustement = 0;

    public FindSymbolsAlgorithm(Candidat candidat){
        this.candidat = candidat;
    }

    @Override
    public Sequence visit(Session<MastermindResult> session){

        int nbRounds = session.rounds.size();

        // Analyse de l'écart entre les résultats des deux derniers essais:
        if (nbRounds>=2){
            Round<MastermindResult> lastRound = session.rounds.get(nbRounds-1);
            Round<MastermindResult> previousRound = session.rounds.get(nbRounds-2);

            int deltaNbCorrectPosition = lastRound.getResult().getNbCorrectPosition() - previousRound.getResult().getNbCorrectPosition();
            int deltaNbCorrectSymbol = lastRound.getResult().getNbCorrectSymbol() - previousRound.getResult().getNbCorrectSymbol();

            // Identifier le motif d'évolution du résultat
            // Si les résultats n'ont pas changé, soit les deux symbols sont bons soit ils sont incorrects. Pas de décision possible.
            System.out.println("adjustement: " + adjustement + ", deltaBPosition: " + deltaNbCorrectPosition + ", deltaBCouleur: " + deltaNbCorrectSymbol);

            deltaNbCorrectPosition=deltaNbCorrectPosition-adjustement;
            if (deltaNbCorrectPosition==1){
                if (deltaNbCorrectSymbol==-1){
                    // Ancien Symbol est correct Symbol
                    // Nouveau symbol est bien placé
                    candidat.foundSymbol(newSymbol);
                }
                else{
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
                    candidat.invalidSymbol(newSymbol);
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
                    candidat.storePotentialIncorrectPairs(oldSymbol, newSymbol);
                }
            }

        }
        // Suiet à l'affinage de Candidat, si la solution est trouvée, on la propose:
        if (candidat.isSolution()){
            return candidat.generateRandomSequence(true);
        }
        // Sinon, on propose maintenant une nouvelle sequence
        // en changeant 1 seul symbol :
        Sequence latestAttempt = session.rounds.get(nbRounds-1).getAttempt();
        Optional<Sequence> newSequence = replaceOneSymbolByCandidateInSequence(latestAttempt, session);
        if (!newSequence.isPresent()){
            // Probleme!!! Il semble qu'on ait déjà trouvé la bonne solution....
            throw new AlgorithmException("Impossible de trouver une nouvelle sequence. L'algorithme est déficient, désolé...");
        }
        else {
            return newSequence.get();
        }

    }

    public Optional<Sequence> replaceOneSymbolByCandidateInSequence(final Sequence sequence, final Session<MastermindResult> session){
        //System.out.println("Options possibles: " + candidat.toString());

        String stringSequence = sequence.toString();
        System.out.println(stringSequence);
        Sequence temporarySequence = sequence.duplicate();
        adjustement = candidat.setCorrectSymbolsInSequence(temporarySequence);
        System.out.println(candidat.toString());
        System.out.println("Sequence à modifier: "+stringSequence + ". Transformée en " + temporarySequence.toString());

        int currentColumn = 0;
        boolean isPermuted = false;
        //StringBuilder buildString = new StringBuilder();
        for (List<String> symbols : candidat.candidatSequence){

            if (symbols.size()>1){
                if (!isPermuted) {
                    System.out.println("- Column " + currentColumn + " are several possible symbols: " + symbols);
                    for (String symbol : symbols) {

                        if (isPermuted)
                            break;

                        System.out.println("je tente " + symbol);

                        if (!temporarySequence.toString().substring(0, currentColumn).contains(symbol)) {
                        //if (!temporarySequence.toString().contains(symbol)) {
                            System.out.println(" ce symbol n'est pas présent dans la sequence -> il est retenu");
                            //temporarySequence = sequence.duplicate();
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
            /*else{
                // A cette position, on a trouvé le bon symbol. On le place:
                temporarySequence.setSymbolAt(currentColumn, symbols.get(0));
                System.out.println("Symbol Solution trouvé position " + currentColumn + " -> " + temporarySequence.toString());
            }
            */
            currentColumn++;
        }
        return Optional.of(temporarySequence);//.empty();
    }

}
