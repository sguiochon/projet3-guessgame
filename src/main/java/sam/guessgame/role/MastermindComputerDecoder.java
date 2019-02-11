package sam.guessgame.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sam.guessgame.model.*;
import sam.guessgame.strategy.FindSymbolsStrategy;
import sam.guessgame.strategy.KnuthStrategy;
import sam.guessgame.strategy.SessionVisitor;


/**
 * Représente un joueur de Mastermind joué par l'ordinateur, qui doit découvrir la combinaison de l'adversaire.
 */
@Component
public class MastermindComputerDecoder extends Initializer implements IDecoder<MastermindResult> {

    private final static Logger LOGGER = LoggerFactory.getLogger(MastermindComputerDecoder.class.getName());

    @Autowired
    private SessionVisitor<MastermindResult> strategy;

    public MastermindComputerDecoder(@Autowired MastermindCandidat candidat) {
        super(candidat);
    }

    @Override
    public void initSequence() {
        candidat.init();
        if (startingSequence == null)
            startingSequence = candidat.generateRandomSequence(true);
        strategy.init(candidat);
    }

    @Override
    public Sequence generateAttempt(Session<MastermindResult> session) {
        Sequence attempt = null;
        if (session.getRounds().isEmpty()) {
            // first attempt is random
            attempt = startingSequence;
        } else {
            Round<MastermindResult> lastRound = session.getRounds().get(session.getRounds().size() - 1);
            MastermindResult lastResult = lastRound.getResult();
            attempt = strategy.visit(session);
        }
        return attempt;
    }

}
