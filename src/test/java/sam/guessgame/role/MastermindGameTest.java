package sam.guessgame.role;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
    @Ignore
    public void given_When_Then() throws Exception {
        MastermindComputerCoder coder = new MastermindComputerCoder();
        coder.candidat = new Candidat(sequenceSize, possibilities);
        mastermindGame.setCoder(coder);

        MastermindComputerDecoder decoder = new MastermindComputerDecoder();
        decoder.candidat = new Candidat(sequenceSize, possibilities);
        mastermindGame.setDecoder(decoder);

        coder.initSequence();
        decoder.initSequence();
        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.runGameplay());
    }

    @Test
    public void given_When_Then2() throws Exception {
        MastermindComputerCoder coder = new MastermindComputerCoder();
        coder.candidat = new Candidat(sequenceSize, possibilities);
        coder.initSequence();
        coder.winningSequence = new Sequence(new String[]{"D","F","E","C"});
        mastermindGame.setCoder(coder);

        MastermindComputerDecoder decoder = new MastermindComputerDecoder();
        decoder.candidat = new Candidat(sequenceSize, possibilities);
        decoder.initSequence();
        decoder.startingSequence = new Sequence(new String[]{"D","F","G","H"});
        mastermindGame.setDecoder(decoder);

        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.runGameplay());
    }

    @Test
    public void given_When_Then3() throws Exception {
        MastermindComputerCoder coder = new MastermindComputerCoder();
        coder.candidat = new Candidat(sequenceSize, possibilities);
        coder.initSequence();
        coder.winningSequence = new Sequence(new String[]{"G","C","F","D"});
        mastermindGame.setCoder(coder);

        MastermindComputerDecoder decoder = new MastermindComputerDecoder();
        decoder.candidat = new Candidat(sequenceSize, possibilities);
        decoder.initSequence();
        decoder.startingSequence = new Sequence(new String[]{"E","F","A","G"});
        mastermindGame.setDecoder(decoder);

        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.runGameplay());
    }

    @Test
    public void given_When_Then4() throws Exception {
        MastermindComputerCoder coder = new MastermindComputerCoder();
        coder.candidat = new Candidat(sequenceSize, possibilities);
        coder.initSequence();
        coder.winningSequence = new Sequence(new String[]{"A","B","C","D"});
        mastermindGame.setCoder(coder);

        MastermindComputerDecoder decoder = new MastermindComputerDecoder();
        decoder.candidat = new Candidat(sequenceSize, possibilities);
        decoder.initSequence();
        decoder.startingSequence = new Sequence(new String[]{"A","G","B","H"});
        mastermindGame.setDecoder(decoder);

        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.runGameplay());
    }


    @Test
    public void given_When_Then5() throws Exception {
        MastermindComputerCoder coder = new MastermindComputerCoder();
        coder.candidat = new Candidat(sequenceSize, possibilities);
        coder.initSequence();
        coder.winningSequence = new Sequence(new String[]{"A","B","C","D"});
        mastermindGame.setCoder(coder);

        MastermindComputerDecoder decoder = new MastermindComputerDecoder();
        decoder.candidat = new Candidat(sequenceSize, possibilities);
        decoder.initSequence();
        decoder.startingSequence = new Sequence(new String[]{"F","B","G","D"});
        mastermindGame.setDecoder(decoder);

        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.runGameplay());
    }
}
