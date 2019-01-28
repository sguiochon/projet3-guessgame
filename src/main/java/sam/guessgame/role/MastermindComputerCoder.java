package sam.guessgame.role;

import sam.guessgame.model.Candidat;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.Result;


public class MastermindComputerCoder extends Initializer implements IMastermindCoder {

    private Sequence winningSequence;

    public MastermindComputerCoder(Candidat candidat){
        super(candidat);
    }

    public MastermindComputerCoder(Sequence winningSequence){
        super(winningSequence);
    }

    public Sequence getWinningSequence(){
        return winningSequence;
    }

    @Override
    public void initSequence() {
        if (candidat!=null)
            winningSequence = candidat.generateRandomSequence();
        else
            winningSequence = startingSequence;
        System.out.println(">>>Coder.winningSequence: " + winningSequence.toString());
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
