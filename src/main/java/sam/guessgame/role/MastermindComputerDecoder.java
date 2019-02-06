package sam.guessgame.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.guessgame.model.*;
import sam.guessgame.strategy.FindSymbolsStrategy;


/**
 * Représente un joueur de Mastermind joué par l'ordinateur, qui doit découvrir la combinaison de l'adversaire.
 */
public class MastermindComputerDecoder extends Initializer implements IDecoder<MastermindResult> {

    private final static Logger LOGGER = LoggerFactory.getLogger(MastermindComputerDecoder.class.getName());

    private FindSymbolsStrategy strategy;

    public MastermindComputerDecoder(Candidat candidat) {
        super(candidat);
    }

    @Override
    public void initSequence() {
        if (startingSequence == null)
            startingSequence = candidat.generateRandomSequence(true);
        strategy = new FindSymbolsStrategy(candidat);
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

            if (lastResult.getNbCorrectSymbol() + lastResult.getNbCorrectPosition() == candidat.candidatSequence.size()) {
                LOGGER.debug("Tous les symbols sont identifiés...");
                //Réduction des possibilités en éliminant  les symbols manifestement incorrects...
                candidat.removeNotInSequence(lastRound.getAttempt());
                LOGGER.debug("Combinaisons possibles : " + candidat.toString());
            }
            attempt = strategy.visit(session);
        }
        return attempt;
    }

}
