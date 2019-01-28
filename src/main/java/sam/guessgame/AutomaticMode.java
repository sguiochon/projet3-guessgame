package sam.guessgame;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sam.guessgame.exception.InvalidGameSettingsException;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.Session;
import sam.guessgame.role.IMastermindCoder;
import sam.guessgame.role.IMastermindDecoder;
import sam.guessgame.role.MastermindComputerCoder;
import sam.guessgame.role.MastermindComputerDecoder;

@Component
public class AutomaticMode extends SingleGameMode {

    public AutomaticMode(@Value("${sequence.size}") int sequenceSize, @Value("${list.of.symbols}")String[] possibleValues, @Value("${maximum.number.of.attempts}")int maxNbAttempts){
        super(sequenceSize, possibleValues, maxNbAttempts);
    }

    @Override
    public void init(GameType gameType) {
        coder = new MastermindComputerCoder(new Candidat(sequenceSize, possibleValues));
        decoder = new MastermindComputerDecoder(new Candidat(sequenceSize, possibleValues));
    }
}
