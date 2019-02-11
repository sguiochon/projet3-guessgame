package sam.guessgame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.guessgame.model.*;
import sam.guessgame.role.ICoder;
import sam.guessgame.role.IDecoder;

/**
 *  Partie impliquant 2 joueurs jouant à tour de rôle: un {@link: ICoder} et un {@link IDecoder}. La partie s'arrête si
 *  le IDecoder trouve la combinaison secrète du ICoder ou si le nombre de coups atteint la limite autorisée.
 */
public class SingleGameMode<T extends ICoder, U extends IDecoder, V extends IResult> implements IGameMode {

    private final static Logger LOGGER = LoggerFactory.getLogger(SingleGameMode.class.getName());

    private final int sequenceSize;
    private final int maxNbAttempts;

    public T coder;
    public U decoder;
    private Session<V> session = new Session<V>();

    private boolean devMode;

    private String description;

    public SingleGameMode(int sequenceSize, int maxNbAttempts, String description, boolean devMode){
        this.sequenceSize = sequenceSize;
        this.maxNbAttempts = maxNbAttempts;
        this.description = description;
        this.devMode = devMode;
    }

    @Override
    public void init() {
        coder.initSequence();
        decoder.initSequence();
    }

    /**
     * Cette méthode représente le déroulement d'une partie.
     * @return
     */
    @Override
    public boolean run() {

        Sequence attempt;
        int nbAttempts=0;
        boolean isSolutionFound = false;

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

        if (isSolutionFound){
            LOGGER.info("Combinaison trouvée par le Decoder en " + nbAttempts + " coups");
            System.out.println(">>>>> La combinaison a été trouvée en " + nbAttempts + " coup(s) !!! <<<<<");
        }
        else{
            LOGGER.debug("Combinaison PAS trouvée par le Decoder");
            System.out.println(">>>>> La combinaison n'a pas été trouvée! <<<<<");
        }
        return isSolutionFound;
    }


    public String getDescription(){
        return this.description;
    }

}
