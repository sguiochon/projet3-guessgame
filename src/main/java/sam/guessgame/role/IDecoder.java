package sam.guessgame.role;

import sam.guessgame.model.IResult;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.Session;

public interface IDecoder<T extends IResult> extends ISequenceInitializer {

    public Sequence generateAttempt(Session<T> session);
}
