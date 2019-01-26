package sam.guessgame.model;

import sam.guessgame.algorithm.VisitorAlgorithm;

public interface Visitable {

    public Sequence accept(VisitorAlgorithm visitor);

}
