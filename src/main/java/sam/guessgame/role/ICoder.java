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

    /**
     * Méthode retournant un {@link IResult} rendant compte de la pertinence d'une combinaison.
     * @param attempt la combinaison à comparer à la combinaison secrète
     * @return le résultat
     */
    T evaluateAttempt(Sequence attempt);

    default boolean check(T result, int nbSymbols) {
        return result.getNbCorrectPosition() == nbSymbols;
    }

    Sequence getWinningSequence();
}
