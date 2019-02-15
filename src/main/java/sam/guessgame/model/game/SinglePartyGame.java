package sam.guessgame.model.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.guessgame.model.gameplay.IResult;
import sam.guessgame.model.gameplay.Round;
import sam.guessgame.model.gameplay.Sequence;
import sam.guessgame.model.gameplay.Session;
import sam.guessgame.model.player.ICoder;
import sam.guessgame.model.player.IDecoder;

/**
 *  Partie impliquant 2 joueurs jouant à tour de rôle: un {@link ICoder} et un {@link IDecoder}. La partie s'arrête si
 *  le IDecoder trouve la combinaison secrète du ICoder ou si le nombre de coups atteint la limite autorisée.
 */
public class SinglePartyGame<T extends ICoder, U extends IDecoder, V extends IResult> implements IGame {

    private final static Logger LOGGER = LoggerFactory.getLogger(SinglePartyGame.class.getName());

    private final int sequenceSize;
    private final int maxNbAttempts;

    public T coder;
    public U decoder;
    private Session<V> session;

    private boolean devMode;

    private String description;

    public SinglePartyGame(int sequenceSize, int maxNbAttempts, String description, boolean devMode){
        this.sequenceSize = sequenceSize;
        this.maxNbAttempts = maxNbAttempts;
        this.description = description;
        this.devMode = devMode;
    }

    @Override
    public void init() {
        coder.initSequence();
        decoder.initSequence();
        session = new Session<V>();
    }

    /**
     * Cette méthode représente le déroulement d'une partie.
     * @return true si la solution a été trouvée par le décodeur
     */
    @Override
    public boolean run() {

        Sequence attempt;
        int nbAttempts=0;
        boolean isSolutionFound = false;

        do{
            LOGGER.info("----- " + description + " -----");
            LOGGER.info("Tour #" + (nbAttempts+1));
            System.out.println("----- " + description + " -----");
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

            System.out.println("Résultat: " + result.toString());//currentRound.toString());
            session.getRounds().add(currentRound);
            nbAttempts++;
        }
        while(nbAttempts<maxNbAttempts && !isSolutionFound);

        if (isSolutionFound){
            LOGGER.info("Combinaison trouvée par le Decoder en " + nbAttempts + " coups");
            System.out.println("\n>>>>> La combinaison a été trouvée en " + nbAttempts + " coup(s) !!! <<<<<");
        }
        else{
            LOGGER.debug("Combinaison PAS trouvée par le Decoder");
            System.out.println("\n>>>>> La combinaison n'a pas été trouvée! <<<<<");
        }
        return isSolutionFound;
    }

}
