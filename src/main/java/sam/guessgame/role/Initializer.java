package sam.guessgame.role;

import sam.guessgame.model.Candidat;
import sam.guessgame.model.Sequence;

import java.util.Random;

/**
 * Superclasse assurant l'initialisation des différents types de joueurs.
 */
public abstract class Initializer implements ISequenceInitializer {

    private final static Random random = new Random(System.currentTimeMillis());

    public Candidat candidat;

    protected Sequence startingSequence;


    public Initializer(Candidat candidat) {
        this.candidat = candidat;
    }

}
