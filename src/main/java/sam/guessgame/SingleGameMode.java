package sam.guessgame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.guessgame.model.*;
import sam.guessgame.role.ICoder;
import sam.guessgame.role.IDecoder;

/**
 *  Method init is not implemented by the class as this is where the coder/decoder are defined, which depends on the game mode...
 */
public class SingleGameMode<T extends ICoder, U extends IDecoder, V extends IResult> implements IGameMode {

    private final static Logger LOGGER = LoggerFactory.getLogger(SingleGameMode.class.getName());

    public final int sequenceSize;
    public final int maxNbAttempts;
    //public final String[] possibleValues;

    public T coder;
    public U decoder;

    public Session<V> session = new Session();

    public String description;

    public SingleGameMode(int sequenceSize, int maxNbAttempts, String description){
        this.sequenceSize = sequenceSize;
        this.maxNbAttempts = maxNbAttempts;
        this.description = description;
    }

    @Override
    public void init() {
        coder.initSequence();
        decoder.initSequence();
    }

    @Override
    public boolean run() {

        Sequence attempt;
        int nbAttempts=0;
        boolean isSolutionFound = false;

        do{
            System.out.println("----- " + getDescription() + " -----");
            System.out.println("Tour #" + (nbAttempts+1));

            // Decoder makes an attempt:
            attempt = decoder.generateAttempt(session);

            System.out.println("Combinaison proposée: " + attempt);

            // Coder returns a result
            IResult result = coder.evaluateAttempt(attempt);
            //System.out.println("Coder, réponse: " + result.toString());
            Round currentRound = new Round(attempt, result);

            isSolutionFound = coder.check(result, sequenceSize);

            System.out.println(currentRound.toString());
            session.getRounds().add(currentRound);
            nbAttempts++;
        }
        while(nbAttempts<maxNbAttempts && !isSolutionFound);

        if (isSolutionFound){
            LOGGER.debug("Combinaison trouvée par le Decoder");
            System.out.println(">>>>> La combinaison a été trouvée!!! <<<<<");
        }
        else{
            LOGGER.debug("Combinaison pas trouvée par le Decoder");
            System.out.println(">>>>> La combinaison n'a pas été trouvée! <<<<<");
        }
        return isSolutionFound;
    }


    public String getDescription(){
        return this.description;
    }

}
