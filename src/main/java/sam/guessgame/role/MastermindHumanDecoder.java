package sam.guessgame.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sam.guessgame.InputScanner;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.MastermindResult;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.Session;


public class MastermindHumanDecoder extends Initializer implements IDecoder<MastermindResult> {

    InputScanner inputScanner = new InputScanner();

    public MastermindHumanDecoder(Candidat candidat){
        super(candidat);
    }

    @Override
    public Sequence generateAttempt(Session session) {
        return inputScanner.inputSequence("Quelle combinaison proposez-vous?",
                candidat.candidatSequence.size(),
                candidat.candidatSequence.get(0),
                false);
    }

    @Override
    public void initSequence() {

    }
}
