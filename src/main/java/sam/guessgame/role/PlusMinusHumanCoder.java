package sam.guessgame.role;

import sam.guessgame.InputScanner;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.PlusMinusResult;
import sam.guessgame.model.PlusMinusResultValue;
import sam.guessgame.model.Sequence;

import java.util.Arrays;

/**
 * Représente un joueur de PlusMoins joué par un humain dont la combinaison secrète doit être trouvée par l'adversaire.
 */
public class PlusMinusHumanCoder extends Initializer implements ICoder<PlusMinusResult> {

    private Sequence winningSequence;

    public PlusMinusHumanCoder(Candidat candidat) {
        super(candidat);
    }

    @Override
    public PlusMinusResult evaluateAttempt(Sequence attempt) {
        PlusMinusResult result = new PlusMinusResult();
        Sequence sequence = InputScanner.inputSequence("Quelle séquence décrit la tentative de votre adversaire? ",
                candidat.candidatSequence.size(),
                Arrays.asList(PlusMinusResultValue.getSymbols()),
                false);
        int nbCorrectSymbols = 0;
        int position = 0;
        for (String symbol : sequence.getSymbols()) {
            result.setResultAt(position, PlusMinusResultValue.getBySymbol(symbol));
            if (symbol.equals(PlusMinusResultValue.Egal.toString())) {
                nbCorrectSymbols++;
            }
            position++;
        }
        result.setNbCorrectPosition(nbCorrectSymbols);
        return result;
    }


    @Override
    public void initSequence() {
        winningSequence = InputScanner.inputSequence("Combinaison secrète que l'adversaire doit découvrir?",
                candidat.candidatSequence.size(),
                candidat.candidatSequence.get(0),
                false);
    }
}
