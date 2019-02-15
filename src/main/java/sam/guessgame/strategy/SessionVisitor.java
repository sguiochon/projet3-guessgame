package sam.guessgame.strategy;

import sam.guessgame.model.gameplay.Candidat;
import sam.guessgame.model.gameplay.IResult;
import sam.guessgame.model.gameplay.Sequence;
import sam.guessgame.model.gameplay.Session;

/**
 * Interface définissant le comportement consistant à passer en revue l'historique des coups joués dans une partie.
 * @param <T> type de résultat utilisé dans la partie.
 */
public interface SessionVisitor<T extends IResult> {

    Sequence visit(Session<T> session);

    void init(Candidat candidat);

}
