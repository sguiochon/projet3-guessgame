package sam.guessgame.algorithm;

import sam.guessgame.model.IResult;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.Session;

public interface VisitorAlgorithm<T extends IResult> {

    public Sequence visit(Session<T> session);




}
