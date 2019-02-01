package sam.guessgame.role;

import sam.guessgame.model.Candidat;
import sam.guessgame.model.IResult;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.MastermindResult;

public interface ICoder<T extends IResult> extends ISequenceInitializer {

    public T evaluateAttempt(Sequence attempt);

    default public boolean check(T result, int nbSymbols) {
        //System.out.println("Size: " + sequenceSize + ", nb de symbols trouvés: " + result.getNbCorrectPosition());
        if (result.getNbCorrectPosition()==nbSymbols)
            return true;
        else
            return false;
    }
}
