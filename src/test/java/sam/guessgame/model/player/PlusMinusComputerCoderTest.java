package sam.guessgame.model.player;

import org.junit.Assert;
import org.junit.Test;
import sam.guessgame.model.gameplay.PlusMinusCandidat;
import sam.guessgame.model.gameplay.Sequence;

public class PlusMinusComputerCoderTest {

    @Test
    public void test1(){
        PlusMinusCandidat candidat = new PlusMinusCandidat(4, new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"});
        PlusMinusComputerCoder coder = new PlusMinusComputerCoder(candidat);

        coder.winningSequence = new Sequence("0", "9", "4", "5");


        Assert.assertTrue("-+-=".equals(coder.evaluateAttempt(new Sequence("5", "5", "5", "5")).toString()));

    }

}
