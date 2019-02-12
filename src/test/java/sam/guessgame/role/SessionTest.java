package sam.guessgame.role;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sam.guessgame.App;
import sam.guessgame.GameFactory;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.MastermindCandidat;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.Session;

public class SessionTest {

    @Test
    public void givenNothing_WhenCreatingADecoder_ThenItContainsaValidSession(){

        // Arrange
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
        ctx.getEnvironment().setActiveProfiles("knuth");
        MastermindComputerDecoder decoder = ctx.getBean(MastermindComputerDecoder.class);
        decoder.initSequence();

        // Act
        Sequence sequence = decoder.generateAttempt(new Session());

        // Assert
        Assert.assertTrue(!sequence.toString().isEmpty());

    }
}
