package sam.guessgame.model.gameplay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Classe permettant d'instancier un {@link Candidat} avec les paramètres du jeu de PlusMoins.
 */
@Component
@Scope("prototype")
public class PlusMinusCandidat extends Candidat{

    public PlusMinusCandidat(@Value("${plusminus.sequence.size}") int plusminusSequenceSize,
                              @Value("${plusminus.list.of.symbols}")String[] plusminusPossibleValues){
        super(plusminusSequenceSize, plusminusPossibleValues);

    }
}
