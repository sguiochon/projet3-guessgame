package sam.guessgame.strategy;

import sam.guessgame.model.IResult;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.Session;

/**
 * Interface définissant le comportement consistant à passer en revue l'historique des coups joués dans une partie.
 * @param <T> type de résultat utilisé dans la partie.
 */
public interface SessionVisitor<T extends IResult> {

    public Sequence visit(Session<T> session);

}
