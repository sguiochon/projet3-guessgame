package sam.guessgame.role;

import org.springframework.stereotype.Component;
import sam.guessgame.algorithm.FindSymbolsAlgorithm;
import sam.guessgame.model.*;

@Component
public class MastermindComputerDecoder extends Initializer implements IMastermindDecoder {

    private FindSymbolsAlgorithm algo1;

    @Override
    public void initSequence(){
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
