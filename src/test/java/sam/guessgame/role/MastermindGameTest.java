package sam.guessgame.role;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sam.guessgame.model.*;

public class MastermindGameTest {

    private MastermindGame mastermindGame;
    private int sequenceSize = 4;
    private String[] possibilities = new String[] {"A", "B", "C", "D", "E", "F", "G", "H"};
    private int maxNbAttempts = 20;

    @Before
    public void init() throws Exception {
        mastermindGame = new MastermindGame(sequenceSize, possibilities ,maxNbAttempts);
    }

    @Test
    public void given_When_Then() throws Exception {
        mastermindGame.setCoder(new MastermindComputerCoder(new Candidat(sequenceSize, possibilities)));
        mastermindGame.setDecoder(new MastermindComputerDecoder(new Candidat(sequenceSize, possibilities)));
        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.runGameplay());
    }

    @Test
    public void given_When_Then2() throws Exception {
        mastermindGame.setCoder(new MastermindComputerCoder(new Sequence(new String[]{"D","F","E","C"})));
        mastermindGame.setDecoder(new MastermindComputerDecoder(new Candidat(sequenceSize, possibilities), new Sequence(new String[]{"D","F","G","H"})));
        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.runGameplay());
    }

    @Test
    public void given_When_Then3() throws Exception {
        mastermindGame.setCoder(new MastermindComputerCoder(new Sequence(new String[]{"G","C","F","D"})));
        mastermindGame.setDecoder(new MastermindComputerDecoder(new Candidat(sequenceSize, possibilities), new Sequence(new String[]{"E","F","A","G"})));
        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.runGameplay());
    }

}
