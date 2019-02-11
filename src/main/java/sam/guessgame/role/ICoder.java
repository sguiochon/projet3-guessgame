package sam.guessgame.role;

import sam.guessgame.model.IResult;
import sam.guessgame.model.Sequence;

/**
 * Comportement d'un joueur représentant le 'codeur', c'est à dire celui générant la séquence à trouver
 * et qui doit indiquer le résultat d'une combinaison qui lui est proposée. Voir aussi {@link IDecoder}
 *
 * @param <T> type de résultat d'une combinaison.
 */
public interface ICoder<T extends IResult> extends ISequenceInitializer {

    T evaluateAttempt(Sequence attempt);

    default boolean check(T result, int nbSymbols) {
        return result.getNbCorrectPosition() == nbSymbols;
    }

    Sequence getWinningSequence();
}
