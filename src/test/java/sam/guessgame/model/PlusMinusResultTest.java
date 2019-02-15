package sam.guessgame.model;

import org.junit.Assert;
import org.junit.Test;
import sam.guessgame.model.gameplay.PlusMinusResult;
import sam.guessgame.model.gameplay.PlusMinusResultValue;

public class PlusMinusResultTest {

    @Test
    public void givenAnEmptyResult_WhenSettingAPosition_ThenResultIsAsExpected(){
        PlusMinusResult result = new PlusMinusResult();

        result.setResultAt(0, PlusMinusResultValue.Inconnu);
        Assert.assertTrue("?".equals(result.toString()));

        result.setResultAt(3, PlusMinusResultValue.Moins);

        Assert.assertTrue("???-".equals(result.toString()));

        result.setResultAt(0, PlusMinusResultValue.Plus);
        Assert.assertTrue("+??-".equals(result.toString()));

        result.setResultAt(1, PlusMinusResultValue.Plus);
        Assert.assertTrue("++?-".equals(result.toString()));

        result.setResultAt(4, PlusMinusResultValue.Plus);
        Assert.assertTrue("++?-+".equals(result.toString()));

    }
}
