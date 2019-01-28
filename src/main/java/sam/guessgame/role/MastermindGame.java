package sam.guessgame.role;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sam.guessgame.exception.InvalidGameSettingsException;
import sam.guessgame.model.*;

@Component
public class MastermindGame {

    public final int sequenceSize;
    public final int maxNbAttempts;
    public final String[] possibleValues;

    IMastermindDecoder decoder;
    IMastermindCoder coder;

    public Session session;

    public MastermindGame(@Value("${sequence.size}") int sequenceSize, @Value("${list.of.symbols}")String[] possibleValues, @Value("${maximum.number.of.attempts}")int maxNbAttempts) throws InvalidGameSettingsException {
        this.sequenceSize = sequenceSize;
        this.possibleValues = possibleValues;
        this.maxNbAttempts = maxNbAttempts;

        if (sequenceSize>possibleValues.length)
            throw new InvalidGameSettingsException("Number of possible symbols not allowing unicity of each symbol in the sequence to guess.... Whether add new symbols or reduce the sequence length.");

        session = new Session();
    }

    public void setCoder(IMastermindCoder coder){
        this.coder = coder;
    }

    public void setDecoder(IMastermindDecoder decoder){
        this.decoder = decoder;
    }

    public boolean runGameplay(){

        Sequence attempt;
        int nbAttempts=0;
        boolean isSolutionFound = false;
        do{
            System.out.println("--------------------------------------");
            System.out.println("Tour #" + (nbAttempts+1));

            // Decoder makes an attempt:
            attempt = decoder.generateAttempt(session);

            // Coder returns a result
            Result result = coder.evaluateAttempt(attempt);
            //System.out.println("Coder, réponse: " + result.toString());

            isSolutionFound = check(result);

            Round currentRound = new Round(attempt, result);
            System.out.println(currentRound.toString());
            session.rounds.add(currentRound);
            nbAttempts++;
        }
        while(nbAttempts<maxNbAttempts && !isSolutionFound);
        if (isSolutionFound){
            System.out.println(">>>>> BRAVO!!! <<<<<");
        }
        return isSolutionFound;
    }

    private boolean check(Result result) {
        //System.out.println("Size: " + sequenceSize + ", nb de symbols trouvés: " + result.getNbCorrectPosition());
        if (result.getNbCorrectPosition()==sequenceSize)
            return true;
        else
            return false;
    }
}
