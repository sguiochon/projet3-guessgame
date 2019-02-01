package sam.guessgame.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.MastermindResult;


public class MastermindComputerCoder extends Initializer implements ICoder<MastermindResult> {

    private final static Logger LOGGER = LoggerFactory.getLogger(MastermindComputerCoder.class.getName());

    public Sequence winningSequence;


    public MastermindComputerCoder(Candidat candidat){
        super(candidat);
        LOGGER.debug("creation");
    }

    /*
    public MastermindComputerCoder(Sequence winningSequence){
        super(winningSequence);
        LOGGER.debug("creation2");
    }
    */

    public Sequence getWinningSequence(){
        return winningSequence;
    }

    @Override
    public void initSequence() {
        //LOGGER.debug("initSequence", this);
        if (candidat!=null) {
            winningSequence = candidat.generateRandomSequence();
            LOGGER.debug("Séquence aléatoire");
        }
        else {
            winningSequence = startingSequence;
            LOGGER.debug("Séquence spécifiée (pas aléatoirement)");
        }
        LOGGER.debug("initSequence -> winning sequence: " + winningSequence.toString());
    }


    @Override
    public MastermindResult evaluateAttempt(Sequence attempt) {
        MastermindResult result = new MastermindResult();
        int attemptSymbolPosition = 0;
        for (String attemptSymbol : attempt.getSymbols()){
            int winningSymbolPosition = 0;
            for (String winningSymbol : winningSequence.getSymbols()){
                if (attemptSymbol.equals(winningSymbol)){
                    if (attemptSymbolPosition==winningSymbolPosition){
                        result.incNbCorrectPosition();
                    }
                    else{
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
