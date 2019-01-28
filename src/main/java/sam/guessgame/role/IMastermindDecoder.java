package sam.guessgame.role;

import sam.guessgame.model.Sequence;
import sam.guessgame.model.Session;

public interface IDecoder extends IInitializer {

    public Sequence generateAttempt(Session session);
}
