package sam.guessgame.role;

import sam.guessgame.algorithm.FindSymbolsAlgorithm;
import sam.guessgame.model.*;

public class MastermindComputerDecoder extends Initializer implements IMastermindDecoder {


    private FindSymbolsAlgorithm algo1;


    public MastermindComputerDecoder(Candidat candidat){
        super(candidat);
    }

    public MastermindComputerDecoder(Candidat candidat, Sequence startingSequence){
        super(candidat);
        this.startingSequence = startingSequence;
    }

    @Override
    public void initSequence(){
        // Create a candidate: lists all possible symbols of each position
        //candidat = new Candidat(sequenceSize, possibleValues);
        //session = new Session();
        if (startingSequence==null)
            startingSequence = candidat.generateRandomSequence();
        algo1 = new FindSymbolsAlgorithm(candidat);
    }

    @Override
    public Sequence generateAttempt(Session session) {
        Sequence attempt = null;
        if (session.rounds.isEmpty()) {
            // first attempt is random
            attempt = startingSequence;
        }
        else {
            Round lastRound = session.rounds.get(session.rounds.size()-1);
            Result lastResult = lastRound.getResult();

            if (lastResult.getNbCorrectSymbol() + lastResult.getNbCorrectPosition() < candidat.candidatSequence.size()) {
                // If not all the symbols are properly guessed (whether correctly positionned or not), try to...
                //System.out.println("Tous les symbols ne sont pas encore identifiés...");
                attempt = algo1.visit(session);
            }
            else {
                System.out.println("Tous les symbols sont identifiés. Il faut bien les placer maintenant...");
                // Else, guess the correct position of all symbols
                //TODO: implémenter l'algorithme...
                System.out.println("Candidats: " + candidat.toString());
                attempt = algo1.visit(session);
            }
        }
        return attempt;
    }

}
