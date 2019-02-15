package sam.guessgame.model.player;

import org.springframework.stereotype.Component;
import sam.guessgame.InputScanner;
import sam.guessgame.model.gameplay.PlusMinusCandidat;
import sam.guessgame.model.gameplay.PlusMinusResult;
import sam.guessgame.model.gameplay.Sequence;
import sam.guessgame.model.gameplay.Session;

/**
 * Représente un joueur de PlusMoins joué par un humain qui doit découvrir la combinaison de l'adversaire.
 */
@Component
public class PlusMinusHumanDecoder extends Initializer implements IDecoder<PlusMinusResult> {

    public PlusMinusHumanDecoder(PlusMinusCandidat candidat) {
        super(candidat);
    }

    @Override
    public Sequence generateAttempt(Session<PlusMinusResult> session) {
        return InputScanner.inputSequence("Quelle combinaison proposez-vous?",
                candidat.candidatSequence.size(),
                candidat.candidatSequence.get(0),
                false);
    }

    @Override
    public void initSequence() {
        // Nothing to do
    }
}
