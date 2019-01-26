package sam.guessgame.role;

import sam.guessgame.role.ICoder;
import sam.guessgame.role.Initializer;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.Result;

public class ComputerCoder extends Initializer implements ICoder {

    private Sequence winningSequence;

    public ComputerCoder(Candidat candidat){
        super(candidat);
    }

    public ComputerCoder(Sequence winningSequence){
        super(winningSequence);
    }

    public Sequence getWinningSequence(){
        return winningSequence;
    }

    @Override
    public void init() {
        if (candidat!=null)
            winningSequence = candidat.generateRandomSequence();
        else
            winningSequence = startingSequence;
        System.out.println("Coder.winningSequence: " + winningSequence.toString());
    }


    @Override
    public Result evaluateAttempt(Sequence attempt) {
        Result result = new Result();
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
