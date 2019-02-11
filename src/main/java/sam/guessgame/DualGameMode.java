package sam.guessgame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sam.guessgame.model.IResult;
import sam.guessgame.model.Round;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.Session;
import sam.guessgame.role.ICoder;
import sam.guessgame.role.IDecoder;


public class DualGameMode<T extends ICoder, U extends IDecoder, TT extends ICoder, UU extends IDecoder, V extends IResult> implements IGameMode {

    private final static Logger LOGGER = LoggerFactory.getLogger(DualGameMode.class.getName());

    public final int sequenceSize;
    public final int maxNbAttempts;

    public T coder1;
    public U decoder1;
    public Session<V> session1 = new Session();

    public TT coder2;
    public UU decoder2;
    public Session<V> session2 = new Session();

    public String description;

    private boolean devMode;

    public DualGameMode(int sequenceSize, int maxNbAttempts, String description, boolean devMode){
        this.sequenceSize = sequenceSize;
        this.maxNbAttempts = maxNbAttempts;
        this.description = description;
        this.devMode = devMode;
    }

    @Override
    public void init() {
        coder1.initSequence();
        decoder1.initSequence();
        coder2.initSequence();
        decoder2.initSequence();
    }

    @Override
    public boolean run() {

        Sequence attempt1;
        Sequence attempt2;
        int nbAttempts=0;
        boolean isSolutionFound1 = false;
        boolean isSolutionFound2 = false;

        do{
            LOGGER.info("----- " + getDescription() + " -----");
            LOGGER.info("Tour #" + (nbAttempts+1));
            System.out.println("----- " + getDescription() + " -----");
            if (devMode)
                System.out.println("Combinaison secrète: " + coder.getWinningSequence());
            System.out.println("Tour #" + (nbAttempts+1));

            // Decoder makes an attempt:
            attempt = decoder.generateAttempt(session);

            LOGGER.info("Combinaison proposée: " + attempt);
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

        return true;
    }
}
