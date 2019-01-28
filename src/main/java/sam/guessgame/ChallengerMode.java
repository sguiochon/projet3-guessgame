package sam.guessgame;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChallengerMode extends SingleGameMode {

    public ChallengerMode(@Value("${sequence.size}") int sequenceSize, @Value("${list.of.symbols}")String[] possibleValues, @Value("${maximum.number.of.attempts}")int maxNbAttempts) {
        super(sequenceSize, possibleValues, maxNbAttempts);
    }

    @Override
    public void init(GameType gameType) {

    }
}
