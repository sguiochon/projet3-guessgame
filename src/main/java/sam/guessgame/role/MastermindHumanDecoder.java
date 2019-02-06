package sam.guessgame.role;

import sam.guessgame.InputScanner;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.MastermindResult;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.Session;

/**
 * Représente un joueur de Mastermind joué par un humain qui doit découvrir la combinaison de l'adversaire.
 */
public class MastermindHumanDecoder extends Initializer implements IDecoder<MastermindResult> {

    public MastermindHumanDecoder(Candidat candidat) {
        super(candidat);
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
