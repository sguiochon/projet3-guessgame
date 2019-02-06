package sam.guessgame.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.guessgame.InputScanner;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.MastermindResult;
import sam.guessgame.model.Sequence;

/**
 * Représente un joueur de Mastermind joué par un humain dont la combinaison secrète doit être trouvée par l'adversaire.
 */
public class MastermindHumanCoder extends Initializer implements ICoder<MastermindResult> {

    private final static Logger LOGGER = LoggerFactory.getLogger(MastermindHumanCoder.class.getName());

    private Sequence winningSequence;

    public MastermindHumanCoder(Candidat candidat) {
        super(candidat);
    }

    @Override
    public MastermindResult evaluateAttempt(Sequence attempt) {
        System.out.println("Votre adversaire propose la combinaison " + attempt + " (Solution: " + winningSequence + ")");
        int nbCorrectPosition = InputScanner.inputIntegerFromMinMax("Nombre de symbols corrects et bien placés?", 0, candidat.candidatSequence.size());
        int nbCorrectSymbol = InputScanner.inputIntegerFromMinMax("Nombre de symbols corrects mais mal placés?", 0, candidat.candidatSequence.size());
        return new MastermindResult(nbCorrectPosition, nbCorrectSymbol);
    }

    @Override
    public void initSequence() {
        winningSequence = InputScanner.inputSequence("Combinaison secrète que l'adversaire doit découvrir?",
                candidat.candidatSequence.size(),
                candidat.candidatSequence.get(0),
                true);
    }
}
