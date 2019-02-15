package sam.guessgame.model.gameplay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Classe permettant d'instancier un {@link Candidat} avec les paramètres du jeu de Mastermind.
 */
@Component
@Scope("prototype")
public class MastermindCandidat extends Candidat {

    public MastermindCandidat(@Value("${mastermind.sequence.size}") int mastermindSequenceSize,
                              @Value("${mastermind.list.of.symbols}") String[] mastermindPossibleValues) {
        super(mastermindSequenceSize, mastermindPossibleValues);

    }
}
