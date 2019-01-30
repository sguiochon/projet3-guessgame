package sam.guessgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sam.guessgame.role.MastermindComputerCoder;
import sam.guessgame.role.MastermindComputerDecoder;
import sam.guessgame.role.MastermindHumanDecoder;

@Component
public class ChallengerMode extends SingleGameMode {


    @Autowired
    MastermindComputerCoder internalCoder;

    @Autowired
    MastermindHumanDecoder internalDecoder;

    public ChallengerMode(@Value("${sequence.size}") int sequenceSize, @Value("${list.of.symbols}")String[] possibleValues, @Value("${maximum.number.of.attempts}")int maxNbAttempts) {
        super(sequenceSize, possibleValues, maxNbAttempts);
    }

    @Override
    public void init(GameType gameType) {
        this.coder = internalCoder;
        this.decoder = internalDecoder;

        coder.initSequence();
        //decoder.initSequence();
    }

    @Override
    public String getDescription() {
        return "A VOUS de trouver la combinaison de l'ordinateur";
    }
}
