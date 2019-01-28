package sam.guessgame.role;

import sam.guessgame.model.Sequence;
import sam.guessgame.model.Result;

public interface IMastermindCoder extends ISequenceInitializer {

    public Result evaluateAttempt(Sequence attempt);
}
