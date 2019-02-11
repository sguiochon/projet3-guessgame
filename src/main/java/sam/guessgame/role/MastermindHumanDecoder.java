package sam.guessgame.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sam.guessgame.InputScanner;
import sam.guessgame.model.*;

/**
 * Représente un joueur de Mastermind joué par un humain qui doit découvrir la combinaison de l'adversaire.
 */
@Component
public class MastermindHumanDecoder extends Initializer implements IDecoder<MastermindResult> {

    public MastermindHumanDecoder(@Autowired MastermindCandidat mastermindCandidat) {
        super(mastermindCandidat);
    }

    @Override
    public Sequence generateAttempt(Session session) {
        return InputScanner.inputSequence("Quelle combinaison proposez-vous?",
                candidat.candidatSequence.size(),
                candidat.candidatSequence.get(0),
                false);
    }

    @Override
    public void initSequence() {

    }
}
