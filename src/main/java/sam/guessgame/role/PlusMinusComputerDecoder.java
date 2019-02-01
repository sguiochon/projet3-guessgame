package sam.guessgame.role;

import sam.guessgame.algorithm.FindIntegersAlgorithm;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.PlusMinusResult;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.Session;

public class PlusMinusComputerDecoder extends Initializer implements IDecoder<PlusMinusResult> {

    private FindIntegersAlgorithm algo;

    public PlusMinusComputerDecoder(Candidat candidat) {
        super(candidat);
    }

    @Override
    public Sequence generateAttempt(Session<PlusMinusResult> session) {
        Sequence attempt = null;
        if (session.rounds.isEmpty()) {
            // first attempt is random
            attempt = startingSequence;
        }
        else {
            return algo.visit(session);
        }

        return new Sequence("5", "5","5","5");
    }

    @Override
    public void initSequence() {
        if (startingSequence == null)
            startingSequence = candidat.generateRandomSequence();
        algo = new FindIntegersAlgorithm(candidat);
    }
}
