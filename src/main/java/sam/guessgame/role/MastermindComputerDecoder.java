package sam.guessgame.role;

import org.springframework.stereotype.Component;
import sam.guessgame.algorithm.FindSymbolsAlgorithm;
import sam.guessgame.model.*;


public class MastermindComputerDecoder extends Initializer implements IDecoder<MastermindResult> {

    private FindSymbolsAlgorithm algo1;

    public MastermindComputerDecoder(Candidat candidat){
        super(candidat);
    }

    @Override
    public void initSequence(){
        if (startingSequence==null)
            startingSequence = candidat.generateRandomSequence();
        algo1 = new FindSymbolsAlgorithm(candidat);
    }

    @Override
    public Sequence generateAttempt(Session<MastermindResult> session) {
        Sequence attempt = null;
        if (session.rounds.isEmpty()) {
            // first attempt is random
            attempt = startingSequence;
        }
        else {
            Round<MastermindResult> lastRound = session.rounds.get(session.rounds.size()-1);
            MastermindResult lastResult = (MastermindResult) lastRound.getResult();

            if (lastResult.getNbCorrectSymbol() + lastResult.getNbCorrectPosition() == candidat.candidatSequence.size()) {
                System.out.println("Tous les symbols sont identifiés...");
                //TODO: réduire le candidat pour éliminer les symbols manifestement incorrects...
                System.out.println("Candidats: " + candidat.toString());
            }
            attempt = algo1.visit(session);
        }
        return attempt;
    }

}
