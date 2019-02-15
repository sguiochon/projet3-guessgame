package sam.guessgame.model.player;

import sam.guessgame.model.gameplay.Candidat;
import sam.guessgame.model.gameplay.Sequence;

import java.util.Random;

/**
 * Superclasse assurant l'initialisation des différents types de joueurs.
 */
public abstract class Initializer implements ISequenceInitializer {

    private final static Random random = new Random(System.currentTimeMillis());

    public Candidat candidat;

    protected Sequence startingSequence;

    protected Initializer(Candidat candidat) {
        this.candidat = candidat;
    }

}
