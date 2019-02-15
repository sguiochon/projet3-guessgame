package sam.guessgame.model.gameplay;

import sam.guessgame.strategy.SessionVisitor;

/**
 * Pattern visiteur permettant d'externaliser la logique des stratégies
 * mises en oeuvre pour trouver la combinaison, à partir de l'historique des coups joués.
 */
public interface Visitable {

    public Sequence accept(SessionVisitor visitor);

}
