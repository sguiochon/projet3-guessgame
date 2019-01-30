package sam.guessgame.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sam.guessgame.InputScanner;
import sam.guessgame.algorithm.FindSymbolsAlgorithm;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.Result;
import sam.guessgame.model.Sequence;

@Component
public class MastermindHumanCoder extends Initializer implements IMastermindCoder {

    private final static Logger LOGGER = LoggerFactory.getLogger(MastermindHumanCoder.class.getName());

    private Sequence winningSequence;

    @Autowired
    private InputScanner inputScanner;

    @Override
    public Result evaluateAttempt(Sequence attempt) {
        System.out.println("Votre adversaire propose la combinaison "+ attempt + " (Solution: " + winningSequence + ")");
        int nbCorrectPosition = inputScanner.inputIntegerFromMinMax("Nombre de symbols corrects et bien placés?", 0, candidat.candidatSequence.size());
        int nbCorrectSymbol = inputScanner.inputIntegerFromMinMax("Nombre de symbols corrects mais mal placés?", 0, candidat.candidatSequence.size());
        return new Result(nbCorrectPosition,nbCorrectSymbol);
    }

    @Override
    public void initSequence() {
        winningSequence = inputScanner.inputSequence("Combinaison secrète que l'adversaire doit découvrir?",
                candidat.candidatSequence.size(),
                candidat.candidatSequence.get(0),
                true);
    }
}
