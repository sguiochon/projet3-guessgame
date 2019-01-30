package sam.guessgame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(AutomaticMode.class.getName());

    @Autowired
    MastermindComputerCoder internalCoder;

    @Autowired
    MastermindComputerDecoder internalDecoder;

    public AutomaticMode(@Value("${sequence.size}") int sequenceSize, @Value("${list.of.symbols}")String[] possibleValues, @Value("${maximum.number.of.attempts}")int maxNbAttempts){
        super(sequenceSize, possibleValues, maxNbAttempts);
        LOGGER.debug("constructeur");
    }

    @Override
    public void init(GameType gameType) {
        //coder = new MastermindComputerCoder(candidat1);
        coder = internalCoder;
        decoder = internalDecoder;
        //decoder = new MastermindComputerDecoder(candidat2);
        LOGGER.debug("Appel initSequence de MasterminComputerCoder:");
        coder.initSequence();
        decoder.initSequence();
    }

    @Override
    public String getDescription() {
        return "L'ordinateur joue contre lui-même";
    }

}
