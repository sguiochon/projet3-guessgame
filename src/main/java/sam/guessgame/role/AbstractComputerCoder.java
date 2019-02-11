package sam.guessgame.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.MastermindResult;
import sam.guessgame.model.Sequence;

/**
 * Encapsule le comportement des joueurs générant la combinaison secrète.
 */
public abstract class AbstractComputerCoder extends Initializer  {

    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractComputerCoder.class.getName());

    protected Sequence winningSequence;

    protected AbstractComputerCoder(Candidat candidat) {
        super(candidat);
    }

    @Override
    public void initSequence() {
        if (candidat != null) {
            winningSequence = candidat.generateRandomSequence(true);
            LOGGER.debug("Séquence aléatoire");
        } else {
            winningSequence = startingSequence;
            LOGGER.debug("Séquence spécifiée (pas aléatoirement)");
        }
        LOGGER.debug("initSequence -> winning sequence: " + winningSequence.toString());
    }
}
