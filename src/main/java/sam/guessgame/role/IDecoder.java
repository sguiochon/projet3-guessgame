package sam.guessgame.role;

import sam.guessgame.model.IResult;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.Session;

/**
 * COmportement d'un joueur devant deviner la combinaison. En l'occurrence, à chaque tour, il doit proposer une
 * séquence.
 * @param <T> type de résultat utilisé lors d'une partie.
 */
public interface IDecoder<T extends IResult> extends ISequenceInitializer {

    Sequence generateAttempt(Session<T> session);
}
