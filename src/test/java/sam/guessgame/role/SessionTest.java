package sam.guessgame.role;

import org.junit.Test;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.Session;

public class SessionTest {

    @Test
    public void givenNothing_WhenCreatingADecoder_ThenItContainsaValidSession(){

        ComputerDecoder computerDecoder = new ComputerDecoder(new Candidat(3, new String[]{"A", "B", "C", "D", "E", "F"}));
        Sequence sequence = computerDecoder.generateAttempt(new Session());

        System.out.println(sequence.toString());

    }
}
