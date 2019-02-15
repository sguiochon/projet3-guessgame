package sam.guessgame.model.player;

import sam.guessgame.model.gameplay.IResult;
import sam.guessgame.model.gameplay.Sequence;
import sam.guessgame.model.gameplay.Session;

/**
 * COmportement d'un joueur devant deviner la combinaison. En l'occurrence, à chaque tour, il doit proposer une
 * séquence.
 * @param <T> type de résultat utilisé lors d'une partie.
 */
public interface IDecoder<T extends IResult> extends ISequenceInitializer {

    /**
     * Méthode réalisant la recherche d'une combinaison sur la base de tous les tours précédents
     * @param session tours réalisés jusqu'à présent dans la partie
     * @return la combinaison proposée par le décodeur
     */
    Sequence generateAttempt(Session<T> session);
}
