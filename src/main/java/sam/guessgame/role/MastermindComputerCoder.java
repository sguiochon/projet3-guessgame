package sam.guessgame.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.MastermindResult;
import sam.guessgame.model.Sequence;

/**
 * Représente un joueur de Mastermind joué par l'ordinateur dont la combinaison secrète doit être trouvée par l'adversaire.
 */
public class MastermindComputerCoder extends AbstractComputerCoder implements ICoder<MastermindResult> {

    private final static Logger LOGGER = LoggerFactory.getLogger(MastermindComputerCoder.class.getName());

    public MastermindComputerCoder(Candidat candidat) {
        super(candidat);
    }

    /**
     * Evalue le résultat de la comparaison d'une combinaison avec la combinaison secrète.
     *
     * @param attempt la combinaison à comparer
     * @return résultat de la comparaison.
     */
    @Override
    public MastermindResult evaluateAttempt(Sequence attempt) {
        MastermindResult result = new MastermindResult();
        int attemptSymbolPosition = 0;
        for (String attemptSymbol : attempt.getSymbols()) {
            int winningSymbolPosition = 0;
            for (String winningSymbol : winningSequence.getSymbols()) {
                if (attemptSymbol.equals(winningSymbol)) {
                    if (attemptSymbolPosition == winningSymbolPosition) {
                        result.incNbCorrectPosition();
                    } else {
                        result.incNbCorrectSymbol();
                    }
                    break;
                }
                winningSymbolPosition++;
            }
            attemptSymbolPosition++;
        }
        return result;
    }
}
