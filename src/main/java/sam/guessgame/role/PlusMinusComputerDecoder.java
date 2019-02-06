package sam.guessgame.role;

import sam.guessgame.strategy.FindIntegersStrategy;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.PlusMinusResult;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.Session;

/**
 * Représente un joueur de PlusMoins joué par l'ordinateur, qui doit découvrir la combinaison de l'adversaire.
 */
public class PlusMinusComputerDecoder extends Initializer implements IDecoder<PlusMinusResult> {

    private FindIntegersStrategy strategy;

    public PlusMinusComputerDecoder(Candidat candidat) {
        super(candidat);
    }

    @Override
    public Sequence generateAttempt(Session<PlusMinusResult> session) {
        Sequence attempt = null;
        if (session.getRounds().isEmpty()) {
            // first attempt is random
            return startingSequence;
        }
        else {
            return strategy.visit(session);
        }
    }

    @Override
    public void initSequence() {
        if (startingSequence == null)
            startingSequence = candidat.generateRandomSequence(false);
        strategy = new FindIntegersStrategy(candidat);
    }
}
