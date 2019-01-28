package sam.guessgame.role;

import org.junit.Assert;
import org.junit.Test;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.Result;
import sam.guessgame.model.Sequence;

public class CoderTest {


    @Test
    public void givenNothing_WhenInitCoder_ThenWinningSequenceIsValid(){
        Assert.assertTrue(hasNoDuplicatedSymbol(new Sequence(new String[]{"5", "3"})));
        Assert.assertTrue(!hasNoDuplicatedSymbol(new Sequence(new String[]{"6", "3", "A", "6"})));

        MastermindComputerCoder coder = new MastermindComputerCoder(new Candidat(4, new String[] {"A", "B", "C", "D", "E", "F", "G"}));
        System.out.println("Coder.winningSequence: " + coder.getWinningSequence().toString());
        Assert.assertTrue(hasNoDuplicatedSymbol(coder.getWinningSequence()));
    }

    @Test
    public void givenCoder_WhenSubmittingSequence_ThenCoderResultIsValid(){
        Result result;
        MastermindComputerCoder coder = new MastermindComputerCoder( new Candidat(4, new String[] {"A", "B", "C", "D"}));
        result = coder.evaluateAttempt(coder.getWinningSequence());
        Assert.assertTrue(result.getNbCorrectPosition()==4);
        Assert.assertTrue(result.getNbCorrectSymbol()==0);

        result = coder.evaluateAttempt(new Sequence(new String[]{"A","1", "2", "3"}));
        Assert.assertTrue(result.getNbCorrectSymbol()+result.getNbCorrectPosition()==1 );

        result = coder.evaluateAttempt(new Sequence(new String[]{"0","1", "2", "3"}));
        Assert.assertTrue(result.getNbCorrectSymbol()+result.getNbCorrectPosition()==0 );
    }

    private boolean hasNoDuplicatedSymbol(Sequence sequence){
        if (sequence.getSymbols().size()==0)
            return false;

        String concatString = "";
        for (String symbol : sequence.getSymbols()){
            if (concatString.contains(symbol))
                return false;
            concatString += symbol;
        }
        return true;
    }
}
