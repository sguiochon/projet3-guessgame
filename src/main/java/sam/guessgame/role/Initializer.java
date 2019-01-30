package sam.guessgame.role;

import org.springframework.beans.factory.annotation.Autowired;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.Sequence;

import java.util.Random;

public abstract class Initializer implements ISequenceInitializer {

    private final static Random random = new Random(System.currentTimeMillis());;

    @Autowired
    protected Candidat candidat;

    protected Sequence startingSequence;

    /*
    public Initializer(Sequence sequence){
        this.startingSequence = sequence;
    }

    public Initializer(Candidat candidat){
        this.candidat = candidat;
    }
*/
}
