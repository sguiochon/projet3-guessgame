package sam.guessgame;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sam.guessgame.model.Candidat;
import sam.guessgame.role.MastermindComputerCoder;
import sam.guessgame.role.MastermindComputerDecoder;
import sam.guessgame.role.MastermindHumanCoder;

@Component
public class DefenseurMode extends SingleGameMode {

    public DefenseurMode(@Value("${sequence.size}") int sequenceSize, @Value("${list.of.symbols}")String[] possibleValues, @Value("${maximum.number.of.attempts}")int maxNbAttempts) {
        super(sequenceSize, possibleValues, maxNbAttempts);
    }

    @Override
    public void init(GameType gameType) {
        // ici on implémente un Coder Humain et un Decodeur Machine
        coder = new MastermindHumanCoder(new Candidat(sequenceSize, possibleValues));
        decoder = new MastermindComputerDecoder(new Candidat(sequenceSize, possibleValues));
    }
}
