package sam.guessgame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sam.guessgame.model.Candidat;
import sam.guessgame.role.MastermindComputerCoder;
import sam.guessgame.role.MastermindComputerDecoder;
import sam.guessgame.role.MastermindHumanCoder;

@Component
public class DefenseurMode extends SingleGameMode {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefenseurMode.class.getName());

    @Autowired
    Candidat candidat;

    @Autowired
    MastermindHumanCoder internalCoder;

    @Autowired
    MastermindComputerDecoder internalDecoder;

    public DefenseurMode(@Value("${sequence.size}") int sequenceSize, @Value("${list.of.symbols}")String[] possibleValues, @Value("${maximum.number.of.attempts}")int maxNbAttempts) {
        super(sequenceSize, possibleValues, maxNbAttempts);
        LOGGER.debug("constructeur");
    }

    @Override
    public void init(GameType gameType) {
        // ici on implémente un Coder Humain et un Decodeur Machine
        this.coder = internalCoder;//new MastermindHumanCoder(candidat);
        this.decoder = internalDecoder;//new MastermindComputerDecoder(candidat);
        coder.initSequence();
        decoder.initSequence();
    }

    @Override
    public String getDescription() {
        return "L'ordinateur doit trouver VOTRE combinaison";
    }
}
