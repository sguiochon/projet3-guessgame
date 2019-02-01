package sam.guessgame.role;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sam.guessgame.SingleGameMode;
import sam.guessgame.model.*;

public class AutomaticModeTest {

    private SingleGameMode<MastermindComputerCoder, MastermindComputerDecoder, MastermindResult> mastermindGame;
    private int maxNbAttempts = 20;

    @Before
    public void init() throws Exception {

        Candidat candidat = new Candidat(4, new String[]{"A", "B", "C", "D", "E", "F", "G", "H"});
        mastermindGame = new SingleGameMode<MastermindComputerCoder, MastermindComputerDecoder, MastermindResult>(
                4,
                20,
                "--- ordinateur VS ordinateur ---");
        mastermindGame.coder = new MastermindComputerCoder(candidat);
        mastermindGame.decoder = new MastermindComputerDecoder(candidat);
        mastermindGame.init();
    }


    @Test
    public void given_When_Then() throws Exception {
        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.run());
    }

    @Test
    public void given_When_Then2() throws Exception {
        ((MastermindComputerCoder)mastermindGame.coder).winningSequence = new Sequence(new String[]{"D","F","E","C"});
        ((MastermindComputerDecoder)mastermindGame.decoder).startingSequence = new Sequence(new String[]{"D","F","G","H"});
        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.run());
    }

    @Test
    public void given_When_Then3() throws Exception {
        ((MastermindComputerCoder)mastermindGame.coder).winningSequence = new Sequence(new String[]{"G","C","F","D"});
        ((MastermindComputerDecoder)mastermindGame.decoder).startingSequence = new Sequence(new String[]{"E","F","A","G"});
        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.run());
    }

    @Test
    public void given_When_Then4() throws Exception {
        ((MastermindComputerCoder)mastermindGame.coder).winningSequence = new Sequence(new String[]{"A","B","C","D"});
        ((MastermindComputerDecoder)mastermindGame.decoder).startingSequence = new Sequence(new String[]{"A","G","B","H"});
        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.run());
    }


    @Test
    public void given_When_Then5() throws Exception {
        ((MastermindComputerCoder)mastermindGame.coder).winningSequence = new Sequence(new String[]{"A","B","C","D"});
        ((MastermindComputerDecoder)mastermindGame.decoder).startingSequence = new Sequence(new String[]{"F","B","G","D"});
        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.run());
    }
}
