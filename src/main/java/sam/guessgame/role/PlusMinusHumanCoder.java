package sam.guessgame.role;

import sam.guessgame.InputScanner;
import sam.guessgame.model.*;

import java.util.Arrays;

public class PlusMinusHumanCoder extends Initializer implements ICoder<PlusMinusResult>{

    private Sequence winningSequence;

    private InputScanner inputScanner = new InputScanner();

    public PlusMinusHumanCoder(Candidat candidat){
        super(candidat);
    }

    @Override
    public PlusMinusResult evaluateAttempt(Sequence attempt) {
        PlusMinusResult result = new PlusMinusResult();
        Sequence sequence = inputScanner.inputSequence("Quelle séquence décrit la tentative de votre adversaire? ",
                candidat.candidatSequence.size(),
                Arrays.asList(PlusMinusResultValue.getSymbols()),
        false);
        int nbCorrectSymbols = 0;
        int position=0;
        for (String symbol : sequence.getSymbols()){
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
        winningSequence = inputScanner.inputSequence("Combinaison secrète que l'adversaire doit découvrir?",
                candidat.candidatSequence.size(),
                candidat.candidatSequence.get(0),
                false);
    }
}
