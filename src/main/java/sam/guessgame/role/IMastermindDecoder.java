package sam.guessgame.role;

import sam.guessgame.model.Sequence;
import sam.guessgame.model.Session;

public interface IMastermindDecoder extends ISequenceInitializer {

    public Sequence generateAttempt(Session session);
}
