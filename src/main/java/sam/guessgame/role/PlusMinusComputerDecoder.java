package sam.guessgame.role;

import org.springframework.stereotype.Component;
import sam.guessgame.model.*;
import sam.guessgame.strategy.FindIntegersStrategy;
import sam.guessgame.strategy.SessionVisitor;

/**
 * Représente un joueur de PlusMoins joué par l'ordinateur, qui doit découvrir la combinaison de l'adversaire.
 */
@Component
public class PlusMinusComputerDecoder extends Initializer implements IDecoder<PlusMinusResult> {

    private SessionVisitor<PlusMinusResult> strategy;

    public PlusMinusComputerDecoder(PlusMinusCandidat candidat) {
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
        candidat.init();
        if (startingSequence == null)
            startingSequence = candidat.generateRandomSequence(false);
        strategy = new FindIntegersStrategy();
        strategy.init(candidat);
    }
}
