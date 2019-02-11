package sam.guessgame.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sam.guessgame.model.*;

/**
 * Représente un joueur de PlusMoins joué par l'ordinateur dont la combinaison secrète doit être trouvée par l'adversaire.
 */
@Component
public class PlusMinusComputerCoder extends AbstractComputerCoder implements ICoder<PlusMinusResult>{

    private final static Logger LOGGER = LoggerFactory.getLogger(PlusMinusComputerCoder.class.getName());

    public PlusMinusComputerCoder(PlusMinusCandidat candidat){
        super(candidat);
    }

    @Override
    public PlusMinusResult evaluateAttempt(Sequence attempt) {
        PlusMinusResult result = new PlusMinusResult();
        int position = 0;
        int intAttempt = 0;
        int intWinning = 0;
        for (String symbol : winningSequence.getSymbols()){
            intAttempt = Integer.parseInt(attempt.getSymbolAt(position));
            intWinning = Integer.parseInt(symbol);
            LOGGER.debug("Comparing winning value '" + intWinning +"' with attempt value '"+ intAttempt + "'");
            if (intWinning<intAttempt){
                result.setResultAt(position, PlusMinusResultValue.Moins);
            }
            else if (intWinning==intAttempt){
                result.setResultAt(position, PlusMinusResultValue.Egal);
            }
            else{
                result.setResultAt(position, PlusMinusResultValue.Plus);
            }
            position++;
        }
        return result;
    }

    @Override
    public boolean check(PlusMinusResult result, int nbSymbols) {
        int nbFound = 0;
        for (PlusMinusResultValue value :  result.results){
            if (value==PlusMinusResultValue.Egal)
                nbFound++;
        }
        return (nbFound==nbSymbols);
    }

    public Sequence getWinningSequence() {
        return winningSequence;
    }


}
