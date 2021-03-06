package sam.guessgame.model.player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sam.guessgame.model.gameplay.MastermindCandidat;
import sam.guessgame.model.gameplay.MastermindResult;
import sam.guessgame.model.gameplay.Sequence;

public class CoderTest {


    @Before
    public void init(){

    }
    @Test
    public void givenNothing_WhenInitCoder_ThenWinningSequenceIsValid(){
        Assert.assertTrue(hasNoDuplicatedSymbol(new Sequence(new String[]{"5", "3"})));
        Assert.assertTrue(!hasNoDuplicatedSymbol(new Sequence(new String[]{"6", "3", "A", "6"})));

        MastermindComputerCoder coder = new MastermindComputerCoder(new MastermindCandidat(4, new String[] {"A", "B", "C", "D", "E", "F", "G"}));
        coder.initSequence();
        System.out.println("Coder.winningSequence: " + coder.getWinningSequence().toString());
        Assert.assertTrue(hasNoDuplicatedSymbol(coder.getWinningSequence()));
    }

    @Test
    public void givenCoder_WhenSubmittingSequence_ThenCoderResultIsValid(){
        MastermindResult result;
        MastermindComputerCoder coder = new MastermindComputerCoder(new MastermindCandidat(4, new String[] {"A", "B", "C", "D"}));
        coder.initSequence();
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
