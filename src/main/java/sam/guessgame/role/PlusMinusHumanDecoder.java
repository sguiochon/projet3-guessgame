package sam.guessgame.role;

import sam.guessgame.InputScanner;
import sam.guessgame.model.*;

public class PlusMinusHumanDecoder extends Initializer implements IDecoder<PlusMinusResult>{

    InputScanner inputScanner = new InputScanner();

    public PlusMinusHumanDecoder(Candidat candidat){
        super(candidat);
    }
    @Override
    public Sequence generateAttempt(Session<PlusMinusResult> session) {
        return inputScanner.inputSequence("Quelle combinaison proposez-vous?",
                candidat.candidatSequence.size(),
                candidat.candidatSequence.get(0),
                false);
    }

    @Override
    public void initSequence() {
        // Nothing to do
    }
}
