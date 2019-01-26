package sam.guessgame.algorithm;

import sam.guessgame.model.Result;
import sam.guessgame.model.Round;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.Session;

public interface VisitorAlgorithm {

    public Sequence visit(Session session);




}
