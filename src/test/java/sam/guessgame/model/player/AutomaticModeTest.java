package sam.guessgame.model.player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sam.guessgame.*;
import sam.guessgame.model.game.GameMode;
import sam.guessgame.model.game.GameType;
import sam.guessgame.model.game.SinglePartyGame;
import sam.guessgame.model.gameplay.MastermindResult;
import sam.guessgame.model.gameplay.Sequence;

public class AutomaticModeTest {

    private SinglePartyGame<MastermindComputerCoder, MastermindComputerDecoder, MastermindResult> mastermindGame;
    private int maxNbAttempts = 20;

    IGameFactory f;

    @Before
    public void init() throws Exception {
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
        f = ctx.getBean(GameFactory.class);
        mastermindGame = (SinglePartyGame<MastermindComputerCoder, MastermindComputerDecoder, MastermindResult>) f.getGameMode(GameMode.Spectateur, GameType.MasterMind);
        mastermindGame.init();
    }

    @Test
    public void executionAléatoireDe10parties(){
        for (int i = 0; i<100; i++) {
            mastermindGame = (SinglePartyGame<MastermindComputerCoder, MastermindComputerDecoder, MastermindResult>) f.getGameMode(GameMode.Spectateur, GameType.MasterMind);

            //MastermindCandidat candidat = new MastermindCandidat(4, new String[]{"A", "B", "C", "D", "E", "F", "G", "H"});
            //mastermindGame = new SingleGameMode<MastermindComputerCoder, MastermindComputerDecoder, MastermindResult>(4,20,"--- ordinateur VS ordinateur ---", true);
            //mastermindGame.coder = new MastermindComputerCoder(candidat);
            //mastermindGame.decoder = new MastermindComputerDecoder(candidat);
            mastermindGame.init();
            Assert.assertTrue(i + ": solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.run());
        }
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

    @Test
    public void given_When_Then6() throws Exception {
        ((MastermindComputerCoder)mastermindGame.coder).winningSequence = new Sequence(new String[]{"G","E","F","B"});
        ((MastermindComputerDecoder)mastermindGame.decoder).startingSequence = new Sequence(new String[]{"G","F","A","H"});
        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.run());
    }

    @Test
    public void given_When_Then7() throws Exception {
        ((MastermindComputerCoder)mastermindGame.coder).winningSequence = new Sequence(new String[]{"H","C","E","B"});
        ((MastermindComputerDecoder)mastermindGame.decoder).startingSequence = new Sequence(new String[]{"D","C","A","E"});
        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.run());
    }

    @Test
    public void given_When_Then8() throws Exception {
        ((MastermindComputerCoder)mastermindGame.coder).winningSequence = new Sequence(new String[]{"C","A","E","B"});
        ((MastermindComputerDecoder)mastermindGame.decoder).startingSequence = new Sequence(new String[]{"G","A","E","D"});
        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.run());
    }

    @Test
    public void given_When_Then9() throws Exception {
        ((MastermindComputerCoder)mastermindGame.coder).winningSequence = new Sequence(new String[]{"G","B","A","E"});
        ((MastermindComputerDecoder)mastermindGame.decoder).startingSequence = new Sequence(new String[]{"E","B","G","C"});
        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.run());
    }

    @Test
    public void given_When_Then10() throws Exception{
        ((MastermindComputerCoder)mastermindGame.coder).winningSequence = new Sequence(new String[]{"F","G","H","A"});
        ((MastermindComputerDecoder)mastermindGame.decoder).startingSequence = new Sequence(new String[]{"C","G","F","A"});
        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.run());
    }

    @Test
    public void given_When_Then11() throws Exception{
        ((MastermindComputerCoder)mastermindGame.coder).winningSequence = new Sequence(new String[]{"C","G","E","A"});
        ((MastermindComputerDecoder)mastermindGame.decoder).startingSequence = new Sequence(new String[]{"C","D","H","B"});
        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.run());
    }

    @Test
    public void battle() throws Exception{
        ((MastermindComputerCoder)mastermindGame.coder).winningSequence = new Sequence(new String[]{"A","B","C","D"});
        //((MastermindComputerDecoder)mastermindGame.decoder).startingSequence = new Sequence(new String[]{"C","D","H","B"});
        Assert.assertTrue("Solution trouvée en moins de " + maxNbAttempts + " tentatives", mastermindGame.run());
    }
}
